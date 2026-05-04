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

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.cutnow.sdk.R
import com.cutnow.sdk.model.MediaLayer
import com.cutnow.sdk.model.KeyframeProperty
import com.cutnow.sdk.engine.KeyframeEngine
import kotlin.math.*

/**
 * Overlay view that provides interaction handles (Reset, Rotate, Scale, Flip)
 * for a selected sticker/text layer.
 */
class StickerControlOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var selectedLayer: MediaLayer? = null
    private var videoRatio: Float = 1f
    private var currentTimeMs: Long = 0L
    
    var showDelete: Boolean = true
    var showFlip: Boolean = true
    var showRotate: Boolean = true
    var showScale: Boolean = true

    
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 3f
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }

    private val handlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        setShadowLayer(4f, 0f, 2f, Color.BLACK)
    }

    private val flipIcon = ContextCompat.getDrawable(context, R.drawable.ic_flip_sticker)
    private val rotateIcon = ContextCompat.getDrawable(context, R.drawable.ic_rotate_sticker)
    private val zoomIcon = ContextCompat.getDrawable(context, R.drawable.ic_zoom_sticker)
    private val deleteIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_close_clear_cancel)

    private val handleRadius = 24f
    private val dragSensitivityMultiplier = 1.0f // Restored to 1.0f based on user feedback for more speed
    
    // Calculated bounds in pixel coordinates
    private var stickerRect = RectF()

    private var activeHandle: HandleType = HandleType.NONE
    
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    
    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            // Inverse transform touch coordinates using INTERPOLATED values
            val w = width.toFloat()
            val h = height.toFloat()
            val layer = selectedLayer ?: return false
            
            val currentPosX = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posX, KeyframeProperty.POS_X)
            val currentPosY = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posY, KeyframeProperty.POS_Y)
            val currentRotation = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.rotation, KeyframeProperty.ROTATION)

            val centerX = w * currentPosX
            val centerY = h * currentPosY
            
            val dx = e.x - centerX
            val dy = e.y - centerY
            
            val rad = -Math.toRadians(currentRotation.toDouble())
            val localX = (dx * cos(rad) - dy * sin(rad)).toFloat()
            val localY = (dx * sin(rad) + dy * cos(rad)).toFloat()
            
            if (stickerRect.contains(localX, localY)) {
                listener?.onDoubleTap()
                return true
            }
            return false
        }
        override fun onLongPress(e: MotionEvent) {
            val layer = selectedLayer ?: return
            
            // Inverse transform check
            val w = width.toFloat()
            val h = height.toFloat()
            val currentPosX = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posX, KeyframeProperty.POS_X)
            val currentPosY = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posY, KeyframeProperty.POS_Y)
            val currentRotation = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.rotation, KeyframeProperty.ROTATION)

            val centerX = w * currentPosX
            val centerY = h * currentPosY
            
            val dx = e.x - centerX
            val dy = e.y - centerY
            val rad = -Math.toRadians(currentRotation.toDouble())
            val localX = (dx * cos(rad) - dy * sin(rad)).toFloat()
            val localY = (dx * sin(rad) + dy * cos(rad)).toFloat()
            
            if (stickerRect.contains(localX, localY)) {
                listener?.onLongPress()
            }
        }
    })
    
    enum class HandleType { NONE, BODY, FLIP, ROTATE, ZOOM, DELETE }

    interface OnTransformationListener {
        fun onGestureStart()
        fun onMove(dx: Float, dy: Float)
        fun onRotate(deltaAngle: Float)
        fun onScale(scaleFactor: Float)
        fun onFlip()
        fun onDelete()
        fun onDoubleTap()
        fun onLongPress()
        fun onCommit()
    }

    var listener: OnTransformationListener? = null

    fun update(layer: MediaLayer?, ratio: Float, timeMs: Long = 0L) {
        this.selectedLayer = layer
        this.videoRatio = ratio
        this.currentTimeMs = timeMs
        visibility = if (layer != null) VISIBLE else GONE
        invalidate()
    }

    fun isHandlingTouch(): Boolean = activeHandle != HandleType.NONE
    
    fun getActiveHandle(): HandleType = activeHandle

    fun cancelInteraction() {
        activeHandle = HandleType.NONE
        invalidate()
    }

    fun isPointInOverlay(x: Float, y: Float): Boolean {
        if (selectedLayer == null || visibility != VISIBLE) return false
        
        // Use the same logic as getHandleAt, but with raw coordinates
        // Actually, we need to inverse transform the coordinates first
        val w = width.toFloat()
        val h = height.toFloat()
        if (w <= 0 || h <= 0) return false
        
        val layer = selectedLayer!!
        val currentPosX = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posX, KeyframeProperty.POS_X)
        val currentPosY = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posY, KeyframeProperty.POS_Y)
        val currentRotation = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.rotation, KeyframeProperty.ROTATION)

        val centerX = w * currentPosX
        val centerY = h * currentPosY
        
        val dx = x - centerX
        val dy = y - centerY
        
        val rad = -Math.toRadians(currentRotation.toDouble())
        val localX = (dx * cos(rad) - dy * sin(rad)).toFloat()
        val localY = (dx * sin(rad) + dy * cos(rad)).toFloat()
        
        return getHandleAt(localX, localY) != HandleType.NONE
    }

    override fun onDraw(canvas: Canvas) {
        val layer = selectedLayer ?: return
        val w = width.toFloat()
        val h = height.toFloat()
        if (w <= 0 || h <= 0) return

        // Interpolate properties if keyframes exist
        val currentPosX = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posX, KeyframeProperty.POS_X)
        val currentPosY = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posY, KeyframeProperty.POS_Y)
        val currentScale = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.scaleX, KeyframeProperty.SCALE)
        val currentRotation = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.rotation, KeyframeProperty.ROTATION)

        // Match the GL renderer's sticker sizing:
        //   GL uses baseOverlayScale = 0.3 * viewRatio, and layer.scaleX is the kfScale multiplier.
        //   Converted to screen pixels: width = viewWidth * 0.3 * kfScale
        //   Height preserves the sticker's original pixel aspect ratio.
        val stickerAspect = if (layer.originalHeight > 0f) layer.originalWidth / layer.originalHeight else 1f
        val scaledWidth = w * 0.3f * currentScale
        val scaledHeight = if (stickerAspect > 0f) scaledWidth / stickerAspect else scaledWidth
        
        val centerX = w * currentPosX
        val centerY = h * currentPosY
        
        canvas.save()
        canvas.translate(centerX, centerY)
        canvas.rotate(currentRotation)
        
        val left = -scaledWidth / 2f
        val top = -scaledHeight / 2f
        val right = scaledWidth / 2f
        val bottom = scaledHeight / 2f
        
        stickerRect.set(left, top, right, bottom)
        
        // Draw Border
        canvas.drawRect(stickerRect, borderPaint)
        
        // Draw Handles
        if (showFlip) drawHandle(canvas, left, top, flipIcon, HandleType.FLIP)       // Top-Left
        if (showRotate) drawHandle(canvas, left, bottom, rotateIcon, HandleType.ROTATE) // Bottom-Left
        if (showScale) drawHandle(canvas, right, bottom, zoomIcon, HandleType.ZOOM)    // Bottom-Right
        if (showDelete) drawHandle(canvas, right, top, deleteIcon, HandleType.DELETE)   // Top-Right

        // Draw Keyframe Indicator if active at this time
        val hasDirectKeyframe = layer.keyframes.any { abs(it.timeMs - currentTimeMs) < 33L }
        if (hasDirectKeyframe) {
            val kfSize = 16f
            val kfPath = Path().apply {
                moveTo(0f, top - kfSize)
                lineTo(kfSize, top)
                lineTo(0f, top + kfSize)
                lineTo(-kfSize, top)
                close()
            }
            val kfPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = ContextCompat.getColor(context, R.color.keyframe_active)
                style = Paint.Style.FILL
            }
            canvas.drawPath(kfPath, kfPaint)
            
            val kfBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.BLACK
                style = Paint.Style.STROKE
                strokeWidth = 2f
            }
            canvas.drawPath(kfPath, kfBorder)
        }
        
        canvas.restore()
    }

    private fun drawHandle(canvas: Canvas, x: Float, y: Float, icon: android.graphics.drawable.Drawable?, type: HandleType) {
        canvas.drawCircle(x, y, handleRadius, handlePaint)
        icon?.let {
            val iconSize = (handleRadius * 1.2f).toInt()
            it.setBounds((x - iconSize/2).toInt(), (y - iconSize/2).toInt(), (x + iconSize/2).toInt(), (y + iconSize/2).toInt())
            it.draw(canvas)
        }
        
        // Update Hit Rects (need to be in rotated/translated space? 
        // No, we'll handle hit testing by inverse-transforming the touch)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val layer = selectedLayer ?: return false
        
        // Let GestureDetector check for double tap
        if (gestureDetector.onTouchEvent(event)) return true
        
        // Inverse transform touch coordinates using INTERPOLATED values
        val w = width.toFloat()
        val h = height.toFloat()
        
        val currentPosX = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posX, KeyframeProperty.POS_X)
        val currentPosY = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.posY, KeyframeProperty.POS_Y)
        val currentRotation = KeyframeEngine.interpolate(layer.keyframes, currentTimeMs, layer.rotation, KeyframeProperty.ROTATION)

        val centerX = w * currentPosX
        val centerY = h * currentPosY
        
        val dx = event.x - centerX
        val dy = event.y - centerY
        
        // Rotate touch vector in opposite direction of layer rotation
        val rad = -Math.toRadians(currentRotation.toDouble())
        val localX = (dx * cos(rad) - dy * sin(rad)).toFloat()
        val localY = (dx * sin(rad) + dy * cos(rad)).toFloat()
        
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                activeHandle = getHandleAt(localX, localY)
                if (activeHandle != HandleType.NONE) {
                    parent?.requestDisallowInterceptTouchEvent(true)
                    listener?.onGestureStart()
                    lastTouchX = event.x
                    lastTouchY = event.y
                    return true
                }
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                if (activeHandle == HandleType.NONE) return false
                
                parent?.requestDisallowInterceptTouchEvent(true)
                val deltaX = event.x - lastTouchX
                val deltaY = event.y - lastTouchY
                
                when (activeHandle) {
                    HandleType.BODY -> {
                        listener?.onMove((deltaX * dragSensitivityMultiplier) / w, (deltaY * dragSensitivityMultiplier) / h)
                    }
                    HandleType.ZOOM -> {
                        // Calculate distance from center to current touch vs previous touch
                        val distPrev = sqrt(lastTouchX.pow(2) + lastTouchY.pow(2)) // This is wrong, need local dist
                        val distNew = sqrt(localX.pow(2) + localY.pow(2))
                        val initialDist = sqrt((stickerRect.width()/2).pow(2) + (stickerRect.height()/2).pow(2))
                        
                        // Simple proportional scaling based on radial distance
                        val factor = distNew / initialDist
                        // We need to pass the DELTA or the target? listener expects delta typically in our current editor.
                        // Actually, let's just use the ratio of distance change
                        val oldDist = sqrt((localX - deltaX).pow(2) + (localY - deltaY).pow(2))
                        if (oldDist > 0) {
                            listener?.onScale(distNew / oldDist)
                        }
                    }
                    HandleType.ROTATE -> {
                        val anglePrev = atan2(localY - deltaY, localX - deltaX)
                        val angleNew = atan2(localY, localX)
                        listener?.onRotate(Math.toDegrees((angleNew - anglePrev).toDouble()).toFloat())
                    }
                    else -> {}
                }
                
                lastTouchX = event.x
                lastTouchY = event.y
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (activeHandle == HandleType.FLIP && getHandleAt(localX, localY) == HandleType.FLIP) {
                    listener?.onFlip()
                } else if (activeHandle == HandleType.DELETE && getHandleAt(localX, localY) == HandleType.DELETE) {
                    listener?.onDelete()
                }
                
                if (activeHandle != HandleType.NONE) {
                    listener?.onCommit()
                }
                parent?.requestDisallowInterceptTouchEvent(false)
                activeHandle = HandleType.NONE
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun getHandleAt(x: Float, y: Float): HandleType {
        if (showFlip && dist(x, y, stickerRect.left, stickerRect.top) < handleRadius * 1.5f) return HandleType.FLIP
        if (showRotate && dist(x, y, stickerRect.left, stickerRect.bottom) < handleRadius * 1.5f) return HandleType.ROTATE
        if (showScale && dist(x, y, stickerRect.right, stickerRect.bottom) < handleRadius * 1.5f) return HandleType.ZOOM
        if (showDelete && dist(x, y, stickerRect.right, stickerRect.top) < handleRadius * 1.5f) return HandleType.DELETE
        
        if (stickerRect.left - 20f <= x && x <= stickerRect.right + 20f &&
            stickerRect.top - 20f <= y && y <= stickerRect.bottom + 20f) return HandleType.BODY
        
        return HandleType.NONE
    }

    private fun dist(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt((x1 - x2).pow(2) + (y1 - y2).pow(2))
    }
}


