package com.cutnow.sdk.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J(\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u00a8\u0006\u0014"}, d2 = {"Lcom/cutnow/sdk/util/WaveformGenerator;", "", "()V", "calculateRms", "", "buffer", "Ljava/nio/ByteBuffer;", "size", "", "generateWaveform", "", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "samples", "(Landroid/content/Context;Landroid/net/Uri;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "selectAudioTrack", "extractor", "Landroid/media/MediaExtractor;", "cutNowSDK_debug"})
public final class WaveformGenerator {
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.util.WaveformGenerator INSTANCE = null;
    
    private WaveformGenerator() {
        super();
    }
    
    /**
     * Extracts amplitude data from an audio file.
     * Returns a FloatArray where each value is a normalized amplitude [0, 1].
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object generateWaveform(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri, int samples, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super float[]> $completion) {
        return null;
    }
    
    private final int selectAudioTrack(android.media.MediaExtractor extractor) {
        return 0;
    }
    
    private final float calculateRms(java.nio.ByteBuffer buffer, int size) {
        return 0.0F;
    }
}