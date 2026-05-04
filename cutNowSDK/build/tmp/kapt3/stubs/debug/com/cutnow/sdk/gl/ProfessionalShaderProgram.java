package com.cutnow.sdk.gl;

/**
 * Shader program that handles frame-by-frame rendering with custom GLSL.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\tH\u0016J\b\u0010\u0019\u001a\u00020\u0016H\u0016R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/cutnow/sdk/gl/ProfessionalShaderProgram;", "Landroidx/media3/effect/BaseGlShaderProgram;", "context", "Landroid/content/Context;", "effectType", "Lcom/cutnow/sdk/model/EffectType;", "intensity", "", "startTimeUs", "", "durationUs", "useHdr", "", "(Landroid/content/Context;Lcom/cutnow/sdk/model/EffectType;FJJZ)V", "program", "Lcom/cutnow/sdk/util/UtilGlProgram;", "configure", "Landroidx/media3/common/util/Size;", "inputWidth", "", "inputHeight", "drawFrame", "", "inputTexId", "presentationTimeUs", "release", "cutNowSDK_debug"})
@androidx.media3.common.util.UnstableApi()
public final class ProfessionalShaderProgram extends androidx.media3.effect.BaseGlShaderProgram {
    @org.jetbrains.annotations.NotNull()
    private final com.cutnow.sdk.model.EffectType effectType = null;
    private final float intensity = 0.0F;
    private final long startTimeUs = 0L;
    private final long durationUs = 0L;
    @org.jetbrains.annotations.NotNull()
    private final com.cutnow.sdk.util.UtilGlProgram program = null;
    
    public ProfessionalShaderProgram(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.EffectType effectType, float intensity, long startTimeUs, long durationUs, boolean useHdr) {
        super(false, 0);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.media3.common.util.Size configure(int inputWidth, int inputHeight) {
        return null;
    }
    
    @java.lang.Override()
    public void drawFrame(int inputTexId, long presentationTimeUs) {
    }
    
    @java.lang.Override()
    public void release() {
    }
}