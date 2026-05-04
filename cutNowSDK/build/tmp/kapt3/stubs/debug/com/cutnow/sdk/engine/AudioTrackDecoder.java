package com.cutnow.sdk.engine;

/**
 * Decodes an audio track and provides PCM samples for mixing.
 * Integrates Sonic for high-quality time-stretching.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0017\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\n\n\u0002\b\u0012\b\u0007\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0002\u0010\fJ\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u001aH\u0002J\u0010\u00105\u001a\u0002032\u0006\u00104\u001a\u00020\u001aH\u0002J\u0010\u00106\u001a\u0002032\u0006\u00104\u001a\u00020\u001aH\u0002J\u0010\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\u0007H\u0016J0\u0010:\u001a\u0002032\u0006\u0010;\u001a\u00020\u00072\u0006\u0010<\u001a\u00020\u000e2\u0006\u0010=\u001a\u00020\u000e2\u0006\u0010>\u001a\u00020\u000e2\u0006\u0010?\u001a\u00020\u001aH\u0016J\b\u0010@\u001a\u00020%H\u0007J\u0010\u0010A\u001a\u0002032\u0006\u0010B\u001a\u00020\u0007H\u0002J\b\u0010C\u001a\u000203H\u0002J\u0018\u0010D\u001a\u0002032\u0006\u00109\u001a\u00020\u00072\u0006\u0010E\u001a\u00020\u0007H\u0016J\b\u0010F\u001a\u000203H\u0002J\u0006\u0010G\u001a\u000203J\u0010\u0010H\u001a\u0002032\u0006\u00109\u001a\u00020\u0007H\u0016J\b\u0010I\u001a\u000203H\u0002R\u000e\u0010\r\u001a\u00020\u000eX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u0004\u0018\u00010*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010!R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101\u00a8\u0006J"}, d2 = {"Lcom/cutnow/sdk/engine/AudioTrackDecoder;", "Lcom/cutnow/sdk/engine/AudioMixerProcessor$ExternalAudioSource;", "context", "Landroid/content/Context;", "audioUri", "Landroid/net/Uri;", "startTimeUs", "", "durationUs", "volume", "", "speed", "(Landroid/content/Context;Landroid/net/Uri;JJFF)V", "TARGET_CHANNELS", "", "baseTimelinePositionUs", "bufferCondition", "Ljava/util/concurrent/locks/Condition;", "kotlin.jvm.PlatformType", "bufferLock", "Ljava/util/concurrent/locks/ReentrantLock;", "channelCount", "currentTimelinePositionUs", "decodeThread", "Ljava/lang/Thread;", "decodedSamples", "", "decodedSamplesCount", "decoder", "Landroid/media/MediaCodec;", "decoderLock", "", "getDurationUs", "()J", "extractor", "Landroid/media/MediaExtractor;", "hasReachedEos", "", "isDecoding", "isInitialized", "lastRequestedTimelinePosUs", "pendingOutputBuffer", "Ljava/nio/ByteBuffer;", "sampleRate", "sonicInputBuffer", "sonicProcessor", "Landroidx/media3/common/audio/SonicAudioProcessor;", "getStartTimeUs", "getVolume", "()F", "addProcessedSamples", "", "samples", "appendToBuffer", "appendToBufferInternal", "getSampleAt", "", "positionUs", "getSamples", "startPositionUs", "sampleCount", "targetSampleRate", "targetChannelCount", "output", "initialize", "internalDecodeAhead", "targetTimelineUs", "internalRelease", "prepareForPosition", "lookaheadUs", "processSonicOutput", "release", "seekTo", "startDecodingThread", "cutNowSDK_debug"})
@androidx.media3.common.util.UnstableApi()
public final class AudioTrackDecoder implements com.cutnow.sdk.engine.AudioMixerProcessor.ExternalAudioSource {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.net.Uri audioUri = null;
    private final long startTimeUs = 0L;
    private final long durationUs = 0L;
    private final float volume = 0.0F;
    private final float speed = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object decoderLock = null;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaExtractor extractor;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaCodec decoder;
    private int sampleRate = 44100;
    private int channelCount = 2;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.NotNull()
    private volatile short[] decodedSamples;
    @kotlin.jvm.Volatile()
    private volatile int decodedSamplesCount = 0;
    @kotlin.jvm.Volatile()
    private volatile long baseTimelinePositionUs = 0L;
    @kotlin.jvm.Volatile()
    private volatile long currentTimelinePositionUs = 0L;
    private boolean isInitialized = false;
    private boolean hasReachedEos = false;
    @kotlin.jvm.Volatile()
    private volatile long lastRequestedTimelinePosUs = 0L;
    @org.jetbrains.annotations.Nullable()
    private androidx.media3.common.audio.SonicAudioProcessor sonicProcessor;
    @org.jetbrains.annotations.Nullable()
    private java.nio.ByteBuffer sonicInputBuffer;
    @org.jetbrains.annotations.NotNull()
    private java.nio.ByteBuffer pendingOutputBuffer;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Thread decodeThread;
    @kotlin.jvm.Volatile()
    private volatile boolean isDecoding = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.locks.ReentrantLock bufferLock = null;
    private final java.util.concurrent.locks.Condition bufferCondition = null;
    private final int TARGET_CHANNELS = 2;
    
    public AudioTrackDecoder(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri audioUri, long startTimeUs, long durationUs, float volume, float speed) {
        super();
    }
    
    @java.lang.Override()
    public long getStartTimeUs() {
        return 0L;
    }
    
    @java.lang.Override()
    public long getDurationUs() {
        return 0L;
    }
    
    @java.lang.Override()
    public float getVolume() {
        return 0.0F;
    }
    
    @android.annotation.SuppressLint(value = {"WrongConstant"})
    public final boolean initialize() {
        return false;
    }
    
    @java.lang.Override()
    public short getSampleAt(long positionUs) {
        return 0;
    }
    
    @java.lang.Override()
    public void getSamples(long startPositionUs, int sampleCount, int targetSampleRate, int targetChannelCount, @org.jetbrains.annotations.NotNull()
    short[] output) {
    }
    
    @java.lang.Override()
    public void prepareForPosition(long positionUs, long lookaheadUs) {
    }
    
    @java.lang.Override()
    public void seekTo(long positionUs) {
    }
    
    private final void internalDecodeAhead(long targetTimelineUs) {
    }
    
    private final void addProcessedSamples(short[] samples) {
    }
    
    private final void processSonicOutput() {
    }
    
    private final void startDecodingThread() {
    }
    
    private final void appendToBuffer(short[] samples) {
    }
    
    private final void appendToBufferInternal(short[] samples) {
    }
    
    public final void release() {
    }
    
    private final void internalRelease() {
    }
}