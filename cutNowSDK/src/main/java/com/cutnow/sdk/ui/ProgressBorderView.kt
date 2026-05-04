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
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ProgressBorderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 0f
    private val strokeWidthPixels = 8f
    private val cornerRadius = 12f * context.resources.displayMetrics.density

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#33FFFFFF")
        style = Paint.Style.STROKE
        strokeWidth = strokeWidthPixels
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFD700") // Gold
        style = Paint.Style.STROKE
        strokeWidth = strokeWidthPixels
        strokeCap = Paint.Cap.ROUND
    }

    private val path = Path()
    private val measure = PathMeasure()
    private val drawPath = Path()

    fun setProgress(value: Float) {
        progress = value / 100f
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()
        val halfStroke = strokeWidthPixels / 2f
        
        // Build the rectangular path with rounded corners
        path.reset()
        // Start at top-center to be cap-friendly, or just top-left.
        // Cap-friendly: move to middle of top edge
        path.moveTo(w / 2f, halfStroke)
        path.lineTo(w - cornerRadius, halfStroke)
        path.quadTo(w - halfStroke, halfStroke, w - halfStroke, cornerRadius)
        path.lineTo(w - halfStroke, h - cornerRadius)
        path.quadTo(w - halfStroke, h - halfStroke, w - cornerRadius, h - halfStroke)
        path.lineTo(cornerRadius, h - halfStroke)
        path.quadTo(halfStroke, h - halfStroke, halfStroke, h - cornerRadius)
        path.lineTo(halfStroke, cornerRadius)
        path.quadTo(halfStroke, halfStroke, cornerRadius, halfStroke)
        path.lineTo(w / 2f, halfStroke)

        // Draw background
        canvas.drawPath(path, bgPaint)

        // Draw progress segment
        measure.setPath(path, false)
        val fullLength = measure.length
        drawPath.reset()
        measure.getSegment(0f, fullLength * progress, drawPath, true)
        canvas.drawPath(drawPath, progressPaint)
    }
}


