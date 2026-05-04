/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.gl

import com.cutnow.sdk.R

import android.graphics.Color

data class TextOverlay(
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val color: Int = Color.WHITE,
    val fontSizeSp: Float = 24f,
    val positionX: Float = 0.5f, // normalized 0..1
    val positionY: Float = 0.5f, // normalized 0..1
    val startTimeMs: Long = 0L,
    val endTimeMs: Long = Long.MAX_VALUE
)





