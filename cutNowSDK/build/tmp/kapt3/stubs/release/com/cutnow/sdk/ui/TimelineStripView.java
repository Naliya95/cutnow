package com.cutnow.sdk.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0014J,\u0010+\u001a\u00020(2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010-\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\f2\u0006\u0010&\u001a\u00020\u0010R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R0\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u00122\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0012@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R$\u0010 \u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0010@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/cutnow/sdk/ui/TimelineStripView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "clipPath", "Landroid/graphics/Path;", "cornerRadius", "", "dstRect", "Landroid/graphics/Rect;", "frameIntervalMs", "", "frames", "", "Landroid/graphics/Bitmap;", "keyframeBorderPaint", "Landroid/graphics/Paint;", "keyframePaint", "value", "Lcom/cutnow/sdk/model/Keyframe;", "keyframes", "getKeyframes", "()Ljava/util/List;", "setKeyframes", "(Ljava/util/List;)V", "paint", "pixelsPerSecond", "segmentStartTimeMs", "getSegmentStartTimeMs", "()J", "setSegmentStartTimeMs", "(J)V", "srcRect", "trimStartMs", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "updateData", "bitmaps", "intervalMs", "cutNowSDK_release"})
public final class TimelineStripView extends android.view.View {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<android.graphics.Bitmap> frames;
    private long frameIntervalMs = 1000L;
    private float pixelsPerSecond = 100.0F;
    private long trimStartMs = 0L;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.cutnow.sdk.model.Keyframe> keyframes;
    private long segmentStartTimeMs = 0L;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint paint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint keyframePaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint keyframeBorderPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Rect srcRect = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Rect dstRect = null;
    private float cornerRadius;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Path clipPath = null;
    
    @kotlin.jvm.JvmOverloads()
    public TimelineStripView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cutnow.sdk.model.Keyframe> getKeyframes() {
        return null;
    }
    
    public final void setKeyframes(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Keyframe> value) {
    }
    
    public final long getSegmentStartTimeMs() {
        return 0L;
    }
    
    public final void setSegmentStartTimeMs(long value) {
    }
    
    public final void updateData(@org.jetbrains.annotations.NotNull()
    java.util.List<android.graphics.Bitmap> bitmaps, long intervalMs, float pixelsPerSecond, long trimStartMs) {
    }
    
    @java.lang.Override()
    protected void onDraw(@org.jetbrains.annotations.NotNull()
    android.graphics.Canvas canvas) {
    }
    
    @kotlin.jvm.JvmOverloads()
    public TimelineStripView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public TimelineStripView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
}