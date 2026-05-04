package com.cutnow.sdk.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \'2\u00020\u0001:\u0001\'B3\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J7\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020\u0007H\u00d6\u0001J\u0006\u0010$\u001a\u00020%J\t\u0010&\u001a\u00020%H\u00d6\u0001R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R \u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\u00a8\u0006("}, d2 = {"Lcom/cutnow/sdk/model/CanvasData;", "", "type", "Lcom/cutnow/sdk/model/CanvasType;", "blur", "", "color", "", "gradientColors", "", "(Lcom/cutnow/sdk/model/CanvasType;FILjava/util/List;)V", "getBlur", "()F", "setBlur", "(F)V", "getColor", "()I", "setColor", "(I)V", "getGradientColors", "()Ljava/util/List;", "setGradientColors", "(Ljava/util/List;)V", "getType", "()Lcom/cutnow/sdk/model/CanvasType;", "setType", "(Lcom/cutnow/sdk/model/CanvasType;)V", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toJson", "", "toString", "Companion", "cutNowSDK_debug"})
public final class CanvasData {
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.CanvasType type;
    private float blur;
    private int color;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.lang.Integer> gradientColors;
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.model.CanvasData.Companion Companion = null;
    
    public CanvasData(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.CanvasType type, float blur, int color, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> gradientColors) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.CanvasType getType() {
        return null;
    }
    
    public final void setType(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.CanvasType p0) {
    }
    
    public final float getBlur() {
        return 0.0F;
    }
    
    public final void setBlur(float p0) {
    }
    
    public final int getColor() {
        return 0;
    }
    
    public final void setColor(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getGradientColors() {
        return null;
    }
    
    public final void setGradientColors(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String toJson() {
        return null;
    }
    
    public CanvasData() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.CanvasType component1() {
        return null;
    }
    
    public final float component2() {
        return 0.0F;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.CanvasData copy(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.CanvasType type, float blur, int color, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> gradientColors) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/cutnow/sdk/model/CanvasData$Companion;", "", "()V", "fromJson", "Lcom/cutnow/sdk/model/CanvasData;", "jsonStr", "", "cutNowSDK_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cutnow.sdk.model.CanvasData fromJson(@org.jetbrains.annotations.NotNull()
        java.lang.String jsonStr) {
            return null;
        }
    }
}