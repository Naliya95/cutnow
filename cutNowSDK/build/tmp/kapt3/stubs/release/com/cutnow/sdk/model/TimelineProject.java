package com.cutnow.sdk.model;

/**
 * Root data structure for a video editing project.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\b\u0018\u0000 ?2\u00020\u0001:\u0001?B\u007f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0012J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0011H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0007H\u00c6\u0003J\t\u0010/\u001a\u00020\u0007H\u00c6\u0003J\u000f\u00100\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\u000f\u00101\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\u000f\u00102\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\u0010\u00103\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\u0088\u0001\u00104\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u00c6\u0001\u00a2\u0006\u0002\u00105J\u0013\u00106\u001a\u0002072\b\u00108\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u00109\u001a\u00020\u0007J\t\u0010:\u001a\u00020;H\u00d6\u0001J\u0006\u0010<\u001a\u00020=J\t\u0010>\u001a\u00020\u0003H\u00d6\u0001R\u001e\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0017\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001d\"\u0004\b!\u0010\"R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001f\"\u0004\b$\u0010%R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0019R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u001f\"\u0004\b(\u0010%R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0019\u00a8\u0006@"}, d2 = {"Lcom/cutnow/sdk/model/TimelineProject;", "", "id", "", "name", "thumbnailPath", "creationDate", "", "lastModified", "videoTracks", "", "Lcom/cutnow/sdk/model/Track;", "audioTracks", "overlayTracks", "aspectRatio", "", "canvas", "Lcom/cutnow/sdk/model/CanvasData;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJLjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/Float;Lcom/cutnow/sdk/model/CanvasData;)V", "getAspectRatio", "()Ljava/lang/Float;", "setAspectRatio", "(Ljava/lang/Float;)V", "Ljava/lang/Float;", "getAudioTracks", "()Ljava/util/List;", "getCanvas", "()Lcom/cutnow/sdk/model/CanvasData;", "getCreationDate", "()J", "getId", "()Ljava/lang/String;", "getLastModified", "setLastModified", "(J)V", "getName", "setName", "(Ljava/lang/String;)V", "getOverlayTracks", "getThumbnailPath", "setThumbnailPath", "getVideoTracks", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJLjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/Float;Lcom/cutnow/sdk/model/CanvasData;)Lcom/cutnow/sdk/model/TimelineProject;", "equals", "", "other", "getTotalTimelineDuration", "hashCode", "", "toJson", "Lorg/json/JSONObject;", "toString", "Companion", "cutNowSDK_release"})
public final class TimelineProject {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String name;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String thumbnailPath;
    private final long creationDate = 0L;
    private long lastModified;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cutnow.sdk.model.Track> videoTracks = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cutnow.sdk.model.Track> audioTracks = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cutnow.sdk.model.Track> overlayTracks = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Float aspectRatio;
    @org.jetbrains.annotations.NotNull()
    private final com.cutnow.sdk.model.CanvasData canvas = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.model.TimelineProject.Companion Companion = null;
    
    public TimelineProject(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String thumbnailPath, long creationDate, long lastModified, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Track> videoTracks, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Track> audioTracks, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Track> overlayTracks, @org.jetbrains.annotations.Nullable()
    java.lang.Float aspectRatio, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.CanvasData canvas) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getThumbnailPath() {
        return null;
    }
    
    public final void setThumbnailPath(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final long getCreationDate() {
        return 0L;
    }
    
    public final long getLastModified() {
        return 0L;
    }
    
    public final void setLastModified(long p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cutnow.sdk.model.Track> getVideoTracks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cutnow.sdk.model.Track> getAudioTracks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cutnow.sdk.model.Track> getOverlayTracks() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getAspectRatio() {
        return null;
    }
    
    public final void setAspectRatio(@org.jetbrains.annotations.Nullable()
    java.lang.Float p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.CanvasData getCanvas() {
        return null;
    }
    
    public final long getTotalTimelineDuration() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.json.JSONObject toJson() {
        return null;
    }
    
    public TimelineProject() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.CanvasData component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final long component5() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cutnow.sdk.model.Track> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cutnow.sdk.model.Track> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cutnow.sdk.model.Track> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TimelineProject copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String thumbnailPath, long creationDate, long lastModified, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Track> videoTracks, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Track> audioTracks, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Track> overlayTracks, @org.jetbrains.annotations.Nullable()
    java.lang.Float aspectRatio, @org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/cutnow/sdk/model/TimelineProject$Companion;", "", "()V", "fromJson", "Lcom/cutnow/sdk/model/TimelineProject;", "json", "Lorg/json/JSONObject;", "cutNowSDK_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cutnow.sdk.model.TimelineProject fromJson(@org.jetbrains.annotations.NotNull()
        org.json.JSONObject json) {
            return null;
        }
    }
}