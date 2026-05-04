package com.cutnow.sdk.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0010\t\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0007H\u0002J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0014J\u0010\u0010/\u001a\u00020\n2\u0006\u00100\u001a\u00020\u001bH\u0002R$\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u0010\u0012\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\r\"\u0004\b\u0014\u0010\u000fR$\u0010\u0015\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R0\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R$\u0010!\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\r\"\u0004\b#\u0010\u000fR\u000e\u0010$\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u0010%\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\r\"\u0004\b\'\u0010\u000f\u00a8\u00061"}, d2 = {"Lcom/cutnow/sdk/ui/TimeRulerView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "value", "", "leadingOffsetPx", "getLeadingOffsetPx", "()F", "setLeadingOffsetPx", "(F)V", "linePaint", "Landroid/graphics/Paint;", "pixelsPerSecond", "getPixelsPerSecond", "setPixelsPerSecond", "scrollXOffset", "getScrollXOffset", "()I", "setScrollXOffset", "(I)V", "", "", "segmentDurations", "getSegmentDurations", "()Ljava/util/List;", "setSegmentDurations", "(Ljava/util/List;)V", "startPadding", "getStartPadding", "setStartPadding", "textPaint", "transitionWidthPx", "getTransitionWidthPx", "setTransitionWidthPx", "formatTime", "", "seconds", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "timeToTimelineX", "timeMs", "cutNowSDK_debug"})
public final class TimeRulerView extends android.view.View {
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint linePaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint textPaint = null;
    private float pixelsPerSecond = 100.0F;
    private int scrollXOffset = 0;
    private float startPadding = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.lang.Long> segmentDurations;
    private float transitionWidthPx = 0.0F;
    private float leadingOffsetPx = 0.0F;
    
    @kotlin.jvm.JvmOverloads()
    public TimeRulerView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    public final float getPixelsPerSecond() {
        return 0.0F;
    }
    
    public final void setPixelsPerSecond(float value) {
    }
    
    public final int getScrollXOffset() {
        return 0;
    }
    
    public final void setScrollXOffset(int value) {
    }
    
    public final float getStartPadding() {
        return 0.0F;
    }
    
    public final void setStartPadding(float value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Long> getSegmentDurations() {
        return null;
    }
    
    public final void setSegmentDurations(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Long> value) {
    }
    
    public final float getTransitionWidthPx() {
        return 0.0F;
    }
    
    public final void setTransitionWidthPx(float value) {
    }
    
    public final float getLeadingOffsetPx() {
        return 0.0F;
    }
    
    public final void setLeadingOffsetPx(float value) {
    }
    
    private final float timeToTimelineX(long timeMs) {
        return 0.0F;
    }
    
    @java.lang.Override()
    protected void onDraw(@org.jetbrains.annotations.NotNull()
    android.graphics.Canvas canvas) {
    }
    
    private final java.lang.String formatTime(int seconds) {
        return null;
    }
    
    @kotlin.jvm.JvmOverloads()
    public TimeRulerView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public TimeRulerView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
}