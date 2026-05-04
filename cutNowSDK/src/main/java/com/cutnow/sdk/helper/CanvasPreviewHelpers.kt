/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.helper

import com.cutnow.sdk.R

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import com.cutnow.sdk.model.TextWarpType
import com.cutnow.sdk.util.SinhalaConverter
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Helper utilities for Canvas Preview system
 */
object CanvasPreviewHelpers {
    
    /**
     * Create emoji bitmap for sticker display
     */
    fun createEmojiBitmap(emoji: String, width: Int, height: Int): Bitmap? {
        return try {
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                textSize = width * 0.8f
                textAlign = Paint.Align.CENTER
            }
            val x = width / 2f
            val y = height / 2f - (paint.descent() + paint.ascent()) / 2f
            canvas.drawText(emoji, x, y, paint)
            bitmap
        } catch (e: Exception) {
            null
        }
    }

    fun createTextBitmap(
        text: String,
        fontSize: Float,
        textColor: Int,
        fontFamily: String,
        strokeColor: Int,
        strokeWidth: Float,
        backgroundColor: Int,
        videoWidth: Int,
        videoHeight: Int,
        revealChars: Int = -1,
        gradientColors: IntArray? = null,
        warpType: TextWarpType = TextWarpType.NONE,
        warpIntensity: Float = 0.5f,
        textBgPaddingX: Float = -1f,
        textBgPaddingY: Float = -1f,
        textBgCornerRadius: Float = -1f,
        textAlignment: String = "CENTER",
        context: android.content.Context? = null
    ): Bitmap? {
        return try {
            val alignment = when (textAlignment.uppercase()) {
                "LEFT" -> android.text.Layout.Alignment.ALIGN_NORMAL
                "RIGHT" -> android.text.Layout.Alignment.ALIGN_OPPOSITE
                else -> android.text.Layout.Alignment.ALIGN_CENTER
            }

            // 1. Resolution Normalization Logic
            // Reference dimension = 1080 (matching EditorFragment preview)
            val referenceDim = 1080f
            val currentMaxDim = maxOf(videoWidth, videoHeight).toFloat().coerceAtLeast(1f)
            val resScale = currentMaxDim / referenceDim
            
            // 2. Scaled Properties
            val scaledFontSize = fontSize * resScale
            val scaledStrokeWidth = strokeWidth * resScale
            
            val displayTextRaw = if (revealChars >= 0) text.take(revealChars) else text
            
            // Handle Sinhala Legacy Font Conversion with Punctuation Support
            val displayText: CharSequence = if (context != null && SinhalaConverter.isSinhalaLegacyFont(fontFamily)) {
                SinhalaConverter.unicodeToLegacySpannable(displayTextRaw, context, fontFamily)
            } else {
                displayTextRaw
            }

            if (displayText.isEmpty() && text.isNotEmpty() && revealChars == 0) {
                // Return a tiny transparent bitmap instead of null to avoid error logs
                return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            }

            // 3. Setup Paint
            val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
                this.color = textColor
                this.textSize = scaledFontSize
                if (fontFamily.isNotEmpty() && fontFamily != "sans-serif") {
                    if (context != null && fontFamily.startsWith("fonts/") && fontFamily.endsWith(".ttf")) {
                        try {
                            this.typeface = android.graphics.Typeface.createFromAsset(context.assets, fontFamily)
                        } catch (e: Exception) {
                            this.typeface = android.graphics.Typeface.create(fontFamily, android.graphics.Typeface.NORMAL)
                        }
                    } else {
                        this.typeface = android.graphics.Typeface.create(fontFamily, android.graphics.Typeface.NORMAL)
                    }
                } else {
                    this.typeface = android.graphics.Typeface.DEFAULT
                }
            }
            
            val maxTextWidth = (videoWidth * 0.9f).toInt().coerceAtLeast(100) // wrap at 90% of screen
            
            // Build StaticLayout
            val staticLayout = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                android.text.StaticLayout.Builder.obtain(displayText, 0, displayText.length, paint, maxTextWidth)
                    .setAlignment(alignment)
                    .setLineSpacing(0f, 1f)
                    .setIncludePad(true)
                    .build()
            } else {
                @Suppress("DEPRECATION")
                android.text.StaticLayout(displayText, paint, maxTextWidth, alignment, 1f, 0f, true)
            }
            
            // Measure actual width and finding the leftmost bound
            var minLineLeft = Float.MAX_VALUE
            var maxLineWidth = 0f
            for (i in 0 until staticLayout.lineCount) {
                minLineLeft = minOf(minLineLeft, staticLayout.getLineLeft(i))
                maxLineWidth = maxOf(maxLineWidth, staticLayout.getLineWidth(i))
            }
            val actualWidth = maxLineWidth.toInt().coerceAtLeast(1)
            
            // 4. Calculate Scaled Padding and Stroke Buffer
            val basePaddingX = if (textBgPaddingX >= -50f) (textBgPaddingX * resScale).toInt() else (scaledFontSize * 0.5f).toInt()
            val basePaddingY = if (textBgPaddingY >= -50f) (textBgPaddingY * resScale).toInt() else (scaledFontSize * 0.5f).toInt()
            
            // Stroke margin: stroke extends strokeWidth/2 beyond the text.
            // We ensure padding is at least strokeWidth/2 + a small safety buffer (2px).
            val strokeBuffer = if (scaledStrokeWidth > 0f) (scaledStrokeWidth / 2f).toInt() + 2 else 0
            val paddingX = maxOf(basePaddingX, strokeBuffer)
            val paddingY = maxOf(basePaddingY, strokeBuffer)
            
            val cornerRadius = if (textBgCornerRadius >= 0f) (textBgCornerRadius * resScale) else max(0, max(paddingX, paddingY)).toFloat()
            
            // Bitmap size is based on the actual text content + padding
            val bgWidth = actualWidth + paddingX * 2
            val bgHeight = staticLayout.height + paddingY * 2
            
            val bitmapWidth = bgWidth.coerceAtLeast(1)
            val bitmapHeight = bgHeight.coerceAtLeast(1)
            
            val bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            
            // Draw Background
            if (backgroundColor != android.graphics.Color.TRANSPARENT) {
                val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = backgroundColor
                    style = Paint.Style.FILL
                }
                canvas.drawRoundRect(0f, 0f, bitmapWidth.toFloat(), bitmapHeight.toFloat(), cornerRadius, cornerRadius, bgPaint)
            }
            
            // Position the layout so the visible text starts at paddingX
            // StaticLayout might have internal padding/offsets depending on alignment
            val textX = paddingX - minLineLeft 
            val textY = paddingY.toFloat()
            canvas.translate(textX, textY)

            // 175: Add Gradient Setup
            if (gradientColors != null && gradientColors.size >= 2) {
                val alpha = android.graphics.Color.alpha(textColor)
                val colorsWithAlpha = if (alpha < 255) {
                    gradientColors.map { color ->
                        androidx.core.graphics.ColorUtils.setAlphaComponent(color, alpha)
                    }.toIntArray()
                } else {
                    gradientColors
                }
                
                val positions = FloatArray(colorsWithAlpha.size) { it / (colorsWithAlpha.size - 1).toFloat() }
                paint.shader = android.graphics.LinearGradient(
                    0f, 0f, maxTextWidth.toFloat(), 0f,
                    colorsWithAlpha, positions, android.graphics.Shader.TileMode.CLAMP
                )
            }

            // Draw Stroke (Behind Fill)
            if (scaledStrokeWidth > 0f && strokeColor != android.graphics.Color.TRANSPARENT) {
                val originalShader = paint.shader
                val originalStyle = paint.style
                val originalStrokeWidth = paint.strokeWidth
                val originalColor = paint.color

                paint.shader = null
                paint.style = android.graphics.Paint.Style.STROKE
                paint.strokeWidth = scaledStrokeWidth
                paint.color = strokeColor
                paint.strokeJoin = android.graphics.Paint.Join.ROUND
                paint.strokeCap = android.graphics.Paint.Cap.ROUND

                staticLayout.draw(canvas)

                // Restore for Fill
                paint.shader = originalShader
                paint.style = originalStyle
                paint.strokeWidth = originalStrokeWidth
                paint.color = originalColor
            }
            
            // Draw Fill
            staticLayout.draw(canvas)
            
            if (warpType != TextWarpType.NONE && warpIntensity != 0.5f) {
                return applyWarpToBitmap(bitmap, warpType, warpIntensity)
            }
            
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun applyWarpToBitmap(source: Bitmap, warpType: TextWarpType, intensity: Float): Bitmap {
        // intensity is roughly 0.0 to 1.0 (0.5 is neutral)
        // transform to -1.0 to 1.0
        val mappedIntensity = (intensity - 0.5f) * 2f
        if (mappedIntensity == 0f || warpType == TextWarpType.NONE) return source
        
        val width = source.width
        val height = source.height
        
        // Define mesh resolution
        val meshWidth = 40
        val meshHeight = 40
        val verts = FloatArray((meshWidth + 1) * (meshHeight + 1) * 2)
        
        var index = 0
        for (y in 0..meshHeight) {
            val fy = y.toFloat() / meshHeight
            for (x in 0..meshWidth) {
                val fx = x.toFloat() / meshWidth
                
                // Base coordinates aligned to the top-left of the original image
                var px = fx * width
                var py = fy * height
                
                // Distort based on type
                when (warpType) {
                    TextWarpType.ARC -> {
                        val normalizedX = (fx - 0.5f) * 2f
                        val bend = (1f - (normalizedX * normalizedX)) * height * mappedIntensity * 0.5f
                        py -= bend
                    }
                    TextWarpType.WAVE -> {
                        val angle = fx * Math.PI * 2 * 1.0 // 1 wave
                        val waveAmplitude = height * 0.3f * mappedIntensity
                        py += (sin(angle) * waveAmplitude).toFloat()
                    }
                    TextWarpType.BULGE -> {
                        val normalizedX = (fx - 0.5f) * 2f
                        val normalizedY = (fy - 0.5f) * 2f
                        val distance = sqrt((normalizedX * normalizedX + normalizedY * normalizedY).toDouble()).toFloat()
                        if (distance < 1f) {
                            val bulgeFactor = (1f - distance) * mappedIntensity * 0.5f
                            px += px * bulgeFactor * normalizedX
                            py += py * bulgeFactor * normalizedY
                        }
                    }
                    TextWarpType.FLAG -> {
                        val angle = fy * Math.PI * 1.5 * mappedIntensity
                        px += (sin(angle) * width * 0.1f).toFloat()
                    }
                    TextWarpType.TWIST -> {
                        val nx = fx - 0.5f
                        val ny = fy - 0.5f
                        val radius = sqrt((nx * nx + ny * ny).toDouble()).toFloat()
                        val angle = radius * Math.PI * mappedIntensity * 2.0
                        val cos = cos(angle).toFloat()
                        val sin = sin(angle).toFloat()
                        px = (nx * cos - ny * sin + 0.5f) * width
                        py = (nx * sin + ny * cos + 0.5f) * height
                    }
                    TextWarpType.FISHEYE -> {
                        val nx = (fx - 0.5f) * 2f
                        val ny = (fy - 0.5f) * 2f
                        val r = sqrt((nx * nx + ny * ny).toDouble()).toFloat()
                        if (r < 1f) {
                            val f = (1f + r * r * 0.5f * mappedIntensity)
                            px = (nx * f * 0.5f + 0.5f) * width
                            py = (ny * f * 0.5f + 0.5f) * height
                        }
                    }
                    TextWarpType.SQUEEZE -> {
                        val nx = (fx - 0.5f) * 2f
                        val ny = (fy - 0.5f) * 2f
                        val r = sqrt((nx * nx + ny * ny).toDouble()).toFloat()
                        if (r < 1f) {
                            val f = (1f - (1f - r) * 0.5f * mappedIntensity)
                            px = (nx * f * 0.5f + 0.5f) * width
                            py = (ny * f * 0.5f + 0.5f) * height
                        }
                    }
                    else -> {}
                }
                
                verts[index * 2] = px
                verts[index * 2 + 1] = py
                index++
            }
        }
        
        // Find bounds of the new mesh to create a properly sized output bitmap
        var minX = Float.MAX_VALUE
        var maxX = Float.MIN_VALUE
        var minY = Float.MAX_VALUE
        var maxY = Float.MIN_VALUE
        
        for (i in verts.indices step 2) {
            val vx = verts[i]
            val vy = verts[i+1]
            if (vx < minX) minX = vx
            if (vx > maxX) maxX = vx
            if (vy < minY) minY = vy
            if (vy > maxY) maxY = vy
        }
        
        val newWidth = (maxX - minX).toInt().coerceAtLeast(1)
        val newHeight = (maxY - minY).toInt().coerceAtLeast(1)
        
        // Shift vertices so minX and minY are at 0
        for (i in verts.indices step 2) {
            verts[i] -= minX
            verts[i+1] -= minY
        }
        
        val resultBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap)
        
        canvas.drawBitmapMesh(source, meshWidth, meshHeight, verts, 0, null, 0, null)
        
        // Don't recycle source here in case it's used elsewhere, 
        // but normally we'd want to manage memory if this is intermediate.
        // For our pipeline, we will just return the new one. 
        return resultBitmap
    }
}
