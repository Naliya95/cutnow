package com.cutnow.sdk.engine;

/**
 * AudioProcessor that:
 * - Applies video volume scaling to the main video audio track
 * - Mixes in external audio sources (background music)
 *
 * ROBUST IMPLEMENTATION:
 * - Uses a FIFO for input buffers to prevent data loss.
 * - Tracks position using PROCESSED frames to avoid jitter.
 * - Supports playback speed for accurate timeline mapping.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0017\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0016\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0018\b\u0007\u0018\u00002\u00020\u0001:\u00016B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0005J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0010\u0010 \u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010!\u001a\u00020\u001fH\u0016J\b\u0010\"\u001a\u00020\u0011H\u0016J\u0010\u0010#\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u0007H\u0002J\b\u0010%\u001a\u00020\u000eH\u0016J\b\u0010&\u001a\u00020\u000eH\u0016J\u0010\u0010\'\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u0011H\u0002J\b\u0010)\u001a\u00020\u001fH\u0016J\u0010\u0010*\u001a\u00020\u001f2\u0006\u0010+\u001a\u00020\u0011H\u0016J\b\u0010,\u001a\u00020\u001fH\u0016J\u000e\u0010-\u001a\u00020\u001f2\u0006\u0010.\u001a\u00020\u0015J\u000e\u0010/\u001a\u00020\u001f2\u0006\u00100\u001a\u00020\u0015J\u000e\u00101\u001a\u00020\u001f2\u0006\u00102\u001a\u00020\u0019J\u000e\u00103\u001a\u00020\u001f2\u0006\u0010.\u001a\u00020\u0015J\u000e\u00104\u001a\u00020\u001f2\u0006\u00105\u001a\u00020\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00067"}, d2 = {"Lcom/cutnow/sdk/engine/AudioMixerProcessor;", "Landroidx/media3/common/audio/AudioProcessor;", "()V", "externalAudioSources", "Ljava/util/concurrent/CopyOnWriteArrayList;", "Lcom/cutnow/sdk/engine/AudioMixerProcessor$ExternalAudioSource;", "externalPositionUs", "", "externalSampleBuffer", "", "framesProcessedSinceLastUpdate", "inputAudioFormat", "Landroidx/media3/common/audio/AudioProcessor$AudioFormat;", "inputEnded", "", "inputQueue", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Ljava/nio/ByteBuffer;", "mixingBuffer", "", "musicVolume", "", "outputBuffer", "playbackSpeed", "segmentBoundariesUs", "", "transitionRampUs", "videoVolume", "addAudioSource", "source", "clearAudioSources", "", "configure", "flush", "getOutput", "getRampFactor", "posUs", "isActive", "isEnded", "mixAudio", "input", "queueEndOfStream", "queueInput", "inputBuffer", "reset", "setMusicVolume", "volume", "setPlaybackSpeed", "speed", "setSegmentBoundaries", "boundariesUs", "setVideoVolume", "updatePlaybackPosition", "positionUs", "ExternalAudioSource", "cutNowSDK_debug"})
@androidx.media3.common.util.UnstableApi()
public final class AudioMixerProcessor implements androidx.media3.common.audio.AudioProcessor {
    @org.jetbrains.annotations.NotNull()
    private androidx.media3.common.audio.AudioProcessor.AudioFormat inputAudioFormat;
    @org.jetbrains.annotations.NotNull()
    private java.nio.ByteBuffer outputBuffer;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentLinkedQueue<java.nio.ByteBuffer> inputQueue = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.CopyOnWriteArrayList<com.cutnow.sdk.engine.AudioMixerProcessor.ExternalAudioSource> externalAudioSources = null;
    @kotlin.jvm.Volatile()
    private volatile long externalPositionUs = 0L;
    @kotlin.jvm.Volatile()
    private volatile float videoVolume = 1.0F;
    @kotlin.jvm.Volatile()
    private volatile float musicVolume = 1.0F;
    @kotlin.jvm.Volatile()
    private volatile float playbackSpeed = 1.0F;
    private long framesProcessedSinceLastUpdate = 0L;
    @org.jetbrains.annotations.NotNull()
    private long[] segmentBoundariesUs;
    private final long transitionRampUs = 50000L;
    @org.jetbrains.annotations.NotNull()
    private short[] externalSampleBuffer;
    @org.jetbrains.annotations.NotNull()
    private int[] mixingBuffer;
    private boolean inputEnded = false;
    
    public AudioMixerProcessor() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.media3.common.audio.AudioProcessor.AudioFormat configure(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.audio.AudioProcessor.AudioFormat inputAudioFormat) {
        return null;
    }
    
    @java.lang.Override()
    public boolean isActive() {
        return false;
    }
    
    @java.lang.Override()
    public void queueInput(@org.jetbrains.annotations.NotNull()
    java.nio.ByteBuffer inputBuffer) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.nio.ByteBuffer getOutput() {
        return null;
    }
    
    @java.lang.Override()
    public void queueEndOfStream() {
    }
    
    @java.lang.Override()
    public boolean isEnded() {
        return false;
    }
    
    @java.lang.Override()
    public void flush() {
    }
    
    @java.lang.Override()
    public void reset() {
    }
    
    private final float getRampFactor(long posUs) {
        return 0.0F;
    }
    
    private final java.nio.ByteBuffer mixAudio(java.nio.ByteBuffer input) {
        return null;
    }
    
    public final void setSegmentBoundaries(@org.jetbrains.annotations.NotNull()
    long[] boundariesUs) {
    }
    
    public final boolean addAudioSource(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.engine.AudioMixerProcessor.ExternalAudioSource source) {
        return false;
    }
    
    public final void clearAudioSources() {
    }
    
    public final void updatePlaybackPosition(long positionUs) {
    }
    
    public final void setPlaybackSpeed(float speed) {
    }
    
    public final void setVideoVolume(float volume) {
    }
    
    public final void setMusicVolume(float volume) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0017\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0003H&J0\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0017H&J\u0018\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003H&J\u0010\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001b"}, d2 = {"Lcom/cutnow/sdk/engine/AudioMixerProcessor$ExternalAudioSource;", "", "durationUs", "", "getDurationUs", "()J", "startTimeUs", "getStartTimeUs", "volume", "", "getVolume", "()F", "getSampleAt", "", "positionUs", "getSamples", "", "startPositionUs", "sampleCount", "", "targetSampleRate", "targetChannelCount", "output", "", "prepareForPosition", "lookaheadUs", "seekTo", "cutNowSDK_debug"})
    public static abstract interface ExternalAudioSource {
        
        public abstract float getVolume();
        
        public abstract long getStartTimeUs();
        
        public abstract long getDurationUs();
        
        public abstract short getSampleAt(long positionUs);
        
        public abstract void getSamples(long startPositionUs, int sampleCount, int targetSampleRate, int targetChannelCount, @org.jetbrains.annotations.NotNull()
        short[] output);
        
        public abstract void seekTo(long positionUs);
        
        public abstract void prepareForPosition(long positionUs, long lookaheadUs);
    }
}