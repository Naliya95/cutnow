/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.gl

import com.cutnow.sdk.R

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.cutnow.sdk.util.ShaderSource
import android.opengl.EGL14
import android.opengl.EGLConfig
import android.opengl.EGLContext
import android.opengl.EGLDisplay
import android.opengl.EGLSurface
import android.opengl.GLES20
import android.util.Log
import com.cutnow.sdk.model.EffectType
import com.cutnow.sdk.model.FilterType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * GPU-accelerated filter/effect preview renderer.
 *
 * Maintains a headless EGL context and an offscreen PBuffer surface.
 * The `preview_img.jpg` drawable is uploaded to a GL texture once and
 * reused for every render call.  Each FilterType/EffectType is rendered
 * once and then stored in an in-memory LRU cache.
 *
 * All OpenGL calls happen on [Dispatchers.IO] — never on the main thread.
 */
object FilterGlPreviewRenderer {

    private const val TAG = "FilterGlPreview"
    private const val PREVIEW_SIZE = 120          // px – thumbnail output size
    private const val MAX_CACHE = 40              // max cached bitmaps

    // ── EGL state ────────────────────────────────────────────────────────────
    private var eglDisplay: EGLDisplay = EGL14.EGL_NO_DISPLAY
    private var eglContext: EGLContext = EGL14.EGL_NO_CONTEXT
    private var eglSurface: EGLSurface = EGL14.EGL_NO_SURFACE
    private var initialized = false
    private var initFailed  = false

    // ── GL resources ─────────────────────────────────────────────────────────
    private var previewTexId  = 0
    private var quadVbo       = 0

    // ── Thread-safety: every EGL call runs inside this mutex ─────────────────
    private val glMutex = Mutex()

    // ── Bitmap LRU cache ─────────────────────────────────────────────────────
    private val filterCache: LinkedHashMap<FilterType, Bitmap> =
        object : LinkedHashMap<FilterType, Bitmap>(16, 0.75f, true) {
            override fun removeEldestEntry(eldest: Map.Entry<FilterType, Bitmap>): Boolean {
                if (size > MAX_CACHE) { eldest.value.recycle(); return true }
                return false
            }
        }
    private val effectCache: LinkedHashMap<EffectType, Bitmap> =
        object : LinkedHashMap<EffectType, Bitmap>(16, 0.75f, true) {
            override fun removeEldestEntry(eldest: Map.Entry<EffectType, Bitmap>): Boolean {
                if (size > MAX_CACHE) { eldest.value.recycle(); return true }
                return false
            }
        }

    // ─────────────────────────────────────────────────────────────────────────
    // Public API
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun renderFilter(context: Context, type: FilterType): Bitmap =
        withContext(Dispatchers.IO) {
            glMutex.withLock {
                filterCache[type]?.let { return@withLock it }
                val bmp = renderWithShader(context, ShaderSource.getFilterShader(type))
                filterCache[type] = bmp
                bmp
            }
        }

    suspend fun renderEffect(context: Context, type: EffectType): Bitmap =
        withContext(Dispatchers.IO) {
            glMutex.withLock {
                effectCache[type]?.let { return@withLock it }
                val bmp = renderWithShader(context, ShaderSource.getFragmentShader(type))
                effectCache[type] = bmp
                bmp
            }
        }

    fun release() {
        // Call from ViewModel.onCleared() or Fragment.onDestroyView()
        if (!initialized) return
        try {
            EGL14.eglMakeCurrent(eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT)
            if (previewTexId != 0) GLES20.glDeleteTextures(1, intArrayOf(previewTexId), 0)
            if (quadVbo != 0)      GLES20.glDeleteBuffers(1, intArrayOf(quadVbo), 0)
            EGL14.eglDestroySurface(eglDisplay, eglSurface)
            EGL14.eglDestroyContext(eglDisplay, eglContext)
            EGL14.eglTerminate(eglDisplay)
        } catch (e: Exception) {
            Log.w(TAG, "release error", e)
        } finally {
            eglDisplay    = EGL14.EGL_NO_DISPLAY
            eglContext    = EGL14.EGL_NO_CONTEXT
            eglSurface    = EGL14.EGL_NO_SURFACE
            previewTexId  = 0
            quadVbo       = 0
            initialized   = false
            initFailed    = false
            filterCache.values.forEach { it.recycle() }
            effectCache.values.forEach { it.recycle() }
            filterCache.clear()
            effectCache.clear()
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Private helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Core render path: make context current, compile shader, draw quad,
     * read pixels back as a Bitmap.
     * Must be called while [glMutex] is held and on [Dispatchers.IO].
     */
    private fun renderWithShader(context: Context, fragmentSrc: String): Bitmap {
        ensureInitialized(context)
        if (initFailed) {
            // EGL not available — fall back to a plain copy of the preview image
            return loadPreviewImageBitmap(context)
        }

        EGL14.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)

        GLES20.glViewport(0, 0, PREVIEW_SIZE, PREVIEW_SIZE)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        val program = compileProgram(ShaderSource.VERTEX_SHADER_SIMPLE, fragmentSrc)
        GLES20.glUseProgram(program)

        // Bind preview texture
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, previewTexId)
        val uTexSampler = GLES20.glGetUniformLocation(program, "uTexSampler")
        if (uTexSampler >= 0) GLES20.glUniform1i(uTexSampler, 0)

        // Standard uniforms (intensity = 1.0, time = 0, progress = 1)
        setUniformIfPresent(program, "uIntensity", 1.0f)
        setUniformIfPresent(program, "uTime",      0.0f)
        setUniformIfPresent(program, "uProgress",  1.0f)

        // Draw full-screen quad
        drawQuad(program)

        // Read pixels
        val bitmap = readPixels()

        GLES20.glDeleteProgram(program)
        EGL14.eglMakeCurrent(eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT)

        return bitmap
    }

    private fun ensureInitialized(context: Context) {
        if (initialized || initFailed) return
        try {
            initEgl()
            uploadPreviewTexture(context)
            initialized = true
            Log.d(TAG, "EGL initialised OK")
        } catch (e: Exception) {
            Log.e(TAG, "EGL init failed – falling back to CPU rendering", e)
            initFailed = true
        }
    }

    private fun initEgl() {
        eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY)
        check(eglDisplay != EGL14.EGL_NO_DISPLAY) { "No EGL display" }

        val version = IntArray(2)
        check(EGL14.eglInitialize(eglDisplay, version, 0, version, 1)) { "eglInitialize failed" }

        val attribList = intArrayOf(
            EGL14.EGL_RED_SIZE,       8,
            EGL14.EGL_GREEN_SIZE,     8,
            EGL14.EGL_BLUE_SIZE,      8,
            EGL14.EGL_ALPHA_SIZE,     8,
            EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
            EGL14.EGL_SURFACE_TYPE,   EGL14.EGL_PBUFFER_BIT,
            EGL14.EGL_NONE
        )
        val configs = arrayOfNulls<EGLConfig>(1)
        val numConfigs = IntArray(1)
        check(EGL14.eglChooseConfig(eglDisplay, attribList, 0, configs, 0, 1, numConfigs, 0)) { "eglChooseConfig failed" }
        check(numConfigs[0] > 0) { "No matching EGL config" }

        val ctxAttribs = intArrayOf(EGL14.EGL_CONTEXT_CLIENT_VERSION, 2, EGL14.EGL_NONE)
        eglContext = EGL14.eglCreateContext(eglDisplay, configs[0]!!, EGL14.EGL_NO_CONTEXT, ctxAttribs, 0)
        check(eglContext != EGL14.EGL_NO_CONTEXT) { "eglCreateContext failed" }

        val pbAttribs = intArrayOf(EGL14.EGL_WIDTH, PREVIEW_SIZE, EGL14.EGL_HEIGHT, PREVIEW_SIZE, EGL14.EGL_NONE)
        eglSurface = EGL14.eglCreatePbufferSurface(eglDisplay, configs[0]!!, pbAttribs, 0)
        check(eglSurface != EGL14.EGL_NO_SURFACE) { "eglCreatePbufferSurface failed" }

        EGL14.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)
    }

    private fun uploadPreviewTexture(context: Context) {
        val texIds = IntArray(1)
        GLES20.glGenTextures(1, texIds, 0)
        previewTexId = texIds[0]

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, previewTexId)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)

        val bmp = loadPreviewImageBitmap(context)
        android.opengl.GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0)
        bmp.recycle()

        EGL14.eglMakeCurrent(eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT)
    }

    private fun loadPreviewImageBitmap(context: Context): Bitmap {
        val opts = BitmapFactory.Options().apply {
            inPreferredConfig = Bitmap.Config.ARGB_8888
        }
        val raw = BitmapFactory.decodeResource(context.resources, R.drawable.preview_img, opts)
            ?: throw IllegalStateException("preview_img drawable not found")
        return Bitmap.createScaledBitmap(raw, PREVIEW_SIZE, PREVIEW_SIZE, true)
            .also { if (it !== raw) raw.recycle() }
    }

    // ── Shader compilation ────────────────────────────────────────────────────

    private fun compileProgram(vertSrc: String, fragSrc: String): Int {
        val vert = compileShader(GLES20.GL_VERTEX_SHADER,   vertSrc)
        val frag = compileShader(GLES20.GL_FRAGMENT_SHADER, fragSrc)
        val prog = GLES20.glCreateProgram()
        GLES20.glAttachShader(prog, vert)
        GLES20.glAttachShader(prog, frag)
        GLES20.glLinkProgram(prog)
        GLES20.glDeleteShader(vert)
        GLES20.glDeleteShader(frag)
        return prog
    }

    private fun compileShader(type: Int, src: String): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, src)
        GLES20.glCompileShader(shader)
        return shader
    }

    private fun setUniformIfPresent(program: Int, name: String, value: Float) {
        val loc = GLES20.glGetUniformLocation(program, name)
        if (loc >= 0) GLES20.glUniform1f(loc, value)
    }

    // ── Quad drawing ─────────────────────────────────────────────────────────

    /**
     * Full-screen quad: positions in NDC (-1..1), UVs (0..1) interleaved [x, y, u, v].
     *
     * V is flipped (1 at bottom, 0 at top) because Android Bitmap row-0 (visual top)
     * is uploaded by texImage2D to GL V=0 (texture bottom). Flipping V here corrects
     * the image orientation so it renders right-side-up before readPixels flips it back.
     */
    private val QUAD_DATA = floatArrayOf(
        -1f, -1f,  0f, 1f,   // bottom-left  → sample image bottom
         1f, -1f,  1f, 1f,   // bottom-right → sample image bottom-right
        -1f,  1f,  0f, 0f,   // top-left     → sample image top
         1f,  1f,  1f, 0f,   // top-right    → sample image top-right
    )
    private val STRIDE = 4 * 4 // 4 floats × 4 bytes

    private fun drawQuad(program: Int) {
        val buf: FloatBuffer = ByteBuffer
            .allocateDirect(QUAD_DATA.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .apply { put(QUAD_DATA); rewind() }

        val posLoc = GLES20.glGetAttribLocation(program, "aPosition")
        val uvLoc  = GLES20.glGetAttribLocation(program, "aTexCoord")

        if (posLoc >= 0) {
            GLES20.glEnableVertexAttribArray(posLoc)
            GLES20.glVertexAttribPointer(posLoc, 2, GLES20.GL_FLOAT, false, STRIDE, buf.position(0))
        }
        if (uvLoc >= 0) {
            GLES20.glEnableVertexAttribArray(uvLoc)
            // Offset by 2 floats for the UV component
            GLES20.glVertexAttribPointer(uvLoc, 2, GLES20.GL_FLOAT, false, STRIDE, buf.position(2))
        }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)

        if (posLoc >= 0) GLES20.glDisableVertexAttribArray(posLoc)
        if (uvLoc  >= 0) GLES20.glDisableVertexAttribArray(uvLoc)
    }

    // ── Pixel readback ────────────────────────────────────────────────────────

    private fun readPixels(): Bitmap {
        val buf = ByteBuffer
            .allocateDirect(PREVIEW_SIZE * PREVIEW_SIZE * 4)
            .order(ByteOrder.nativeOrder())
        GLES20.glReadPixels(0, 0, PREVIEW_SIZE, PREVIEW_SIZE, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buf)
        buf.rewind()

        // OpenGL origin is bottom-left; Bitmap origin is top-left → flip vertically
        val pixels = IntArray(PREVIEW_SIZE * PREVIEW_SIZE)
        val rowBuf = IntArray(PREVIEW_SIZE)
        val intBuf = buf.asIntBuffer()
        for (row in 0 until PREVIEW_SIZE) {
            intBuf.position((PREVIEW_SIZE - 1 - row) * PREVIEW_SIZE)
            intBuf.get(rowBuf)
            // RGBA → ARGB
            for (col in 0 until PREVIEW_SIZE) {
                val rgba = rowBuf[col]
                val r = (rgba shr  0) and 0xFF
                val g = (rgba shr  8) and 0xFF
                val b = (rgba shr 16) and 0xFF
                val a = (rgba shr 24) and 0xFF
                pixels[row * PREVIEW_SIZE + col] = (a shl 24) or (r shl 16) or (g shl 8) or b
            }
        }
        return Bitmap.createBitmap(pixels, PREVIEW_SIZE, PREVIEW_SIZE, Bitmap.Config.ARGB_8888)
    }
}


