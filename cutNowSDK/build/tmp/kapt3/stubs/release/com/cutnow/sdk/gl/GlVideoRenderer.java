package com.cutnow.sdk.gl;

/**
 * Renderer for GlPreviewView.
 * Handles video rendering from ExoPlayer (SurfaceTexture) and applies effects/overlays.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00e2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u0000 \u009e\u00012\u00020\u00012\u00020\u0002:\u0002\u009e\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u001a\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020\f2\b\u0010h\u001a\u0004\u0018\u00010\\H\u0002J\b\u0010i\u001a\u00020fH\u0002J\u0012\u0010j\u001a\u00020f2\b\u0010h\u001a\u0004\u0018\u00010\\H\u0002J\u0010\u0010k\u001a\u00020f2\u0006\u0010l\u001a\u00020\u0015H\u0002J\b\u0010m\u001a\u00020fH\u0002J\b\u0010n\u001a\u00020fH\u0002J\b\u0010o\u001a\u00020fH\u0002J\u0006\u0010p\u001a\u00020fJ\u001c\u0010q\u001a\u0004\u0018\u0001092\u0006\u0010r\u001a\u0002022\b\b\u0002\u0010s\u001a\u00020\u0015H\u0002J\u0010\u0010t\u001a\u00020\u00152\u0006\u0010u\u001a\u000209H\u0002J\u0012\u0010v\u001a\u00020f2\b\u0010w\u001a\u0004\u0018\u00010xH\u0016J\u0012\u0010y\u001a\u00020f2\b\u0010O\u001a\u0004\u0018\u00010?H\u0016J\"\u0010z\u001a\u00020f2\b\u0010w\u001a\u0004\u0018\u00010x2\u0006\u0010{\u001a\u00020\u00152\u0006\u0010|\u001a\u00020\u0015H\u0016J\u001c\u0010}\u001a\u00020f2\b\u0010w\u001a\u0004\u0018\u00010x2\b\u0010~\u001a\u0004\u0018\u00010\u007fH\u0016J\u0007\u0010\u0080\u0001\u001a\u00020fJ-\u0010\u0081\u0001\u001a\u00020f2\u0007\u0010\u0082\u0001\u001a\u00020\u00152\u0007\u0010\u0083\u0001\u001a\u00020\u00152\u0007\u0010\u0084\u0001\u001a\u00020\'2\u0007\u0010\u0085\u0001\u001a\u00020QH\u0002J\u0012\u0010\u0086\u0001\u001a\u00020f2\u0007\u0010\u0087\u0001\u001a\u00020\u0017H\u0007J\u0016\u0010\u0088\u0001\u001a\u00020f2\r\u0010\u0089\u0001\u001a\b\u0012\u0004\u0012\u00020201J\u001a\u0010\u008a\u0001\u001a\u00020f2\u0007\u0010\u008b\u0001\u001a\u00020\t2\b\u0010\u008c\u0001\u001a\u00030\u008d\u0001J\u0016\u0010\u008e\u0001\u001a\u00020f2\r\u0010\u008f\u0001\u001a\b\u0012\u0004\u0012\u00020\\01J\u0017\u0010\u0090\u0001\u001a\u00020f2\u0006\u0010{\u001a\u00020\u00152\u0006\u0010|\u001a\u00020\u0015J\u0010\u0010\u0091\u0001\u001a\u00020f2\u0007\u0010\u0092\u0001\u001a\u00020!J+\u0010\u0093\u0001\u001a\u00020f2\u0007\u0010\u0094\u0001\u001a\u00020\'2\u0007\u0010\u0095\u0001\u001a\u00020\'2\u0007\u0010\u0096\u0001\u001a\u00020\'2\u0007\u0010\u0097\u0001\u001a\u00020\'J\u0019\u0010\u0098\u0001\u001a\u00020f2\u0006\u0010{\u001a\u00020\u00152\u0006\u0010|\u001a\u00020\u0015H\u0002J\u0018\u0010\u0099\u0001\u001a\u00020f2\r\u0010\u0089\u0001\u001a\b\u0012\u0004\u0012\u00020201H\u0002J\u0010\u0010\u009a\u0001\u001a\u00020f2\u0007\u0010\u009b\u0001\u001a\u000202J/\u0010\u009c\u0001\u001a\u00020f2\r\u0010\u0089\u0001\u001a\b\u0012\u0004\u0012\u000202012\u0015\u0010\u009d\u0001\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u0001090HH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\'0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00150&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00150&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u00100\u001a\n\u0012\u0004\u0012\u000202\u0018\u000101X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u00103\u001a\b\u0012\u0004\u0012\u00020201X\u0082\u000e\u00a2\u0006\u0002\n\u0000RN\u00104\u001aB\u0012\f\u0012\n 6*\u0004\u0018\u00010\t0\t\u0012\f\u0012\n 6*\u0004\u0018\u00010!0! 6* \u0012\f\u0012\n 6*\u0004\u0018\u00010\t0\t\u0012\f\u0012\n 6*\u0004\u0018\u00010!0!\u0018\u00010505X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u00107\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u00108\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u0002090&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010:\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010;\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020<0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010=\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010>\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020?0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010@\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020A0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010B\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00150&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010C\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020#0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010D\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020!0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010F\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00150&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010G\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u000109\u0018\u00010HX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010I\u001a\n\u0012\u0004\u0012\u000202\u0018\u000101X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010J\u001a\n\u0012\u0004\u0012\u000202\u0018\u000101X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020LX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010M\u001a\u0004\u0018\u00010AX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010O\u001a\u0004\u0018\u00010?X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010P\u001a\u000e\u0012\u0004\u0012\u00020Q\u0012\u0004\u0012\u00020\f0\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020U0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010W\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020X0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010Y\u001a\u00020\'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u00020\'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010[\u001a\b\u0012\u0004\u0012\u00020\\01X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010]\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010^\u001a\u00020#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010_\u001a\u00020#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020\'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020\'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010c\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010d\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u009f\u0001"}, d2 = {"Lcom/cutnow/sdk/gl/GlVideoRenderer;", "Landroid/opengl/GLSurfaceView$Renderer;", "Landroid/graphics/SurfaceTexture$OnFrameAvailableListener;", "context", "Landroid/content/Context;", "previewView", "Lcom/cutnow/sdk/ui/GlPreviewView;", "(Landroid/content/Context;Lcom/cutnow/sdk/ui/GlPreviewView;)V", "adjustmentGlslBlock", "", "blitFragmentShaderCode", "blitProgram", "Lcom/cutnow/sdk/util/UtilGlProgram;", "blitVertexShaderCode", "blurProgram", "borderFragmentShaderCode", "borderProgram", "borderVertexShaderCode", "canvasFragmentShaderCode", "canvasProgram", "currentClipIndex", "", "currentTimeMs", "", "effectPrograms", "", "fboIds", "", "fboLastFrameId", "fboTexIds", "fragmentShaderCode2D", "fragmentShaderCodeOES", "hasPendingLayerEvent", "", "identityMatrix", "", "imageProgram", "imageSegmentAspects", "Ljava/util/concurrent/ConcurrentHashMap;", "", "imageSegmentTextures", "imageVertexShaderCode", "isVideoInteracting", "lastCapturedForClipIndex", "lastFrameClipId", "lastFrameTexId", "lastLayersSnapshot", "lastRevealCharsMap", "latestIntendedLayers", "", "Lcom/cutnow/sdk/model/MediaLayer;", "layers", "loadingLayerIds", "Ljava/util/concurrent/ConcurrentHashMap$KeySetView;", "kotlin.jvm.PlatformType", "oesProgram", "overlayBitmaps", "Landroid/graphics/Bitmap;", "overlayContentCache", "overlayMovies", "Landroid/graphics/Movie;", "overlayProgram", "overlaySurfaceTextures", "Landroid/graphics/SurfaceTexture;", "overlaySurfaces", "Landroid/view/Surface;", "overlayTextures", "overlayTransformMatrices", "overlayUpdateFlags", "overlayVertexShaderCode", "overlayVideoTextures", "pendingBitmaps", "", "pendingLayers", "pendingNewLayers", "renderScope", "Lkotlinx/coroutines/CoroutineScope;", "surface", "surfaceReady", "surfaceTexture", "transitionPrograms", "Lcom/cutnow/sdk/model/TransitionType;", "updateSurface", "vertexShaderCode", "videoFrameJobMap", "Lkotlinx/coroutines/Job;", "videoHeight", "videoRetrieverMap", "Landroid/media/MediaMetadataRetriever;", "videoRotation", "videoScale", "videoSegments", "Lcom/cutnow/sdk/model/VideoSegment;", "videoTextureId", "videoTextureTransformMatrix", "videoTransformMatrix", "videoTranslateX", "videoTranslateY", "videoWidth", "viewHeight", "viewWidth", "applyAdjustmentsUniform", "", "program", "segment", "captureLastFrameToCache", "drawCanvasBackground", "drawFboToScreen", "texId", "drawOverlaysLayer", "drawVideoBorder", "drawVideoLayer", "invalidateLayerSnapshot", "loadBitmapForLayer", "layer", "revealChars", "loadTexture", "bitmap", "onDrawFrame", "gl", "Ljavax/microedition/khronos/opengles/GL10;", "onFrameAvailable", "onSurfaceChanged", "width", "height", "onSurfaceCreated", "config", "Ljavax/microedition/khronos/egl/EGLConfig;", "release", "renderTransition", "currentTexId", "lastTexId", "progress", "type", "setCurrentTime", "timeMs", "setLayers", "newLayers", "setOverlayVideoSurface", "layerId", "player", "Landroidx/media3/exoplayer/ExoPlayer;", "setSegments", "segments", "setVideoDimensions", "setVideoInteracting", "interacting", "setVideoTransform", "scale", "rotation", "dx", "dy", "setupFbos", "updateEffectPrograms", "updateLayerDirect", "updatedLayer", "updateOverlayTextures", "preloadedBitmaps", "Companion", "cutNowSDK_release"})
public final class GlVideoRenderer implements android.opengl.GLSurfaceView.Renderer, android.graphics.SurfaceTexture.OnFrameAvailableListener {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cutnow.sdk.ui.GlPreviewView previewView = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "GlVideoRenderer";
    @org.jetbrains.annotations.Nullable()
    private android.graphics.SurfaceTexture surfaceTexture;
    @org.jetbrains.annotations.Nullable()
    private android.view.Surface surface;
    private int videoTextureId = 0;
    @org.jetbrains.annotations.NotNull()
    private final float[] videoTextureTransformMatrix = null;
    @org.jetbrains.annotations.NotNull()
    private final float[] identityMatrix = null;
    private boolean updateSurface = false;
    private boolean surfaceReady = false;
    @org.jetbrains.annotations.Nullable()
    private com.cutnow.sdk.util.UtilGlProgram oesProgram;
    @org.jetbrains.annotations.Nullable()
    private com.cutnow.sdk.util.UtilGlProgram imageProgram;
    @org.jetbrains.annotations.Nullable()
    private com.cutnow.sdk.util.UtilGlProgram canvasProgram;
    private int videoWidth = 0;
    private int videoHeight = 0;
    private int viewWidth = 0;
    private int viewHeight = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.String> overlayContentCache = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Integer> lastRevealCharsMap = null;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<com.cutnow.sdk.model.MediaLayer> pendingLayers;
    @org.jetbrains.annotations.Nullable()
    private java.util.Map<java.lang.String, android.graphics.Bitmap> pendingBitmaps;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Integer> overlayVideoTextures = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, android.graphics.SurfaceTexture> overlaySurfaceTextures = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, android.view.Surface> overlaySurfaces = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, float[]> overlayTransformMatrices = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Boolean> overlayUpdateFlags = null;
    @org.jetbrains.annotations.NotNull()
    private int[] fboIds;
    @org.jetbrains.annotations.NotNull()
    private int[] fboTexIds;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, com.cutnow.sdk.util.UtilGlProgram> effectPrograms = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<com.cutnow.sdk.model.TransitionType, com.cutnow.sdk.util.UtilGlProgram> transitionPrograms = null;
    @org.jetbrains.annotations.Nullable()
    private com.cutnow.sdk.util.UtilGlProgram blitProgram;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.cutnow.sdk.model.VideoSegment> videoSegments;
    private int lastFrameTexId = -1;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String lastFrameClipId;
    private int fboLastFrameId = -1;
    private int currentClipIndex = -1;
    private int lastCapturedForClipIndex = -1;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String vertexShaderCode = "\n        attribute vec4 aFramePosition;\n        attribute vec4 aTexSamplingCoord;\n        uniform mat4 uMatrix;\n        uniform mat4 uVideoMatrix;\n        varying vec2 vTexCoord;\n        void main() {\n            gl_Position = uVideoMatrix * aFramePosition;\n            vTexCoord = (uMatrix * aTexSamplingCoord).xy;\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String overlayVertexShaderCode = "\n        attribute vec4 aFramePosition;\n        attribute vec4 aTexSamplingCoord;\n        uniform mat4 uMatrix;\n        varying vec2 vTexCoord;\n        void main() {\n            gl_Position = uMatrix * aFramePosition;\n            vTexCoord = aTexSamplingCoord.xy;\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String blitVertexShaderCode = "\n        attribute vec4 aFramePosition;\n        attribute vec4 aTexSamplingCoord;\n        varying vec2 vTexCoord;\n        void main() {\n            gl_Position = aFramePosition;\n            vTexCoord = aTexSamplingCoord.xy;\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String imageVertexShaderCode = "\n        attribute vec4 aFramePosition;\n        attribute vec4 aTexSamplingCoord;\n        uniform mat4 uVideoMatrix;\n        varying vec2 vTexCoord;\n        void main() {\n            gl_Position = uVideoMatrix * aFramePosition;\n            vTexCoord = aTexSamplingCoord.xy;\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String blitFragmentShaderCode = "\n        precision mediump float;\n        varying vec2 vTexCoord;\n        uniform sampler2D uTexture;\n        void main() {\n            gl_FragColor = texture2D(uTexture, vTexCoord);\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String adjustmentGlslBlock = "\n        uniform float uAdj[15]; \n        \n        float rand(vec2 co) {\n            return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);\n        }\n\n        vec3 applyAdjustments(vec3 color, vec2 uv) {\n            // Brightness\n            color += uAdj[0];\n            \n            // Contrast\n            color = (color - 0.5) * uAdj[1] + 0.5;\n            \n            // Saturation\n            float lum = dot(color, vec3(0.299, 0.587, 0.114));\n            color = mix(vec3(lum), color, uAdj[2]);\n            \n            // Temp\n            color.r += uAdj[3] * 0.1;\n            color.b -= uAdj[3] * 0.1;\n            \n            // Hue\n            float angle = uAdj[4] * 3.14159;\n            float s = sin(angle), c = cos(angle);\n            vec3 weights = (vec3(2.0 * c, -sqrt(3.0) * s - c, sqrt(3.0) * s - c) + 1.0) / 3.0;\n            color = vec3(dot(color, weights.xyz), dot(color, weights.zxy), dot(color, weights.yzx));\n            \n            // Fade\n            color = mix(color, vec3(0.5), uAdj[5] * 0.5);\n            \n            // Highlights / Shadows\n            float luma = dot(color, vec3(0.299, 0.587, 0.114));\n            float shadowMask = 1.0 - smoothstep(0.0, 0.5, luma);\n            float highlightMask = smoothstep(0.5, 1.0, luma);\n            color += uAdj[9] * shadowMask * 0.5;\n            color += uAdj[8] * highlightMask * 0.5;\n            \n            // Vignette\n            float dist = distance(uv, vec2(0.5, 0.5));\n            float fVignette = smoothstep(0.8, 0.2, dist * (1.0 + uAdj[6]));\n            if (uAdj[6] > 0.0) {\n                color *= mix(1.0, fVignette, uAdj[6]);\n            }\n            \n            // Grain\n            if (uAdj[7] > 0.0) {\n                float noise = rand(uv * 10.0) - 0.5;\n                color += noise * uAdj[7] * 0.2;\n            }\n            \n            // Blacks and Whites simplified\n            color += uAdj[11] * shadowMask * 0.2;\n            color += uAdj[10] * highlightMask * 0.2;\n            \n            // Brilliance\n            color += uAdj[12] * 0.1;\n            \n            // Clarity (Local Contrast / Mid-tone enhancement)\n            float midtone = 1.0 - abs(lum * 2.0 - 1.0);\n            color += (color - 0.5) * uAdj[13] * midtone * 0.3;\n            \n            return clamp(color, 0.0, 1.0);\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String fragmentShaderCodeOES = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String fragmentShaderCode2D = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String canvasFragmentShaderCode = "\n        precision mediump float;\n        varying vec2 vTexCoord;\n        uniform int uType; // 1=COLOR, 2=GRADIENT\n        uniform vec4 uColor;\n        uniform vec4 uGradientColor1;\n        uniform vec4 uGradientColor2;\n\n        void main() {\n            if (uType == 1) {\n                gl_FragColor = uColor;\n            } else if (uType == 2) {\n                gl_FragColor = mix(uGradientColor1, uGradientColor2, vTexCoord.y);\n            } else {\n                gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);\n            }\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String borderVertexShaderCode = "\n        attribute vec4 aFramePosition;\n        uniform mat4 uMatrix;\n        void main() {\n            gl_Position = uMatrix * aFramePosition;\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String borderFragmentShaderCode = "\n        precision mediump float;\n        uniform vec4 uColor;\n        void main() {\n            gl_FragColor = uColor;\n        }\n    ";
    @org.jetbrains.annotations.Nullable()
    private com.cutnow.sdk.util.UtilGlProgram overlayProgram;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.cutnow.sdk.model.MediaLayer> layers;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Integer> overlayTextures = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, android.graphics.Bitmap> overlayBitmaps = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, android.graphics.Movie> overlayMovies = null;
    private final java.util.concurrent.ConcurrentHashMap.KeySetView<java.lang.String, java.lang.Boolean> loadingLayerIds = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Integer> imageSegmentTextures = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Float> imageSegmentAspects = null;
    private long currentTimeMs = 0L;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private volatile java.util.List<com.cutnow.sdk.model.MediaLayer> latestIntendedLayers;
    @kotlin.jvm.Volatile()
    private volatile boolean hasPendingLayerEvent = false;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private volatile java.util.List<com.cutnow.sdk.model.MediaLayer> pendingNewLayers;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.NotNull()
    private volatile java.lang.String lastLayersSnapshot = "";
    private float videoScale = 1.0F;
    private float videoRotation = 0.0F;
    private float videoTranslateX = 0.5F;
    private float videoTranslateY = 0.5F;
    @org.jetbrains.annotations.NotNull()
    private final float[] videoTransformMatrix = null;
    private boolean isVideoInteracting = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, android.media.MediaMetadataRetriever> videoRetrieverMap = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, kotlinx.coroutines.Job> videoFrameJobMap = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope renderScope = null;
    @org.jetbrains.annotations.Nullable()
    private com.cutnow.sdk.util.UtilGlProgram borderProgram;
    @org.jetbrains.annotations.Nullable()
    private com.cutnow.sdk.util.UtilGlProgram blurProgram;
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.gl.GlVideoRenderer.Companion Companion = null;
    
    public GlVideoRenderer(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.ui.GlPreviewView previewView) {
        super();
    }
    
    private final void renderTransition(int currentTexId, int lastTexId, float progress, com.cutnow.sdk.model.TransitionType type) {
    }
    
    @java.lang.Override()
    public void onSurfaceCreated(@org.jetbrains.annotations.Nullable()
    javax.microedition.khronos.opengles.GL10 gl, @org.jetbrains.annotations.Nullable()
    javax.microedition.khronos.egl.EGLConfig config) {
    }
    
    @java.lang.Override()
    public void onSurfaceChanged(@org.jetbrains.annotations.Nullable()
    javax.microedition.khronos.opengles.GL10 gl, int width, int height) {
    }
    
    private final void setupFbos(int width, int height) {
    }
    
    @java.lang.Override()
    public void onDrawFrame(@org.jetbrains.annotations.Nullable()
    javax.microedition.khronos.opengles.GL10 gl) {
    }
    
    private final void drawFboToScreen(int texId) {
    }
    
    private final void captureLastFrameToCache() {
    }
    
    public final void setLayers(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.MediaLayer> newLayers) {
    }
    
    private final void updateOverlayTextures(java.util.List<com.cutnow.sdk.model.MediaLayer> newLayers, java.util.Map<java.lang.String, android.graphics.Bitmap> preloadedBitmaps) {
    }
    
    public final void updateLayerDirect(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.MediaLayer updatedLayer) {
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
    public final void invalidateLayerSnapshot() {
    }
    
    @android.annotation.SuppressLint(value = {"SuspiciousIndentation"})
    public final void setCurrentTime(long timeMs) {
    }
    
    public final void setSegments(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.VideoSegment> segments) {
    }
    
    private final void updateEffectPrograms(java.util.List<com.cutnow.sdk.model.MediaLayer> newLayers) {
    }
    
    private final int loadTexture(android.graphics.Bitmap bitmap) {
        return 0;
    }
    
    private final android.graphics.Bitmap loadBitmapForLayer(com.cutnow.sdk.model.MediaLayer layer, int revealChars) {
        return null;
    }
    
    private final void drawOverlaysLayer() {
    }
    
    public final void setVideoInteracting(boolean interacting) {
    }
    
    private final void drawCanvasBackground(com.cutnow.sdk.model.VideoSegment segment) {
    }
    
    private final void drawVideoBorder() {
    }
    
    private final void applyAdjustmentsUniform(com.cutnow.sdk.util.UtilGlProgram program, com.cutnow.sdk.model.VideoSegment segment) {
    }
    
    private final void drawVideoLayer() {
    }
    
    public final void setVideoTransform(float scale, float rotation, float dx, float dy) {
    }
    
    @java.lang.Override()
    public void onFrameAvailable(@org.jetbrains.annotations.Nullable()
    android.graphics.SurfaceTexture surfaceTexture) {
    }
    
    public final void setVideoDimensions(int width, int height) {
    }
    
    public final void release() {
    }
    
    public final void setOverlayVideoSurface(@org.jetbrains.annotations.NotNull()
    java.lang.String layerId, @org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer player) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/cutnow/sdk/gl/GlVideoRenderer$Companion;", "", "()V", "TAG", "", "cutNowSDK_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}