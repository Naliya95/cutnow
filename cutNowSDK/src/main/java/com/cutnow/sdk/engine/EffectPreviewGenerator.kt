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

import android.content.Context
import android.graphics.Bitmap
import com.cutnow.sdk.gl.FilterGlPreviewRenderer
import com.cutnow.sdk.model.EffectType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Generates preview thumbnails for video effects via GPU (OpenGL ES).
 *
 * Delegates to [FilterGlPreviewRenderer] which renders [preview_img] through
 * each effect's GLSL shader off-screen and caches the resulting [Bitmap].
 * Falls back to the CPU path automatically if EGL is unavailable.
 */
object EffectPreviewGenerator {

    /**
     * Start an async load of the effect preview bitmap and deliver it via [onReady].
     * Returns the [Job] so the caller can cancel on rebind.
     */
    fun loadAsync(
        context: Context,
        scope: CoroutineScope,
        effectType: EffectType,
        onReady: (Bitmap) -> Unit
    ): Job = scope.launch(Dispatchers.Main) {
        val bmp = FilterGlPreviewRenderer.renderEffect(context.applicationContext, effectType)
        onReady(bmp)
    }

}
