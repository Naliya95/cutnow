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

import com.cutnow.sdk.model.Keyframe
import com.cutnow.sdk.model.KeyframeProperty
import kotlin.math.pow

object KeyframeEngine {

    /**
     * Interpolates a property value at a specific time based on a list of keyframes.
     * If no keyframes exist for the property, returns the baseValue.
     */
    fun interpolate(
        keyframes: List<Keyframe>,
        timeMs: Long,
        baseValue: Float,
        property: KeyframeProperty
    ): Float {
        if (keyframes.isEmpty()) return baseValue

        var k1: Keyframe? = null
        var k2: Keyframe? = null

        // Find the bounding keyframes for the given property.
        // We assume keyframes is sorted by timeMs (guaranteed by EditorViewModel).
        for (i in keyframes.indices) {
            val k = keyframes[i]
            if (getPropertyValue(k, property) != null) {
                if (k.timeMs <= timeMs) {
                    k1 = k
                } else {
                    k2 = k
                    break
                }
            }
        }

        // Case 1: No keyframes found for this property
        if (k1 == null && k2 == null) return baseValue

        // Case 2: Time is before the first keyframe found
        if (k1 == null) return getPropertyValue(k2!!, property) ?: baseValue

        // Case 3: Time is after the last keyframe found
        if (k2 == null) return getPropertyValue(k1, property) ?: baseValue

        // Case 4: Time is exactly at k1
        if (k1.timeMs == timeMs) return getPropertyValue(k1, property) ?: baseValue

        // Case 5: Interpolate between k1 and k2
        val t = (timeMs - k1.timeMs).toFloat() / (k2.timeMs - k1.timeMs).toFloat()
        val easedT = applyEasing(t.coerceIn(0f, 1f), k1.easeType)

        val v1 = getPropertyValue(k1, property) ?: baseValue
        val v2 = getPropertyValue(k2, property) ?: baseValue

        return if (property == KeyframeProperty.ROTATION) {
            interpolateRotation(v1, v2, easedT)
        } else {
            v1 + (v2 - v1) * easedT
        }
    }

    private fun interpolateRotation(v1: Float, v2: Float, t: Float): Float {
        // Shortest path interpolation for angles
        var diff = (v2 - v1) % 360f
        if (diff > 180f) diff -= 360f
        if (diff < -180f) diff += 360f
        
        var result = v1 + diff * t
        
        // Normalize result to [0, 360)
        result %= 360f
        if (result < 0) result += 360f
        return result
    }

    private fun getPropertyValue(keyframe: Keyframe, property: KeyframeProperty): Float? {
        return when (property) {
            KeyframeProperty.POS_X -> keyframe.posX
            KeyframeProperty.POS_Y -> keyframe.posY
            KeyframeProperty.SCALE -> keyframe.scale
            KeyframeProperty.ROTATION -> keyframe.rotation
            KeyframeProperty.OPACITY -> keyframe.opacity
        }
    }

    private fun applyEasing(t: Float, easeType: String): Float {
        return when (easeType) {
            "EASE_IN" -> t * t * t
            "EASE_OUT" -> 1f - (1f - t).pow(3)
            "EASE_IN_OUT" -> if (t < 0.5f) 4f * t * t * t else 1f - (-2f * t + 2f).pow(3) / 2f
            else -> t // LINEAR
        }
    }
}
