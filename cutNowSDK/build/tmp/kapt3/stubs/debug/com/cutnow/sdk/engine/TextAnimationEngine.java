package com.cutnow.sdk.engine;

/**
 * Computes per-frame transforms for animated text layers.
 *
 * This is the single source of truth for text animation timing so that
 * Canvas preview and Media3 export stay perfectly in sync.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0011B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002\u00a8\u0006\u0012"}, d2 = {"Lcom/cutnow/sdk/engine/TextAnimationEngine;", "", "()V", "computeTextTransform", "Lcom/cutnow/sdk/engine/TextAnimationEngine$TextTransform;", "layer", "Lcom/cutnow/sdk/model/MediaLayer;", "timeMs", "", "surfaceHeight", "", "easeOutBack", "", "x", "easeOutBounce", "easeOutCubic", "easeOutElastic", "TextTransform", "cutNowSDK_debug"})
public final class TextAnimationEngine {
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.engine.TextAnimationEngine INSTANCE = null;
    
    private TextAnimationEngine() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.engine.TextAnimationEngine.TextTransform computeTextTransform(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.MediaLayer layer, long timeMs, int surfaceHeight) {
        return null;
    }
    
    private final float easeOutCubic(float x) {
        return 0.0F;
    }
    
    private final float easeOutBack(float x) {
        return 0.0F;
    }
    
    private final float easeOutElastic(float x) {
        return 0.0F;
    }
    
    private final float easeOutBounce(float x) {
        return 0.0F;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0019\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BU\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\nH\u00c6\u0003J\t\u0010 \u001a\u00020\fH\u00c6\u0003JY\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u00c6\u0001J\u0013\u0010\"\u001a\u00020\f2\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010$\u001a\u00020\nH\u00d6\u0001J\t\u0010%\u001a\u00020&H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000f\u00a8\u0006\'"}, d2 = {"Lcom/cutnow/sdk/engine/TextAnimationEngine$TextTransform;", "", "alpha", "", "offsetXPx", "offsetYPx", "scaleX", "scaleY", "rotation", "revealChars", "", "cursorVisible", "", "(FFFFFFIZ)V", "getAlpha", "()F", "getCursorVisible", "()Z", "getOffsetXPx", "getOffsetYPx", "getRevealChars", "()I", "getRotation", "getScaleX", "getScaleY", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "toString", "", "cutNowSDK_debug"})
    public static final class TextTransform {
        private final float alpha = 0.0F;
        private final float offsetXPx = 0.0F;
        private final float offsetYPx = 0.0F;
        private final float scaleX = 0.0F;
        private final float scaleY = 0.0F;
        private final float rotation = 0.0F;
        private final int revealChars = 0;
        private final boolean cursorVisible = false;
        
        public TextTransform(float alpha, float offsetXPx, float offsetYPx, float scaleX, float scaleY, float rotation, int revealChars, boolean cursorVisible) {
            super();
        }
        
        public final float getAlpha() {
            return 0.0F;
        }
        
        public final float getOffsetXPx() {
            return 0.0F;
        }
        
        public final float getOffsetYPx() {
            return 0.0F;
        }
        
        public final float getScaleX() {
            return 0.0F;
        }
        
        public final float getScaleY() {
            return 0.0F;
        }
        
        public final float getRotation() {
            return 0.0F;
        }
        
        public final int getRevealChars() {
            return 0;
        }
        
        public final boolean getCursorVisible() {
            return false;
        }
        
        public TextTransform() {
            super();
        }
        
        public final float component1() {
            return 0.0F;
        }
        
        public final float component2() {
            return 0.0F;
        }
        
        public final float component3() {
            return 0.0F;
        }
        
        public final float component4() {
            return 0.0F;
        }
        
        public final float component5() {
            return 0.0F;
        }
        
        public final float component6() {
            return 0.0F;
        }
        
        public final int component7() {
            return 0;
        }
        
        public final boolean component8() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cutnow.sdk.engine.TextAnimationEngine.TextTransform copy(float alpha, float offsetXPx, float offsetYPx, float scaleX, float scaleY, float rotation, int revealChars, boolean cursorVisible) {
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
    }
}