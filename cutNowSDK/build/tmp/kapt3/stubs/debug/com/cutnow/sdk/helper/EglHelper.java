package com.cutnow.sdk.helper;

/**
 * Helper class for managing EGL context for offscreen rendering.
 * Used by GlVideoExporter to render frames without a display surface.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\fJ\u000e\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010J\u001e\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015J\u0006\u0010\u0017\u001a\u00020\u0018R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/cutnow/sdk/helper/EglHelper;", "", "()V", "eglConfig", "Landroid/opengl/EGLConfig;", "eglContext", "Landroid/opengl/EGLContext;", "eglDisplay", "Landroid/opengl/EGLDisplay;", "eglSurface", "Landroid/opengl/EGLSurface;", "makeCurrent", "", "release", "setPresentationTime", "nsecs", "", "setupEglContext", "encoderSurface", "Landroid/view/Surface;", "width", "", "height", "swapBuffers", "", "Companion", "cutNowSDK_debug"})
public final class EglHelper {
    @org.jetbrains.annotations.NotNull()
    private android.opengl.EGLDisplay eglDisplay;
    @org.jetbrains.annotations.NotNull()
    private android.opengl.EGLContext eglContext;
    @org.jetbrains.annotations.NotNull()
    private android.opengl.EGLSurface eglSurface;
    @org.jetbrains.annotations.Nullable()
    private android.opengl.EGLConfig eglConfig;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "EglHelper";
    private static final int EGL_RECORDABLE_ANDROID = 12610;
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.helper.EglHelper.Companion Companion = null;
    
    public EglHelper() {
        super();
    }
    
    /**
     * Initialize EGL with an encoder surface (for MediaCodec input).
     */
    public final void setupEglContext(@org.jetbrains.annotations.NotNull()
    android.view.Surface encoderSurface, int width, int height) {
    }
    
    /**
     * Make this EGL context current for the calling thread.
     */
    public final void makeCurrent() {
    }
    
    /**
     * Swap buffers to present the rendered frame.
     */
    public final boolean swapBuffers() {
        return false;
    }
    
    /**
     * Set presentation timestamp for the current frame.
     */
    public final void setPresentationTime(long nsecs) {
    }
    
    /**
     * Release EGL resources.
     */
    public final void release() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/cutnow/sdk/helper/EglHelper$Companion;", "", "()V", "EGL_RECORDABLE_ANDROID", "", "TAG", "", "cutNowSDK_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}