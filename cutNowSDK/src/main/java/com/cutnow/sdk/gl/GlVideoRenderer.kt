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

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import android.view.Surface
import com.cutnow.sdk.model.*
import com.cutnow.sdk.engine.KeyframeEngine
import com.cutnow.sdk.engine.TextAnimationEngine
import com.cutnow.sdk.helper.CanvasPreviewHelpers
import com.cutnow.sdk.model.KeyframeProperty
import com.cutnow.sdk.model.LayerType
import com.cutnow.sdk.util.UtilGlProgram
import com.cutnow.sdk.util.ShaderSource
import com.cutnow.sdk.ui.GlPreviewView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlinx.coroutines.*

/**
 * Renderer for GlPreviewView.
 * Handles video rendering from ExoPlayer (SurfaceTexture) and applies effects/overlays.
 */
class GlVideoRenderer(private val context: Context, private val previewView: GlPreviewView) : GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

    companion object {
        private const val TAG = "GlVideoRenderer"
    }

    private var surfaceTexture: SurfaceTexture? = null
    private var surface: Surface? = null
    private var videoTextureId: Int = 0
    
    // Matrix for OES texture transformation (provided by SurfaceTexture)
    private val videoTextureTransformMatrix = FloatArray(16)
    
    // Standard Identity Matrix
    private val identityMatrix = FloatArray(16)

    // Flag to indicate a new frame is available
    private var updateSurface = false
    private var surfaceReady = false
    
    // Programs
    private var oesProgram: UtilGlProgram? = null // For drawing OES texture to Framebuffer or Screen
    private var imageProgram: UtilGlProgram? = null // For drawing standard 2D image segments on main track
    private var canvasProgram: UtilGlProgram? = null // For drawing Color/Gradient background
    
    // Current state
    private var videoWidth = 0
    private var videoHeight = 0
    private var viewWidth = 0
    private var viewHeight = 0
    private val overlayContentCache = java.util.concurrent.ConcurrentHashMap<String, String>()
    private val lastRevealCharsMap = java.util.concurrent.ConcurrentHashMap<String, Int>()

    // Pending layers to load after surface is ready
    private var pendingLayers: List<MediaLayer>? = null
    private var pendingBitmaps: Map<String, android.graphics.Bitmap?>? = null
    
    // Video Overlay ExoPlayer Texture handling
    private val overlayVideoTextures = java.util.concurrent.ConcurrentHashMap<String, Int>()
    private val overlaySurfaceTextures = java.util.concurrent.ConcurrentHashMap<String, SurfaceTexture>()
    private val overlaySurfaces = java.util.concurrent.ConcurrentHashMap<String, Surface>()
    private val overlayTransformMatrices = java.util.concurrent.ConcurrentHashMap<String, FloatArray>()
    private val overlayUpdateFlags = java.util.concurrent.ConcurrentHashMap<String, Boolean>()
    
    // Framebuffers for ping-pong rendering or intermediate steps
    private var fboIds = IntArray(2)
    private var fboTexIds = IntArray(2)
    
    // Shader programs for effects and filters
    private val effectPrograms = mutableMapOf<String, UtilGlProgram>()
    private val transitionPrograms = mutableMapOf<TransitionType, UtilGlProgram>()
    private var blitProgram: UtilGlProgram? = null
    
    // Video Segments for transition logic
    private var videoSegments: List<VideoSegment> = emptyList()
    
    // Last frame caching for transitions
    private var lastFrameTexId = -1
    private var lastFrameClipId: String? = null
    private var fboLastFrameId = -1
    private var currentClipIndex = -1
    // Tracks which clip segment index the last frame was captured for, to avoid repeat captures
    private var lastCapturedForClipIndex = -1
    
    // Shader source for OES Rendering
    private val vertexShaderCode = """
        attribute vec4 aFramePosition;
        attribute vec4 aTexSamplingCoord;
        uniform mat4 uMatrix;
        uniform mat4 uVideoMatrix;
        varying vec2 vTexCoord;
        void main() {
            gl_Position = uVideoMatrix * aFramePosition;
            vTexCoord = (uMatrix * aTexSamplingCoord).xy;
        }
    """

    private val overlayVertexShaderCode = """
        attribute vec4 aFramePosition;
        attribute vec4 aTexSamplingCoord;
        uniform mat4 uMatrix;
        varying vec2 vTexCoord;
        void main() {
            gl_Position = uMatrix * aFramePosition;
            vTexCoord = aTexSamplingCoord.xy;
        }
    """

    private val blitVertexShaderCode = """
        attribute vec4 aFramePosition;
        attribute vec4 aTexSamplingCoord;
        varying vec2 vTexCoord;
        void main() {
            gl_Position = aFramePosition;
            vTexCoord = aTexSamplingCoord.xy;
        }
    """

    private val imageVertexShaderCode = """
        attribute vec4 aFramePosition;
        attribute vec4 aTexSamplingCoord;
        uniform mat4 uVideoMatrix;
        varying vec2 vTexCoord;
        void main() {
            gl_Position = uVideoMatrix * aFramePosition;
            vTexCoord = aTexSamplingCoord.xy;
        }
    """

    private val blitFragmentShaderCode = """
        precision mediump float;
        varying vec2 vTexCoord;
        uniform sampler2D uTexture;
        void main() {
            gl_FragColor = texture2D(uTexture, vTexCoord);
        }
    """

    private val adjustmentGlslBlock = """
        uniform float uAdj[15]; 
        
        float rand(vec2 co) {
            return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
        }

        vec3 applyAdjustments(vec3 color, vec2 uv) {
            // Brightness
            color += uAdj[0];
            
            // Contrast
            color = (color - 0.5) * uAdj[1] + 0.5;
            
            // Saturation
            float lum = dot(color, vec3(0.299, 0.587, 0.114));
            color = mix(vec3(lum), color, uAdj[2]);
            
            // Temp
            color.r += uAdj[3] * 0.1;
            color.b -= uAdj[3] * 0.1;
            
            // Hue
            float angle = uAdj[4] * 3.14159;
            float s = sin(angle), c = cos(angle);
            vec3 weights = (vec3(2.0 * c, -sqrt(3.0) * s - c, sqrt(3.0) * s - c) + 1.0) / 3.0;
            color = vec3(dot(color, weights.xyz), dot(color, weights.zxy), dot(color, weights.yzx));
            
            // Fade
            color = mix(color, vec3(0.5), uAdj[5] * 0.5);
            
            // Highlights / Shadows
            float luma = dot(color, vec3(0.299, 0.587, 0.114));
            float shadowMask = 1.0 - smoothstep(0.0, 0.5, luma);
            float highlightMask = smoothstep(0.5, 1.0, luma);
            color += uAdj[9] * shadowMask * 0.5;
            color += uAdj[8] * highlightMask * 0.5;
            
            // Vignette
            float dist = distance(uv, vec2(0.5, 0.5));
            float fVignette = smoothstep(0.8, 0.2, dist * (1.0 + uAdj[6]));
            if (uAdj[6] > 0.0) {
                color *= mix(1.0, fVignette, uAdj[6]);
            }
            
            // Grain
            if (uAdj[7] > 0.0) {
                float noise = rand(uv * 10.0) - 0.5;
                color += noise * uAdj[7] * 0.2;
            }
            
            // Blacks and Whites simplified
            color += uAdj[11] * shadowMask * 0.2;
            color += uAdj[10] * highlightMask * 0.2;
            
            // Brilliance
            color += uAdj[12] * 0.1;
            
            // Clarity (Local Contrast / Mid-tone enhancement)
            float midtone = 1.0 - abs(lum * 2.0 - 1.0);
            color += (color - 0.5) * uAdj[13] * midtone * 0.3;
            
            return clamp(color, 0.0, 1.0);
        }
    """

    private val fragmentShaderCodeOES = """
        #extension GL_OES_EGL_image_external : require
        precision mediump float;
        varying vec2 vTexCoord;
        uniform samplerExternalOES uTexture;
        
        $adjustmentGlslBlock
        
        void main() {
            vec4 color = texture2D(uTexture, vTexCoord);
            color.rgb = applyAdjustments(color.rgb, vTexCoord);
            gl_FragColor = color;
        }
    """

    private val fragmentShaderCode2D = """
        precision mediump float;
        varying vec2 vTexCoord;
        uniform sampler2D uTexture;
        uniform float uAlpha;
        
        $adjustmentGlslBlock
        
        void main() {
            vec4 color = texture2D(uTexture, vTexCoord);
            color.rgb = applyAdjustments(color.rgb, vTexCoord);
            gl_FragColor = vec4(color.rgb, color.a * uAlpha);
        }
    """

    private val canvasFragmentShaderCode = """
        precision mediump float;
        varying vec2 vTexCoord;
        uniform int uType; // 1=COLOR, 2=GRADIENT
        uniform vec4 uColor;
        uniform vec4 uGradientColor1;
        uniform vec4 uGradientColor2;

        void main() {
            if (uType == 1) {
                gl_FragColor = uColor;
            } else if (uType == 2) {
                gl_FragColor = mix(uGradientColor1, uGradientColor2, vTexCoord.y);
            } else {
                gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);
            }
        }
    """

    private fun renderTransition(currentTexId: Int, lastTexId: Int, progress: Float, type: TransitionType) {
        // Load or update transition program for the specific type
        val shader = TransitionManager.getTransitionShader(type)
        
        // Use a map of transition programs for each type to avoid recompiling
        val cachedProg = transitionPrograms.getOrPut(type) {
            UtilGlProgram(TransitionManager.VERTEX_SHADER, shader)
        }

        cachedProg.use()
        cachedProg.setSamplerTexIdUniform("uTexCurrent", currentTexId, 0, false)
        cachedProg.setSamplerTexIdUniform("uTexLastFrame", lastTexId, 1, false)
        cachedProg.setFloatUniform("uProgress", progress)
        cachedProg.bindAttributesAndUniforms()
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
    }
    private val borderVertexShaderCode = """
        attribute vec4 aFramePosition;
        uniform mat4 uMatrix;
        void main() {
            gl_Position = uMatrix * aFramePosition;
        }
    """

    private val borderFragmentShaderCode = """
        precision mediump float;
        uniform vec4 uColor;
        void main() {
            gl_FragColor = uColor;
        }
    """
    
    // Overlay State
    private var overlayProgram: UtilGlProgram? = null
    private var layers: List<MediaLayer> = emptyList()
    private val overlayTextures = java.util.concurrent.ConcurrentHashMap<String, Int>() 
    private val overlayBitmaps = java.util.concurrent.ConcurrentHashMap<String, android.graphics.Bitmap>() 
    private val overlayMovies = java.util.concurrent.ConcurrentHashMap<String, android.graphics.Movie>()
    private val loadingLayerIds = java.util.concurrent.ConcurrentHashMap.newKeySet<String>()
    // GL textures for image segments in the main video track (segment.id -> GL textureId)
    private val imageSegmentTextures = java.util.concurrent.ConcurrentHashMap<String, Int>()
    // Aspect ratio (width/height) for each image segment
    private val imageSegmentAspects = java.util.concurrent.ConcurrentHashMap<String, Float>()
    private var currentTimeMs: Long = 0L

    // --- Coalescing / de-duplication for setLayers ---
    // Prevents GL-thread event queue flooding when the 60fps loop calls setLayers() every frame.
    // Only a single GL event is outstanding at a time; newer layer lists replace the pending one.
    @Volatile private var latestIntendedLayers: List<MediaLayer>? = null
    @Volatile private var hasPendingLayerEvent: Boolean = false
    @Volatile private var pendingNewLayers: List<MediaLayer>? = null
    // Snapshot of the last layer list committed to the renderer, used to skip no-op calls.
    @Volatile private var lastLayersSnapshot: String = ""
    
    // Video Transformation State
    private var videoScale = 1.0f
    private var videoRotation = 0f
    private var videoTranslateX = 0.5f
    private var videoTranslateY = 0.5f
    private val videoTransformMatrix = FloatArray(16)
    private var isVideoInteracting = false

    private val videoRetrieverMap = java.util.concurrent.ConcurrentHashMap<String, android.media.MediaMetadataRetriever>()
    private val videoFrameJobMap = java.util.concurrent.ConcurrentHashMap<String, Job>()
    private val renderScope = CoroutineScope(Dispatchers.IO + Job()) 

    private var borderProgram: UtilGlProgram? = null
    private var blurProgram: UtilGlProgram? = null

    init {
        Matrix.setIdentityM(identityMatrix, 0)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
       // Log.d(TAG, "onSurfaceCreated")

        // Mark surface as not ready during initialization
        surfaceReady = false

        val textures = IntArray(1)
        GLES20.glGenTextures(1, textures, 0)
        videoTextureId = textures[0]

        GLES20.glBindTexture(android.opengl.GLES11Ext.GL_TEXTURE_EXTERNAL_OES, videoTextureId)
        GLES20.glTexParameterf(android.opengl.GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST.toFloat())
        GLES20.glTexParameterf(android.opengl.GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat())

        surfaceTexture = SurfaceTexture(videoTextureId)
        surfaceTexture?.setOnFrameAvailableListener(this)
        surface = Surface(surfaceTexture)

        borderProgram = UtilGlProgram(borderVertexShaderCode, borderFragmentShaderCode)

        previewView.post {
            previewView.onSurfaceVideoReady(surface)
        }

        try {
            oesProgram = UtilGlProgram(vertexShaderCode, fragmentShaderCodeOES)
            imageProgram = UtilGlProgram(imageVertexShaderCode, blitFragmentShaderCode)
            overlayProgram = UtilGlProgram(overlayVertexShaderCode, fragmentShaderCode2D)
            blitProgram = UtilGlProgram(blitVertexShaderCode, blitFragmentShaderCode)
            canvasProgram = UtilGlProgram(blitVertexShaderCode, canvasFragmentShaderCode)
            blurProgram = UtilGlProgram(
                ShaderSource.VERTEX_SHADER,
                ShaderSource.getFragmentShader(EffectType.BLUR)
            )

            // Setup Last Frame FBO
            val fbo = IntArray(1)
            val tex = IntArray(1)
            GLES20.glGenFramebuffers(1, fbo, 0)
            GLES20.glGenTextures(1, tex, 0)
            fboLastFrameId = fbo[0]
            lastFrameTexId = tex[0]

            // Use view dimensions so the last-frame texture matches the actual canvas size
            val initW = if (viewWidth > 0) viewWidth else 1080
            val initH = if (viewHeight > 0) viewHeight else 1920
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, lastFrameTexId)
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, initW, initH, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)

            setupFbos(viewWidth, viewHeight)
            surfaceReady = true
        } catch (e: Exception) {
           // Log.e(TAG, "Failed to create GL programs", e)
        }

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDisable(GLES20.GL_DEPTH_TEST)
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

        // Clear caches because old texture IDs are invalid in a new context
       // Log.d(TAG, "Clearing overlay caches due to context recreation")
        overlayTextures.clear()
        overlayBitmaps.clear()
        overlayMovies.clear()
        overlayContentCache.clear()
        lastLayersSnapshot = ""
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
      //  Log.d(TAG, "onSurfaceChanged: $width x $height")
        viewWidth = width
        viewHeight = height
        GLES20.glViewport(0, 0, width, height)

        setupFbos(width, height)

        // Mark surface as ready and process any pending layers
        surfaceReady = true
        
        // Re-apply segments to ensure image textures load if they were queued before surface was ready
        if (videoSegments.isNotEmpty()) {
            setSegments(videoSegments)
        }

        // If we have existing layers but no textures (due to clear in onSurfaceCreated),
        // we must trigger a re-load.
        if (layers.isNotEmpty() && overlayTextures.isEmpty()) {
           // Log.d(TAG, "Re-triggering setLayers for existing layers in new context")
            setLayers(layers)
        } else if (pendingLayers != null && pendingBitmaps != null) {
           // Log.d(TAG, "Processing pending layers after surface ready")
            updateOverlayTextures(pendingLayers!!, pendingBitmaps!!)
            updateEffectPrograms(pendingLayers!!)
            layers = pendingLayers!!
            pendingLayers = null
            pendingBitmaps = null
            previewView.requestRender()
        }
    }

    private fun setupFbos(width: Int, height: Int) {
        if (fboIds[0] != 0) {
            GLES20.glDeleteFramebuffers(2, fboIds, 0)
            GLES20.glDeleteTextures(2, fboTexIds, 0)
        }

        GLES20.glGenFramebuffers(2, fboIds, 0)
        GLES20.glGenTextures(2, fboTexIds, 0)

        for (i in 0..1) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, fboTexIds[i])
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)

            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboIds[i])
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, fboTexIds[i], 0)
        }
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)

        // Also resize the last-frame capture texture to match the new view dimensions.
        // This ensures transition rendering never samples from a mis-sized texture.
        if (lastFrameTexId != -1 && width > 0 && height > 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, lastFrameTexId)
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null)
        }
        // Reset capture tracker so the new-sized frame is captured before the next transition
        lastCapturedForClipIndex = -1
    }

    override fun onDrawFrame(gl: GL10?) {
        synchronized(this) {
            if (updateSurface) {
                surfaceTexture?.updateTexImage()
                surfaceTexture?.getTransformMatrix(videoTextureTransformMatrix)
                updateSurface = false
            }
            // Check all overlay surfaces
            for ((id, st) in overlaySurfaceTextures) {
                if (overlayUpdateFlags[id] == true) {
                    try {
                        st.updateTexImage()
                        val mtx = FloatArray(16)
                        st.getTransformMatrix(mtx)
                        overlayTransformMatrices[id] = mtx
                    } catch (e: Exception) {
                       // Log.e(TAG, "Error updating overlay surface $id", e)
                    }
                    overlayUpdateFlags[id] = false
                }
            }
        }

        // 1. Identify current segment and transition
        var accumulatedTime = 0L
        var activeTransitionIndex = -1
        var transitionProgress = 0f
        var currentSegIndex = -1
        // Track how far we are from the next clip boundary for smart last-frame capture
        var msUntilNextBoundary = Long.MAX_VALUE

        for (i in videoSegments.indices) {
            val seg = videoSegments[i]
            val nextAccumulated = accumulatedTime + seg.effectiveDurationMs
            val isLast = i == videoSegments.size - 1
            if (currentTimeMs >= accumulatedTime && (currentTimeMs < nextAccumulated || (isLast && currentTimeMs <= nextAccumulated))) {
                currentSegIndex = i

                // How many ms remain before the boundary to the next clip
                msUntilNextBoundary = nextAccumulated - currentTimeMs

                // Check if we are in the transition zone leading into THIS clip (from previous clip)
                // Transitions are centered on clip boundaries: the transition starts when the
                // current time enters the next clip's first transDur milliseconds.
                if (i > 0) {
                    val prevSeg = videoSegments[i - 1]
                    if (prevSeg.transitionType != TransitionType.NONE) {
                        val transDur = prevSeg.transitionDurationMs
                        if (currentTimeMs < accumulatedTime + transDur) {
                            activeTransitionIndex = i - 1
                            // Clamp to [0,1] to avoid first-frame snaps from timing jitter
                            transitionProgress = ((currentTimeMs - accumulatedTime).toFloat() / transDur.toFloat()).coerceIn(0f, 1f)
                        }
                    }
                }
                break
            }
            accumulatedTime = nextAccumulated
        }

        // Update clip index tracking
        this.currentClipIndex = currentSegIndex

    val activeEffects = layers.filter {
        (it.type == LayerType.EFFECT || it.type == LayerType.FILTER) &&
        currentTimeMs >= it.startTimeMs && currentTimeMs <= it.startTimeMs + it.durationMs
    }

        // ALWAYS render to FBO 0 first to satisfy effects and transition caching
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboIds[0])
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        
        val currentSegment = if (currentSegIndex in videoSegments.indices) videoSegments[currentSegIndex] else null
        drawCanvasBackground(currentSegment)
        
        drawVideoLayer()
        drawOverlaysLayer()

        // ALWAYS Draw border
        drawVideoBorder()

        var currentFboIndex = 0

        if (activeEffects.isEmpty() && activeTransitionIndex == -1) {
            // Blit FBO 0 to screen
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
            drawFboToScreen(fboTexIds[0])
        } else {
            // Pipeline render: Transition -> Effects -> Screen

            // 2. Wrap basic render with Transition if active
            if (activeTransitionIndex != -1) {
                val currentTexId = fboTexIds[currentFboIndex]
                currentFboIndex = 1 - currentFboIndex

                if (activeEffects.isEmpty()) {
                    GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
                } else {
                    GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboIds[currentFboIndex])
                }

                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
                renderTransition(currentTexId, lastFrameTexId, transitionProgress, videoSegments[activeTransitionIndex].transitionType)
            }

            // 3. Apply Effects sequentially (Ping-Pong)
            for (i in activeEffects.indices) {
                val layer = activeEffects[i]
                val isLast = i == activeEffects.size - 1
                val program = effectPrograms[layer.id] ?: continue

                val sourceTexId = fboTexIds[currentFboIndex]
                currentFboIndex = 1 - currentFboIndex // Switch

                if (isLast) {
                    GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
                } else {
                    GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboIds[currentFboIndex])
                }

                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
                program.use()
                program.setSamplerTexIdUniform("uTexSampler", sourceTexId, 0, false)
                program.setFloatUniform("uTime", currentTimeMs / 1000f)
                program.setFloatUniform("uIntensity", if (layer.type == LayerType.FILTER) layer.filterIntensity else layer.effectIntensity)

                val progress = if (layer.durationMs > 0) {
                    (currentTimeMs - layer.startTimeMs).toFloat() / layer.durationMs.toFloat()
                } else 1.0f
                program.setFloatUniform("uProgress", progress.coerceIn(0f, 1f))

                program.bindAttributesAndUniforms()
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
            }
        }

        // Smart last-frame capture:
        // Only capture when approaching a clip boundary that has a transition defined.
        // This avoids expensive glCopyTexImage2D calls on every frame during normal playback.
        // We capture exactly once per clip (when within the upcoming transition's duration window),
        // so the cached frame is always fresh and represents the outgoing clip's final frame.
        if (activeTransitionIndex == -1 && currentSegIndex != -1 && currentSegIndex < videoSegments.size) {
            val nextSegIndex = currentSegIndex + 1
            if (nextSegIndex < videoSegments.size) {
                val currentSeg = videoSegments[currentSegIndex]
                val nextTransDur = currentSeg.transitionDurationMs
                val hasNextTransition = currentSeg.transitionType != TransitionType.NONE
                // Capture once when entering the pre-transition window (within transDuration of boundary)
                if (hasNextTransition && msUntilNextBoundary <= nextTransDur && lastCapturedForClipIndex != currentSegIndex) {
                    captureLastFrameToCache()
                    lastCapturedForClipIndex = currentSegIndex
                }
            } else {
                // Last clip — no next transition, reset the capture tracker
                lastCapturedForClipIndex = -1
            }
        }
    }

    private fun drawFboToScreen(texId: Int) {
        val prog = blitProgram ?: return // Use dedicated non-flipping blit program
        prog.use()
        prog.bindAttributesAndUniforms()

        // Final screen blit uses standard 2D texture
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId)
        val texHandle = GLES20.glGetUniformLocation(prog.programId, "uTexture")
        if (texHandle != -1) GLES20.glUniform1i(texHandle, 0)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
    }

    private fun captureLastFrameToCache() {
        if (fboIds[0] == 0) return
        // Copy current FBO content to lastFrameTexId
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboIds[0])
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, lastFrameTexId)
        // Ensure texture is the right size
        GLES20.glCopyTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, 0, 0, viewWidth, viewHeight, 0)
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
    }


    fun setLayers(newLayers: List<MediaLayer>) {
        latestIntendedLayers = newLayers

        // --- Change-detection: build a lightweight snapshot of transform state.
        // contentHash already covers texture content; here we also hash position/scale/rotation
        // so that pure transform changes (drag/scale/rotate) are detected without bitmap reloads.
        val snapshot = newLayers.joinToString(separator = "|") { l ->
            "${l.id}:${l.contentHash}:${l.posX}:${l.posY}:${l.scaleX}:${l.scaleY}:${l.rotation}:${l.startTimeMs}:${l.durationMs}:${l.isSelected}:${l.flipHorizontal}:${l.flipVertical}:${l.animationIn.name}:${l.animationOut.name}:${l.animationLoop.name}:${l.animInDuration}:${l.animOutDuration}:${l.animLoopDuration}"
        }
        if (snapshot == lastLayersSnapshot) return  // Nothing changed — skip entirely
        lastLayersSnapshot = snapshot

        // 1. Identify layers needing bitmap loading (content changed or new)
        val layersToLoad = newLayers.filter { layer ->
            val needsLoad = (layer.type == LayerType.STICKER || layer.type == LayerType.IMAGE || layer.type == LayerType.TEXT || layer.type == LayerType.VIDEO) &&
                    (overlayContentCache[layer.id] != layer.contentHash || !overlayTextures.containsKey(layer.id)) &&
                    !loadingLayerIds.contains(layer.id)
            if (needsLoad) Log.d(TAG, "Layer ${layer.id} (${layer.type}) needs loading. Content hash: ${layer.contentHash}. Current cache: ${overlayContentCache[layer.id]}. Tex exists: ${overlayTextures.containsKey(layer.id)}")
            needsLoad
        }

        if (layersToLoad.isEmpty()) {
            // No new bitmaps needed — coalesce: store the latest layer list and only enqueue
            // ONE GL event. If an event is already pending, just update the stored list so the
            // already-queued event will use the freshest data when it runs.
            pendingNewLayers = newLayers
            if (!hasPendingLayerEvent) {
                hasPendingLayerEvent = true
                previewView.queueEvent {
                    val latestLayers = latestIntendedLayers ?: pendingNewLayers ?: newLayers
                    hasPendingLayerEvent = false
                    pendingNewLayers = null
                    if (surfaceReady) {
                        updateOverlayTextures(latestLayers, emptyMap())
                        updateEffectPrograms(latestLayers)
                        this@GlVideoRenderer.layers = latestLayers
                        previewView.requestRender()
                    } else {
                        pendingLayers = latestLayers
                        pendingBitmaps = emptyMap()
                    }
                }
            }
        } else {
            // New bitmaps needed: bitmap loading is inherently coalesced because loadingLayerIds
            // prevents duplicate loads. Mark as loading immediately.
            layersToLoad.forEach { loadingLayerIds.add(it.id) }

            // 2. Load bitmaps on IO thread
            GlobalScope.launch(Dispatchers.IO) {
                val loadedBitmaps = mutableMapOf<String, android.graphics.Bitmap?>()
                for (layer in layersToLoad) {
                    loadedBitmaps[layer.id] = loadBitmapForLayer(layer)
                }

                // 3. Upload textures on GL thread.
                // Use latestIntendedLayers: by the time load finishes, the caller may have
                // provided a newer layer list (updated positions etc.) — use the freshest one.
                previewView.queueEvent {
                    layersToLoad.forEach { loadingLayerIds.remove(it.id) }
                    // Use the most recent layer list that was passed in, in case position changed
                    val latestLayers = latestIntendedLayers ?: pendingNewLayers ?: newLayers
                    pendingNewLayers = null
                    hasPendingLayerEvent = false

                    if (surfaceReady) {
                        updateOverlayTextures(latestLayers, loadedBitmaps)
                        updateEffectPrograms(latestLayers)
                        this@GlVideoRenderer.layers = latestLayers
                        previewView.requestRender()
                    } else {
                        pendingLayers = latestLayers
                        pendingBitmaps = loadedBitmaps
                    }
                }
            }
        }
    }

    private fun updateOverlayTextures(newLayers: List<MediaLayer>, preloadedBitmaps: Map<String, android.graphics.Bitmap?>) {
        val newLayerIds = newLayers.map { it.id }.toSet()

        // Remove old textures for layers that no longer exist
        val iterator = overlayTextures.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (!newLayerIds.contains(entry.key)) {
                val textureIds = intArrayOf(entry.value)
                GLES20.glDeleteTextures(1, textureIds, 0)
                iterator.remove()
                overlayBitmaps.remove(entry.key)
                overlayMovies.remove(entry.key)
                overlayContentCache.remove(entry.key)
            }
        }

        // Add or update textures ONLY for layers in preloadedBitmaps
        for (layer in newLayers) {
             if (layer.type != LayerType.TEXT && layer.type != LayerType.STICKER && layer.type != LayerType.IMAGE && layer.type != LayerType.VIDEO) continue

            // Only process if this layer has a bitmap in preloadedBitmaps
            if (!preloadedBitmaps.containsKey(layer.id)) continue

            val bitmap = preloadedBitmaps[layer.id]
            if (bitmap != null) {
                // Remove existing if replacing
                overlayTextures[layer.id]?.let { oldId ->
                    GLES20.glDeleteTextures(1, intArrayOf(oldId), 0)
                }

                // Upload new texture (MUST be on GL thread)
                val textureId = try {
                    loadTexture(bitmap)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to load texture for layer ${layer.id}: ${e.message}", e)
                    0
                }

                if (textureId > 0) {
                    overlayTextures[layer.id] = textureId
                    overlayBitmaps[layer.id] = bitmap
                    overlayContentCache[layer.id] = layer.contentHash
                    //Log.d(TAG, "Texture loaded successfully for layer ${layer.id}")
                } else {
                    Log.e(TAG, "Failed to generate texture for layer ${layer.id}: TexId=0, Bitmap=${bitmap.width}x${bitmap.height}, Config=${bitmap.config}")
                }
            }
        }
    }

    fun updateLayerDirect(updatedLayer: MediaLayer) {
        // updateLayerDirect is used for immediate in-place transform updates (rotate/scale during
        // gesture). It bypasses the coalescing queue and updates the renderer's layer list directly
        // on the GL thread for zero-latency feedback.
        //
        // Also update lastLayersSnapshot so that the next setLayers() call from the 60fps loop
        // sees the updated position and doesn't redundantly re-enqueue an event to "undo" this.
        previewView.queueEvent {
            val index = layers.indexOfFirst { it.id == updatedLayer.id }
            if (index >= 0) {
                val oldLayer = layers[index]
                if (oldLayer.effectType != updatedLayer.effectType || oldLayer.filterType != updatedLayer.filterType) {
                    updateEffectPrograms(layers.toMutableList().apply { set(index, updatedLayer) })
                }

                val mutableLayers = layers.toMutableList()
                mutableLayers[index] = updatedLayer
                this.layers = mutableLayers

                // Invalidate snapshot so next setLayers() call re-evaluates (detects the
                // updated position committed here) rather than skipping as "unchanged".
                lastLayersSnapshot = ""

                previewView.requestRender()
            }
        }
    }

    /**
     * Clears the cached layer snapshot so the next [setLayers] call will re-evaluate and
     * trigger a [requestRender] even if layer property values haven't changed.
     *
     * Call this from [GlPreviewView.forceRender] after [EditorViewModel.syncLayersToEngine]
     * to ensure the re-mapped layers from [EditorViewModel.refreshFromEngine] always produce a
     * rendered frame (the new MediaLayer instances carry identical values but are different
     * objects — without invalidating the snapshot the snapshot hash would still match and
     * [setLayers] would silently skip [requestRender]).
     */
    fun invalidateLayerSnapshot() {
        lastLayersSnapshot = ""
    }

    @SuppressLint("SuspiciousIndentation")
    fun setCurrentTime(timeMs: Long) {
        if (currentTimeMs != timeMs) {
            currentTimeMs = timeMs
            // Optimization: Only log occasionally to avoid flood
            if (currentTimeMs % 1000 < 50) //Log.v(TAG, "setCurrentTime: $currentTimeMs")
            previewView.requestRender()
        }
    }

    fun setSegments(segments: List<VideoSegment>) {
        // Cleanup GL textures for segments that no longer exist
        val currentIds = segments.map { it.id }.toSet()
        val toRemove = imageSegmentTextures.keys - currentIds
        if (toRemove.isNotEmpty()) {
            previewView.queueEvent {
                toRemove.forEach { id ->
                    imageSegmentTextures.remove(id)?.let { texId ->
                        GLES20.glDeleteTextures(1, intArrayOf(texId), 0)
                    }
                }
            }
        }

        // Load GL textures for any new image segments not yet cached
        val newImageSegments = segments.filter { it.isImage && !imageSegmentTextures.containsKey(it.id) }
        for (seg in newImageSegments) {
            renderScope.launch {
                try {
                    val bmp = context.contentResolver.openInputStream(seg.uri)?.use {
                        val opts = android.graphics.BitmapFactory.Options().apply { inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888 }
                        android.graphics.BitmapFactory.decodeStream(it, null, opts)
                    } ?: return@launch
                    // Store the true aspect ratio so drawVideoLayer can use it
                    val aspect = bmp.width.toFloat() / bmp.height.toFloat().coerceAtLeast(1f)
                    imageSegmentAspects[seg.id] = aspect
                    previewView.queueEvent {
                        if (surfaceReady) {
                            val texId = loadTexture(bmp)
                            if (texId > 0) imageSegmentTextures[seg.id] = texId
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to load image texture for segment ${seg.id}", e)
                }
            }
        }

        this.videoSegments = segments
    }

    private fun updateEffectPrograms(newLayers: List<MediaLayer>) {
        val activeIds = newLayers.filter { it.type == LayerType.EFFECT || it.type == LayerType.FILTER }.map { it.id }.toSet()

        // Cleanup old
        val it = effectPrograms.iterator()
        while (it.hasNext()) {
            val (id, program) = it.next()
            if (!activeIds.contains(id)) {
                program.delete()
                it.remove()
            }
        }

        // Add new
        for (layer in newLayers) {
            if ((layer.type == LayerType.EFFECT || layer.type == LayerType.FILTER) && !effectPrograms.containsKey(layer.id)) {
                try {
                    val shader = if (layer.type == LayerType.EFFECT) {
                        ShaderSource.getFragmentShader(layer.effectType ?: EffectType.NONE)
                    } else {
                        ShaderSource.getFilterShader(layer.filterType ?: FilterType.NORMAL)
                    }
                    effectPrograms[layer.id] = UtilGlProgram(ShaderSource.VERTEX_SHADER, shader)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to load effect program for ${layer.id}", e)
                }
            }
        }
    }


    private fun loadTexture(bitmap: android.graphics.Bitmap): Int {
        // Double check GL context
        if (android.opengl.EGL14.eglGetCurrentContext() == android.opengl.EGL14.EGL_NO_CONTEXT) {
            //Log.e(TAG, "No current GL context in loadTexture. Thread: ${Thread.currentThread().name}")
            return 0
        }

        val textureIds = IntArray(1)
        GLES20.glGenTextures(1, textureIds, 0)
        val textureId = textureIds[0]

        // Check if texture generation succeeded
        if (textureId == 0) {
            val error = GLES20.glGetError()
           // Log.e(TAG, "Failed to generate texture ID: GL Error=$error, Bitmap=${bitmap.width}x${bitmap.height}")
            return 0
        }

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)

        try {
            android.opengl.GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
            val error = GLES20.glGetError()
            if (error != GLES20.GL_NO_ERROR) {
               // Log.e(TAG, "GL Error loading texture: $error, Bitmap: ${bitmap.width}x${bitmap.height}, Config: ${bitmap.config}")
                // Clean up the texture
                GLES20.glDeleteTextures(1, intArrayOf(textureId), 0)
                return 0
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception loading texture: Bitmap: ${bitmap.width}x${bitmap.height}, Config: ${bitmap.config}", e)
            // Clean up the texture
            GLES20.glDeleteTextures(1, intArrayOf(textureId), 0)
            return 0
        }

        return textureId
    }

    private fun loadBitmapForLayer(layer: MediaLayer, revealChars: Int = -1): android.graphics.Bitmap? {
       // Log.d(TAG, "Loading bitmap for layer ${layer.id}, type=${layer.type}, content=${layer.content}")
        
       // Only clear leftover movie caches for non-sticker/image layers (e.g. TEXT → STICKER type change).
       // For STICKER/IMAGE layers, the Movie will be overwritten if a new GIF is decoded,
       // or preserved if the load fails — avoids race conditions during surface recreation.
       if (layer.type != LayerType.STICKER && layer.type != LayerType.IMAGE) {
           overlayMovies.remove(layer.id)
       }

        return try {
            when (layer.type) {
                LayerType.TEXT -> {
                    val width = if (videoWidth > 0) videoWidth else 1080
                    val height = if (videoHeight > 0) videoHeight else 1920
                    CanvasPreviewHelpers.createTextBitmap(
                        text = layer.content,
                        fontSize = layer.fontSize,
                        textColor = layer.textColor,
                        fontFamily = layer.fontFamily,
                        strokeColor = layer.strokeColor,
                        strokeWidth = layer.strokeWidth,
                        backgroundColor = layer.backgroundColor,
                        videoWidth = width,
                        videoHeight = height,
                        revealChars = revealChars,
                        gradientColors = layer.textGradientColors?.toIntArray(),
                        warpType = layer.textWarpType,
                        warpIntensity = layer.textWarpIntensity,
                        textBgPaddingX = layer.textBgPaddingX,
                        textBgPaddingY = layer.textBgPaddingY,
                        textBgCornerRadius = layer.textBgCornerRadius,
                        textAlignment = layer.textAlignment.name,
                        context = context
                    )
                }
                LayerType.STICKER, LayerType.IMAGE -> {
                    if (layer.content.startsWith("sticker://emoji/")) {
                        val emoji = layer.content.substringAfter("sticker://emoji/")
                        CanvasPreviewHelpers.createEmojiBitmap(emoji, 256, 256)
                    } else {
                        val uri = android.net.Uri.parse(layer.content)
                        if (uri.scheme == "http" || uri.scheme == "https") {
                            try {
                                val urlStr = uri.toString()
                                val isSticker = layer.type == LayerType.STICKER
                                if (urlStr.endsWith(".gif") || urlStr.contains(".gif") || isSticker) {
                                    val file = com.bumptech.glide.Glide.with(context).downloadOnly().load(urlStr).submit().get()
                                    val bytes = file.readBytes()
                                    val movie = android.graphics.Movie.decodeByteArray(bytes, 0, bytes.size)
                                    if (movie != null && movie.duration() > 0) {
                                        Log.d(TAG, "Aggressive Remote GIF detected for sticker layer ${layer.id}")
                                        overlayMovies[layer.id] = movie
                                        val bmp = android.graphics.Bitmap.createBitmap(movie.width().coerceAtLeast(1), movie.height().coerceAtLeast(1), android.graphics.Bitmap.Config.ARGB_8888)
                                        val canvas = android.graphics.Canvas(bmp)
                                        movie.setTime(0)
                                        movie.draw(canvas, 0f, 0f)
                                        return bmp
                                    }
                                }
                                com.bumptech.glide.Glide.with(context)
                                    .asBitmap()
                                    .load(urlStr)
                                    .submit()
                                    .get()
                            } catch (e: Exception) {
                                null
                            }
                        } else {
                            // Handle local files (including stickers cached from history and assets)
                            val isSticker = layer.type == LayerType.STICKER
                            val isGif = layer.content.lowercase().endsWith(".gif")
                            Log.d(TAG, "Local Sticker/GIF Check: content=${layer.content}, isSticker=$isSticker, isGif=$isGif")
                            
                            if (isGif || isSticker) {
                                try {
                                    val inputStream = context.contentResolver.openInputStream(uri)
                                    val bytes = inputStream?.use { it.readBytes() }
                                    if (bytes != null) {
                                        val movie = android.graphics.Movie.decodeByteArray(bytes, 0, bytes.size)
                                        // ONLY use Movie if it actually has duration/animation
                                        if (movie != null && movie.duration() > 0) {
                                            Log.d(TAG, "Aggressive Local GIF/Sticker detected for layer ${layer.id}")
                                            overlayMovies[layer.id] = movie
                                            val bmp = android.graphics.Bitmap.createBitmap(movie.width().coerceAtLeast(1), movie.height().coerceAtLeast(1), android.graphics.Bitmap.Config.ARGB_8888)
                                            val canvas = android.graphics.Canvas(bmp)
                                            movie.setTime(0)
                                            movie.draw(canvas, 0f, 0f)
                                            return bmp
                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.d(TAG, "Animated decode attempt failed for ${layer.content}, falling back to static decode")
                                }
                            }

                            val options = android.graphics.BitmapFactory.Options().apply {
                                inJustDecodeBounds = true
                                inScaled = false // CRITICAL: Stop Android from pre-scaling from assets
                            }

                            // Helper: opens a stream regardless of URI scheme (file:// or content://)
                            fun openStream(u: android.net.Uri): java.io.InputStream? {
                                return try {
                                    if (u.scheme == "file") {
                                        val path = u.path ?: return null
                                        java.io.FileInputStream(java.io.File(path))
                                    } else {
                                        context.contentResolver.openInputStream(u)
                                    }
                                } catch (e: Exception) {
                                    Log.e(TAG, "Failed to open stream for Uri: $u", e)
                                    null
                                }
                            }

                            // 1. Decode bounds only
                            try {
                                openStream(uri)?.use {
                                    android.graphics.BitmapFactory.decodeStream(it, null, options)
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "Error decoding bounds for ${layer.content}", e)
                            }

                            // 2. Calculate inSampleSize (only if it's massive)
                            val maxDim = 4096 // Increased for quality
                            var inSampleSize = 1
                            if (options.outHeight > maxDim || options.outWidth > maxDim) {
                                val halfHeight: Int = options.outHeight / 2
                                val halfWidth: Int = options.outWidth / 2
                                while ((halfHeight / inSampleSize) >= maxDim && (halfWidth / inSampleSize) >= maxDim) {
                                    inSampleSize *= 2
                                }
                            }

                            options.inJustDecodeBounds = false
                            options.inSampleSize = inSampleSize
                            options.inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888

                            val bitmap = try {
                                if (uri.toString().startsWith("file:///android_asset/")) {
                                    val assetPath = uri.toString().substringAfter("file:///android_asset/")
                                    context.assets.open(assetPath).use {
                                        android.graphics.BitmapFactory.decodeStream(it, null, options)
                                    }
                                } else {
                                    openStream(uri)?.use {
                                        android.graphics.BitmapFactory.decodeStream(it, null, options)
                                    }
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "Error decoding bitmap for ${layer.content}", e)
                                null
                            }

                            // Ensure ARGB_8888 (sometimes decoders ignore the hint)
                            if (bitmap != null && bitmap.config != android.graphics.Bitmap.Config.ARGB_8888) {
                                val converted = bitmap.copy(android.graphics.Bitmap.Config.ARGB_8888, false)
                                bitmap.recycle()
                                converted
                            } else {
                                bitmap
                            }
                        }
                    }
                }
                LayerType.VIDEO -> {
                    // Video layers now use ExoPlayer and SurfaceTexture, not Bitmaps
                    null
                }
                else -> null
            }?.also {
                 Log.d(TAG, "Bitmap loaded successfully: ${it.width}x${it.height} config=${it.config}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading bitmap for layer ${layer.id}", e)
            null
        }
    }

    private fun drawOverlaysLayer() {
        val program = overlayProgram ?: return
        if (layers.isEmpty()) return

        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

        program.use()

        for (layer in layers) {
            if (layer.type != LayerType.TEXT && layer.type != LayerType.STICKER && layer.type != LayerType.IMAGE && layer.type != LayerType.VIDEO) continue
            if (currentTimeMs < layer.startTimeMs || currentTimeMs > layer.startTimeMs + layer.durationMs) continue

           // Log.v(TAG, "Drawing overlay layer ${layer.id} at $currentTimeMs. Range: [${layer.startTimeMs}, ${layer.startTimeMs + layer.durationMs}]")

            val animTransform = TextAnimationEngine.computeTextTransform(
                layer, currentTimeMs, videoHeight
            )

            // Handle Typewriter effect (CURSOR) - support both legacy and pro fields
            if (layer.type == LayerType.TEXT && (layer.textAnimation == TextAnimationType.CURSOR || layer.animationIn == TextAnimationType.CURSOR)) {
                var textureId = overlayTextures[layer.id]
                var bitmap = overlayBitmaps[layer.id]

                val currentReveal = animTransform.revealChars
                if (currentReveal != lastRevealCharsMap[layer.id]) {
                    // Reveal changed! Re-render bitmap and upload texture
                    loadBitmapForLayer(layer, currentReveal)?.let { newBitmap ->
                        textureId?.let { tid -> GLES20.glDeleteTextures(1, intArrayOf(tid), 0) }

                        val newTextureId = loadTexture(newBitmap)
                        if (newTextureId > 0) {
                            overlayTextures[layer.id] = newTextureId
                            overlayBitmaps[layer.id] = newBitmap
                            lastRevealCharsMap[layer.id] = currentReveal
                        }
                    }
                }
            }

            // Handle Video Overlay refresh - No longer needed here, handled by ExoPlayer and SurfaceTexture hooks

            var textureId = overlayTextures[layer.id]
            var bitmap = overlayBitmaps[layer.id]

            // Handle Animated GIFs
            val movie = overlayMovies[layer.id]
            if (movie != null && bitmap != null && textureId != null) {
                val animTimeMs = (currentTimeMs - layer.startTimeMs).toInt()
                val cycleTime = if (movie.duration() > 0) animTimeMs % movie.duration() else 0
                
                bitmap.eraseColor(android.graphics.Color.TRANSPARENT)
                val canvas = android.graphics.Canvas(bitmap)
                movie.setTime(cycleTime)
                movie.draw(canvas, 0f, 0f)
                
                // Update texture sub-image quickly
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
                android.opengl.GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, bitmap)
            } else if (movie == null && layer.type == LayerType.STICKER && textureId != null && bitmap != null
                       && !loadingLayerIds.contains(layer.id)) {
                // Safety net: Movie was lost (e.g., due to race condition during surface recreation).
                // Trigger async re-load to recover the animation.
                loadingLayerIds.add(layer.id)
                GlobalScope.launch(Dispatchers.IO) {
                    val reloadedBitmap = loadBitmapForLayer(layer)
                    previewView.queueEvent {
                        loadingLayerIds.remove(layer.id)
                        if (overlayMovies.containsKey(layer.id)) {
                            // Movie was recovered, update bitmap and texture for animation
                            if (reloadedBitmap != null) {
                                overlayBitmaps[layer.id] = reloadedBitmap
                                val oldTex = overlayTextures[layer.id]
                                if (oldTex != null) GLES20.glDeleteTextures(1, intArrayOf(oldTex), 0)
                                val newTex = loadTexture(reloadedBitmap)
                                if (newTex > 0) overlayTextures[layer.id] = newTex
                            }
                            previewView.requestRender()
                        }
                    }
                }
            }

            // For videos, use OES texture logic using oesProgram instead of standard 2D overlayProgram
            if (layer.type == LayerType.VIDEO) {
                textureId = overlayVideoTextures[layer.id]
                if (textureId == null) continue
                // Video relies on oesProgram
                val vProg = oesProgram ?: continue
                vProg.use()

                val viewRatio = if (viewWidth > 0 && viewHeight > 0) viewWidth.toFloat() / viewHeight.toFloat() else 1f
                val videoRatio = if (videoWidth > 0 && videoHeight > 0) videoWidth.toFloat() / videoHeight.toFloat() else 1f

                val projection = FloatArray(16)
                Matrix.orthoM(projection, 0, -viewRatio, viewRatio, -1f, 1f, -1f, 1f)

                val modelMatrix = FloatArray(16)
                Matrix.setIdentityM(modelMatrix, 0)

                val kfPosX = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posX, KeyframeProperty.POS_X)
                val kfPosY = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posY, KeyframeProperty.POS_Y)
                val kfScale = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.scaleX, KeyframeProperty.SCALE)
                val kfRotation = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.rotation, KeyframeProperty.ROTATION)

                val effectiveVidWidth = videoWidth.toFloat().coerceAtLeast(1.0f)
                val effectiveVidHeight = videoHeight.toFloat().coerceAtLeast(1.0f)
                val squareX = (kfPosX - 0.5f + animTransform.offsetXPx / effectiveVidWidth) * 2f * viewRatio
                val squareY = -(kfPosY - 0.5f + animTransform.offsetYPx / effectiveVidHeight) * 2f
                Matrix.translateM(modelMatrix, 0, squareX, squareY, 0f)
                Matrix.rotateM(modelMatrix, 0, -(kfRotation + animTransform.rotation), 0f, 0f, 1f)

                // Assuming video is roughly 16:9 for scale factor if metadata is missing
                val stickerRatio = 9f/16f
                val baseOverlayScale = 0.3f * viewRatio
                val finalScaleX = kfScale * animTransform.scaleX * baseOverlayScale * (if (layer.flipHorizontal) -1f else 1f)
                val finalScaleY = kfScale * animTransform.scaleY / stickerRatio * baseOverlayScale * (if (layer.flipVertical) -1f else 1f)
                Matrix.scaleM(modelMatrix, 0, finalScaleX, finalScaleY, 1f)

                val mvpMatrix = FloatArray(16)
                Matrix.multiplyMM(mvpMatrix, 0, projection, 0, modelMatrix, 0)

                val stTransform = overlayTransformMatrices[layer.id] ?: identityMatrix
                vProg.bindAttributesAndUniforms()
                vProg.setSamplerTexIdUniform("uTexture", textureId, 0, true)
                vProg.setMatrixUniform("uMatrix", stTransform)
                vProg.setMatrixUniform("uVideoMatrix", mvpMatrix)
                
                applyAdjustmentsUniform(vProg, null)

                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)

                // Switch back to overlayProgram for the next standard layers
                program.use()
                continue
            }

            if (layer.type == LayerType.IMAGE) {
                if (textureId == null || bitmap == null) {
                    Log.e(TAG, "SKIPPING IMAGE layer ${layer.id}: TexId=$textureId, Bitmap=${bitmap != null}")
                    continue
                }
                // Log once every few seconds if it's active but maybe invisible for other reasons
                if (System.currentTimeMillis() % 5000 < 50) {
                    Log.d(TAG, "Rendering IMAGE layer ${layer.id}: TexId=$textureId, Bitmap=${bitmap.width}x${bitmap.height}, Alpha=")
                }
            } else {
                if (textureId == null || bitmap == null) continue
            }

            // Apply Professional Animations using TextAnimationEngine


            // IMPORTANT: Since we are rendering into a View-sized FBO,
            // the orthogonal projection must match the VIEW's aspect ratio,
            // not the video's aspect ratio, to prevent shifts/stretches.
            val viewRatio = if (viewWidth > 0 && viewHeight > 0) viewWidth.toFloat() / viewHeight.toFloat() else 1f
            val videoRatio = if (videoWidth > 0 && videoHeight > 0) videoWidth.toFloat() / videoHeight.toFloat() else 1f

            val projection = FloatArray(16)
            Matrix.orthoM(projection, 0, -viewRatio, viewRatio, -1f, 1f, -1f, 1f)

            val modelMatrix = FloatArray(16)
            Matrix.setIdentityM(modelMatrix, 0)

            // Apply base position + animation offsets + keyframes
            // Keyframe Interpolation
            val kfPosX = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posX, KeyframeProperty.POS_X)
            val kfPosY = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posY, KeyframeProperty.POS_Y)
            val kfScale = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.scaleX, KeyframeProperty.SCALE)
            val kfRotation = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.rotation, KeyframeProperty.ROTATION)
            val kfOpacity = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, 1.0f, KeyframeProperty.OPACITY)

            // Combine with TextAnimationEngine transform
            val effectiveVidWidth = videoWidth.toFloat().coerceAtLeast(1.0f)
            val effectiveVidHeight = videoHeight.toFloat().coerceAtLeast(1.0f)
            val squareX = (kfPosX - 0.5f + animTransform.offsetXPx / effectiveVidWidth) * 2f * viewRatio
            val squareY = -(kfPosY - 0.5f + animTransform.offsetYPx / effectiveVidHeight) * 2f
            Matrix.translateM(modelMatrix, 0, squareX, squareY, 0f)
            Matrix.rotateM(modelMatrix, 0, -(kfRotation + animTransform.rotation), 0f, 0f, 1f)

            val stickerRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
            val baseOverlayScale = 0.3f * viewRatio

            val finalScaleX = kfScale * animTransform.scaleX * baseOverlayScale * (if (layer.flipHorizontal) -1f else 1f)
            // Negate Y scale because Android Bitmap coordinate system (top-left)
            // is flipped relative to OpenGL (bottom-left).
            val finalScaleY = -kfScale * animTransform.scaleY / stickerRatio * baseOverlayScale * (if (layer.flipVertical) -1f else 1f)
            Matrix.scaleM(modelMatrix, 0, finalScaleX, finalScaleY, 1f)

            val mvpMatrix = FloatArray(16)
            Matrix.multiplyMM(mvpMatrix, 0, projection, 0, modelMatrix, 0)

            program.bindAttributesAndUniforms()
            program.setSamplerTexIdUniform("uTexture", textureId, 0, false)
            program.setMatrixUniform("uMatrix", mvpMatrix)

            program.setFloatUniform("uAlpha", animTransform.alpha * kfOpacity)

            program.setIntUniform("uSelected", if (layer.isSelected) 1 else 0)
            program.setFloatUniform("uTime", currentTimeMs / 1000f)
            
            applyAdjustmentsUniform(program, null)

            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)

            val error = GLES20.glGetError()
            if (error != GLES20.GL_NO_ERROR) {
                Log.e(TAG, "GL Error drawing overlay layer ${layer.id}: $error")
            }
        }

        // If we have animated overlays, request a continuous render to keep them moving
        // even if the main video/seek position hasn't changed.
        if (overlayMovies.isNotEmpty()) {
            previewView.requestRender()
        }
    }

    fun setVideoInteracting(interacting: Boolean) {
        this.isVideoInteracting = interacting
        previewView.requestRender()
    }

    private fun drawCanvasBackground(segment: VideoSegment?) {
        if (segment == null) return
        val canvas = segment.canvas
        if (canvas.type == CanvasType.NONE) return

        if (canvas.type == CanvasType.COLOR || canvas.type == CanvasType.GRADIENT) {
            val prog = canvasProgram ?: return
            prog.use()
            prog.setIntUniform("uType", if (canvas.type == CanvasType.COLOR) 1 else 2)
            
            if (canvas.type == CanvasType.COLOR) {
                val c = canvas.color
                prog.setFloatVec4Uniform("uColor", 
                    android.graphics.Color.red(c) / 255f,
                    android.graphics.Color.green(c) / 255f,
                    android.graphics.Color.blue(c) / 255f,
                    1.0f
                )
            } else {
                val c1 = if (canvas.gradientColors.size > 0) canvas.gradientColors[0] else android.graphics.Color.BLACK
                val c2 = if (canvas.gradientColors.size > 1) canvas.gradientColors[1] else android.graphics.Color.DKGRAY
                prog.setFloatVec4Uniform("uGradientColor1", 
                    android.graphics.Color.red(c1) / 255f,
                    android.graphics.Color.green(c1) / 255f,
                    android.graphics.Color.blue(c1) / 255f,
                    1.0f
                )
                prog.setFloatVec4Uniform("uGradientColor2", 
                    android.graphics.Color.red(c2) / 255f,
                    android.graphics.Color.green(c2) / 255f,
                    android.graphics.Color.blue(c2) / 255f,
                    1.0f
                )
            }

            prog.bindAttributesAndUniforms()
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
        } else if (canvas.type == CanvasType.BLUR) {
            if (lastFrameTexId != -1) {
                val prog = blurProgram ?: return
                prog.use()
                prog.setSamplerTexIdUniform("uTexSampler", lastFrameTexId, 0, false)
                prog.setFloatUniform("uIntensity", canvas.blur)
                prog.setFloatUniform("uProgress", 0f)
                prog.setFloatUniform("uTime", currentTimeMs / 1000f)
                prog.bindAttributesAndUniforms()
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
            }
        }
    }

    private fun drawVideoBorder() {
        val prog = borderProgram ?: return
        prog.use()

        // 1. Setup Buffers ONCE
        prog.bindAttributesAndUniforms()

        val viewRatio = if (viewWidth > 0 && viewHeight > 0) viewWidth.toFloat() / viewHeight.toFloat() else 1f
        val videoRatio = if (videoWidth > 0 && videoHeight > 0) videoWidth.toFloat() / videoHeight.toFloat() else 1f

        // Calculate Sticky Base Scale (same logic as in drawVideoLayer)
        var baseScaleX = 1f
        var baseScaleY = 1f
        if (videoRatio > viewRatio) {
            baseScaleY = viewRatio / videoRatio
        } else if (videoRatio < viewRatio) {
            baseScaleX = videoRatio / viewRatio
        }

        // Project-space matrix (Sticky - no pan/zoom/rotate)
        val stickyTransformMatrix = FloatArray(16)
        Matrix.setIdentityM(stickyTransformMatrix, 0)
        Matrix.scaleM(stickyTransformMatrix, 0, baseScaleX * viewRatio, baseScaleY, 1f)

        val projection = FloatArray(16)
        Matrix.orthoM(projection, 0, -viewRatio, viewRatio, -1f, 1f, -1f, 1f)

        val mvpMatrix = FloatArray(16)
        Matrix.multiplyMM(mvpMatrix, 0, projection, 0, stickyTransformMatrix, 0)

        // 2. Draw Bounding Box Border as LINE_LOOP
        val borderColor = if (isVideoInteracting) {
            floatArrayOf(1.0f, 1.0f, 0.0f, 1.0f) // Yellow
        } else {
            floatArrayOf(1.0f, 1.0f, 1.0f, 0.4f) // More subtle White
        }
        prog.setMatrixUniform("uMatrix", mvpMatrix)
        prog.setFloatVec4Uniform("uColor", borderColor[0], borderColor[1], borderColor[2], borderColor[3])

        GLES20.glLineWidth(2f)
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, 4)

        // 3. Draw Corner Handles (small squares)
        val handleSize = 0.03f
        val corners = arrayOf(
            floatArrayOf(-1f, -1f), // BL
            floatArrayOf(1f, -1f),  // BR
            floatArrayOf(1f, 1f),   // TR
            floatArrayOf(-1f, 1f)   // TL
        )

        prog.setFloatVec4Uniform("uColor", 1.0f, 1.0f, 1.0f, 0.8f)

        for (corner in corners) {
            val hModelMatrix = FloatArray(16)
            Matrix.setIdentityM(hModelMatrix, 0)
            Matrix.multiplyMM(hModelMatrix, 0, stickyTransformMatrix, 0, hModelMatrix, 0)
            Matrix.translateM(hModelMatrix, 0, corner[0], corner[1], 0f)

            val handleMvpMatrix = FloatArray(16)
            Matrix.multiplyMM(handleMvpMatrix, 0, projection, 0, hModelMatrix, 0)
            Matrix.scaleM(handleMvpMatrix, 0, handleSize / viewRatio, handleSize, 1f)

            prog.setMatrixUniform("uMatrix", handleMvpMatrix)
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
        }

        // Note: Rotation handle is removed for the "sticky" border as it doesn't rotate.
        // If user wants to rotate, they can still do it via gestures.
    }

    private fun applyAdjustmentsUniform(program: UtilGlProgram, segment: VideoSegment?) {
        val adj = segment?.adjustments ?: AdjustmentData()
        val floatArray = floatArrayOf(
            adj.brightness,
            adj.contrast,
            adj.saturation,
            adj.temp,
            adj.hue,
            adj.fade,
            adj.vignate,
            adj.grain,
            adj.highlights,
            adj.shadows,
            adj.whites,
            adj.blacks,
            adj.brilliance,
            adj.clarity,
            0f // Padding to 15
        )
        program.setFloatArrayUniform("uAdj", floatArray)
    }

    private fun drawVideoLayer() {
        // If we are at the end of the timeline (filler zone), skip drawing the background video.
        if (currentClipIndex == -1 && !isVideoInteracting) return

        // Determine if the active segment is a static image
        val currentSegment = if (currentClipIndex in videoSegments.indices) videoSegments[currentClipIndex] else null
        val isImageSegment = currentSegment?.isImage == true

        if (isImageSegment) {
            // ── Image segment: draw using custom image program that supports matrices ──────────
            val prog = imageProgram ?: return
            val texId = imageSegmentTextures[currentSegment!!.id] ?: return // texture not yet loaded

            val viewRatio = if (viewWidth > 0 && viewHeight > 0) viewWidth.toFloat() / viewHeight.toFloat() else 1f
            // Use the true image aspect ratio (stored when bitmap was decoded), fall back to 1:1
            val imageAspect = imageSegmentAspects[currentSegment.id] ?: 1f

            // Letterbox/pillarbox to preserve the image's own proportions
            var baseScaleX = 1f
            var baseScaleY = 1f
            if (imageAspect > viewRatio) {
                baseScaleY = viewRatio / imageAspect
            } else if (imageAspect < viewRatio) {
                baseScaleX = imageAspect / viewRatio
            }

            // Resolve transform: interactive (gesture) overrides segment defaults, just like video
            var finalScale: Float
            var finalRotation: Float
            var finalTransX: Float
            var finalTransY: Float
            if (isVideoInteracting) {
                // Live gesture values from setVideoTransform()
                finalScale    = videoScale
                finalRotation = videoRotation
                finalTransX   = videoTranslateX
                finalTransY   = videoTranslateY
            } else if (currentSegment.keyframes.isNotEmpty()) {
                finalScale    = KeyframeEngine.interpolate(currentSegment.keyframes, currentTimeMs, currentSegment.scaleX, KeyframeProperty.SCALE)
                finalRotation = KeyframeEngine.interpolate(currentSegment.keyframes, currentTimeMs, currentSegment.rotation.toFloat(), KeyframeProperty.ROTATION)
                finalTransX   = KeyframeEngine.interpolate(currentSegment.keyframes, currentTimeMs, currentSegment.posX, KeyframeProperty.POS_X)
                finalTransY   = KeyframeEngine.interpolate(currentSegment.keyframes, currentTimeMs, currentSegment.posY, KeyframeProperty.POS_Y)
            } else {
                finalScale    = currentSegment.scaleX
                finalRotation = currentSegment.rotation.toFloat()
                finalTransX   = currentSegment.posX
                finalTransY   = currentSegment.posY
            }

            val transX = (finalTransX - 0.5f) * 2f * viewRatio
            val transY = -(finalTransY - 0.5f) * 2f
            Matrix.setIdentityM(videoTransformMatrix, 0)
            Matrix.translateM(videoTransformMatrix, 0, transX, transY, 0f)
            Matrix.rotateM(videoTransformMatrix, 0, -finalRotation, 0f, 0f, 1f)
            // Negate Y to flip the bitmap vertically (OpenGL vs Android bitmap coordinate mismatch)
            Matrix.scaleM(videoTransformMatrix, 0, finalScale * baseScaleX * viewRatio, -finalScale * baseScaleY, 1f)

            val projection = FloatArray(16)
            Matrix.orthoM(projection, 0, -viewRatio, viewRatio, -1f, 1f, -1f, 1f)
            val mvpMatrix = FloatArray(16)
            Matrix.multiplyMM(mvpMatrix, 0, projection, 0, videoTransformMatrix, 0)

            prog.use()
            prog.bindAttributesAndUniforms()
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId)
            val texHandle = GLES20.glGetUniformLocation(prog.programId, "uTexture")
            if (texHandle != -1) GLES20.glUniform1i(texHandle, 0)
            val matHandle = GLES20.glGetUniformLocation(prog.programId, "uVideoMatrix")
            if (matHandle != -1) GLES20.glUniformMatrix4fv(matHandle, 1, false, mvpMatrix, 0)
            
            applyAdjustmentsUniform(prog, currentSegment)
            
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
            return
        }

        // ── Normal video segment path ──────────────────────────────────────
        val program = oesProgram ?: return

        program.use()

        // Calculate Video Transform Matrix
        Matrix.setIdentityM(videoTransformMatrix, 0)

        // Aspect Ratio Correction logic:
        // We want to preserve the video's aspect ratio within the preview.
        // The preview's projection is -viewRatio..viewRatio, -1..1
        val viewRatio = if (viewWidth > 0 && viewHeight > 0) viewWidth.toFloat() / viewHeight.toFloat() else 1f
        val videoRatio = if (videoWidth > 0 && videoHeight > 0) videoWidth.toFloat() / videoHeight.toFloat() else 1f

        // Scale factor to preserve aspect ratio (Letterbox/Pillarbox effect)
        var baseScaleX = 1f
        var baseScaleY = 1f
        
        if (videoRatio > viewRatio) {
            baseScaleY = viewRatio / videoRatio
        } else if (videoRatio < viewRatio) {
            baseScaleX = videoRatio / viewRatio
        }
        
        // Interpolate Transform
        var finalScale = videoScale
        var finalRotation = videoRotation
        var finalTransX = videoTranslateX
        var finalTransY = videoTranslateY
        
        if (System.currentTimeMillis() % 1000 < 16) {
            // android.util.Log.d("GlVideoRenderer", "drawVideoLayer: interaction=$isVideoInteracting, scale=$finalScale, pos=($finalTransX, $finalTransY)")
        }

        // Only interpolate if NOT interacting and we have a valid segment
        if (!isVideoInteracting && currentClipIndex != -1 && currentClipIndex < videoSegments.size) {
            val segment = videoSegments[currentClipIndex]
            
            if (segment.keyframes.isNotEmpty()) {
                finalScale = KeyframeEngine.interpolate(segment.keyframes, currentTimeMs, segment.scaleX, KeyframeProperty.SCALE)
                finalRotation = KeyframeEngine.interpolate(segment.keyframes, currentTimeMs, segment.rotation.toFloat(), KeyframeProperty.ROTATION)
                finalTransX = KeyframeEngine.interpolate(segment.keyframes, currentTimeMs, segment.posX, KeyframeProperty.POS_X)
                finalTransY = KeyframeEngine.interpolate(segment.keyframes, currentTimeMs, segment.posY, KeyframeProperty.POS_Y)
            } else {
                finalScale = segment.scaleX
                finalRotation = segment.rotation.toFloat()
                finalTransX = segment.posX
                finalTransY = segment.posY
            }
        }

        // Apply Translation
        val transX = (finalTransX - 0.5f) * 2f * viewRatio
        val transY = -(finalTransY - 0.5f) * 2f
        Matrix.translateM(videoTransformMatrix, 0, transX, transY, 0f)
        
        // Apply Rotation
        Matrix.rotateM(videoTransformMatrix, 0, -finalRotation, 0f, 0f, 1f)
        
        // Apply Scale
        Matrix.scaleM(videoTransformMatrix, 0, finalScale * baseScaleX * viewRatio, finalScale * baseScaleY, 1f)
        
        val projection = FloatArray(16)
        Matrix.orthoM(projection, 0, -viewRatio, viewRatio, -1f, 1f, -1f, 1f)
        
        val mvpMatrix = FloatArray(16)
        Matrix.multiplyMM(mvpMatrix, 0, projection, 0, videoTransformMatrix, 0)
        
        program.bindAttributesAndUniforms()
        program.setSamplerTexIdUniform("uTexture", videoTextureId, 0, true)
        program.setMatrixUniform("uMatrix", videoTextureTransformMatrix)
        program.setMatrixUniform("uVideoMatrix", mvpMatrix)
        
        applyAdjustmentsUniform(program, currentSegment)
        
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4)
    }

    fun setVideoTransform(scale: Float, rotation: Float, dx: Float, dy: Float) {
        // Log occasionally to avoid spamming
        if (System.currentTimeMillis() % 100 < 16) {
            //android.util.Log.d("GlVideoRenderer", "setVideoTransform: scale=$scale, rot=$rotation, dx=$dx, dy=$dy")
        }
        videoScale = scale
        videoRotation = rotation
        videoTranslateX = dx
        videoTranslateY = dy
        previewView.requestRender()
    }

    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {
        synchronized(this) {
            updateSurface = true
        }
        previewView.requestRender()
    }
    
    fun setVideoDimensions(width: Int, height: Int) {
        videoWidth = width
        videoHeight = height
    }
    
    fun release() {
        surface?.release()
        surfaceTexture?.release()
        oesProgram?.delete()
        overlayProgram?.delete()
        effectPrograms.values.forEach { it.delete() }
        if (fboIds[0] != 0) GLES20.glDeleteFramebuffers(2, fboIds, 0)
        if (fboTexIds[0] != 0) GLES20.glDeleteTextures(2, fboTexIds, 0)
        
        videoRetrieverMap.values.forEach { it.release() }
        videoRetrieverMap.clear()
        videoFrameJobMap.values.forEach { it.cancel() }
        videoFrameJobMap.clear()
        renderScope.cancel()
        
        overlaySurfaceTextures.values.forEach { it.release() }
        overlaySurfaces.values.forEach { it.release() }
        overlaySurfaceTextures.clear()
        overlaySurfaces.clear()
    }
    
    fun setOverlayVideoSurface(layerId: String, player: androidx.media3.exoplayer.ExoPlayer) {
        previewView.queueEvent {
            if (!overlayVideoTextures.containsKey(layerId)) {
                val textures = IntArray(1)
                GLES20.glGenTextures(1, textures, 0)
                val textureId = textures[0]

                GLES20.glBindTexture(android.opengl.GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId)
                GLES20.glTexParameterf(android.opengl.GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST.toFloat())
                GLES20.glTexParameterf(android.opengl.GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat())

                val st = SurfaceTexture(textureId)
                st.setOnFrameAvailableListener {
                    synchronized(this) {
                        overlayUpdateFlags[layerId] = true
                    }
                    previewView.requestRender()
                }
                val surface = Surface(st)

                overlayVideoTextures[layerId] = textureId
                overlaySurfaceTextures[layerId] = st
                overlaySurfaces[layerId] = surface
                
                // Jump to Main thread to bind the surface to ExoPlayer
                previewView.post {
                    player.setVideoSurface(surface)
                }
            } else {
                // Surface already exists, just bind it
                val existingSurface = overlaySurfaces[layerId]
                if (existingSurface != null) {
                    previewView.post {
                        player.setVideoSurface(existingSurface)
                    }
                }
            }
        }
    }
}


