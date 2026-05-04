package com.cutnow.sdk.engine;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001/B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J&\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\fJ\u000e\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u0014J\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u000e\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u0014J&\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u0012J\b\u0010\"\u001a\u00020\u000eH\u0002J\u0006\u0010#\u001a\u00020\u000eJ\u0010\u0010$\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\u001cH\u0002J\u0010\u0010&\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\u001cH\u0002J\u001e\u0010\'\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010(\u001a\u00020\u0014J\u0006\u0010)\u001a\u00020\u000eJ\u001e\u0010*\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010+\u001a\u00020\u0016J\u001e\u0010,\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010-\u001a\u00020.R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00060"}, d2 = {"Lcom/cutnow/sdk/engine/TimelineEngine;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "<set-?>", "Lcom/cutnow/sdk/model/TimelineProject;", "currentProject", "getCurrentProject", "()Lcom/cutnow/sdk/model/TimelineProject;", "stateListeners", "", "Lcom/cutnow/sdk/engine/TimelineEngine$ITimelineStateListener;", "addClip", "", "trackType", "Lcom/cutnow/sdk/model/TrackType;", "trackIndex", "", "filePath", "", "durationMs", "", "addStateListener", "listener", "createNewProject", "name", "getTrack", "Lcom/cutnow/sdk/model/Track;", "loadProject", "projectId", "moveClip", "fromIndex", "toIndex", "notifyProjectLoaded", "notifyTimelineUpdated", "notifyTrackUpdated", "track", "recalculateTrackTiming", "removeClip", "clipId", "saveProject", "splitClip", "splitTimeMs", "updateClip", "updatedClip", "Lcom/cutnow/sdk/model/Clip;", "ITimelineStateListener", "cutNowSDK_debug"})
public final class TimelineEngine {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.TimelineProject currentProject;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cutnow.sdk.engine.TimelineEngine.ITimelineStateListener> stateListeners = null;
    
    public TimelineEngine(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TimelineProject getCurrentProject() {
        return null;
    }
    
    public final void addStateListener(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.engine.TimelineEngine.ITimelineStateListener listener) {
    }
    
    public final void createNewProject(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void loadProject(@org.jetbrains.annotations.NotNull()
    java.lang.String projectId) {
    }
    
    public final void saveProject() {
    }
    
    public final void removeClip(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TrackType trackType, int trackIndex, @org.jetbrains.annotations.NotNull()
    java.lang.String clipId) {
    }
    
    public final void moveClip(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TrackType trackType, int trackIndex, int fromIndex, int toIndex) {
    }
    
    public final void updateClip(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TrackType trackType, int trackIndex, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.Clip updatedClip) {
    }
    
    private final com.cutnow.sdk.model.Track getTrack(com.cutnow.sdk.model.TrackType trackType, int trackIndex) {
        return null;
    }
    
    private final void recalculateTrackTiming(com.cutnow.sdk.model.Track track) {
    }
    
    public final void splitClip(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TrackType trackType, int trackIndex, long splitTimeMs) {
    }
    
    public final void addClip(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TrackType trackType, int trackIndex, @org.jetbrains.annotations.NotNull()
    java.lang.String filePath, long durationMs) {
    }
    
    private final void notifyProjectLoaded() {
    }
    
    private final void notifyTrackUpdated(com.cutnow.sdk.model.Track track) {
    }
    
    public final void notifyTimelineUpdated() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&\u00a8\u0006\n"}, d2 = {"Lcom/cutnow/sdk/engine/TimelineEngine$ITimelineStateListener;", "", "onProjectLoaded", "", "project", "Lcom/cutnow/sdk/model/TimelineProject;", "onTimelineUpdated", "onTrackUpdated", "track", "Lcom/cutnow/sdk/model/Track;", "cutNowSDK_debug"})
    public static abstract interface ITimelineStateListener {
        
        public abstract void onProjectLoaded(@org.jetbrains.annotations.NotNull()
        com.cutnow.sdk.model.TimelineProject project);
        
        public abstract void onTrackUpdated(@org.jetbrains.annotations.NotNull()
        com.cutnow.sdk.model.Track track);
        
        public abstract void onTimelineUpdated();
    }
}