package com.cutnow.sdk.engine;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u001f\u0010\b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u00a2\u0006\u0002\u0010\rJ,\u0010\u000e\u001a\u00020\u00042\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fJ \u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a8\u0006\u0017"}, d2 = {"Lcom/cutnow/sdk/engine/KeyframeEngine;", "", "()V", "applyEasing", "", "t", "easeType", "", "getPropertyValue", "keyframe", "Lcom/cutnow/sdk/model/Keyframe;", "property", "Lcom/cutnow/sdk/model/KeyframeProperty;", "(Lcom/cutnow/sdk/model/Keyframe;Lcom/cutnow/sdk/model/KeyframeProperty;)Ljava/lang/Float;", "interpolate", "keyframes", "", "timeMs", "", "baseValue", "interpolateRotation", "v1", "v2", "cutNowSDK_debug"})
public final class KeyframeEngine {
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.engine.KeyframeEngine INSTANCE = null;
    
    private KeyframeEngine() {
        super();
    }
    
    /**
     * Interpolates a property value at a specific time based on a list of keyframes.
     * If no keyframes exist for the property, returns the baseValue.
     */
    public final float interpolate(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Keyframe> keyframes, long timeMs, float baseValue, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.KeyframeProperty property) {
        return 0.0F;
    }
    
    private final float interpolateRotation(float v1, float v2, float t) {
        return 0.0F;
    }
    
    private final java.lang.Float getPropertyValue(com.cutnow.sdk.model.Keyframe keyframe, com.cutnow.sdk.model.KeyframeProperty property) {
        return null;
    }
    
    private final float applyEasing(float t, java.lang.String easeType) {
        return 0.0F;
    }
}