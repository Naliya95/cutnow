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

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.EGL14
import android.opengl.EGLConfig
import android.opengl.EGLContext
import android.opengl.EGLDisplay
import android.opengl.EGLSurface
import com.cutnow.sdk.R
import android.opengl.GLES20
import com.cutnow.sdk.model.TransitionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * GPU-accelerated transition preview renderer.
 * 
 * Works similarly to FilterGlPreviewRenderer but renders sequence of frames
 * for a transition between two sample bitmaps.
 */
object TransitionGlPreviewRenderer {

    private const val PREVIEW_SIZE = 120
    private const val FRAME_COUNT = 15 // Number of frames per transition preview animation

    private var eglDisplay: EGLDisplay = EGL14.EGL_NO_DISPLAY
    private var eglContext: EGLContext = EGL14.EGL_NO_CONTEXT
    private var eglSurface: EGLSurface = EGL14.EGL_NO_SURFACE
    private var initialized = false
    
    private var texCurrent = 0
    private var texLast = 0
    
    private val glMutex = Mutex()
    
    // Animation Cache: TransitionType -> List of Bitmaps
    private val animationCache = mutableMapOf<TransitionType, List<Bitmap>>()

    suspend fun getTransitionFrames(context: Context, type: TransitionType): List<Bitmap> =
        withContext(Dispatchers.IO) {
            glMutex.withLock {
                animationCache[type]?.let { return@withLock it }
                
                ensureInitialized(context)
                EGL14.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)
                
                val frames = mutableListOf<Bitmap>()
                val program = compileProgram(TransitionManager.VERTEX_SHADER, TransitionManager.getTransitionShader(type))
                
                try {
                    GLES20.glUseProgram(program)
                    GLES20.glViewport(0, 0, PREVIEW_SIZE, PREVIEW_SIZE)
                    
                    // Setup Locations
                    val uTexCurrent = GLES20.glGetUniformLocation(program, "uTexCurrent")
                    val uTexLast = GLES20.glGetUniformLocation(program, "uTexLastFrame")
                    val uProgress = GLES20.glGetUniformLocation(program, "uProgress")

                    for (i in 0 until FRAME_COUNT) {
                        val progress = i.toFloat() / (FRAME_COUNT - 1)
                        
                        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

                        // Bind Textures
                        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
                        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texCurrent)
                        if (uTexCurrent >= 0) GLES20.glUniform1i(uTexCurrent, 0)

                        GLES20.glActiveTexture(GLES20.GL_TEXTURE1)
                        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texLast)
                        if (uTexLast >= 0) GLES20.glUniform1i(uTexLast, 1)

                        // Update Progress
                        if (uProgress >= 0) GLES20.glUniform1f(uProgress, progress)

                        drawQuad(program)
                        frames.add(readPixels())
                    }
                } finally {
                    GLES20.glDeleteProgram(program)
                    EGL14.eglMakeCurrent(eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT)
                }
                
                animationCache[type] = frames
                frames
            }
        }


    private fun ensureInitialized(context: Context) {
        if (initialized) return
        initEgl()
        uploadTextures(context)
        initialized = true
    }

    private fun initEgl() {
        eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY)
        val version = IntArray(2)
        EGL14.eglInitialize(eglDisplay, version, 0, version, 1)

        val attribList = intArrayOf(
            EGL14.EGL_RED_SIZE, 8,
            EGL14.EGL_GREEN_SIZE, 8,
            EGL14.EGL_BLUE_SIZE, 8,
            EGL14.EGL_ALPHA_SIZE, 8,
            EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
            EGL14.EGL_SURFACE_TYPE, EGL14.EGL_PBUFFER_BIT,
            EGL14.EGL_NONE
        )
        val configs = arrayOfNulls<EGLConfig>(1)
        val numConfigs = IntArray(1)
        EGL14.eglChooseConfig(eglDisplay, attribList, 0, configs, 0, 1, numConfigs, 0)

        val ctxAttribs = intArrayOf(EGL14.EGL_CONTEXT_CLIENT_VERSION, 2, EGL14.EGL_NONE)
        eglContext = EGL14.eglCreateContext(eglDisplay, configs[0]!!, EGL14.EGL_NO_CONTEXT, ctxAttribs, 0)

        val pbAttribs = intArrayOf(EGL14.EGL_WIDTH, PREVIEW_SIZE, EGL14.EGL_HEIGHT, PREVIEW_SIZE, EGL14.EGL_NONE)
        eglSurface = EGL14.eglCreatePbufferSurface(eglDisplay, configs[0]!!, pbAttribs, 0)
        
        EGL14.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)
    }

    private fun uploadTextures(context: Context) {
        val texIds = IntArray(2)
        GLES20.glGenTextures(2, texIds, 0)
        texCurrent = texIds[0]
        texLast = texIds[1]

        val decoded = BitmapFactory.decodeResource(context.resources, R.drawable.preview_img)
        val bmp1 = Bitmap.createScaledBitmap(decoded, PREVIEW_SIZE, PREVIEW_SIZE, true)
        
        // Create a variation for the second image (e.g., Grayscale or shift)
        val bmp2 = Bitmap.createBitmap(PREVIEW_SIZE, PREVIEW_SIZE, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bmp2)
        val paint = android.graphics.Paint()
        val matrix = android.graphics.ColorMatrix()
        matrix.setSaturation(0f) // Grayscale version for visual distinction
        paint.colorFilter = android.graphics.ColorMatrixColorFilter(matrix)
        canvas.drawBitmap(bmp1, 0f, 0f, paint)

        uploadToTex(texCurrent, bmp1)
        uploadToTex(texLast, bmp2)

        bmp1.recycle()
        bmp2.recycle()
        decoded.recycle()
    }

    private fun uploadToTex(id: Int, bmp: Bitmap) {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, id)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
        android.opengl.GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0)
    }

    private fun compileProgram(vertSrc: String, fragSrc: String): Int {
        val vert = compileShader(GLES20.GL_VERTEX_SHADER, vertSrc)
        val frag = compileShader(GLES20.GL_FRAGMENT_SHADER, fragSrc)
        val prog = GLES20.glCreateProgram()
        GLES20.glAttachShader(prog, vert)
        GLES20.glAttachShader(prog, frag)
        GLES20.glLinkProgram(prog)
        return prog
    }

    private fun compileShader(type: Int, src: String): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, src)
        GLES20.glCompileShader(shader)
        return shader
    }

    private fun drawQuad(program: Int) {
        val vertexData = floatArrayOf(
            -1f, -1f, 0f, 0f, // BL
             1f, -1f, 1f, 0f, // BR
             1f,  1f, 1f, 1f, // TR
            -1f,  1f, 0f, 1f  // TL
        )
        val buf = ByteBuffer.allocateDirect(vertexData.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().apply { put(vertexData); position(0) }
        
        val posLoc = GLES20.glGetAttribLocation(program, "aFramePosition")
        val uvLoc = GLES20.glGetAttribLocation(program, "aTexSamplingCoord")

        GLES20.glEnableVertexAttribArray(posLoc)
        GLES20.glVertexAttribPointer(posLoc, 2, GLES20.GL_FLOAT, false, 4 * 4, buf.position(0))
        GLES20.glEnableVertexAttribArray(uvLoc)
        GLES20.glVertexAttribPointer(uvLoc, 2, GLES20.GL_FLOAT, false, 4 * 4, buf.position(2))

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
    }

    private fun readPixels(): Bitmap {
        val buf = ByteBuffer.allocateDirect(PREVIEW_SIZE * PREVIEW_SIZE * 4).order(ByteOrder.nativeOrder())
        GLES20.glReadPixels(0, 0, PREVIEW_SIZE, PREVIEW_SIZE, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buf)
        buf.rewind()
        
        val pixels = IntArray(PREVIEW_SIZE * PREVIEW_SIZE)
        val intBuf = buf.asIntBuffer()
        for (i in 0 until PREVIEW_SIZE) {
            for (j in 0 until PREVIEW_SIZE) {
                // OpenGL's first row (i=0) is the bottom.
                // texImage2D sends the first row of a Bitmap (top) to row 0 of the texture.
                // So row 0 of GL is already the TOP of the original image.
                // We just read it directly.
                val rgba = intBuf.get(i * PREVIEW_SIZE + j)
                val r = (rgba shr 0) and 0xFF
                val g = (rgba shr 8) and 0xFF
                val b = (rgba shr 16) and 0xFF
                val a = (rgba shr 24) and 0xFF
                pixels[i * PREVIEW_SIZE + j] = (a shl 24) or (r shl 16) or (g shl 8) or b
            }
        }
        return Bitmap.createBitmap(pixels, PREVIEW_SIZE, PREVIEW_SIZE, Bitmap.Config.ARGB_8888)
    }

    fun release() {
        if (!initialized) return
        animationCache.values.flatten().forEach { it.recycle() }
        animationCache.clear()
        // Cleanup EGL...
    }
}


