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
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer
import kotlin.math.sqrt

object WaveformGenerator {

    /**
     * Extracts amplitude data from an audio file.
     * Returns a FloatArray where each value is a normalized amplitude [0, 1].
     */
    suspend fun generateWaveform(context: Context, uri: Uri, samples: Int = 100): FloatArray = withContext(Dispatchers.IO) {
        val extractor = MediaExtractor()
        try {
            extractor.setDataSource(context, uri, null)
            val trackIndex = selectAudioTrack(extractor)
            if (trackIndex < 0) return@withContext FloatArray(samples)

            extractor.selectTrack(trackIndex)
            val format = extractor.getTrackFormat(trackIndex)
            val durationUs = format.getLong(MediaFormat.KEY_DURATION)
            val sampleIntervalUs = durationUs / samples

            val amplitudes = FloatArray(samples)
            val buffer = ByteBuffer.allocate(1024 * 16)
            
            for (i in 0 until samples) {
                val targetTimeUs = i * sampleIntervalUs
                extractor.seekTo(targetTimeUs, MediaExtractor.SEEK_TO_CLOSEST_SYNC)
                
                var maxRms = 0f
                // Read a few chunks around the target time to get a better representation
                for (j in 0 until 5) {
                    val sampleSize = extractor.readSampleData(buffer, 0)
                    if (sampleSize < 0) break
                    
                    val rms = calculateRms(buffer, sampleSize)
                    if (rms > maxRms) maxRms = rms
                    extractor.advance()
                }
                amplitudes[i] = maxRms
            }

            // Normalization
            val maxVal = amplitudes.maxOrNull() ?: 1f
            if (maxVal > 0) {
                for (i in amplitudes.indices) {
                    amplitudes[i] = amplitudes[i] / maxVal
                }
            }

            return@withContext amplitudes
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext FloatArray(samples)
        } finally {
            extractor.release()
        }
    }

    private fun selectAudioTrack(extractor: MediaExtractor): Int {
        for (i in 0 until extractor.trackCount) {
            val format = extractor.getTrackFormat(i)
            val mime = format.getString(MediaFormat.KEY_MIME) ?: ""
            if (mime.startsWith("audio/")) return i
        }
        return -1
    }

    private fun calculateRms(buffer: ByteBuffer, size: Int): Float {
        var sum = 0.0
        val shortBuffer = buffer.asShortBuffer()
        val count = size / 2 // 16-bit audio
        for (i in 0 until count) {
            if (i >= shortBuffer.limit()) break
            val sample = shortBuffer.get(i).toDouble()
            sum += sample * sample
        }
        return if (count > 0) sqrt(sum / count).toFloat() else 0f
    }
}
