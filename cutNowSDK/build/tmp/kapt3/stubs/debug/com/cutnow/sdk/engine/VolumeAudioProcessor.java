package com.cutnow.sdk.engine;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0006H\u0016J\b\u0010\u0011\u001a\u00020\nH\u0016J\b\u0010\u0012\u001a\u00020\nH\u0016J\b\u0010\u0013\u001a\u00020\u000fH\u0016J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0006H\u0016J\b\u0010\u0016\u001a\u00020\u000fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/cutnow/sdk/engine/VolumeAudioProcessor;", "Landroidx/media3/common/audio/AudioProcessor;", "volume", "", "(F)V", "buffer", "Ljava/nio/ByteBuffer;", "inputAudioFormat", "Landroidx/media3/common/audio/AudioProcessor$AudioFormat;", "inputEnded", "", "outputAudioFormat", "outputBuffer", "configure", "flush", "", "getOutput", "isActive", "isEnded", "queueEndOfStream", "queueInput", "inputBuffer", "reset", "cutNowSDK_debug"})
@androidx.media3.common.util.UnstableApi()
public final class VolumeAudioProcessor implements androidx.media3.common.audio.AudioProcessor {
    private final float volume = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private androidx.media3.common.audio.AudioProcessor.AudioFormat inputAudioFormat;
    @org.jetbrains.annotations.NotNull()
    private androidx.media3.common.audio.AudioProcessor.AudioFormat outputAudioFormat;
    @org.jetbrains.annotations.NotNull()
    private java.nio.ByteBuffer buffer;
    @org.jetbrains.annotations.NotNull()
    private java.nio.ByteBuffer outputBuffer;
    private boolean inputEnded = false;
    
    public VolumeAudioProcessor(float volume) {
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
    public boolean isEnded() {
        return false;
    }
    
    @java.lang.Override()
    public void queueEndOfStream() {
    }
    
    @java.lang.Override()
    public void flush() {
    }
    
    @java.lang.Override()
    public void reset() {
    }
}