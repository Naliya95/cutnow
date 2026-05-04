/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.util

import com.cutnow.sdk.R

import com.cutnow.sdk.model.EffectType
import com.cutnow.sdk.model.FilterType

/**
 * Collection of professional GLSL shaders for the SDK.
 */
object ShaderSource {
    const val VERTEX_SHADER = """
        attribute vec4 aFramePosition;
        attribute vec4 aTexSamplingCoord;
        varying vec2 vTexSamplingCoord;
        void main() {
            gl_Position = aFramePosition;
            vTexSamplingCoord = aTexSamplingCoord.xy;
        }
    """

    const val VERTEX_SHADER_SIMPLE = """
        attribute vec2 aPosition;
        attribute vec2 aTexCoord;
        varying vec2 vTexSamplingCoord;
        void main() {
            gl_Position = vec4(aPosition, 0.0, 1.0);
            vTexSamplingCoord = aTexCoord;
        }
    """

    fun getFragmentShader(type: EffectType): String {
        return when (type) {
            EffectType.RGB_SPLIT -> CHROMATIC_ABERRATION
            EffectType.GLITCH -> SOPHISTICATED_GLITCH
            EffectType.ZOOM_BLUR -> MULTI_TAP_ZOOM_BLUR
            EffectType.RETRO_VHS -> VHS_RETRO
            EffectType.SHAKE -> SHAKE_PRO
            EffectType.PIXELATE -> PIXELATE_PRO
            EffectType.BLUR -> GAUSSIAN_BLUR
            EffectType.MIRROR -> MIRROR_EFFECT
            EffectType.VIGNETTE -> VIGNETTE_EFFECT
            EffectType.BW -> GRAYSCALE
            EffectType.INVERT -> INVERT_EFFECT
            EffectType.BRIGHTNESS -> BRIGHTNESS_EFFECT
            EffectType.CONTRAST -> CONTRAST_EFFECT
            EffectType.HORIZONTAL_OPEN -> HORIZONTAL_OPEN_EFFECT
            EffectType.VERTICAL_OPEN -> VERTICAL_OPEN_EFFECT
            else -> DEFAULT
        }
    }

    fun getFilterShader(type: FilterType): String {
        return when (type) {
            FilterType.BRIGHT -> BRIGHTNESS_EFFECT
            FilterType.WARM -> WARM_FILTER
            FilterType.COOL -> COOL_FILTER
            FilterType.BW -> GRAYSCALE
            FilterType.VINTAGE -> VINTAGE_FILTER
            FilterType.SEPIA -> SEPIA_FILTER
            FilterType.CONTRAST -> CONTRAST_EFFECT
            FilterType.SATURATION -> SATURATION_FILTER
            FilterType.VIGNETTE -> VIGNETTE_EFFECT
            FilterType.SHARPEN -> SHARPEN_FILTER
            else -> DEFAULT
        }
    }

    const val DEFAULT = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        void main() {
            gl_FragColor = texture2D(uTexSampler, vTexSamplingCoord);
        }
    """

    private const val CHROMATIC_ABERRATION = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec2 uv = vTexSamplingCoord;
            float dist = distance(uv, vec2(0.5, 0.5));
            float amount = uIntensity * 0.04 * dist;
            vec4 r = texture2D(uTexSampler, uv + vec2(amount, 0.0));
            vec4 g = texture2D(uTexSampler, uv);
            vec4 b = texture2D(uTexSampler, uv - vec2(amount, 0.0));
            gl_FragColor = vec4(r.r, g.g, b.b, g.a);
        }
    """

    private const val SOPHISTICATED_GLITCH = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uTime;
        uniform float uIntensity;
        float rand(vec2 co) {
            return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
        }
        void main() {
            vec2 uv = vTexSamplingCoord;
            float time = uTime * 10.0;
            if (rand(vec2(floor(uv.y * 20.0), floor(time))) < uIntensity * 0.2) {
                uv.x += (rand(vec2(floor(uv.y * 20.0), floor(time))) - 0.5) * 0.05 * uIntensity;
            }
            float shift = 0.015 * uIntensity * rand(vec2(time));
            vec4 r = texture2D(uTexSampler, uv + vec2(shift, 0.0));
            vec4 g = texture2D(uTexSampler, uv);
            vec4 b = texture2D(uTexSampler, uv - vec2(shift, 0.0));
            float scanline = sin(uv.y * 800.0) * 0.03 * uIntensity;
            gl_FragColor = vec4(r.r, g.g, b.b, g.a) - scanline;
        }
    """

    private const val MULTI_TAP_ZOOM_BLUR = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec2 uv = vTexSamplingCoord;
            vec2 center = vec2(0.5, 0.5);
            vec2 dir = uv - center;
            vec4 sum = vec4(0.0);
            float strength = uIntensity * 0.1;
            for(float i = 0.0; i < 8.0; i++) {
                sum += texture2D(uTexSampler, uv - dir * (i / 8.0) * strength);
            }
            gl_FragColor = sum / 8.0;
        }
    """

    private const val VHS_RETRO = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uTime;
        uniform float uIntensity;
        float rand(vec2 co) {
            return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
        }
        void main() {
            vec2 uv = vTexSamplingCoord;
            float time = uTime;
            uv.x += (rand(vec2(time, floor(uv.y * 300.0))) - 0.5) * 0.005 * uIntensity;
            float noise = (rand(uv + time) - 0.5) * 0.1 * uIntensity;
            vec4 color = texture2D(uTexSampler, uv);
            float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
            color.rgb = mix(color.rgb, vec3(gray), 0.3 * uIntensity);
            gl_FragColor = color + noise;
        }
    """

    private const val SHAKE_PRO = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uTime;
        uniform float uIntensity;
        float rand(float n) { return fract(sin(n) * 43758.5453123); }
        void main() {
            float time = uTime * 5.0;
            vec2 uv = vTexSamplingCoord;
            float dx = (rand(floor(time)) - 0.5) * 0.03 * uIntensity;
            float dy = (rand(floor(time) + 1.0) - 0.5) * 0.03 * uIntensity;
            gl_FragColor = texture2D(uTexSampler, uv + vec2(dx, dy));
        }
    """

    private const val PIXELATE_PRO = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            float pixels = 100.0 - (uIntensity * 80.0);
            vec2 uv = vTexSamplingCoord;
            uv = floor(uv * pixels) / pixels;
            gl_FragColor = texture2D(uTexSampler, uv);
        }
    """

    private const val GAUSSIAN_BLUR = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec2 uv = vTexSamplingCoord;
            float radius = uIntensity * 0.02;
            vec4 color = vec4(0.0);
            float total = 0.0;
            for (float x = -2.0; x <= 2.0; x++) {
                for (float y = -2.0; y <= 2.0; y++) {
                    float weight = 1.0;
                    color += texture2D(uTexSampler, uv + vec2(x, y) * radius) * weight;
                    total += weight;
                }
            }
            gl_FragColor = color / total;
        }
    """

    private const val GRAYSCALE = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec4 color = texture2D(uTexSampler, vTexSamplingCoord);
            float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
            gl_FragColor = vec4(mix(color.rgb, vec3(gray), uIntensity), color.a);
        }
    """

    private const val BRIGHTNESS_EFFECT = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec4 color = texture2D(uTexSampler, vTexSamplingCoord);
            gl_FragColor = vec4(color.rgb + (uIntensity * 0.4), color.a);
        }
    """

    private const val CONTRAST_EFFECT = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec4 color = texture2D(uTexSampler, vTexSamplingCoord);
            float contrast = 1.0 + uIntensity;
            gl_FragColor = vec4(((color.rgb - 0.5) * contrast) + 0.5, color.a);
        }
    """

    private const val MIRROR_EFFECT = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec2 uv = vTexSamplingCoord;
            if (uv.x > 0.5) uv.x = 1.0 - uv.x;
            gl_FragColor = texture2D(uTexSampler, uv);
        }
    """

    private const val INVERT_EFFECT = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec4 color = texture2D(uTexSampler, vTexSamplingCoord);
            gl_FragColor = vec4(mix(color.rgb, 1.0 - color.rgb, uIntensity), color.a);
        }
    """

    private const val VIGNETTE_EFFECT = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec4 color = texture2D(uTexSampler, vTexSamplingCoord);
            float dist = distance(vTexSamplingCoord, vec2(0.5, 0.5));
            float vignette = smoothstep(0.8, 0.5 - (uIntensity * 0.3), dist);
            gl_FragColor = vec4(color.rgb * vignette, color.a);
        }
    """

    private const val WARM_FILTER = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec4 color = texture2D(uTexSampler, vTexSamplingCoord);
            vec3 warm = color.rgb * vec3(1.0 + 0.2 * uIntensity, 1.0 + 0.1 * uIntensity, 1.0 - 0.2 * uIntensity);
            gl_FragColor = vec4(mix(color.rgb, warm, uIntensity), color.a);
        }
    """

    private const val COOL_FILTER = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec4 color = texture2D(uTexSampler, vTexSamplingCoord);
            vec3 cool = color.rgb * vec3(1.0 - 0.2 * uIntensity, 1.0 + 0.1 * uIntensity, 1.0 + 0.2 * uIntensity);
            gl_FragColor = vec4(mix(color.rgb, cool, uIntensity), color.a);
        }
    """

    private const val SEPIA_FILTER = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec4 color = texture2D(uTexSampler, vTexSamplingCoord);
            vec3 sepia = vec3(
                dot(color.rgb, vec3(0.393, 0.769, 0.189)),
                dot(color.rgb, vec3(0.349, 0.686, 0.168)),
                dot(color.rgb, vec3(0.272, 0.534, 0.131))
            );
            gl_FragColor = vec4(mix(color.rgb, sepia, uIntensity), color.a);
        }
    """

    private const val VINTAGE_FILTER = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec4 color = texture2D(uTexSampler, vTexSamplingCoord);
            vec3 vintage = vec3(
                color.r * (1.0 - 0.1 * uIntensity) + 0.1 * uIntensity,
                color.g * (1.0 - 0.05 * uIntensity),
                color.b * (1.0 - 0.2 * uIntensity)
            );
            gl_FragColor = vec4(mix(color.rgb, vintage, uIntensity), color.a);
        }
    """
    
    private const val SATURATION_FILTER = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec4 color = texture2D(uTexSampler, vTexSamplingCoord);
            float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
            gl_FragColor = vec4(mix(vec3(gray), color.rgb, 1.0 + uIntensity), color.a);
        }
    """

    private const val SHARPEN_FILTER = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        void main() {
            vec2 uv = vTexSamplingCoord;
            float step = 1.0 / 512.0;
            vec4 center = texture2D(uTexSampler, uv);
            vec4 top = texture2D(uTexSampler, uv + vec2(0.0, step));
            vec4 bottom = texture2D(uTexSampler, uv - vec2(0.0, step));
            vec4 left = texture2D(uTexSampler, uv - vec2(step, 0.0));
            vec4 right = texture2D(uTexSampler, uv + vec2(step, 0.0));
            vec4 sharpened = center * 5.0 - (top + bottom + left + right);
            gl_FragColor = vec4(mix(center.rgb, sharpened.rgb, uIntensity), center.a);
        }
    """

    private const val HORIZONTAL_OPEN_EFFECT = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        uniform float uProgress;
        void main() {
            vec2 uv = vTexSamplingCoord;
            float openness = uProgress * uIntensity;
            float mask = step(abs(uv.x - 0.5), openness * 0.5);
            vec4 color = texture2D(uTexSampler, uv);
            gl_FragColor = vec4(color.rgb * mask, color.a);
        }
    """

    private const val VERTICAL_OPEN_EFFECT = """
        precision mediump float;
        varying vec2 vTexSamplingCoord;
        uniform sampler2D uTexSampler;
        uniform float uIntensity;
        uniform float uProgress;
        void main() {
            vec2 uv = vTexSamplingCoord;
            float openness = uProgress * uIntensity;
            float mask = step(abs(uv.y - 0.5), openness * 0.5);
            vec4 color = texture2D(uTexSampler, uv);
            gl_FragColor = vec4(color.rgb * mask, color.a);
        }
    """
}
