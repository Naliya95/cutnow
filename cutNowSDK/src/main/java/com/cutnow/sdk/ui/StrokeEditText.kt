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

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import kotlin.math.max

class StrokeEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    var strokeWidth: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    var strokeColor: Int = Color.TRANSPARENT
        set(value) {
            field = value
            invalidate()
        }
    
    var bgPaddingX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bgPaddingY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bgCornerRadius: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bgColor: Int = Color.TRANSPARENT
        set(value) {
            field = value
            invalidate()
        }
    
    var textGradientColors: IntArray? = null
        set(value) {
            field = value
            if (value != null && value.size > 1) {
                super.setTextColor(Color.WHITE)
            }
            invalidate()
        }
    
    var textAlpha: Int = 255
        set(value) {
            field = value
            invalidate()
        }

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val bgRect = RectF()

    @SuppressLint("RtlHardcoded")
    override fun onDraw(canvas: Canvas) {
        // Draw custom background if color is set
        if (bgColor != Color.TRANSPARENT) {
            bgPaint.color = bgColor
            
            val viewWidth = width.toFloat()
            val viewHeight = height.toFloat()
            
            val textLayout = layout
            if (textLayout != null) {
                var maxLineWidth = 0f
                for (i in 0 until textLayout.lineCount) {
                    maxLineWidth = max(maxLineWidth, textLayout.getLineWidth(i))
                }
                
                val bgWidth = maxLineWidth + bgPaddingX * 2
                val bgHeight = textLayout.height + bgPaddingY * 2
                
                // Calculate left position based on gravity
                val horizontalGravity = gravity and android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK
                val left = when {
                    (horizontalGravity == android.view.Gravity.LEFT || horizontalGravity == android.view.Gravity.START) -> {
                        paddingLeft.toFloat() - bgPaddingX
                    }
                    (horizontalGravity == android.view.Gravity.RIGHT || horizontalGravity == android.view.Gravity.END) -> {
                        viewWidth - paddingRight.toFloat() - maxLineWidth - bgPaddingX
                    }
                    else -> { // Center
                        (viewWidth - bgWidth) / 2f
                    }
                }
                
                val top = (viewHeight - bgHeight) / 2f
                bgRect.set(left, top, left + bgWidth, top + bgHeight)
                canvas.drawRoundRect(bgRect, bgCornerRadius, bgCornerRadius, bgPaint)
            }
        }

        // Apply Gradient if set
        if (textGradientColors != null && textGradientColors!!.size > 1) {
            val colorsWithAlpha = textGradientColors!!.map { color ->
                androidx.core.graphics.ColorUtils.setAlphaComponent(color, textAlpha)
            }.toIntArray()
            
            // For a better look, we want the gradient box to ideally matches the text Layout bounds
            val textLayout = layout
            val gradWidth: Float
            val gradLeft: Float
            
            if (textLayout != null) {
                var maxLineWidth = 0f
                for (i in 0 until textLayout.lineCount) {
                    maxLineWidth = max(maxLineWidth, textLayout.getLineWidth(i))
                }
                gradWidth = maxLineWidth.coerceAtLeast(1f)
                
                val horizontalGravity = gravity and android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK
                gradLeft = when {
                    (horizontalGravity == android.view.Gravity.LEFT || horizontalGravity == android.view.Gravity.START) -> paddingLeft.toFloat()
                    (horizontalGravity == android.view.Gravity.RIGHT || horizontalGravity == android.view.Gravity.END) -> width - paddingRight.toFloat() - maxLineWidth
                    else -> (width - maxLineWidth) / 2f
                }
            } else {
                gradWidth = width.toFloat().coerceAtLeast(1f)
                gradLeft = 0f
            }
            
            paint.shader = android.graphics.LinearGradient(
                gradLeft, 0f, gradLeft + gradWidth, 0f,
                colorsWithAlpha, null, android.graphics.Shader.TileMode.CLAMP
            )
            // Note: setTextColor(WHITE) is moved to textGradientColors setter to avoid onDraw loops
            paint.color = androidx.core.graphics.ColorUtils.setAlphaComponent(Color.WHITE, textAlpha)
        } else {
            paint.shader = null
        }

        if (strokeWidth > 0 && strokeColor != Color.TRANSPARENT) {
            val originalStrokeWidth = paint.strokeWidth
            val originalStyle = paint.style
            val originalStrokeJoin = paint.strokeJoin
            val originalStrokeCap = paint.strokeCap
            val originalColor = paint.color
            val originalShader = paint.shader
            
            // 1. Draw stroke pass manually using Layout
            val textLayout = layout
            if (textLayout != null) {
                canvas.save()
                canvas.translate(totalPaddingLeft.toFloat(), totalPaddingTop.toFloat())
                
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = strokeWidth
                paint.strokeJoin = Paint.Join.ROUND
                paint.strokeCap = Paint.Cap.ROUND
                paint.color = strokeColor
                paint.shader = null 
                
                textLayout.draw(canvas)
                canvas.restore()
            }
            
            // 2. Draw fill pass and UI elements
            paint.style = originalStyle
            paint.strokeWidth = originalStrokeWidth
            paint.strokeJoin = originalStrokeJoin
            paint.strokeCap = originalStrokeCap
            paint.color = originalColor
            paint.shader = originalShader
            
            super.onDraw(canvas)
        } else {
            super.onDraw(canvas)
        }
    }
}


