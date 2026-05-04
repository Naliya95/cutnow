/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.util

import com.cutnow.sdk.R

import android.content.Context
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import kotlin.math.abs

object AudioWaveformExtractor {

    private const val TARGET_POINTS = 200

    fun extractWaveform(context: Context, uri: Uri): List<Float> {
        val extractor = MediaExtractor()
        try {
            extractor.setDataSource(context, uri, null)
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }

        var trackIndex = -1
        for (i in 0 until extractor.trackCount) {
            val format = extractor.getTrackFormat(i)
            val mime = format.getString(MediaFormat.KEY_MIME)
            if (mime?.startsWith("audio/") == true) {
                trackIndex = i
                break
            }
        }

        if (trackIndex == -1) {
            extractor.release()
            return emptyList()
        }

        extractor.selectTrack(trackIndex)
        val format = extractor.getTrackFormat(trackIndex)
        val mime = format.getString(MediaFormat.KEY_MIME) ?: ""
        val codec = MediaCodec.createDecoderByType(mime)
        codec.configure(format, null, null, 0)
        codec.start()

        val sampleList = mutableListOf<Float>()
        val info = MediaCodec.BufferInfo()
        var isExtractorDone = false
        var isDecoderDone = false

        while (!isDecoderDone) {
            if (!isExtractorDone) {
                val inputBufferIndex = codec.dequeueInputBuffer(1000)
                if (inputBufferIndex >= 0) {
                    val inputBuffer = codec.getInputBuffer(inputBufferIndex)
                    val sampleSize = extractor.readSampleData(inputBuffer!!, 0)
                    if (sampleSize < 0) {
                        codec.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                        isExtractorDone = true
                    } else {
                        codec.queueInputBuffer(inputBufferIndex, 0, sampleSize, extractor.sampleTime, 0)
                        extractor.advance()
                    }
                }
            }

            val outputBufferIndex = codec.dequeueOutputBuffer(info, 1000)
            if (outputBufferIndex >= 0) {
                val outputBuffer = codec.getOutputBuffer(outputBufferIndex)
                if (outputBuffer != null) {
                    // Extract amplitudes from the PCM data
                    // We assume 16-bit PCM for simplicity
                    while (outputBuffer.remaining() >= 2) {
                        val sample = outputBuffer.short
                        sampleList.add(abs(sample.toFloat()) / Short.MAX_VALUE)
                    }
                }
                codec.releaseOutputBuffer(outputBufferIndex, false)
                if ((info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    isDecoderDone = true
                }
            }
        }

        codec.stop()
        codec.release()
        extractor.release()

        // Downsample to TARGET_POINTS
        if (sampleList.isEmpty()) return emptyList()
        
        val result = mutableListOf<Float>()
        val blockSize = sampleList.size / TARGET_POINTS
        if (blockSize <= 1) return sampleList.take(TARGET_POINTS)

        for (i in 0 until TARGET_POINTS) {
            var max = 0f
            val start = i * blockSize
            val end = minOf(start + blockSize, sampleList.size)
            for (j in start until end) {
                if (sampleList[j] > max) max = sampleList[j]
            }
            result.add(max)
        }

        return result
    }
}
