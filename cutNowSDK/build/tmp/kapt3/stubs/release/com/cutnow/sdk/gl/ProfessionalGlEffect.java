package com.cutnow.sdk.gl;

/**
 * A professional video effect implementation that uses custom GLSL shaders.
 * This provides higher quality than basic matrix transformations.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b\u00a8\u0006\u0017"}, d2 = {"Lcom/cutnow/sdk/gl/ProfessionalGlEffect;", "Landroidx/media3/effect/GlEffect;", "effectType", "Lcom/cutnow/sdk/model/EffectType;", "intensity", "", "startTimeUs", "", "durationUs", "(Lcom/cutnow/sdk/model/EffectType;FJJ)V", "getDurationUs", "()J", "getEffectType", "()Lcom/cutnow/sdk/model/EffectType;", "getIntensity", "()F", "getStartTimeUs", "toGlShaderProgram", "Landroidx/media3/effect/GlShaderProgram;", "context", "Landroid/content/Context;", "useHdr", "", "cutNowSDK_release"})
@androidx.media3.common.util.UnstableApi()
public final class ProfessionalGlEffect implements androidx.media3.effect.GlEffect {
    @org.jetbrains.annotations.NotNull()
    private final com.cutnow.sdk.model.EffectType effectType = null;
    private final float intensity = 0.0F;
    private final long startTimeUs = 0L;
    private final long durationUs = 0L;
    
    public ProfessionalGlEffect(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.EffectType effectType, float intensity, long startTimeUs, long durationUs) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.EffectType getEffectType() {
        return null;
    }
    
    public final float getIntensity() {
        return 0.0F;
    }
    
    public final long getStartTimeUs() {
        return 0L;
    }
    
    public final long getDurationUs() {
        return 0L;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.media3.effect.GlShaderProgram toGlShaderProgram(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean useHdr) {
        return null;
    }
}