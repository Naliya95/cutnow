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
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.ContextCompat
import com.cutnow.sdk.R
import com.cutnow.sdk.model.Keyframe
import kotlin.math.abs

class TimelineTrackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var layerId: String = ""
    var layerType: String = "TEXT" 
        set(value) {
        field = value
        updateColors()
        invalidate()
    }
    var label: String = ""
    var sourceStartTimeSec: Float = 0f
    var sourceDurationSec: Float = 0f
    var pixelsPerSecond: Float = 100f
        set(value) {
            if (field != value) {
                field = value
                invalidate()
                requestLayout()
            }
        }
    var waveformPoints: List<Float>? = null
        set(value) {
        field = value
        invalidate()
    }
    
    var keyframes: List<Keyframe> = emptyList()
        set(value) {
            field = value
            postInvalidate()
        }


    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f
        color = Color.WHITE
    }
    private val handlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 30f
        textAlign = Paint.Align.CENTER
    }
    private val keyframePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }
    private val keyframeBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }


    private val rect = RectF()
    private val handleWidth = 40f
    private var isSelectedLayer = false

    fun setIsSelected(selected: Boolean) {
        isSelectedLayer = selected
        borderPaint.alpha = if (selected) 255 else 100
        invalidate()
    }

    // Snapping configuration
    var playheadAbsX: Float = 0f // The absolute X position of the playhead on screen
    
    var scrollOffset: Int = 0    // Current scroll offset of the container
        set(value) {
            if (field != value) {
                field = value
                // Note: scrollOffset is used in touch calculations (line 310)
                // No visual invalidation needed as this doesn't affect rendering
            }
        }
    
    private val snapThreshold = 15f // Pixels to trigger a snap
    
    // Haptic feedback helper
    private fun performSnapFeedback() {
        performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY)
    }

    // Interaction states
    private var dragMode = DragMode.NONE
    private var lastTouchX = 0f
    private var lastTouchY = 0f

    enum class DragMode { NONE, TRIM_START, TRIM_END, MOVE }
    
    // External visibility of movement state
    var isCurrentlyMoving = false
        private set

    interface OnTrackChangeListener {
        fun onGestureStart()
        fun onTrimStart(dx: Float)
        fun onTrimEnd(dx: Float)
        fun onMove(dx: Float, dy: Float)
        fun onSelect()
        fun onReorderStart(offsetX: Float, offsetY: Float)
        fun onGestureEnd()
        /**
         * Optional: invoked when the user performs a quick double-tap on the track.
         * Default implementation is a no-op so existing listeners remain valid.
         */
        fun onDoubleTap() {}
    }

    var listener: OnTrackChangeListener? = null
    
    private val longPressHandler = android.os.Handler(android.os.Looper.getMainLooper())
    private var isLongPressTriggered = false
    private var isGestureInProgress = false


    init {
        updateColors()
        isFocusable = false
        isFocusableInTouchMode = false
    }





    private fun updateColors() {
        // CapCut style colors loaded from colors.xml
        val colorResId = when (layerType) {
            "TEXT" -> R.color.layer_text
            "STICKER" -> R.color.layer_sticker
            "AUDIO" -> R.color.layer_audio
            "EFFECT" -> R.color.layer_effect
            "FILTER" -> R.color.layer_filter
            "IMAGE" -> R.color.layer_image
            "VIDEO" -> R.color.layer_image // Use image color or a similar blue/purple
            else -> R.color.layer_default
        }
        bgPaint.color = ContextCompat.getColor(context, colorResId)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()

        if (w < 1 || h < 1) return

        rect.set(0f, 0f, w, h)
        
        // 1. Calculate absolute position for highlights
        val viewAbsX = this.x + ((parent as? android.view.ViewGroup)?.paddingLeft ?: 0) - scrollOffset
        val isPlayheadOver = playheadAbsX >= viewAbsX && playheadAbsX <= viewAbsX + w

        // 2. Draw Track Background with Playhead Highlight
        if (isPlayheadOver) {
            canvas.drawRoundRect(rect, 12f, 12f, bgPaint)
            // Add a subtle overlap glow
            val highlightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
                alpha = 20
                style = Paint.Style.FILL
            }
            canvas.drawRoundRect(rect, 12f, 12f, highlightPaint)
        } else {
            canvas.drawRoundRect(rect, 12f, 12f, bgPaint)
        }
        
        // 3. Draw Time 0 Guideline (Alignment Reference)
        val params = layoutParams as? android.view.ViewGroup.MarginLayoutParams
        val timeZeroInView = 60f * resources.displayMetrics.density - (params?.marginStart ?: 0)
        if (timeZeroInView > 0 && timeZeroInView < w) {
            val guidePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
                alpha = 40
                strokeWidth = 2f
            }
            canvas.drawLine(timeZeroInView, 0f, timeZeroInView, h, guidePaint)
        }

        // 4. Label & Icon
        if (label.isNotEmpty()) {
            val metrics = textPaint.fontMetrics
            val y = (h - metrics.descent - metrics.ascent) / 2
            val iconX = handleWidth + 20f
            
            // Draw Icon
            when(layerType) {
                "TEXT" -> {
                    val p = android.graphics.Path()
                    p.moveTo(iconX - 8f, y - 10f); p.lineTo(iconX + 8f, y - 10f)
                    p.moveTo(iconX, y - 10f); p.lineTo(iconX, y + 10f)
                    borderPaint.style = Paint.Style.STROKE; borderPaint.strokeWidth = 4f; borderPaint.color = Color.WHITE
                    canvas.drawPath(p, borderPaint)
                }
                "STICKER" -> canvas.drawCircle(iconX, y, 8f, handlePaint)
                "AUDIO" -> {
                    canvas.drawCircle(iconX - 4f, y + 6f, 6f, handlePaint)
                    canvas.drawLine(iconX + 2f, y + 6f, iconX + 2f, y - 10f, borderPaint)
                    canvas.drawLine(iconX + 2f, y - 10f, iconX + 10f, y - 6f, borderPaint)
                    
                    waveformPoints?.let { points ->
                        val wavePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; alpha = 140; style = Paint.Style.FILL }
                        val startX = handleWidth + 40f
                        val endX = w - handleWidth - 10f
                        val availableWaveWidth = endX - startX
                        if (availableWaveWidth > 10) {
                            val step = availableWaveWidth / points.size
                            points.forEachIndexed { index, amplitude ->
                                val barX = startX + index * step
                                val barH = (h * 0.5f) * amplitude.coerceIn(0.1f, 1.0f)
                                canvas.drawRect(barX, (h - barH) / 2f, barX + step * 0.7f, (h + barH) / 2f, wavePaint)
                            }
                        }
                    }
                }
                "EFFECT", "FILTER" -> {
                    val p = android.graphics.Path()
                    p.moveTo(iconX - 8f, y - 8f); p.lineTo(iconX + 8f, y - 8f)
                    p.lineTo(iconX + 4f, y + 8f); p.lineTo(iconX - 4f, y + 8f); p.close()
                    borderPaint.style = Paint.Style.STROKE; borderPaint.strokeWidth = 4f; borderPaint.color = Color.WHITE
                    canvas.drawPath(p, borderPaint)
                }
                "IMAGE" -> {
                    canvas.drawRect(iconX - 8f, y - 8f, iconX + 8f, y + 8f, borderPaint)
                    canvas.drawCircle(iconX - 2f, y - 2f, 2f, handlePaint)
                }
                "VIDEO" -> {
                    // Draw a small camera/video icon
                    val rectPath = android.graphics.Path()
                    rectPath.addRoundRect(iconX - 10f, y - 7f, iconX + 2f, y + 7f, 2f, 2f, android.graphics.Path.Direction.CW)
                    borderPaint.style = Paint.Style.STROKE; borderPaint.strokeWidth = 3f; borderPaint.color = Color.WHITE
                    canvas.drawPath(rectPath, borderPaint)
                    
                    val trianglePath = android.graphics.Path()
                    trianglePath.moveTo(iconX + 4f, y - 5f)
                    trianglePath.lineTo(iconX + 10f, y)
                    trianglePath.lineTo(iconX + 4f, y + 5f)
                    trianglePath.close()
                    borderPaint.style = Paint.Style.FILL
                    canvas.drawPath(trianglePath, borderPaint)
                }
            }
            
            // Draw Label
            val iconSpace = 40f
            val availableWidth = w - handleWidth * 2 - iconSpace
            val textToDraw = if (textPaint.measureText(label) > availableWidth) {
                var t = label
                while (t.isNotEmpty() && textPaint.measureText(t + "...") > availableWidth) t = t.dropLast(1)
                t + "..."
            } else label
            textPaint.textAlign = Paint.Align.LEFT
            textPaint.typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
            canvas.drawText(textToDraw, iconX + 40f, y, textPaint)
        }

        // 5. Draw Keyframes
        if (keyframes.isNotEmpty()) {
            val kfSize = 18f
            keyframes.forEach { kf ->
                val kfX = (kf.timeMs / 1000f) * pixelsPerSecond
                val relativeX = kfX - (params?.marginStart ?: 0)
                
                if (relativeX in -20f..(w + 20f)) {
                    val p = android.graphics.Path()
                    p.moveTo(relativeX, h / 2 - kfSize); p.lineTo(relativeX + kfSize, h / 2)
                    p.lineTo(relativeX, h / 2 + kfSize); p.lineTo(relativeX - kfSize, h / 2); p.close()
                    
                    val isSmoothed = kf.easeType != "LINEAR"
                    val isNearPlayhead = abs((viewAbsX + relativeX) - playheadAbsX) < 15f
                    
                    if (isNearPlayhead) {
                        val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; alpha = 100; style = Paint.Style.STROKE; strokeWidth = 6f }
                        canvas.drawPath(p, glowPaint)
                    }

                    if (isSmoothed) {
                         keyframePaint.color = ContextCompat.getColor(context, R.color.keyframe_active)
                         keyframePaint.style = Paint.Style.FILL; canvas.drawPath(p, keyframePaint)
                         keyframePaint.color = Color.WHITE; canvas.drawCircle(relativeX, h / 2, 3f, keyframePaint)
                    } else {
                         keyframePaint.color = ContextCompat.getColor(context, R.color.keyframe_active)
                         keyframePaint.style = Paint.Style.FILL; canvas.drawPath(p, keyframePaint)
                    }
                    canvas.drawPath(p, keyframeBorderPaint)
                }
            }
        }

        // 6. Selection Border & Handles
        if (isSelectedLayer) {
            borderPaint.color = Color.WHITE; borderPaint.strokeWidth = 4f; borderPaint.style = Paint.Style.STROKE
            rect.set(0f, 0f, w, h); canvas.drawRoundRect(rect, 12f, 12f, borderPaint)
            
            handlePaint.color = Color.WHITE; handlePaint.style = Paint.Style.FILL
            canvas.drawRoundRect(RectF(0f, 0f, handleWidth, h), 12f, 12f, handlePaint)
            canvas.drawRoundRect(RectF(w - handleWidth, 0f, w, h), 12f, 12f, handlePaint)
            
            borderPaint.strokeWidth = 2f; borderPaint.color = Color.parseColor("#666666")
            val cy = h / 2; val lh = 12f
            canvas.drawLine(handleWidth * 0.4f, cy - lh, handleWidth * 0.4f, cy + lh, borderPaint)
            canvas.drawLine(handleWidth * 0.6f, cy - lh, handleWidth * 0.6f, cy + lh, borderPaint)
            canvas.drawLine(w - handleWidth * 0.4f, cy - lh, w - handleWidth * 0.4f, cy + lh, borderPaint)
            canvas.drawLine(w - handleWidth * 0.6f, cy - lh, w - handleWidth * 0.6f, cy + lh, borderPaint)
        } else {
            borderPaint.color = Color.WHITE; borderPaint.alpha = 40; borderPaint.strokeWidth = 2f; borderPaint.style = Paint.Style.STROKE
            rect.set(0f, 0f, w, h); canvas.drawRoundRect(rect, 12f, 12f, borderPaint)
        }
    }

    // Drag Boundaries
    var minDragX: Float = 0f
    var maxDragX: Float = Float.MAX_VALUE

    private var touchDownX = 0f
    private var touchDownY = 0f // Added to track vertical movement for long-press cancellation
    private var touchDownLimitX = 0f
    private var dragStartMargin = 0
    private var dragStartWidth = 0
    private var lastTapTime: Long = 0L
    private val doubleTapTimeout: Long = ViewConfiguration.getDoubleTapTimeout().toLong()
    
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false
 
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchDownX = event.rawX
                touchDownY = event.rawY
                lastTouchX = event.rawX
                lastTouchY = event.rawY
                
                // Reset Interaction State
                longPressHandler.removeCallbacks(longPressRunnable)
                isLongPressTriggered = false
                isGestureInProgress = false
                
                // CRITICAL: Immediately request parent to NOT intercept. 
                // This prevents HorizontalScrollView from stealing the touch if we move slightly while waiting for long-press.
                parent?.requestDisallowInterceptTouchEvent(true)

                // Check if touching handles (Priority to Trimming)
                // Expanded touch for easier grabbing
                val localX = event.x
                val isLeftHandle = localX < handleWidth * 2f
                val isRightHandle = localX > width - handleWidth * 2f
                
                if (isSelectedLayer) {
                     if (isLeftHandle) {
                         dragMode = DragMode.TRIM_START
                         isGestureInProgress = true
                         listener?.onGestureStart()
                         
                         val params = layoutParams as? android.view.ViewGroup.MarginLayoutParams
                         dragStartMargin = params?.marginStart ?: 0
                         dragStartWidth = params?.width ?: 0
                         return true
                     } else if (isRightHandle) {
                         dragMode = DragMode.TRIM_END
                         isGestureInProgress = true
                         listener?.onGestureStart()
                         
                         val params = layoutParams as? android.view.ViewGroup.MarginLayoutParams
                         dragStartMargin = params?.marginStart ?: 0
                         dragStartWidth = params?.width ?: 0
                         return true
                     }
                }
 
                // If center area:
                // We ALWAYS schedule long-press now. If it's already selected, it will pick up.
                // If NOT selected, the long-press will trigger selection AND move.
                dragMode = DragMode.NONE
                longPressHandler.postDelayed(longPressRunnable, 400) // 400ms for responsiveness
                
                return true
            }
            
            MotionEvent.ACTION_MOVE -> {
                val dxTotal = abs(event.rawX - touchDownX)
                val dyTotal = abs(event.rawY - touchDownY)
                val dxRaw = event.rawX - lastTouchX
                val dyRaw = event.rawY - lastTouchY
                
                // Cancel LongPress if moved significantly (Horizontal or Vertical slop)
                if (!isLongPressTriggered && dragMode == DragMode.NONE) {
                    val slop = 20f // Standard touch slop
                    if (dxTotal > slop || dyTotal > slop) {
                        longPressHandler.removeCallbacks(longPressRunnable)
                        
                        // If we aren't dragging, let parent handle it (e.g. scroll)
                        if (dragMode == DragMode.NONE) {
                            parent?.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                }

                if (dragMode != DragMode.NONE || isLongPressTriggered) {
                     parent?.requestDisallowInterceptTouchEvent(true)
                }
                
                if (dragMode != DragMode.NONE) {
                     // We are dragging
                     parent?.requestDisallowInterceptTouchEvent(true)
                     
                     // Calculate Delta
                     var dx = dxRaw
                     
                     // Snapping Logic (Shared)
                     val container = parent as? android.view.ViewGroup
                     val paddingStart = container?.paddingStart ?: 0
                     val viewAbsX = this.x + paddingStart - scrollOffset
                     
                     if (abs(dx) > 0) {
                         // Apply Immediate Visual Update WITH CLAMPING
                         val params = layoutParams as? android.view.ViewGroup.MarginLayoutParams
                         if (params != null) {
                             isCurrentlyMoving = true
                             when (dragMode) {
                                 DragMode.TRIM_START -> {
                                    val currentEnd = dragStartMargin + dragStartWidth
                                    // Total accumulated dx from ACTION_DOWN
                                    val totalDx = event.rawX - touchDownX
                                    
                                    // Clamp start
                                    var newStart = dragStartMargin + totalDx
                                     
                                     // Clamp to Source Start if specified
                                     if (sourceDurationSec > 0) {
                                         val maxGrowthLeft = sourceStartTimeSec * pixelsPerSecond
                                         val minPossibleStart = dragStartMargin - maxGrowthLeft
                                         if (newStart < minPossibleStart) {
                                             newStart = minPossibleStart
                                         }
                                     }

                                    if (newStart < minDragX) newStart = minDragX
                                    if (newStart > currentEnd - 50) newStart = currentEnd - 50f // Min width
                                    
                                    val clampedDx = newStart - params.marginStart
                                    var effectiveDx = clampedDx

                                    // Snapping (Playhead) logic could override clamping if playhead is valid?
                                    // No, Boundary is absolute.
                                    
                                    // Calculate visual edge for snapping
                                    val visualEdgeX = viewAbsX + effectiveDx
                                    if (abs(visualEdgeX - playheadAbsX) < snapThreshold) {
                                         // Check if snap is within bounds
                                         val snapDx = playheadAbsX - viewAbsX
                                         val snapStart = params.marginStart + snapDx
                                         if (snapStart >= minDragX && snapStart <= currentEnd - 50) {
                                             effectiveDx = snapDx
                                             performSnapFeedback()
                                         }
                                    }
                                    
                                    // Update Layout Immediately
                                    if (abs(effectiveDx) > 0) {
                                        params.width = (params.width - effectiveDx.toInt()).coerceAtLeast(50)
                                        params.marginStart += effectiveDx.toInt()
                                        layoutParams = params
                                        listener?.onTrimStart(effectiveDx)
                                    }
                                }
                                DragMode.TRIM_END -> {
                                    val currentStart = dragStartMargin
                                    // Total accumulated dx from ACTION_DOWN
                                    val totalDx = event.rawX - touchDownX
                                    
                                    // Clamp end
                                     var newWidth = dragStartWidth + totalDx
                                     var newEnd = currentStart + newWidth
                                     
                                     // Clamp to Source Duration if specified
                                     if (sourceDurationSec > 0) {
                                         val remainingSourceSec = sourceDurationSec - sourceStartTimeSec
                                         val maxWidth = remainingSourceSec * pixelsPerSecond
                                         if (newWidth > maxWidth) {
                                             newWidth = maxWidth
                                             newEnd = currentStart + newWidth
                                         }
                                     }

                                     if (newEnd > maxDragX) {
                                         newEnd = maxDragX
                                         newWidth = newEnd - currentStart
                                     }
                                     if (newWidth < 50) newWidth = 50f
                                     
                                    val clampedDx = newWidth - params.width
                                    var effectiveDx = clampedDx

                                    val visualEdgeX = viewAbsX + width + effectiveDx
                                    if (abs(visualEdgeX - playheadAbsX) < snapThreshold) {
                                        val snapDx = playheadAbsX - (viewAbsX + width)
                                        val snapEnd = currentStart + params.width + snapDx
                                        if (snapEnd <= maxDragX && (snapEnd - currentStart) >= 50) {
                                            effectiveDx = snapDx
                                            performSnapFeedback()
                                        }
                                    }
                                    
                                    if (abs(effectiveDx) > 0) {
                                        params.width = (params.width + effectiveDx.toInt()).coerceAtLeast(50)
                                        layoutParams = params
                                        listener?.onTrimEnd(effectiveDx)
                                    }
                                }
                                DragMode.MOVE -> {
                                    var effectiveDx = dx
                                    var newStart = params.marginStart + dx
                                    
                                    // Clamp Bounds
                                    if (newStart < minDragX) newStart = minDragX
                                    if (newStart + params.width > maxDragX) newStart = maxDragX - params.width
                                    
                                    effectiveDx = newStart - params.marginStart
                                    
                                    // Snap start
                                    val visualStartX = viewAbsX + effectiveDx
                                    if (abs(visualStartX - playheadAbsX) < snapThreshold) {
                                        val snapDx = playheadAbsX - viewAbsX
                                        val snapStart = params.marginStart + snapDx
                                        if (snapStart >= minDragX && snapStart + params.width <= maxDragX) {
                                            effectiveDx = snapDx
                                            performSnapFeedback()
                                        }
                                    }
                                    
                                     if (abs(effectiveDx) > 0 || abs(dyRaw) > 0) {
                                         params.marginStart += effectiveDx.toInt()
                                         params.topMargin += dyRaw.toInt()
                                         layoutParams = params
                                         listener?.onMove(effectiveDx, dyRaw)
                                     }
                                }
                                else -> {}
                             }
                         }
                     }
                }
                
                lastTouchX = event.rawX
                lastTouchY = event.rawY
                return true
            }
            
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                longPressHandler.removeCallbacks(longPressRunnable)
                
                if (dragMode == DragMode.NONE && !isLongPressTriggered) {
                    // This was a Tap (no drag / long press)
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastTapTime <= doubleTapTimeout) {
                        // Double-tap detected
                        listener?.onDoubleTap()
                        lastTapTime = 0L
                    } else {
                        // Single tap -> select
                        listener?.onSelect()
                        lastTapTime = currentTime
                    }
                } else if (dragMode == DragMode.MOVE) {
                    // End Move
                    animate().scaleX(1f).scaleY(1f).duration = 150
                    alpha = 1f
                }
                
                dragMode = DragMode.NONE
                isCurrentlyMoving = false
                isLongPressTriggered = false
                parent?.requestDisallowInterceptTouchEvent(false)
                
                if (isGestureInProgress) {
                    listener?.onGestureEnd()
                    isGestureInProgress = false
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private val longPressRunnable = Runnable {
        isGestureInProgress = true
        listener?.onGestureStart() // Signal manipulation state immediately to suppress player resets
        isLongPressTriggered = true
        dragMode = DragMode.MOVE
        performHapticFeedback(android.view.HapticFeedbackConstants.LONG_PRESS)
        
        // Visual Feedback for "Linting"
        animate().scaleX(1.1f).scaleY(1.1f).duration = 150
        alpha = 0.8f
        
        parent?.requestDisallowInterceptTouchEvent(true)
    }
}


