/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.gl

import com.cutnow.sdk.model.TransitionType
import java.util.Locale

/**
 * Manages GLSL shaders and metadata for programmatic transitions.
 */
object TransitionManager {

    /**
     * Common vertex shader for all transitions.
     */
    const val VERTEX_SHADER = """
        attribute vec4 aFramePosition;
        attribute vec4 aTexSamplingCoord;
        varying vec2 vTexCoord;
        void main() {
            gl_Position = aFramePosition;
            vTexCoord = aTexSamplingCoord.xy;
        }
    """

    /**
     * Returns the GLSL fragment shader for a given transition type.
     * The shader expects:
     * - uniform sampler2D uTexCurrent;
     * - uniform sampler2D uTexLastFrame;
     * - uniform float uProgress; [0.0 - 1.0]
     */
    fun getTransitionShader(type: TransitionType): String {
        return """
            precision mediump float;
            varying vec2 vTexCoord;
            uniform sampler2D uTexCurrent;
            uniform sampler2D uTexLastFrame;
            uniform float uProgress;
            
            ${getShaderBody(type)}
            
            void main() {
                gl_FragColor = transition(vTexCoord);
            }
        """
    }

    private fun getShaderBody(type: TransitionType): String {
        return when (type) {
            TransitionType.FADE -> """
                vec4 transition(vec2 uv) {
                    return mix(texture2D(uTexLastFrame, uv), texture2D(uTexCurrent, uv), uProgress);
                }
            """
            TransitionType.SLIDE_LEFT -> """
                vec4 transition(vec2 uv) {
                    if (uv.x < 1.0 - uProgress) {
                        return texture2D(uTexLastFrame, uv + vec2(uProgress, 0.0));
                    } else {
                        return texture2D(uTexCurrent, uv - vec2(1.0 - uProgress, 0.0));
                    }
                }
            """
            TransitionType.SLIDE_RIGHT -> """
                vec4 transition(vec2 uv) {
                    if (uv.x > uProgress) {
                        return texture2D(uTexLastFrame, uv - vec2(uProgress, 0.0));
                    } else {
                        return texture2D(uTexCurrent, uv + vec2(1.0 - uProgress, 0.0));
                    }
                }
            """
            TransitionType.SLIDE_UP -> """
                vec4 transition(vec2 uv) {
                    if (uv.y < 1.0 - uProgress) {
                        return texture2D(uTexLastFrame, uv + vec2(0.0, uProgress));
                    } else {
                        return texture2D(uTexCurrent, uv - vec2(0.0, 1.0 - uProgress));
                    }
                }
            """
            TransitionType.SLIDE_DOWN -> """
                vec4 transition(vec2 uv) {
                    if (uv.y > uProgress) {
                        return texture2D(uTexLastFrame, uv - vec2(0.0, uProgress));
                    } else {
                        return texture2D(uTexCurrent, uv + vec2(0.0, 1.0 - uProgress));
                    }
                }
            """
            TransitionType.WIPE_LEFT -> """
                vec4 transition(vec2 uv) {
                    if (uv.x > 1.0 - uProgress) {
                        return texture2D(uTexCurrent, uv);
                    } else {
                        return texture2D(uTexLastFrame, uv);
                    }
                }
            """
            TransitionType.WIPE_RIGHT -> """
                vec4 transition(vec2 uv) {
                    if (uv.x < uProgress) {
                        return texture2D(uTexCurrent, uv);
                    } else {
                        return texture2D(uTexLastFrame, uv);
                    }
                }
            """
            TransitionType.BLUR -> """
                vec4 transition(vec2 uv) {
                    // Lightweight 3x3 Gaussian-like kernel (9 samples vs the old 25)
                    // Peak blur radius at progress=0.5, none at 0 and 1.
                    float r = sin(uProgress * 3.14159) * 0.025;
                    vec4 c = vec4(0.0);
                    // Weights: corners=1, edges=2, center=4  (sum=16)
                    c += texture2D(uTexLastFrame, uv + vec2(-r,-r)) * (1.0 - uProgress);
                    c += texture2D(uTexLastFrame, uv + vec2( 0.,-r)) * (1.0 - uProgress) * 2.0;
                    c += texture2D(uTexLastFrame, uv + vec2( r,-r)) * (1.0 - uProgress);
                    c += texture2D(uTexLastFrame, uv + vec2(-r, 0.)) * (1.0 - uProgress) * 2.0;
                    c += texture2D(uTexLastFrame, uv) * (1.0 - uProgress) * 4.0;
                    c += texture2D(uTexLastFrame, uv + vec2( r, 0.)) * (1.0 - uProgress) * 2.0;
                    c += texture2D(uTexLastFrame, uv + vec2(-r, r)) * (1.0 - uProgress);
                    c += texture2D(uTexLastFrame, uv + vec2( 0., r)) * (1.0 - uProgress) * 2.0;
                    c += texture2D(uTexLastFrame, uv + vec2( r, r)) * (1.0 - uProgress);
                    vec4 d = vec4(0.0);
                    d += texture2D(uTexCurrent, uv + vec2(-r,-r)) * uProgress;
                    d += texture2D(uTexCurrent, uv + vec2( 0.,-r)) * uProgress * 2.0;
                    d += texture2D(uTexCurrent, uv + vec2( r,-r)) * uProgress;
                    d += texture2D(uTexCurrent, uv + vec2(-r, 0.)) * uProgress * 2.0;
                    d += texture2D(uTexCurrent, uv) * uProgress * 4.0;
                    d += texture2D(uTexCurrent, uv + vec2( r, 0.)) * uProgress * 2.0;
                    d += texture2D(uTexCurrent, uv + vec2(-r, r)) * uProgress;
                    d += texture2D(uTexCurrent, uv + vec2( 0., r)) * uProgress * 2.0;
                    d += texture2D(uTexCurrent, uv + vec2( r, r)) * uProgress;
                    return (c + d) / 16.0;
                }
            """

            TransitionType.WIPE_UP -> """
                vec4 transition(vec2 uv) {
                    if (uv.y > 1.0 - uProgress) {
                        return texture2D(uTexCurrent, uv);
                    } else {
                        return texture2D(uTexLastFrame, uv);
                    }
                }
            """
            TransitionType.WIPE_DOWN -> """
                vec4 transition(vec2 uv) {
                    if (uv.y < uProgress) {
                        return texture2D(uTexCurrent, uv);
                    } else {
                        return texture2D(uTexLastFrame, uv);
                    }
                }
            """
            TransitionType.DISSOLVE -> """
                float noise(vec2 p) {
                    return fract(sin(dot(p, vec2(12.9898, 78.233))) * 43758.5453);
                }
                vec4 transition(vec2 uv) {
                    if (noise(uv) < uProgress) {
                        return texture2D(uTexCurrent, uv);
                    } else {
                        return texture2D(uTexLastFrame, uv);
                    }
                }
            """
            TransitionType.GLITCH -> """
                vec4 transition(vec2 uv) {
                    float noise = fract(sin(dot(uv + uProgress, vec2(12.9898, 78.233))) * 43758.5453);
                    vec2 offset = vec2(noise * 0.05 * sin(uProgress * 10.0), 0.0);
                    return mix(texture2D(uTexLastFrame, uv + offset), texture2D(uTexCurrent, uv - offset), uProgress);
                }
            """
            TransitionType.IRIS_CIRCLE -> """
                vec4 transition(vec2 uv) {
                    float dist = distance(uv, vec2(0.5));
                    if (dist < uProgress * 0.707) {
                        return texture2D(uTexCurrent, uv);
                    } else {
                        return texture2D(uTexLastFrame, uv);
                    }
                }
            """
            TransitionType.ZOOM_IN -> """
                vec4 transition(vec2 uv) {
                    vec2 centeredUv = uv - 0.5;
                    vec2 zoomedLast = (centeredUv / (1.0 + uProgress)) + 0.5;
                    vec2 zoomedCurrent = (centeredUv * (1.0 - uProgress)) + 0.5;
                    return mix(texture2D(uTexLastFrame, zoomedLast), texture2D(uTexCurrent, zoomedCurrent), uProgress);
                }
            """
            TransitionType.ZOOM_OUT -> """
                vec4 transition(vec2 uv) {
                    vec2 centeredUv = uv - 0.5;
                    vec2 zoomedLast = (centeredUv * (1.0 + uProgress)) + 0.5;
                    vec2 zoomedCurrent = (centeredUv / (1.0 - uProgress)) + 0.5;
                    return mix(texture2D(uTexLastFrame, zoomedLast), texture2D(uTexCurrent, zoomedCurrent), uProgress);
                }
            """
            TransitionType.MIX -> """
                vec4 transition(vec2 uv) {
                    return mix(texture2D(uTexLastFrame, uv), texture2D(uTexCurrent, uv), uProgress);
                }
            """
            TransitionType.GLOW -> """
                vec4 transition(vec2 uv) {
                    vec4 color = mix(texture2D(uTexLastFrame, uv), texture2D(uTexCurrent, uv), uProgress);
                    float flash = sin(uProgress * 3.14159);
                    return color + vec4(flash);
                }
            """
            TransitionType.GLOWUP -> """
                vec4 transition(vec2 uv) {
                    float zoom = 1.0 + 0.3 * uProgress;
                    vec2 zoomedUv = (uv - 0.5) / zoom + 0.5;
                    vec4 lastColor = texture2D(uTexLastFrame, zoomedUv);
                    vec4 currentColor = texture2D(uTexCurrent, zoomedUv);
                    float glow = sin(uProgress * 3.14159);
                    vec4 result = mix(lastColor, currentColor, uProgress);
                    result.rgb += vec3(glow * 0.45);
                    result.rg += vec2(glow * 0.05); 
                    return clamp(result, 0.0, 1.0);
                }
            """
            else -> """
                vec4 transition(vec2 uv) {
                    return mix(texture2D(uTexLastFrame, uv), texture2D(uTexCurrent, uv), uProgress);
                }
            """
        }
    }

    fun getDisplayName(type: TransitionType): String {
        return when (type) {
            TransitionType.NONE -> "None"
            TransitionType.FADE -> "Fade"
            TransitionType.SLIDE_LEFT -> "Slide Left"
            TransitionType.SLIDE_RIGHT -> "Slide Right"
            TransitionType.SLIDE_UP -> "Slide Up"
            TransitionType.SLIDE_DOWN -> "Slide Down"
            TransitionType.WIPE_LEFT -> "Wipe Left"
            TransitionType.WIPE_RIGHT -> "Wipe Right"
            TransitionType.WIPE_UP -> "Wipe Up"
            TransitionType.WIPE_DOWN -> "Wipe Down"
            TransitionType.DISSOLVE -> "Dissolve"
            TransitionType.GLITCH -> "Glitch"
            TransitionType.IRIS_CIRCLE -> "Iris"
            TransitionType.BLUR -> "Blur"
            TransitionType.ZOOM_IN -> "Zoom In"
            TransitionType.ZOOM_OUT -> "Zoom Out"
            TransitionType.MIX -> "Mix"
            TransitionType.GLOW -> "Glow"
            TransitionType.GLOWUP -> "Glowup"
            else -> type.name.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        }
    }
}


