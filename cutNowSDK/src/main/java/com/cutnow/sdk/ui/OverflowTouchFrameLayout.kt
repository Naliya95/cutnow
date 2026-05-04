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
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * A FrameLayout that dispatches touch events to children even when they
 * overflow the parent's bounds. Standard Android only dispatches touches
 * within a View's measured bounds; this override fixes that for the
 * 0-width transition icon container used in the CapCut-style timeline.
 */
class OverflowTouchFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val childRect = Rect()

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean = false

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // Check if the touch lands on any child (even if outside our bounds)
        for (i in childCount - 1 downTo 0) {
            val child = getChildAt(i)
            if (child.visibility != VISIBLE) continue

            // Get the child's actual draw position (which may overflow our bounds)
            childRect.set(
                child.left,
                child.top,
                child.left + child.width,
                child.top + child.height
            )

            val x = ev.x
            val y = ev.y
            if (childRect.contains(x.toInt(), y.toInt())) {
                // Translate touch to child coordinates and dispatch
                val childX = x - child.left
                val childY = y - child.top
                ev.offsetLocation(-child.left.toFloat(), -child.top.toFloat())
                val handled = child.dispatchTouchEvent(ev)
                ev.offsetLocation(child.left.toFloat(), child.top.toFloat())
                if (handled) return true
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}


