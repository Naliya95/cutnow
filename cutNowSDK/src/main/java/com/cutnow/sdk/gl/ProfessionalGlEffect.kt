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

import com.cutnow.sdk.R

import android.content.Context
import android.opengl.GLES20
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Size
import androidx.media3.effect.GlEffect
import androidx.media3.effect.GlShaderProgram
import androidx.media3.effect.BaseGlShaderProgram
import com.cutnow.sdk.model.*
import com.cutnow.sdk.util.UtilGlProgram
import com.cutnow.sdk.util.ShaderSource


/**
 * A professional video effect implementation that uses custom GLSL shaders.
 * This provides higher quality than basic matrix transformations.
 */
@UnstableApi
class ProfessionalGlEffect(
    val effectType: EffectType,
    val intensity: Float,
    val startTimeUs: Long = 0,
    val durationUs: Long = 0
) : GlEffect {
    override fun toGlShaderProgram(context: Context, useHdr: Boolean): GlShaderProgram {
        android.util.Log.i("EffectExecution", "🎬 toGlShaderProgram: $effectType, start=$startTimeUs, dur=$durationUs")
        return ProfessionalShaderProgram(context, effectType, intensity, startTimeUs, durationUs, useHdr)
    }
}

/**
 * Shader program that handles frame-by-frame rendering with custom GLSL.
 */
@UnstableApi
class ProfessionalShaderProgram(
    context: Context,
    private val effectType: EffectType,
    private val intensity: Float,
    private val startTimeUs: Long,
    private val durationUs: Long,
    useHdr: Boolean
) : BaseGlShaderProgram(useHdr, 1) {

    private val program: UtilGlProgram = UtilGlProgram(
        ShaderSource.VERTEX_SHADER,
        ShaderSource.getFragmentShader(effectType)
    )

    override fun configure(inputWidth: Int, inputHeight: Int): Size {
        // No special configuration needed, just pass through size
        return Size(inputWidth, inputHeight)
    }

    override fun drawFrame(inputTexId: Int, presentationTimeUs: Long) {
        try {
            android.util.Log.v("EffectExecution", "🎨 ProfessionalShaderProgram.drawFrame: type=$effectType, time=$presentationTimeUs")
            program.use()
            program.setSamplerTexIdUniform("uTexSampler", inputTexId, 0)
            program.setFloatUniform("uTime", presentationTimeUs / 1000000.0f)
            program.setFloatUniform("uIntensity", intensity)
            
            // Calculate progress [0..1]
            val progress = if (durationUs > 0) {
                (presentationTimeUs - startTimeUs).toFloat() / durationUs.toFloat()
            } else 1.0f
            program.setFloatUniform("uProgress", progress.coerceIn(0f, 1f))
            
            program.bindAttributesAndUniforms()
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        } catch (e: Exception) {
            android.util.Log.e("EffectExecution", "❌ Error in drawFrame for $effectType", e)
        }
    }

    override fun release() {
        super.release()
        try {
            program.delete()
        } catch (e: Exception) {
            // Ignore
        }
    }
}


