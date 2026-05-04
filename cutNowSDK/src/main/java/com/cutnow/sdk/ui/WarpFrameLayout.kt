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
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout
import com.cutnow.sdk.model.TextWarpType
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class WarpFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var warpType: TextWarpType = TextWarpType.NONE
    private var warpIntensity: Float = 0.5f
    
    // Mesh settings
    private val meshWidth = 20
    private val meshHeight = 20
    private var verts = FloatArray((meshWidth + 1) * (meshHeight + 1) * 2)
    
    private var bufferBitmap: Bitmap? = null
    private var bufferCanvas: Canvas? = null

    fun setWarp(type: TextWarpType, intensity: Float) {
        if (this.warpType == type && this.warpIntensity == intensity) return
        this.warpType = type
        this.warpIntensity = intensity
        invalidate()
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (warpType == TextWarpType.NONE || childCount == 0) {
            super.dispatchDraw(canvas)
            return
        }

        val width = width.toFloat()
        val height = height.toFloat()
        if (width <= 0 || height <= 0) return

        // Prepare buffer
        if (bufferBitmap == null || bufferBitmap!!.width != width.toInt() || bufferBitmap!!.height != height.toInt()) {
            bufferBitmap?.recycle()
            bufferBitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
            bufferCanvas = Canvas(bufferBitmap!!)
        }

        // Draw children into buffer
        bufferBitmap!!.eraseColor(android.graphics.Color.TRANSPARENT)
        super.dispatchDraw(bufferCanvas!!)

        // Calculate mesh
        updateMesh(width, height)

        // Draw mesh
        canvas.drawBitmapMesh(bufferBitmap!!, meshWidth, meshHeight, verts, 0, null, 0, null)
    }

    private fun updateMesh(width: Float, height: Float) {
        val mappedIntensity = (warpIntensity - 0.5f) * 2f
        var index = 0
        for (y in 0..meshHeight) {
            val fy = y.toFloat() / meshHeight
            for (x in 0..meshWidth) {
                val fx = x.toFloat() / meshWidth
                
                var px = fx * width
                var py = fy * height
                
                when (warpType) {
                    TextWarpType.ARC -> {
                        val nx = (fx - 0.5f) * 2f
                        val bend = (1f - (nx * nx)) * height * mappedIntensity * 0.3f
                        py -= bend
                    }
                    TextWarpType.WAVE -> {
                        val angle = fx * Math.PI * 2
                        val waveAmplitude = height * 0.2f * mappedIntensity
                        py += (sin(angle) * waveAmplitude).toFloat()
                    }
                    TextWarpType.BULGE -> {
                        val nx = (fx - 0.5f) * 2f
                        val ny = (fy - 0.5f) * 2f
                        val dist = sqrt((nx * nx + ny * ny).toDouble()).toFloat()
                        if (dist < 1f) {
                            val factor = (1f - dist) * mappedIntensity * 0.3f
                            px += px * factor * nx
                            py += py * factor * ny
                        }
                    }
                    TextWarpType.FLAG -> {
                        val angle = fy * Math.PI * 1.5 * mappedIntensity
                        px += (sin(angle) * width * 0.1f).toFloat()
                    }
                    TextWarpType.TWIST -> {
                        val nx = fx - 0.5f
                        val ny = fy - 0.5f
                        val r = sqrt((nx * nx + ny * ny).toDouble()).toFloat()
                        val angle = r * Math.PI * mappedIntensity
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
                            val f = (1f + r * r * 0.3f * mappedIntensity)
                            px = (nx * f * 0.5f + 0.5f) * width
                            py = (ny * f * 0.5f + 0.5f) * height
                        }
                    }
                    TextWarpType.SQUEEZE -> {
                        val nx = (fx - 0.5f) * 2f
                        val ny = (fy - 0.5f) * 2f
                        val r = sqrt((nx * nx + ny * ny).toDouble()).toFloat()
                        if (r < 1f) {
                            val f = (1f - (1f - r) * 0.3f * mappedIntensity)
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
    }
}


