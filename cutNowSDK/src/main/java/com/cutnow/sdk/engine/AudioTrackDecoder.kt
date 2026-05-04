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

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import android.util.Log
import androidx.media3.common.audio.AudioProcessor
import androidx.media3.common.audio.SonicAudioProcessor
import androidx.media3.common.util.UnstableApi
import com.cutnow.sdk.engine.AudioMixerProcessor
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.max

/**
 * Decodes an audio track and provides PCM samples for mixing.
 * Integrates Sonic for high-quality time-stretching.
 */
@UnstableApi
class AudioTrackDecoder(
    private val context: Context,
    private val audioUri: Uri,
    override val startTimeUs: Long, // Timeline start
    override val durationUs: Long,  // Timeline duration
    override val volume: Float,
    private val speed: Float = 1.0f
) : AudioMixerProcessor.ExternalAudioSource {
    
    private val decoderLock = Any()
    private var extractor: MediaExtractor? = null
    private var decoder: MediaCodec? = null
    private var sampleRate: Int = 44100
    private var channelCount: Int = 2
    
    // Lock-free data access
    @Volatile private var decodedSamples = ShortArray(44100 * 2) // Start with 1s buffer
    @Volatile private var decodedSamplesCount = 0
    
    // Timeline position of the first sample in decodedSamples
    @Volatile private var baseTimelinePositionUs: Long = 0
    // Current timeline position of the last decoded/processed sample
    @Volatile private var currentTimelinePositionUs: Long = 0
    
    private var isInitialized = false
    private var hasReachedEos: Boolean = false
    
    // Track playback to drive decoding loop
    @Volatile private var lastRequestedTimelinePosUs: Long = 0
    
    // Media3 Professional Audio Engine
    private var sonicProcessor: SonicAudioProcessor? = null
    private var sonicInputBuffer: ByteBuffer? = null
    private var pendingOutputBuffer: ByteBuffer = AudioProcessor.EMPTY_BUFFER
    
    // Background decoding thread
    private var decodeThread: Thread? = null
    @Volatile private var isDecoding = false
    private val bufferLock = java.util.concurrent.locks.ReentrantLock()
    private val bufferCondition = bufferLock.newCondition()
    
    // Target state for the mixer
    private val TARGET_CHANNELS = 2
    
    @SuppressLint("WrongConstant")
    fun initialize(): Boolean {
        synchronized(decoderLock) {
            try {
                extractor = MediaExtractor().apply {
                    setDataSource(context, audioUri, null)
                }
                
                val trackCount = extractor?.trackCount ?: 0
                val trackIndex = (0 until trackCount).firstOrNull { index ->
                    val format = extractor?.getTrackFormat(index)
                    format?.getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true
                } ?: return false
                
                extractor?.selectTrack(trackIndex)
                val format = extractor?.getTrackFormat(trackIndex) ?: return false
                
                sampleRate = if (format.containsKey(MediaFormat.KEY_SAMPLE_RATE)) format.getInteger(MediaFormat.KEY_SAMPLE_RATE) else 44100
                channelCount = if (format.containsKey(MediaFormat.KEY_CHANNEL_COUNT)) format.getInteger(MediaFormat.KEY_CHANNEL_COUNT) else 2
                
                val mime = format.getString(MediaFormat.KEY_MIME)!!
                // ENFORCE 16-bit PCM
                format.setInteger(MediaFormat.KEY_PCM_ENCODING, android.media.AudioFormat.ENCODING_PCM_16BIT)
                
                decoder = MediaCodec.createDecoderByType(mime).apply {
                    configure(format, null, null, 0)
                    start()
                }
                
                if (speed != 1.0f) {
                    val audioFormat = AudioProcessor.AudioFormat(sampleRate, TARGET_CHANNELS, android.media.AudioFormat.ENCODING_PCM_16BIT)
                    sonicProcessor = SonicAudioProcessor().apply {
                        setSpeed(this@AudioTrackDecoder.speed)
                        try {
                            configure(audioFormat)
                            flush()
                        } catch (e: Exception) {
                            Log.e("AudioTrackDecoder", "Sonic config failed", e)
                        }
                    }
                }
                
                isInitialized = true
                baseTimelinePositionUs = 0
                currentTimelinePositionUs = 0
                hasReachedEos = false
                
                // Pre-decode a bit synchronously to ensure we have data immediately
                internalDecodeAhead(100_000)
                
                // Start background decoding
                startDecodingThread()
                
                return true
            } catch (e: Exception) {
                Log.e("AudioTrackDecoder", "Failed to initialize", e)
                internalRelease()
                return false
            }
        }
    }
    
    override fun getSampleAt(positionUs: Long): Short {
        if (!isInitialized) return 0
        if (positionUs < startTimeUs || positionUs >= startTimeUs + durationUs) return 0
        
        val relativePosUs = positionUs - startTimeUs
        val sampleOffsetUs = relativePosUs - baseTimelinePositionUs
        
        if (sampleOffsetUs < 0) return 0
        
        val sampleIndex = ((sampleOffsetUs * sampleRate) / 1_000_000).toInt() * TARGET_CHANNELS
        
        // Lock-free read
        val currentBuffer = decodedSamples
        val currentCount = decodedSamplesCount
        if (sampleIndex >= 0 && sampleIndex < currentCount) {
            return currentBuffer[sampleIndex]
        }
        return 0
    }
    
    override fun getSamples(startPositionUs: Long, sampleCount: Int, targetSampleRate: Int, targetChannelCount: Int, output: ShortArray) {
        if (!isInitialized) {
            output.fill(0, 0, sampleCount)
            return
        }
        
        val relativeStartUs = startPositionUs - startTimeUs
        if (relativeStartUs < 0 || relativeStartUs >= durationUs) {
            output.fill(0, 0, sampleCount)
            return
        }
        
        lastRequestedTimelinePosUs = relativeStartUs
        
        // Use bufferLock to consistently capture all parameters for this block
        bufferLock.lock()
        try {
            val currentBuffer = decodedSamples
            val currentCount = decodedSamplesCount
            val basePosUs = baseTimelinePositionUs
            
            for (i in 0 until sampleCount step targetChannelCount) {
                // Determine timeline position for this frame in the output buffer
                val frameIndex = i / targetChannelCount
                val currentPosUs = relativeStartUs + (frameIndex.toLong() * 1_000_000L / targetSampleRate)
                
                // Map to buffer-relative sample index with floating-point precision for interpolation
                val samplePosDouble = (currentPosUs - basePosUs).toDouble() * sampleRate / 1_000_000.0
                val floorIdx = samplePosDouble.toInt()
                val fract = (samplePosDouble - floorIdx).toFloat()
                
                for (c in 0 until targetChannelCount) {
                    val outIdx = i + c
                    val sourceChannel = c % TARGET_CHANNELS
                    val idx1 = floorIdx * TARGET_CHANNELS + sourceChannel
                    val idx2 = (floorIdx + 1) * TARGET_CHANNELS + sourceChannel
                    
                    if (floorIdx >= 0 && idx2 < currentCount) {
                        // High-Precision Linear Interpolation
                        val s1 = currentBuffer[idx1].toFloat()
                        val s2 = currentBuffer[idx2].toFloat()
                        output[outIdx] = (s1 + fract * (s2 - s1)).toInt().toShort()
                    } else if (floorIdx >= 0 && idx1 < currentCount) {
                        output[outIdx] = currentBuffer[idx1]
                    } else {
                        output[outIdx] = 0
                    }
                }
            }
        } finally {
            bufferLock.unlock()
        }
    }
    
    override fun prepareForPosition(positionUs: Long, lookaheadUs: Long) {
        if (!isInitialized) return
        val relativePosUs = positionUs - startTimeUs
        if (relativePosUs < 0 || relativePosUs >= durationUs) return
        
        val neededEndTimelineUs = relativePosUs + lookaheadUs
        bufferLock.lock()
        try {
            if (currentTimelinePositionUs < neededEndTimelineUs && !hasReachedEos) {
                bufferCondition.signalAll() // Wake up the decoding thread
            }
            
            // Cleanup old samples to keep buffer manageable (keep 1.5s of history for robustness)
            if (relativePosUs - baseTimelinePositionUs > 2_500_000L) {
                val samplesToDrop = ((1_500_000L * sampleRate / 1_000_000L).toInt() * TARGET_CHANNELS)
                if (samplesToDrop < decodedSamplesCount) {
                    val newCount = decodedSamplesCount - samplesToDrop
                    val newBuffer = ShortArray(max(decodedSamples.size, newCount))
                    System.arraycopy(decodedSamples, samplesToDrop, newBuffer, 0, newCount)
                    
                    decodedSamplesCount = newCount
                    decodedSamples = newBuffer
                    baseTimelinePositionUs += 1_500_000L
                }
            }
        } finally {
            bufferLock.unlock()
        }
    }
    
    override fun seekTo(positionUs: Long) {
        if (!isInitialized) return
        val relativePosUs = (positionUs - startTimeUs).coerceAtLeast(0)
        val sourcePosUs = (relativePosUs * speed).toLong()
        
        synchronized(decoderLock) {
            try {
                // extractor and decoder calls must be synchronized with release
                extractor?.seekTo(sourcePosUs, MediaExtractor.SEEK_TO_CLOSEST_SYNC)
                decoder?.flush()
                
                bufferLock.lock()
                try {
                    sonicProcessor?.flush()
                    pendingOutputBuffer = AudioProcessor.EMPTY_BUFFER
                    
                    decodedSamplesCount = 0
                    val actualSourcePosUs = extractor?.sampleTime ?: sourcePosUs
                    baseTimelinePositionUs = (actualSourcePosUs / speed).toLong()
                    currentTimelinePositionUs = baseTimelinePositionUs
                    hasReachedEos = false
                    
                    bufferCondition.signalAll()
                } finally {
                    bufferLock.unlock()
                }
            } catch (e: Exception) {
                Log.e("AudioTrackDecoder", "Seek failed", e)
            }
        }
    }
    
    private fun internalDecodeAhead(targetTimelineUs: Long) {
        val decoder = this.decoder ?: return
        val extractor = this.extractor ?: return
        
        try {
            val bufferInfo = MediaCodec.BufferInfo()
            var iterations = 0
            
            while (currentTimelinePositionUs < targetTimelineUs && iterations < 50 && !hasReachedEos && isInitialized) {
                iterations++
                
                // Feed Input
                val inputIndex = decoder.dequeueInputBuffer(0)
                if (inputIndex >= 0) {
                    val inputBuffer = decoder.getInputBuffer(inputIndex)!!
                    val size = extractor.readSampleData(inputBuffer, 0)
                    if (size < 0) {
                        decoder.queueInputBuffer(inputIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                    } else {
                        decoder.queueInputBuffer(inputIndex, 0, size, extractor.sampleTime, 0)
                        extractor.advance()
                    }
                }
                
                // Get Output
                val outputIndex = decoder.dequeueOutputBuffer(bufferInfo, 0)
                if (outputIndex >= 0) {
                    val outputBuffer = decoder.getOutputBuffer(outputIndex)!!
                    outputBuffer.position(bufferInfo.offset)
                    outputBuffer.limit(bufferInfo.offset + bufferInfo.size)

                    val rawSamples = ShortArray(bufferInfo.size / 2)
                    outputBuffer.order(ByteOrder.nativeOrder()).asShortBuffer().get(rawSamples)
                    
                    // CRITICAL FIX: Robust Mono to Stereo mapping
                    // Project expects 2 channels (Stereo) for mixing.
                    val stereoSamples = if (channelCount == 1) {
                        val converted = ShortArray(rawSamples.size * 2)
                        for (i in rawSamples.indices) {
                            converted[i * 2] = rawSamples[i]
                            converted[i * 2 + 1] = rawSamples[i]
                        }
                        converted
                    } else {
                        rawSamples
                    }
                    
                    addProcessedSamples(stereoSamples)
                    
                    decoder.releaseOutputBuffer(outputIndex, false)
                    if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                        hasReachedEos = true
                        bufferLock.lock()
                        try {
                            sonicProcessor?.queueEndOfStream()
                            processSonicOutput()
                        } finally {
                            bufferLock.unlock()
                        }
                    }
                } else if (outputIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    val newFormat = decoder.outputFormat
                    channelCount = if (newFormat.containsKey(MediaFormat.KEY_CHANNEL_COUNT)) newFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT) else channelCount
                    sampleRate = if (newFormat.containsKey(MediaFormat.KEY_SAMPLE_RATE)) newFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE) else sampleRate
                } else if (outputIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                    if (iterations > 20 && inputIndex < 0) break // Stuck, wait for next cycle
                }
            }
        } catch (e: Exception) {
            Log.e("AudioTrackDecoder", "Decode error", e)
        }
    }
    
    private fun addProcessedSamples(samples: ShortArray) {
        val processor = sonicProcessor
        if (processor != null) {
            val byteCount = samples.size * 2
            if (sonicInputBuffer == null || sonicInputBuffer!!.capacity() < byteCount) {
                sonicInputBuffer = ByteBuffer.allocateDirect(byteCount).order(ByteOrder.nativeOrder())
            }
            sonicInputBuffer!!.clear()
            sonicInputBuffer!!.asShortBuffer().put(samples)
            sonicInputBuffer!!.limit(byteCount)
            
            processor.queueInput(sonicInputBuffer!!)
            processSonicOutput()
        } else {
            appendToBuffer(samples)
        }
    }
    
    private fun processSonicOutput() {
        val processor = sonicProcessor ?: return
        while (true) {
            val output = processor.getOutput()
            if (output == AudioProcessor.EMPTY_BUFFER) break
            
            val shortCount = output.remaining() / 2
            val shortArray = ShortArray(shortCount)
            output.asShortBuffer().get(shortArray)
            appendToBuffer(shortArray)
        }
    }
    
    private fun startDecodingThread() {
        isDecoding = true
        decodeThread = Thread {
            while (isDecoding && isInitialized) {
                try {
                    var needsDecoding = false
                    bufferLock.lock()
                    try {
                        // Decode ahead 1 second from CURRENT REQUESTED POSITION
                        val lookaheadLimitUs = lastRequestedTimelinePosUs + 1_000_000L
                        if (currentTimelinePositionUs < lookaheadLimitUs && !hasReachedEos) {
                            needsDecoding = true
                        } else {
                            bufferCondition.await(100, java.util.concurrent.TimeUnit.MILLISECONDS)
                        }
                    } finally {
                        bufferLock.unlock()
                    }
                    
                    if (needsDecoding) {
                        synchronized(decoderLock) {
                            if (isDecoding && isInitialized) {
                                internalDecodeAhead(lastRequestedTimelinePosUs + 1_200_000L)
                            }
                        }
                        Thread.sleep(5)
                    }
                } catch (e: InterruptedException) {
                    break
                } catch (e: Exception) {
                    Log.e("AudioTrackDecoder", "Decode thread error", e)
                    Thread.sleep(100)
                }
            }
        }.apply { 
            name = "AudioDecoder-${audioUri.lastPathSegment}"
            priority = Thread.NORM_PRIORITY + 1
            start() 
        }
    }
    
    private fun appendToBuffer(samples: ShortArray) {
        bufferLock.lock()
        try {
            appendToBufferInternal(samples)
        } finally {
            bufferLock.unlock()
        }
    }

    private fun appendToBufferInternal(samples: ShortArray) {
        val currentCount = decodedSamplesCount
        if (currentCount + samples.size >= decodedSamples.size) {
            val newBuffer = ShortArray(max(decodedSamples.size * 2, currentCount + samples.size + 1024))
            System.arraycopy(decodedSamples, 0, newBuffer, 0, currentCount)
            System.arraycopy(samples, 0, newBuffer, currentCount, samples.size)
            
            decodedSamples = newBuffer
            decodedSamplesCount = currentCount + samples.size
        } else {
            System.arraycopy(samples, 0, decodedSamples, currentCount, samples.size)
            decodedSamplesCount = currentCount + samples.size
        }

        currentTimelinePositionUs = baseTimelinePositionUs + (decodedSamplesCount.toLong() * 1_000_000L / (sampleRate * TARGET_CHANNELS))
    }
    
    fun release() {
        synchronized(decoderLock) {
            internalRelease()
        }
    }
    
    private fun internalRelease() {
        isDecoding = false
        decodeThread?.interrupt()
        decodeThread = null
        
        try {
            decoder?.stop()
            decoder?.release()
        } catch (e: Exception) {}
        try {
            extractor?.release()
        } catch (e: Exception) {}
        decoder = null
        extractor = null
        isInitialized = false
        sonicProcessor = null
        sonicInputBuffer = null
        
        bufferLock.lock()
        try {
            decodedSamplesCount = 0
            bufferCondition.signalAll()
        } finally {
            bufferLock.unlock()
        }
    }
}


