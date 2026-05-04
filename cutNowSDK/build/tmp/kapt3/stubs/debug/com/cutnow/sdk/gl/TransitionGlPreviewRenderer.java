package com.cutnow.sdk.gl;

/**
 * GPU-accelerated transition preview renderer.
 *
 * Works similarly to FilterGlPreviewRenderer but renders sequence of frames
 * for a transition between two sample bitmaps.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002J\u0018\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0019H\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0004H\u0002J\u0010\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020#H\u0002J$\u0010$\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001c\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010%J\b\u0010&\u001a\u00020\u001fH\u0002J\b\u0010\'\u001a\u00020\nH\u0002J\u0006\u0010(\u001a\u00020\u001fJ\u0010\u0010)\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020#H\u0002J\u0018\u0010*\u001a\u00020\u001f2\u0006\u0010+\u001a\u00020\u00042\u0006\u0010,\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/cutnow/sdk/gl/TransitionGlPreviewRenderer;", "", "()V", "FRAME_COUNT", "", "PREVIEW_SIZE", "animationCache", "", "Lcom/cutnow/sdk/model/TransitionType;", "", "Landroid/graphics/Bitmap;", "eglContext", "Landroid/opengl/EGLContext;", "eglDisplay", "Landroid/opengl/EGLDisplay;", "eglSurface", "Landroid/opengl/EGLSurface;", "glMutex", "Lkotlinx/coroutines/sync/Mutex;", "initialized", "", "texCurrent", "texLast", "compileProgram", "vertSrc", "", "fragSrc", "compileShader", "type", "src", "drawQuad", "", "program", "ensureInitialized", "context", "Landroid/content/Context;", "getTransitionFrames", "(Landroid/content/Context;Lcom/cutnow/sdk/model/TransitionType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "initEgl", "readPixels", "release", "uploadTextures", "uploadToTex", "id", "bmp", "cutNowSDK_debug"})
public final class TransitionGlPreviewRenderer {
    private static final int PREVIEW_SIZE = 120;
    private static final int FRAME_COUNT = 15;
    @org.jetbrains.annotations.NotNull()
    private static android.opengl.EGLDisplay eglDisplay;
    @org.jetbrains.annotations.NotNull()
    private static android.opengl.EGLContext eglContext;
    @org.jetbrains.annotations.NotNull()
    private static android.opengl.EGLSurface eglSurface;
    private static boolean initialized = false;
    private static int texCurrent = 0;
    private static int texLast = 0;
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.sync.Mutex glMutex = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<com.cutnow.sdk.model.TransitionType, java.util.List<android.graphics.Bitmap>> animationCache = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.gl.TransitionGlPreviewRenderer INSTANCE = null;
    
    private TransitionGlPreviewRenderer() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTransitionFrames(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<android.graphics.Bitmap>> $completion) {
        return null;
    }
    
    private final void ensureInitialized(android.content.Context context) {
    }
    
    private final void initEgl() {
    }
    
    private final void uploadTextures(android.content.Context context) {
    }
    
    private final void uploadToTex(int id, android.graphics.Bitmap bmp) {
    }
    
    private final int compileProgram(java.lang.String vertSrc, java.lang.String fragSrc) {
        return 0;
    }
    
    private final int compileShader(int type, java.lang.String src) {
        return 0;
    }
    
    private final void drawQuad(int program) {
    }
    
    private final android.graphics.Bitmap readPixels() {
        return null;
    }
    
    public final void release() {
    }
}