package com.cutnow.sdk.gl;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\nH\u0002J\u0018\u0010\"\u001a\u00020\u00042\u0006\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\nH\u0002J\u0010\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u0004H\u0002J\u0010\u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020*H\u0002J\b\u0010+\u001a\u00020&H\u0002J\u0010\u0010,\u001a\u00020\u000e2\u0006\u0010)\u001a\u00020*H\u0002J\b\u0010-\u001a\u00020\u000eH\u0002J\u0006\u0010.\u001a\u00020&J\u001e\u0010/\u001a\u00020\u000e2\u0006\u0010)\u001a\u00020*2\u0006\u0010#\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u00100J\u001e\u00101\u001a\u00020\u000e2\u0006\u0010)\u001a\u00020*2\u0006\u0010#\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u00102J\u0018\u00103\u001a\u00020\u000e2\u0006\u0010)\u001a\u00020*2\u0006\u00104\u001a\u00020\nH\u0002J \u00105\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u00042\u0006\u00106\u001a\u00020\n2\u0006\u00107\u001a\u000208H\u0002J\u0010\u00109\u001a\u00020&2\u0006\u0010)\u001a\u00020*H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082T\u00a2\u0006\u0002\n\u0000R*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R*\u0010\u0016\u001a\u001e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u000e0\fj\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006:"}, d2 = {"Lcom/cutnow/sdk/gl/FilterGlPreviewRenderer;", "", "()V", "MAX_CACHE", "", "PREVIEW_SIZE", "QUAD_DATA", "", "STRIDE", "TAG", "", "effectCache", "Ljava/util/LinkedHashMap;", "Lcom/cutnow/sdk/model/EffectType;", "Landroid/graphics/Bitmap;", "Lkotlin/collections/LinkedHashMap;", "eglContext", "Landroid/opengl/EGLContext;", "eglDisplay", "Landroid/opengl/EGLDisplay;", "eglSurface", "Landroid/opengl/EGLSurface;", "filterCache", "Lcom/cutnow/sdk/model/FilterType;", "glMutex", "Lkotlinx/coroutines/sync/Mutex;", "initFailed", "", "initialized", "previewTexId", "quadVbo", "compileProgram", "vertSrc", "fragSrc", "compileShader", "type", "src", "drawQuad", "", "program", "ensureInitialized", "context", "Landroid/content/Context;", "initEgl", "loadPreviewImageBitmap", "readPixels", "release", "renderEffect", "(Landroid/content/Context;Lcom/cutnow/sdk/model/EffectType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "renderFilter", "(Landroid/content/Context;Lcom/cutnow/sdk/model/FilterType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "renderWithShader", "fragmentSrc", "setUniformIfPresent", "name", "value", "", "uploadPreviewTexture", "cutNowSDK_release"})
public final class FilterGlPreviewRenderer {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "FilterGlPreview";
    private static final int PREVIEW_SIZE = 120;
    private static final int MAX_CACHE = 40;
    @org.jetbrains.annotations.NotNull()
    private static android.opengl.EGLDisplay eglDisplay;
    @org.jetbrains.annotations.NotNull()
    private static android.opengl.EGLContext eglContext;
    @org.jetbrains.annotations.NotNull()
    private static android.opengl.EGLSurface eglSurface;
    private static boolean initialized = false;
    private static boolean initFailed = false;
    private static int previewTexId = 0;
    private static int quadVbo = 0;
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.sync.Mutex glMutex = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.LinkedHashMap<com.cutnow.sdk.model.FilterType, android.graphics.Bitmap> filterCache = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.LinkedHashMap<com.cutnow.sdk.model.EffectType, android.graphics.Bitmap> effectCache = null;
    
    /**
     * Full-screen quad: positions in NDC (-1..1), UVs (0..1) interleaved [x, y, u, v].
     *
     * V is flipped (1 at bottom, 0 at top) because Android Bitmap row-0 (visual top)
     * is uploaded by texImage2D to GL V=0 (texture bottom). Flipping V here corrects
     * the image orientation so it renders right-side-up before readPixels flips it back.
     */
    @org.jetbrains.annotations.NotNull()
    private static final float[] QUAD_DATA = {-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, -1.0F, 1.0F, 1.0F, -1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F};
    private static final int STRIDE = 16;
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.gl.FilterGlPreviewRenderer INSTANCE = null;
    
    private FilterGlPreviewRenderer() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object renderFilter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.FilterType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super android.graphics.Bitmap> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object renderEffect(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.EffectType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super android.graphics.Bitmap> $completion) {
        return null;
    }
    
    public final void release() {
    }
    
    /**
     * Core render path: make context current, compile shader, draw quad,
     * read pixels back as a Bitmap.
     * Must be called while [glMutex] is held and on [Dispatchers.IO].
     */
    private final android.graphics.Bitmap renderWithShader(android.content.Context context, java.lang.String fragmentSrc) {
        return null;
    }
    
    private final void ensureInitialized(android.content.Context context) {
    }
    
    private final void initEgl() {
    }
    
    private final void uploadPreviewTexture(android.content.Context context) {
    }
    
    private final android.graphics.Bitmap loadPreviewImageBitmap(android.content.Context context) {
        return null;
    }
    
    private final int compileProgram(java.lang.String vertSrc, java.lang.String fragSrc) {
        return 0;
    }
    
    private final int compileShader(int type, java.lang.String src) {
        return 0;
    }
    
    private final void setUniformIfPresent(int program, java.lang.String name, float value) {
    }
    
    private final void drawQuad(int program) {
    }
    
    private final android.graphics.Bitmap readPixels() {
        return null;
    }
}