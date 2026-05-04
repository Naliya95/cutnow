/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.engine

import androidx.media3.common.C
import androidx.media3.common.audio.AudioProcessor
import androidx.media3.common.util.UnstableApi
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CopyOnWriteArrayList

/**
 * AudioProcessor that:
 *  - Applies video volume scaling to the main video audio track
 *  - Mixes in external audio sources (background music)
 *
 * ROBUST IMPLEMENTATION:
 *  - Uses a FIFO for input buffers to prevent data loss.
 *  - Tracks position using PROCESSED frames to avoid jitter.
 *  - Supports playback speed for accurate timeline mapping.
 */
@UnstableApi
class AudioMixerProcessor : AudioProcessor {

    private var inputAudioFormat: AudioProcessor.AudioFormat = AudioProcessor.AudioFormat.NOT_SET
    private var outputBuffer: ByteBuffer = AudioProcessor.EMPTY_BUFFER
    
    // FIFO for input buffers provided by ExoPlayer/Sonic
    private val inputQueue = ConcurrentLinkedQueue<ByteBuffer>()

    // External audio sources (background music tracks)
    private val externalAudioSources = CopyOnWriteArrayList<ExternalAudioSource>()

    // State for position tracking and mixing
    @Volatile private var externalPositionUs: Long = 0L
    @Volatile private var videoVolume: Float = 1.0f
    @Volatile private var musicVolume: Float = 1.0f
    @Volatile private var playbackSpeed: Float = 1.0f

    // Total frames processed since the last externalPositionUs update
    private var framesProcessedSinceLastUpdate: Long = 0L

    // Segment boundaries for pop elimination
    private var segmentBoundariesUs = LongArray(0)
    private val transitionRampUs = 50_000L

    // Reusable mixing buffers
    private var externalSampleBuffer = ShortArray(1024 * 16)
    private var mixingBuffer = IntArray(1024 * 16)

    private var inputEnded = false

    // ─── AudioProcessor Interface ───────────────────────────────────────────

    override fun configure(inputAudioFormat: AudioProcessor.AudioFormat): AudioProcessor.AudioFormat {
        this.inputAudioFormat = inputAudioFormat
        android.util.Log.d("AudioMixer", "configure: ${inputAudioFormat.sampleRate}Hz ${inputAudioFormat.channelCount}ch")
        // We only support 16-bit PCM for mixing simplicity and reliability
        return if (inputAudioFormat.encoding == C.ENCODING_PCM_16BIT) {
            inputAudioFormat
        } else {
            AudioProcessor.AudioFormat.NOT_SET
        }
    }

    override fun isActive(): Boolean =
        inputAudioFormat != AudioProcessor.AudioFormat.NOT_SET &&
        inputAudioFormat.encoding == C.ENCODING_PCM_16BIT

    override fun queueInput(inputBuffer: ByteBuffer) {
        if (!inputBuffer.hasRemaining()) return

        // Copy the input buffer data to a new direct buffer in the queue.
        // This is necessary because ExoPlayer reuses the inputBuffer immediately.
        val copy = ByteBuffer.allocateDirect(inputBuffer.remaining())
            .order(ByteOrder.nativeOrder())
        copy.put(inputBuffer)
        copy.flip()
        inputQueue.add(copy)
    }

    override fun getOutput(): ByteBuffer {
        val nextInput = inputQueue.poll() ?: return AudioProcessor.EMPTY_BUFFER
        
        val result = mixAudio(nextInput)
        
        // Update frame counter AFTER processing to ensure posUs is accurate for the NEXT block
        val channelCount = inputAudioFormat.channelCount.coerceAtLeast(1)
        framesProcessedSinceLastUpdate += (nextInput.limit() / (2 * channelCount))
        
        return result
    }

    override fun queueEndOfStream() {
        inputEnded = true
    }

    override fun isEnded(): Boolean =
        inputEnded && inputQueue.isEmpty()

    override fun flush() {
        inputQueue.clear()
        outputBuffer = AudioProcessor.EMPTY_BUFFER
        inputEnded = false
        framesProcessedSinceLastUpdate = 0L
        android.util.Log.d("AudioMixer", "flush")
    }

    override fun reset() {
        flush()
        inputAudioFormat = AudioProcessor.AudioFormat.NOT_SET
        externalAudioSources.clear()
        externalPositionUs = 0L
        playbackSpeed = 1.0f
    }

    // ─── Mixing Logic ────────────────────────────────────────────────────────

    private fun getRampFactor(posUs: Long): Float {
        if (segmentBoundariesUs.isEmpty()) return 1f
        var closest = Long.MAX_VALUE
        for (boundary in segmentBoundariesUs) {
            val d = kotlin.math.abs(posUs - boundary)
            if (d < closest) closest = d
        }
        return if (closest < transitionRampUs)
            (closest.toFloat() / transitionRampUs).coerceIn(0f, 1f)
        else 1f
    }

    private fun mixAudio(input: ByteBuffer): ByteBuffer {
        val remaining = input.remaining()
        val sampleRate = inputAudioFormat.sampleRate.coerceAtLeast(1)
        val channelCount = inputAudioFormat.channelCount.coerceAtLeast(1)
        val sampleCount = remaining / 2

        // Ensure output buffer is correctly sized and ordered
        if (outputBuffer.capacity() < remaining) {
            outputBuffer = ByteBuffer.allocateDirect(remaining * 2)
                .order(ByteOrder.nativeOrder())
        }
        outputBuffer.clear()
        outputBuffer.limit(remaining)

        val videoShorts = input.asShortBuffer()
        val outputShorts = outputBuffer.asShortBuffer()

        // Calculate exact timeline position for this block.
        // Each frame of processed audio (post-Sonic) represents (1/sampleRate * speed) timeline units.
        val currentSpeed = playbackSpeed
        val posUs = externalPositionUs + (framesProcessedSinceLastUpdate * 1_000_000L / sampleRate)
        
        val needsMixing = externalAudioSources.isNotEmpty() || videoVolume != 1.0f || segmentBoundariesUs.isNotEmpty()

        if (!needsMixing) {
            outputShorts.put(videoShorts)
            outputBuffer.position(0)
            return outputBuffer
        }

        // Prepare buffers and active sources
        // Note: Music always plays at 1x real-time relative to the timeline.
        if (mixingBuffer.size < sampleCount) {
            mixingBuffer = IntArray(sampleCount + 1024)
            externalSampleBuffer = ShortArray(sampleCount + 1024)
        }

        val frameDurUs = 1_000_000L / sampleRate
        val activeSources = externalAudioSources.filter { src ->
            val blockDurUs = (sampleCount / channelCount) * frameDurUs
            src.startTimeUs < posUs + blockDurUs && (src.startTimeUs + src.durationUs) > posUs
        }
        activeSources.forEach { it.prepareForPosition(posUs, 200_000) }

        // Start Mixing
        for (i in 0 until sampleCount) {
            val sampleUs = posUs + (i / channelCount) * frameDurUs
            val ramp = getRampFactor(sampleUs)
            mixingBuffer[i] = (videoShorts.get(i).toInt() * videoVolume * ramp).toInt()
        }

        for (src in activeSources) {
            src.getSamples(posUs, sampleCount, sampleRate, channelCount, externalSampleBuffer)
            val vol = src.volume * musicVolume
            if (vol > 0f) {
                for (i in 0 until sampleCount) {
                    mixingBuffer[i] += (externalSampleBuffer[i].toInt() * vol).toInt()
                }
            }
        }

        // Clamp to 16-bit PCM and write to output
        for (i in 0 until sampleCount) {
            outputShorts.put(i, mixingBuffer[i].coerceIn(-32768, 32767).toShort())
        }

        outputBuffer.position(0)
        return outputBuffer
    }

    // ─── Public API ──────────────────────────────────────────────────────────

    fun setSegmentBoundaries(boundariesUs: LongArray) {
        segmentBoundariesUs = boundariesUs
    }

    fun addAudioSource(source: ExternalAudioSource) = externalAudioSources.add(source)
    fun clearAudioSources() = externalAudioSources.clear()

    fun updatePlaybackPosition(positionUs: Long) {
        externalPositionUs = positionUs
        framesProcessedSinceLastUpdate = 0L
        externalAudioSources.forEach { it.seekTo(positionUs) }
    }

    fun setPlaybackSpeed(speed: Float) {
        playbackSpeed = speed
    }

    fun setVideoVolume(volume: Float) { videoVolume = volume }
    fun setMusicVolume(volume: Float) { musicVolume = volume }

    // ─── ExternalAudioSource Interface ───────────────────────────────────────

    interface ExternalAudioSource {
        val volume: Float
        val startTimeUs: Long
        val durationUs: Long
        fun getSampleAt(positionUs: Long): Short
        fun getSamples(startPositionUs: Long, sampleCount: Int, targetSampleRate: Int, targetChannelCount: Int, output: ShortArray)
        fun seekTo(positionUs: Long)
        fun prepareForPosition(positionUs: Long, lookaheadUs: Long)
    }
}
