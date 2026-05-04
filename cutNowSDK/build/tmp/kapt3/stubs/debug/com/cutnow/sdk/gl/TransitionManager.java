package com.cutnow.sdk.gl;

/**
 * Manages GLSL shaders and metadata for programmatic transitions.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/cutnow/sdk/gl/TransitionManager;", "", "()V", "VERTEX_SHADER", "", "getDisplayName", "type", "Lcom/cutnow/sdk/model/TransitionType;", "getShaderBody", "getTransitionShader", "cutNowSDK_debug"})
public final class TransitionManager {
    
    /**
     * Common vertex shader for all transitions.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String VERTEX_SHADER = "\n        attribute vec4 aFramePosition;\n        attribute vec4 aTexSamplingCoord;\n        varying vec2 vTexCoord;\n        void main() {\n            gl_Position = aFramePosition;\n            vTexCoord = aTexSamplingCoord.xy;\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.gl.TransitionManager INSTANCE = null;
    
    private TransitionManager() {
        super();
    }
    
    /**
     * Returns the GLSL fragment shader for a given transition type.
     * The shader expects:
     * - uniform sampler2D uTexCurrent;
     * - uniform sampler2D uTexLastFrame;
     * - uniform float uProgress; [0.0 - 1.0]
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTransitionShader(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType type) {
        return null;
    }
    
    private final java.lang.String getShaderBody(com.cutnow.sdk.model.TransitionType type) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayName(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType type) {
        return null;
    }
}