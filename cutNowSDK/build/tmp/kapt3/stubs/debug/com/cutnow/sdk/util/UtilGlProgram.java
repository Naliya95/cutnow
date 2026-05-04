package com.cutnow.sdk.util;

/**
 * Custom GL Program wrapper for the SDK.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\fJ\u0018\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0003H\u0002J\u0016\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0016J.\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u0016J\u0016\u0010\u001c\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0007J\u0016\u0010\u001d\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u0014J(\u0010\u001f\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u00072\b\b\u0002\u0010\"\u001a\u00020#J\u0006\u0010$\u001a\u00020\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006%"}, d2 = {"Lcom/cutnow/sdk/util/UtilGlProgram;", "", "vertexShaderCode", "", "fragmentShaderCode", "(Ljava/lang/String;Ljava/lang/String;)V", "_programId", "", "programId", "getProgramId", "()I", "bindAttributesAndUniforms", "", "delete", "loadShader", "type", "shaderCode", "setFloatArrayUniform", "name", "value", "", "setFloatUniform", "", "setFloatVec4Uniform", "x", "y", "z", "w", "setIntUniform", "setMatrixUniform", "matrix", "setSamplerTexIdUniform", "texId", "unit", "isOes", "", "use", "cutNowSDK_debug"})
public final class UtilGlProgram {
    private int _programId = 0;
    
    public UtilGlProgram(@org.jetbrains.annotations.NotNull()
    java.lang.String vertexShaderCode, @org.jetbrains.annotations.NotNull()
    java.lang.String fragmentShaderCode) {
        super();
    }
    
    public final int getProgramId() {
        return 0;
    }
    
    public final void use() {
    }
    
    public final void delete() {
    }
    
    public final void setSamplerTexIdUniform(@org.jetbrains.annotations.NotNull()
    java.lang.String name, int texId, int unit, boolean isOes) {
    }
    
    public final void setFloatUniform(@org.jetbrains.annotations.NotNull()
    java.lang.String name, float value) {
    }
    
    public final void setFloatVec4Uniform(@org.jetbrains.annotations.NotNull()
    java.lang.String name, float x, float y, float z, float w) {
    }
    
    public final void setIntUniform(@org.jetbrains.annotations.NotNull()
    java.lang.String name, int value) {
    }
    
    public final void setMatrixUniform(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    float[] matrix) {
    }
    
    public final void setFloatArrayUniform(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    float[] value) {
    }
    
    public final void bindAttributesAndUniforms() {
    }
    
    private final int loadShader(int type, java.lang.String shaderCode) {
        return 0;
    }
}