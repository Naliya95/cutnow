/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.ui

import com.cutnow.sdk.R

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class WaveformView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#4FC3F7") // Light Blue / Cyan
        style = Paint.Style.FILL
    }

    private var amplitudes: FloatArray = floatArrayOf()

    fun setAmplitudes(data: FloatArray) {
        amplitudes = data
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (amplitudes.isEmpty()) return

        val w = width.toFloat()
        val h = height.toFloat()
        val centerY = h / 2f
        val barWidth = w / amplitudes.size
        val barGap = barWidth * 0.2f

        for (i in amplitudes.indices) {
            val amplitude = amplitudes[i]
            val barHeight = (h * 0.8f) * amplitude
            val left = i * barWidth + barGap / 2
            val top = centerY - barHeight / 2
            val right = left + barWidth - barGap
            val bottom = centerY + barHeight / 2

            canvas.drawRoundRect(left, top, right, bottom, barWidth / 2, barWidth / 2, paint)
        }
    }
}


