/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ExportResolution(val label: String, val width: Int, val height: Int) : Parcelable {
    P480("480P", 854, 480),
    P720("720P", 1280, 720),
    P1080("1080P", 1920, 1080),
    K2("2K", 2560, 1440),
    K4("4K", 3840, 2160);

    companion object
}

@Parcelize
data class ExportSettings(
    val resolution: ExportResolution = ExportResolution.P1080,
    val frameRate: Int = 30,
    val bitrateMbps: Int = 8,
    val targetAspectRatio: Float? = null
) : Parcelable {
    val bitrate: Int get() = bitrateMbps * 1_000_000

    fun getAdjustedDimensions(sourceWidth: Int, sourceHeight: Int): Pair<Int, Int> {
        // If target aspect ratio is set, use it. Otherwise use source aspect ratio.
        val aspect = targetAspectRatio ?: (sourceWidth.toFloat() / sourceHeight.toFloat())
        
        // Calculate based on resolution tier (e.g. 1080p)
        // ExportResolution.width/height are typically defined for 16:9 Landscape (e.g. 1920x1080)
        
        val baseSize = when (resolution) {
            ExportResolution.P480 -> 480
            ExportResolution.P720 -> 720
            ExportResolution.P1080 -> 1080
            ExportResolution.K2 -> 1440
            ExportResolution.K4 -> 2160
        }

        val w: Int
        val h: Int
        
        if (aspect >= 1f) {
            // Wide or Square
            // Height is baseSize (e.g. 1080), Width = Height * aspect
            h = baseSize
            w = (baseSize * aspect).toInt()
        } else {
            // Tall/Portrait
            // Width is baseSize (e.g. 1080), Height = Width / aspect
            w = baseSize
            h = (baseSize / aspect).toInt()
        }
        
        // Ensure even dimensions for encoder
        return (w / 2 * 2) to (h / 2 * 2)
    }
}
