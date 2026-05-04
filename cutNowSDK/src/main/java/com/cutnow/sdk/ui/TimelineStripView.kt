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

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.cutnow.sdk.R

class TimelineStripView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var frames: List<Bitmap> = emptyList()
    private var frameIntervalMs: Long = 1000L
    private var pixelsPerSecond: Float = 100f
    private var trimStartMs: Long = 0L
    
    var keyframes: List<com.cutnow.sdk.model.Keyframe> = emptyList()
        set(value) {
            field = value
            invalidate()
        }
    var segmentStartTimeMs: Long = 0L
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val keyframePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val keyframeBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = android.graphics.Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }
    private val srcRect = Rect()
    private val dstRect = Rect()

    private var cornerRadius: Float = 10f * context.resources.displayMetrics.density
    private val clipPath = android.graphics.Path()

    fun updateData(bitmaps: List<Bitmap>, intervalMs: Long, pixelsPerSecond: Float, trimStartMs: Long) {
        var changed = false
        if (this.frames != bitmaps || this.frameIntervalMs != intervalMs) {
            this.frames = bitmaps
            this.frameIntervalMs = intervalMs
            changed = true
        }
        if (this.pixelsPerSecond != pixelsPerSecond || this.trimStartMs != trimStartMs) {
            this.pixelsPerSecond = pixelsPerSecond
            this.trimStartMs = trimStartMs
            changed = true
        }
        if (changed) invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (frames.isEmpty()) return

        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()

        // Clip the entire segment strip with rounded corners
        clipPath.reset()
        clipPath.addRoundRect(0f, 0f, viewWidth, viewHeight, cornerRadius, cornerRadius, android.graphics.Path.Direction.CW)
        canvas.save()
        canvas.clipPath(clipPath)
        // trimStartMs=1500 means we want to see the video starting from 1.5s.
        // So the frame that covers 1.0s to 2.0s should be shifted left by (1500ms - 1000ms) pixels.
        // More generally: Absolute X of frame i is (i * frameIntervalMs / 1000 * pixelsPerSecond)
        // We subtract the absolute X of the start time (trimStartMs / 1000 * pixelsPerSecond)
        
        val startAnchorPx = (trimStartMs / 1000f) * pixelsPerSecond
        
        for ((i, bitmap) in frames.withIndex()) {
            val frameStartMs = i * frameIntervalMs
            
            // If frame is completely before trim point, skip
            // if ((i + 1) * frameIntervalMs < trimStartMs) continue // Optimization

            val frameAbsX = (frameStartMs / 1000f) * pixelsPerSecond
            val drawX = frameAbsX - startAnchorPx
            
            var frameWidth = (frameIntervalMs / 1000000f * 1000f) * pixelsPerSecond // simplified: (interval / 1000 * zoom)
            // Re-calculate precisely to avoid float drift in the loop
            frameWidth = (frameIntervalMs / 1000f) * pixelsPerSecond
            
            // If drawX + frameWidth < 0, it's off screen to the left, skip
            if (drawX + frameWidth < 0) continue
            // If drawX > viewWidth, it's off screen to the right, skip
            if (drawX > viewWidth) break

            srcRect.set(0, 0, bitmap.width, bitmap.height)
            
            // STRETCH LAST FRAME: If it's the last frame, make sure it reaches the end of the view
            val right = if (i == frames.size - 1) {
                viewWidth.coerceAtLeast(drawX + frameWidth)
            } else {
                drawX + frameWidth
            }
            
            dstRect.set(
                drawX.toInt(),
                0,
                right.toInt(),
                viewHeight.toInt()
            )
            
            canvas.drawBitmap(bitmap, srcRect, dstRect, paint)
        }
        
        // --- DRAW KEYFRAMES ---
        if (keyframes.isNotEmpty()) {
            val kfSize = 18f
            keyframes.forEach { kf ->
                // kf.timeMs is absolute timeline time.
                // relative to segment start:
                val relativeMs = kf.timeMs - segmentStartTimeMs
                // pixels from segment start:
                val kfX = (relativeMs / 1000f) * pixelsPerSecond
                
                if (kfX >= -kfSize && kfX <= viewWidth + kfSize) {
                    val p = android.graphics.Path()
                    p.moveTo(kfX, viewHeight / 2 - kfSize)
                    p.lineTo(kfX + kfSize, viewHeight / 2)
                    p.lineTo(kfX, viewHeight / 2 + kfSize)
                    p.lineTo(kfX - kfSize, viewHeight / 2)
                    p.close()
                    
                    keyframePaint.color = androidx.core.content.ContextCompat.getColor(context, R.color.keyframe_active)
                    canvas.drawPath(p, keyframePaint)
                    canvas.drawPath(p, keyframeBorderPaint)
                }
            }
        }
        
        canvas.restore()
    }
}


