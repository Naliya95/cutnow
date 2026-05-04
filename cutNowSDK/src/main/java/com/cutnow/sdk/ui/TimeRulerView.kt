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

class TimeRulerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#666666")
        strokeWidth = 2f
    }
    
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#AAAAAA")
        textSize = 24f
        textAlign = Paint.Align.CENTER
    }

    var pixelsPerSecond: Float = 100f
        set(value) {
            field = value.coerceAtLeast(1f)
            invalidate()
        }
        
    var scrollXOffset: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    var startPadding: Float = 0f
        set(value) {
            field = value.coerceAtLeast(0f)
            invalidate()
        }

    var segmentDurations: List<Long> = emptyList()
        set(value) {
            field = value
            invalidate()
        }

    var transitionWidthPx: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var leadingOffsetPx: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private fun timeToTimelineX(timeMs: Long): Float {
        if (segmentDurations.isEmpty()) return leadingOffsetPx + (timeMs / 1000f * pixelsPerSecond)
        
        var currentPixels = leadingOffsetPx
        var accumulatedTime = 0L
        
        for (i in segmentDurations.indices) {
            val dur = segmentDurations[i]
            val segWidth = (dur / 1000f * pixelsPerSecond).toInt()
            
            if (timeMs <= accumulatedTime + dur) {
                val timeInSeg = (timeMs - accumulatedTime).coerceAtLeast(0)
                val offsetInSeg = (timeInSeg / 1000f * pixelsPerSecond)
                return currentPixels + offsetInSeg
            }
            
            currentPixels += segWidth
            accumulatedTime += dur
            if (i < segmentDurations.size - 1) {
                currentPixels += transitionWidthPx
            }
        }
        
        // Final fallback: If beyond duration, extend linearly from the last segment's end
        val extraTime = (timeMs - accumulatedTime).coerceAtLeast(0)
        return currentPixels + (extraTime / 1000f * pixelsPerSecond)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        val paddingStart = startPadding // Use dynamic padding

        // Determine step interval based on zoom to avoid clutter
        val stepSeconds = when {
            pixelsPerSecond > 200 -> 0.5f // Every half second
            pixelsPerSecond > 80 -> 1f    // Every second
            pixelsPerSecond > 40 -> 2f    // Every 2 seconds
            pixelsPerSecond > 20 -> 5f    // Every 5 seconds
            else -> 10f                   // Every 10 seconds
        }
        
        val stepPx = stepSeconds * pixelsPerSecond

        // Calculate visible range optimization
        val startX = scrollXOffset.toFloat()
        val endX = startX + w
        
        // Find first tick to draw
        // The ruler "zero" starts at paddingStart absolute X.
        // We need to map screen coordinates to time coordinates.
        // Ticks are drawn relative to view, but shifted by (paddingStart - scrollXOffset)
        
        val effectiveStart = -paddingStart + scrollXOffset
        val startIndex = (effectiveStart / stepPx).toInt().coerceAtLeast(0)
        
        var i = startIndex
        while (true) {
            val timeSec = i * stepSeconds
            val timeMs = (timeSec * 1000).toLong()
            
            val x = timeToTimelineX(timeMs) + paddingStart - scrollXOffset
            
            if (x > w + 200) break // Buffer for scroll
            
            if (x >= -100) {
                // Draw major tick
                val isMajor = (timeSec % 5.0f == 0.0f) // Every 5 seconds is major? Or adaptive?
                // Lets make strict bounds for levels
                
                val heightRatio = if (timeSec % 5.0f == 0.0f) 0.6f else 0.3f
                canvas.drawLine(x, h, x, h - (h * heightRatio), linePaint)
                
                // Draw Text if major
                if (heightRatio > 0.4f) {
                    val timeString = formatTime(timeSec.toInt())
                    canvas.drawText(timeString, x, h - (h * 0.6f) - 10f, textPaint)
                }
            }
            i++
        }
    }
    
    private fun formatTime(seconds: Int): String {
        val m = seconds / 60
        val s = seconds % 60
        return "%02d:%02d".format(m, s)
    }
}


