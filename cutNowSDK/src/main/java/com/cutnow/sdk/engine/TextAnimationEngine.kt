/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.engine

import com.cutnow.sdk.model.*
import kotlin.math.pow


/**
 * Computes per-frame transforms for animated text layers.
 *
 * This is the single source of truth for text animation timing so that
 * Canvas preview and Media3 export stay perfectly in sync.
 */
object TextAnimationEngine {

    data class TextTransform(
        val alpha: Float = 1f,
        val offsetXPx: Float = 0f,
        val offsetYPx: Float = 0f,
        val scaleX: Float = 1f,
        val scaleY: Float = 1f,
        val rotation: Float = 0f,
        val revealChars: Int = -1,
        val cursorVisible: Boolean = false
    )

    fun computeTextTransform(
        layer: MediaLayer,
        timeMs: Long,
        surfaceHeight: Int
    ): TextTransform {
        if (layer.type != LayerType.TEXT && layer.type != LayerType.STICKER && layer.type != LayerType.IMAGE && layer.type != LayerType.VIDEO) return TextTransform()

        // 0. Global Time Checks
        val start = layer.startTimeMs
        val end = layer.endTimeMs
        if (timeMs < start || timeMs > end) {
            return TextTransform(alpha = 0f)
        }

        val duration = layer.durationMs
        val relativeTime = timeMs - start

        // 1. Initialize Base Transform
        var alpha = 1f
        var offsetX = 0f
        var offsetY = 0f
        var scaleX = 1f
        var scaleY = 1f
        var rotation = 0f
        var revealChars = -1
        var cursorVisible = false

        val baseOffset = surfaceHeight * 0.12f

        // --- PHASE 1: IN ANIMATION ---
        val inDuration = layer.animInDuration.coerceAtLeast(0)
        // Legacy fallback: if new animationIn is NONE but old textAnimation is set, map it
        var animInType = layer.animationIn
        if (animInType == TextAnimationType.NONE && layer.textAnimation != TextAnimationType.NONE) {
            // Map legacy types to In types
            animInType = when (layer.textAnimation) {
                 TextAnimationType.FADE, TextAnimationType.FADE_IN -> TextAnimationType.FADE_IN
                 TextAnimationType.SLIDE_UP -> TextAnimationType.SLIDE_UP
                 TextAnimationType.SLIDE_DOWN -> TextAnimationType.SLIDE_DOWN
                 TextAnimationType.SLIDE_LEFT -> TextAnimationType.SLIDE_LEFT
                 TextAnimationType.SLIDE_RIGHT -> TextAnimationType.SLIDE_RIGHT
                 TextAnimationType.CURSOR -> TextAnimationType.CURSOR
                 else -> TextAnimationType.NONE
            }
        }

        if (relativeTime < inDuration && animInType != TextAnimationType.NONE) {
            val t = (relativeTime.toFloat() / inDuration).coerceIn(0f, 1f)
            
            when (animInType) {
                TextAnimationType.FADE_IN -> alpha *= t
                TextAnimationType.SLIDE_UP -> {
                    offsetY += (1f - easeOutCubic(t)) * baseOffset
                    alpha *= t
                }
                TextAnimationType.SLIDE_DOWN -> {
                    offsetY -= (1f - easeOutCubic(t)) * baseOffset
                    alpha *= t
                }
                TextAnimationType.SLIDE_LEFT -> {
                    offsetX += (1f - easeOutCubic(t)) * baseOffset
                    alpha *= t
                }
                TextAnimationType.SLIDE_RIGHT -> {
                    offsetX -= (1f - easeOutCubic(t)) * baseOffset
                    alpha *= t
                }
                TextAnimationType.ZOOM_IN -> {
                    val s = easeOutBack(t)
                    scaleX *= s
                    scaleY *= s
                    alpha *= t
                }
                TextAnimationType.BOUNCE_IN -> {
                    val s = easeOutBounce(t)
                    scaleX *= s
                    scaleY *= s
                    alpha *= t
                }
                TextAnimationType.ELASTIC_IN -> {
                    val s = easeOutElastic(t)
                    scaleX *= s
                    scaleY *= s
                    alpha *= t
                }
                TextAnimationType.SPIN_IN -> {
                    rotation += (1f - easeOutBack(t)) * -360f
                    scaleX *= t
                    scaleY *= t
                    alpha *= t
                }
                TextAnimationType.SLIDE_UP_BOUNCE -> {
                    offsetY += (1f - easeOutBounce(t)) * baseOffset
                    alpha *= t
                }
                TextAnimationType.POP_IN -> {
                    val s = easeOutBack(t) * 1.2f
                    scaleX *= s.coerceAtMost(1f)
                    scaleY *= s.coerceAtMost(1f)
                    alpha *= t
                }
                TextAnimationType.CURSOR -> { // Typewriter logic
                     val totalChars = layer.content.length
                     revealChars = (totalChars * t).toInt().coerceIn(0, totalChars)
                     cursorVisible = true
                }
                else -> {}
            }
        } else if (animInType == TextAnimationType.CURSOR) {
            // Keep cursor visible logic if type was cursor
             val totalChars = layer.content.length
             revealChars = totalChars
        }


        // --- PHASE 2: LOOP ANIMATION ---
        // Loops run continuously throughout the layer's life
        if (layer.animationLoop != TextAnimationType.NONE) {
            val loopDuration = layer.animLoopDuration.coerceAtLeast(100)
            val loopProgress = (relativeTime % loopDuration).toFloat() / loopDuration
            
            when (layer.animationLoop) {
                TextAnimationType.PULSE -> {
                    val s = 1f + 0.1f * kotlin.math.sin(loopProgress * 2 * Math.PI).toFloat()
                    scaleX *= s
                    scaleY *= s
                }
                TextAnimationType.SHAKE -> {
                    rotation += 5f * kotlin.math.sin(loopProgress * 2 * Math.PI).toFloat()
                }
                TextAnimationType.WIGGLE -> {
                    rotation += 10f * kotlin.math.sin(loopProgress * 2 * Math.PI).toFloat()
                    scaleX *= (1f + 0.05f * kotlin.math.cos(loopProgress * 2 * Math.PI).toFloat())
                }
                TextAnimationType.FLOAT -> {
                    offsetY += 20f * kotlin.math.sin(loopProgress * 2 * Math.PI).toFloat()
                }
                TextAnimationType.HEARTBEAT -> {
                     // Fast pulse twice then wait
                     val p = loopProgress * 3f // Speed up phase
                     if (p < 1f) {
                         val s = 1f + 0.2f * kotlin.math.sin(p * Math.PI).toFloat()
                         scaleX *= s; scaleY *= s
                     } else if (p < 2f) {
                         val s = 1f + 0.15f * kotlin.math.sin((p-1f) * Math.PI).toFloat()
                         scaleX *= s; scaleY *= s
                     }
                }
                else -> {}
            }
        }


        // --- PHASE 3: OUT ANIMATION ---
        val outDuration = layer.animOutDuration.coerceAtLeast(0)
        // Legacy fallback
        val animOutType = if (layer.animationOut != TextAnimationType.NONE) layer.animationOut else {
            if (layer.textAnimation == TextAnimationType.FADE_OUT) TextAnimationType.FADE_OUT else TextAnimationType.NONE
        }

        if (outDuration > 0 && relativeTime > (duration - outDuration) && animOutType != TextAnimationType.NONE) {
            val tRaw = (relativeTime - (duration - outDuration)).toFloat() / outDuration
            val t = tRaw.coerceIn(0f, 1f)
            val invT = 1f - t // Goes from 1 to 0
            
            when (animOutType) {
                TextAnimationType.FADE_OUT -> alpha *= invT
                TextAnimationType.ZOOM_OUT -> {
                    val s = 1f + t // Grow out
                    scaleX *= s
                    scaleY *= s
                    alpha *= invT
                }
                TextAnimationType.BOUNCE_OUT -> {
                    // Reverse bounce in
                     val s = easeOutBounce(invT)
                     scaleX *= s
                     scaleY *= s
                     alpha *= invT
                }
                 TextAnimationType.SPIN_OUT -> {
                    rotation += t * 360f
                    scaleX *= invT
                    scaleY *= invT
                    alpha *= invT
                }
                TextAnimationType.POP_OUT -> {
                    val s = easeOutBack(invT)
                    scaleX *= s
                    scaleY *= s
                    alpha *= invT
                }
                else -> {}
            }
        }

        return TextTransform(
            alpha = alpha.coerceIn(0f, 1f),
            offsetXPx = offsetX,
            offsetYPx = offsetY,
            scaleX = scaleX,
            scaleY = scaleY,
            rotation = rotation,
            revealChars = revealChars,
            cursorVisible = cursorVisible
        )
    }

    // --- EASING FUNCTIONS ---

    private fun easeOutCubic(x: Float): Float {
        val t = 1f - x
        return 1f - t * t * t
    }

    private fun easeOutBack(x: Float): Float {
        val c1 = 1.70158f
        val c3 = c1 + 1f
        val t = x - 1f
        return 1f + c3 * t * t * t + c1 * t * t
    }

    private fun easeOutElastic(x: Float): Float {
        val c4 = (2f * Math.PI) / 3f
        return if (x == 0f) 0f
        else if (x == 1f) 1f
        else (2.0.pow(-10.0 * x.toDouble()) * kotlin.math.sin((x * 10f - 0.75f) * c4) + 1f).toFloat()
    }

    private fun easeOutBounce(x: Float): Float {
        val n1 = 7.5625f
        val d1 = 2.75f
        var t = x
        if (t < 1f / d1) {
            return n1 * t * t
        } else if (t < 2f / d1) {
            t -= 1.5f / d1
            return n1 * t * t + 0.75f
        } else if (t < 2.5f / d1) {
            t -= 2.25f / d1
            return n1 * t * t + 0.9375f
        } else {
            t -= 2.625f / d1
            return n1 * t * t + 0.984375f
        }
    }
}
