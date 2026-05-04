package com.cutnow.sdk.model;

/**
 * Legacy/UI-level representation of a video clip.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\bZ\b\u0086\b\u0018\u00002\u00020\u0001B\u00db\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0011\u0012\u000e\b\u0002\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e\u0012\b\b\u0002\u0010 \u001a\u00020!\u0012\b\b\u0002\u0010\"\u001a\u00020#\u00a2\u0006\u0002\u0010$J\t\u0010c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010d\u001a\u00020\u0011H\u00c6\u0003J\t\u0010e\u001a\u00020\u0014H\u00c6\u0003J\t\u0010f\u001a\u00020\u0016H\u00c6\u0003J\t\u0010g\u001a\u00020\u0007H\u00c6\u0003J\t\u0010h\u001a\u00020\u000bH\u00c6\u0003J\t\u0010i\u001a\u00020\u000bH\u00c6\u0003J\t\u0010j\u001a\u00020\u000bH\u00c6\u0003J\t\u0010k\u001a\u00020\u000bH\u00c6\u0003J\t\u0010l\u001a\u00020\u0011H\u00c6\u0003J\u000f\u0010m\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u00c6\u0003J\t\u0010n\u001a\u00020\u0005H\u00c6\u0003J\t\u0010o\u001a\u00020!H\u00c6\u0003J\t\u0010p\u001a\u00020#H\u00c6\u0003J\t\u0010q\u001a\u00020\u0007H\u00c6\u0003J\t\u0010r\u001a\u00020\u0007H\u00c6\u0003J\t\u0010s\u001a\u00020\u0007H\u00c6\u0003J\t\u0010t\u001a\u00020\u000bH\u00c6\u0003J\t\u0010u\u001a\u00020\rH\u00c6\u0003J\t\u0010v\u001a\u00020\u000fH\u00c6\u0003J\t\u0010w\u001a\u00020\u0011H\u00c6\u0003J\u00e1\u0001\u0010x\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00072\b\b\u0002\u0010\u0018\u001a\u00020\u000b2\b\b\u0002\u0010\u0019\u001a\u00020\u000b2\b\b\u0002\u0010\u001a\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u000b2\b\b\u0002\u0010\u001c\u001a\u00020\u00112\u000e\b\u0002\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\b\b\u0002\u0010 \u001a\u00020!2\b\b\u0002\u0010\"\u001a\u00020#H\u00c6\u0001J\u0013\u0010y\u001a\u00020\u00112\b\u0010z\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010{\u001a\u00020\u000fH\u00d6\u0001J\t\u0010|\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010 \u001a\u00020!X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R\u001a\u0010\"\u001a\u00020#X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u0011\u00101\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b2\u00103R\u0011\u00104\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b5\u00103R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u001a\u0010\u0012\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010;\"\u0004\b?\u0010=R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u0010AR\u0011\u0010\u001c\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010;R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010CR\u001a\u0010\u001a\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u001a\u0010\u001b\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bH\u0010E\"\u0004\bI\u0010GR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR\u001a\u0010\u0018\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bN\u0010E\"\u0004\bO\u0010GR\u001a\u0010\u0019\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bP\u0010E\"\u0004\bQ\u0010GR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bR\u00103\"\u0004\bS\u0010TR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bU\u0010E\"\u0004\bV\u0010GR\u001a\u0010\u0017\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bW\u00103\"\u0004\bX\u0010TR\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u001a\u0010\t\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b]\u00103\"\u0004\b^\u0010TR\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b_\u00103\"\u0004\b`\u0010TR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\ba\u0010b\u00a8\u0006}"}, d2 = {"Lcom/cutnow/sdk/model/VideoSegment;", "", "id", "", "uri", "Landroid/net/Uri;", "sourceDurationMs", "", "trimStartMs", "trimEndMs", "speed", "", "cropRect", "Lcom/cutnow/sdk/model/CropRect;", "rotation", "", "flipHorizontal", "", "flipVertical", "filterType", "Lcom/cutnow/sdk/model/FilterType;", "transitionType", "Lcom/cutnow/sdk/model/TransitionType;", "transitionDurationMs", "scaleX", "scaleY", "posX", "posY", "isImage", "keyframes", "", "Lcom/cutnow/sdk/model/Keyframe;", "adjustments", "Lcom/cutnow/sdk/model/AdjustmentData;", "canvas", "Lcom/cutnow/sdk/model/CanvasData;", "(Ljava/lang/String;Landroid/net/Uri;JJJFLcom/cutnow/sdk/model/CropRect;IZZLcom/cutnow/sdk/model/FilterType;Lcom/cutnow/sdk/model/TransitionType;JFFFFZLjava/util/List;Lcom/cutnow/sdk/model/AdjustmentData;Lcom/cutnow/sdk/model/CanvasData;)V", "getAdjustments", "()Lcom/cutnow/sdk/model/AdjustmentData;", "setAdjustments", "(Lcom/cutnow/sdk/model/AdjustmentData;)V", "getCanvas", "()Lcom/cutnow/sdk/model/CanvasData;", "setCanvas", "(Lcom/cutnow/sdk/model/CanvasData;)V", "getCropRect", "()Lcom/cutnow/sdk/model/CropRect;", "setCropRect", "(Lcom/cutnow/sdk/model/CropRect;)V", "durationMs", "getDurationMs", "()J", "effectiveDurationMs", "getEffectiveDurationMs", "getFilterType", "()Lcom/cutnow/sdk/model/FilterType;", "setFilterType", "(Lcom/cutnow/sdk/model/FilterType;)V", "getFlipHorizontal", "()Z", "setFlipHorizontal", "(Z)V", "getFlipVertical", "setFlipVertical", "getId", "()Ljava/lang/String;", "getKeyframes", "()Ljava/util/List;", "getPosX", "()F", "setPosX", "(F)V", "getPosY", "setPosY", "getRotation", "()I", "setRotation", "(I)V", "getScaleX", "setScaleX", "getScaleY", "setScaleY", "getSourceDurationMs", "setSourceDurationMs", "(J)V", "getSpeed", "setSpeed", "getTransitionDurationMs", "setTransitionDurationMs", "getTransitionType", "()Lcom/cutnow/sdk/model/TransitionType;", "setTransitionType", "(Lcom/cutnow/sdk/model/TransitionType;)V", "getTrimEndMs", "setTrimEndMs", "getTrimStartMs", "setTrimStartMs", "getUri", "()Landroid/net/Uri;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "cutNowSDK_release"})
public final class VideoSegment {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final android.net.Uri uri = null;
    private long sourceDurationMs;
    private long trimStartMs;
    private long trimEndMs;
    private float speed;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.CropRect cropRect;
    private int rotation;
    private boolean flipHorizontal;
    private boolean flipVertical;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.FilterType filterType;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.TransitionType transitionType;
    private long transitionDurationMs;
    private float scaleX;
    private float scaleY;
    private float posX;
    private float posY;
    private final boolean isImage = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cutnow.sdk.model.Keyframe> keyframes = null;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.AdjustmentData adjustments;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.CanvasData canvas;
    
    public VideoSegment(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri, long sourceDurationMs, long trimStartMs, long trimEndMs, float speed, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.CropRect cropRect, int rotation, boolean flipHorizontal, boolean flipVertical, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.FilterType filterType, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType transitionType, long transitionDurationMs, float scaleX, float scaleY, float posX, float posY, boolean isImage, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Keyframe> keyframes, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.AdjustmentData adjustments, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.CanvasData canvas) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.net.Uri getUri() {
        return null;
    }
    
    public final long getSourceDurationMs() {
        return 0L;
    }
    
    public final void setSourceDurationMs(long p0) {
    }
    
    public final long getTrimStartMs() {
        return 0L;
    }
    
    public final void setTrimStartMs(long p0) {
    }
    
    public final long getTrimEndMs() {
        return 0L;
    }
    
    public final void setTrimEndMs(long p0) {
    }
    
    public final float getSpeed() {
        return 0.0F;
    }
    
    public final void setSpeed(float p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.CropRect getCropRect() {
        return null;
    }
    
    public final void setCropRect(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.CropRect p0) {
    }
    
    public final int getRotation() {
        return 0;
    }
    
    public final void setRotation(int p0) {
    }
    
    public final boolean getFlipHorizontal() {
        return false;
    }
    
    public final void setFlipHorizontal(boolean p0) {
    }
    
    public final boolean getFlipVertical() {
        return false;
    }
    
    public final void setFlipVertical(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.FilterType getFilterType() {
        return null;
    }
    
    public final void setFilterType(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.FilterType p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TransitionType getTransitionType() {
        return null;
    }
    
    public final void setTransitionType(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType p0) {
    }
    
    public final long getTransitionDurationMs() {
        return 0L;
    }
    
    public final void setTransitionDurationMs(long p0) {
    }
    
    public final float getScaleX() {
        return 0.0F;
    }
    
    public final void setScaleX(float p0) {
    }
    
    public final float getScaleY() {
        return 0.0F;
    }
    
    public final void setScaleY(float p0) {
    }
    
    public final float getPosX() {
        return 0.0F;
    }
    
    public final void setPosX(float p0) {
    }
    
    public final float getPosY() {
        return 0.0F;
    }
    
    public final void setPosY(float p0) {
    }
    
    public final boolean isImage() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cutnow.sdk.model.Keyframe> getKeyframes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.AdjustmentData getAdjustments() {
        return null;
    }
    
    public final void setAdjustments(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.AdjustmentData p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.CanvasData getCanvas() {
        return null;
    }
    
    public final void setCanvas(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.CanvasData p0) {
    }
    
    public final long getDurationMs() {
        return 0L;
    }
    
    public final long getEffectiveDurationMs() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.FilterType component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TransitionType component12() {
        return null;
    }
    
    public final long component13() {
        return 0L;
    }
    
    public final float component14() {
        return 0.0F;
    }
    
    public final float component15() {
        return 0.0F;
    }
    
    public final float component16() {
        return 0.0F;
    }
    
    public final float component17() {
        return 0.0F;
    }
    
    public final boolean component18() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cutnow.sdk.model.Keyframe> component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.net.Uri component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.AdjustmentData component20() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.CanvasData component21() {
        return null;
    }
    
    public final long component3() {
        return 0L;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final long component5() {
        return 0L;
    }
    
    public final float component6() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.CropRect component7() {
        return null;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.VideoSegment copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri, long sourceDurationMs, long trimStartMs, long trimEndMs, float speed, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.CropRect cropRect, int rotation, boolean flipHorizontal, boolean flipVertical, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.FilterType filterType, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType transitionType, long transitionDurationMs, float scaleX, float scaleY, float posX, float posY, boolean isImage, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Keyframe> keyframes, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.AdjustmentData adjustments, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.CanvasData canvas) {
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