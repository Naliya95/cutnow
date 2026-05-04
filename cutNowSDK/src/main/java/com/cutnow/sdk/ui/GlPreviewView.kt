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

import com.cutnow.sdk.model.*
import com.cutnow.sdk.gl.GlVideoRenderer
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.Surface

/**
 * OpenGL-based preview view.
 * Replaces CanvasPreviewView.
 */
class GlPreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {

    private val renderer: GlVideoRenderer
    
    // Callback when Surface is ready for ExoPlayer
    var onSurfaceReady: ((Surface?) -> Unit)? = null
    
    init {
        setEGLContextClientVersion(2)
        renderer = GlVideoRenderer(context, this)
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }
    
    fun onSurfaceVideoReady(surface: Surface?) {
        onSurfaceReady?.invoke(surface)
    }
    
    fun setVideoDimensions(width: Int, height: Int) {
        renderer.setVideoDimensions(width, height)
    }
    
    fun setLayers(layers: List<MediaLayer>) {
        renderer.setLayers(layers)
        requestRender()
    }

    fun setSegments(segments: List<VideoSegment>) {
        renderer.setSegments(segments)
    }
    
    fun updateLayerDirect(layer: MediaLayer) {
        renderer.updateLayerDirect(layer)
    }

    /**
     * Force an immediate re-render of the current frame.
     * Invalidates the renderer's layer snapshot cache so the next setLayers() call
     * will always push a fresh frame — even if layer values haven't changed since the
     * last render (e.g. after refreshFromEngine() creates new layer objects with the
     * same property values).
     */
    fun forceRender() {
        renderer.invalidateLayerSnapshot()
        requestRender()
    }
    
    fun setCurrentTime(timeMs: Long) {
        renderer.setCurrentTime(timeMs)
    }

    fun setVideoTransform(scale: Float, rotation: Float, dx: Float, dy: Float) {
        renderer.setVideoTransform(scale, rotation, dx, dy)
    }

    fun setVideoInteracting(interacting: Boolean) {
        renderer.setVideoInteracting(interacting)
    }
    
    fun setEffect() {
        // renderer.setEffect(effect) // Only if renderer supports it.
        // Assuming renderer needs an update. 
        // For now, let's check if GlVideoRenderer has setEffect. 
        // If not, we might need to add it or ignore.
        // The EditorFragment calls binding.glPreviewView.setEffect(null).
    }

    fun setOverlayVideoSurface(layerId: String, player: androidx.media3.exoplayer.ExoPlayer) {
        renderer.setOverlayVideoSurface(layerId, player)
    }
}


