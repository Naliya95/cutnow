package com.cutnow.sdk.engine;

/**
 * Generates preview thumbnails for video effects via GPU (OpenGL ES).
 *
 * Delegates to [FilterGlPreviewRenderer] which renders [preview_img] through
 * each effect's GLSL shader off-screen and caches the resulting [Bitmap].
 * Falls back to the CPU path automatically if EGL is unavailable.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J2\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f\u00a8\u0006\u000f"}, d2 = {"Lcom/cutnow/sdk/engine/EffectPreviewGenerator;", "", "()V", "loadAsync", "Lkotlinx/coroutines/Job;", "context", "Landroid/content/Context;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "effectType", "Lcom/cutnow/sdk/model/EffectType;", "onReady", "Lkotlin/Function1;", "Landroid/graphics/Bitmap;", "", "cutNowSDK_release"})
public final class EffectPreviewGenerator {
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.engine.EffectPreviewGenerator INSTANCE = null;
    
    private EffectPreviewGenerator() {
        super();
    }
    
    /**
     * Start an async load of the effect preview bitmap and deliver it via [onReady].
     * Returns the [Job] so the caller can cancel on rebind.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job loadAsync(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.EffectType effectType, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super android.graphics.Bitmap, kotlin.Unit> onReady) {
        return null;
    }
}