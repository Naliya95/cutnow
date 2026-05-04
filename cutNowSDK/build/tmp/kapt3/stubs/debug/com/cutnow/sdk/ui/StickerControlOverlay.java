package com.cutnow.sdk.ui;

/**
 * Overlay view that provides interaction handles (Reset, Rotate, Scale, Flip)
 * for a selected sticker/text layer.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0002PQB%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u00106\u001a\u000207J(\u00108\u001a\u00020\u00122\u0006\u00109\u001a\u00020\u00122\u0006\u0010:\u001a\u00020\u00122\u0006\u0010;\u001a\u00020\u00122\u0006\u0010<\u001a\u00020\u0012H\u0002J2\u0010=\u001a\u0002072\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020\u00122\u0006\u0010A\u001a\u00020\u00122\b\u0010B\u001a\u0004\u0018\u00010\u00102\u0006\u0010C\u001a\u00020\nH\u0002J\u0006\u0010D\u001a\u00020\nJ\u0018\u0010E\u001a\u00020\n2\u0006\u0010@\u001a\u00020\u00122\u0006\u0010A\u001a\u00020\u0012H\u0002J\u0006\u0010F\u001a\u00020$J\u0016\u0010G\u001a\u00020$2\u0006\u0010@\u001a\u00020\u00122\u0006\u0010A\u001a\u00020\u0012J\u0010\u0010H\u001a\u0002072\u0006\u0010>\u001a\u00020?H\u0014J\u0010\u0010I\u001a\u00020$2\u0006\u0010J\u001a\u00020KH\u0017J\"\u0010L\u001a\u0002072\b\u0010M\u001a\u0004\u0018\u00010\"2\u0006\u0010N\u001a\u00020\u00122\b\b\u0002\u0010O\u001a\u00020\u000eR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0012X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0010\u0010 \u001a\u0004\u0018\u00010\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010#\u001a\u00020$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R\u001a\u0010)\u001a\u00020$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010&\"\u0004\b+\u0010(R\u001a\u0010,\u001a\u00020$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010&\"\u0004\b.\u0010(R\u001a\u0010/\u001a\u00020$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010&\"\u0004\b1\u0010(R\u000e\u00102\u001a\u000203X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00105\u001a\u0004\u0018\u00010\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006R"}, d2 = {"Lcom/cutnow/sdk/ui/StickerControlOverlay;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "activeHandle", "Lcom/cutnow/sdk/ui/StickerControlOverlay$HandleType;", "borderPaint", "Landroid/graphics/Paint;", "currentTimeMs", "", "deleteIcon", "Landroid/graphics/drawable/Drawable;", "dragSensitivityMultiplier", "", "flipIcon", "gestureDetector", "Landroid/view/GestureDetector;", "handlePaint", "handleRadius", "lastTouchX", "lastTouchY", "listener", "Lcom/cutnow/sdk/ui/StickerControlOverlay$OnTransformationListener;", "getListener", "()Lcom/cutnow/sdk/ui/StickerControlOverlay$OnTransformationListener;", "setListener", "(Lcom/cutnow/sdk/ui/StickerControlOverlay$OnTransformationListener;)V", "rotateIcon", "selectedLayer", "Lcom/cutnow/sdk/model/MediaLayer;", "showDelete", "", "getShowDelete", "()Z", "setShowDelete", "(Z)V", "showFlip", "getShowFlip", "setShowFlip", "showRotate", "getShowRotate", "setShowRotate", "showScale", "getShowScale", "setShowScale", "stickerRect", "Landroid/graphics/RectF;", "videoRatio", "zoomIcon", "cancelInteraction", "", "dist", "x1", "y1", "x2", "y2", "drawHandle", "canvas", "Landroid/graphics/Canvas;", "x", "y", "icon", "type", "getActiveHandle", "getHandleAt", "isHandlingTouch", "isPointInOverlay", "onDraw", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "update", "layer", "ratio", "timeMs", "HandleType", "OnTransformationListener", "cutNowSDK_debug"})
public final class StickerControlOverlay extends android.view.View {
    @org.jetbrains.annotations.Nullable()
    private com.cutnow.sdk.model.MediaLayer selectedLayer;
    private float videoRatio = 1.0F;
    private long currentTimeMs = 0L;
    private boolean showDelete = true;
    private boolean showFlip = true;
    private boolean showRotate = true;
    private boolean showScale = true;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint borderPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint handlePaint = null;
    @org.jetbrains.annotations.Nullable()
    private final android.graphics.drawable.Drawable flipIcon = null;
    @org.jetbrains.annotations.Nullable()
    private final android.graphics.drawable.Drawable rotateIcon = null;
    @org.jetbrains.annotations.Nullable()
    private final android.graphics.drawable.Drawable zoomIcon = null;
    @org.jetbrains.annotations.Nullable()
    private final android.graphics.drawable.Drawable deleteIcon = null;
    private final float handleRadius = 24.0F;
    private final float dragSensitivityMultiplier = 1.0F;
    @org.jetbrains.annotations.NotNull()
    private android.graphics.RectF stickerRect;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.ui.StickerControlOverlay.HandleType activeHandle = com.cutnow.sdk.ui.StickerControlOverlay.HandleType.NONE;
    private float lastTouchX = 0.0F;
    private float lastTouchY = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final android.view.GestureDetector gestureDetector = null;
    @org.jetbrains.annotations.Nullable()
    private com.cutnow.sdk.ui.StickerControlOverlay.OnTransformationListener listener;
    
    @kotlin.jvm.JvmOverloads()
    public StickerControlOverlay(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    public final boolean getShowDelete() {
        return false;
    }
    
    public final void setShowDelete(boolean p0) {
    }
    
    public final boolean getShowFlip() {
        return false;
    }
    
    public final void setShowFlip(boolean p0) {
    }
    
    public final boolean getShowRotate() {
        return false;
    }
    
    public final void setShowRotate(boolean p0) {
    }
    
    public final boolean getShowScale() {
        return false;
    }
    
    public final void setShowScale(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.cutnow.sdk.ui.StickerControlOverlay.OnTransformationListener getListener() {
        return null;
    }
    
    public final void setListener(@org.jetbrains.annotations.Nullable()
    com.cutnow.sdk.ui.StickerControlOverlay.OnTransformationListener p0) {
    }
    
    public final void update(@org.jetbrains.annotations.Nullable()
    com.cutnow.sdk.model.MediaLayer layer, float ratio, long timeMs) {
    }
    
    public final boolean isHandlingTouch() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.ui.StickerControlOverlay.HandleType getActiveHandle() {
        return null;
    }
    
    public final void cancelInteraction() {
    }
    
    public final boolean isPointInOverlay(float x, float y) {
        return false;
    }
    
    @java.lang.Override()
    protected void onDraw(@org.jetbrains.annotations.NotNull()
    android.graphics.Canvas canvas) {
    }
    
    private final void drawHandle(android.graphics.Canvas canvas, float x, float y, android.graphics.drawable.Drawable icon, com.cutnow.sdk.ui.StickerControlOverlay.HandleType type) {
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"ClickableViewAccessibility"})
    public boolean onTouchEvent(@org.jetbrains.annotations.NotNull()
    android.view.MotionEvent event) {
        return false;
    }
    
    private final com.cutnow.sdk.ui.StickerControlOverlay.HandleType getHandleAt(float x, float y) {
        return null;
    }
    
    private final float dist(float x1, float y1, float x2, float y2) {
        return 0.0F;
    }
    
    @kotlin.jvm.JvmOverloads()
    public StickerControlOverlay(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public StickerControlOverlay(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2 = {"Lcom/cutnow/sdk/ui/StickerControlOverlay$HandleType;", "", "(Ljava/lang/String;I)V", "NONE", "BODY", "FLIP", "ROTATE", "ZOOM", "DELETE", "cutNowSDK_debug"})
    public static enum HandleType {
        /*public static final*/ NONE /* = new NONE() */,
        /*public static final*/ BODY /* = new BODY() */,
        /*public static final*/ FLIP /* = new FLIP() */,
        /*public static final*/ ROTATE /* = new ROTATE() */,
        /*public static final*/ ZOOM /* = new ZOOM() */,
        /*public static final*/ DELETE /* = new DELETE() */;
        
        HandleType() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.cutnow.sdk.ui.StickerControlOverlay.HandleType> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\u0003H&J\b\u0010\b\u001a\u00020\u0003H&J\u0018\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH&J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000bH&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000bH&\u00a8\u0006\u0011"}, d2 = {"Lcom/cutnow/sdk/ui/StickerControlOverlay$OnTransformationListener;", "", "onCommit", "", "onDelete", "onDoubleTap", "onFlip", "onGestureStart", "onLongPress", "onMove", "dx", "", "dy", "onRotate", "deltaAngle", "onScale", "scaleFactor", "cutNowSDK_debug"})
    public static abstract interface OnTransformationListener {
        
        public abstract void onGestureStart();
        
        public abstract void onMove(float dx, float dy);
        
        public abstract void onRotate(float deltaAngle);
        
        public abstract void onScale(float scaleFactor);
        
        public abstract void onFlip();
        
        public abstract void onDelete();
        
        public abstract void onDoubleTap();
        
        public abstract void onLongPress();
        
        public abstract void onCommit();
    }
}