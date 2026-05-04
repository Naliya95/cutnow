package com.cutnow.sdk.ui;

/**
 * OpenGL-based preview view.
 * Replaces CanvasPreviewView.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0011\u001a\u00020\nJ\u0010\u0010\u0012\u001a\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\tJ\u000e\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\nJ\u0014\u0010\u0018\u001a\u00020\n2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aJ\u0016\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 J\u0014\u0010!\u001a\u00020\n2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001aJ\u0016\u0010$\u001a\u00020\n2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020&J\u000e\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020*J&\u0010+\u001a\u00020\n2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-2\u0006\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020-J\u000e\u00101\u001a\u00020\n2\u0006\u00102\u001a\u00020\u001bR*\u0010\u0007\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\n\u0018\u00010\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00063"}, d2 = {"Lcom/cutnow/sdk/ui/GlPreviewView;", "Landroid/opengl/GLSurfaceView;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "onSurfaceReady", "Lkotlin/Function1;", "Landroid/view/Surface;", "", "getOnSurfaceReady", "()Lkotlin/jvm/functions/Function1;", "setOnSurfaceReady", "(Lkotlin/jvm/functions/Function1;)V", "renderer", "Lcom/cutnow/sdk/gl/GlVideoRenderer;", "forceRender", "onSurfaceVideoReady", "surface", "setCurrentTime", "timeMs", "", "setEffect", "setLayers", "layers", "", "Lcom/cutnow/sdk/model/MediaLayer;", "setOverlayVideoSurface", "layerId", "", "player", "Landroidx/media3/exoplayer/ExoPlayer;", "setSegments", "segments", "Lcom/cutnow/sdk/model/VideoSegment;", "setVideoDimensions", "width", "", "height", "setVideoInteracting", "interacting", "", "setVideoTransform", "scale", "", "rotation", "dx", "dy", "updateLayerDirect", "layer", "cutNowSDK_release"})
public final class GlPreviewView extends android.opengl.GLSurfaceView {
    @org.jetbrains.annotations.NotNull()
    private final com.cutnow.sdk.gl.GlVideoRenderer renderer = null;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function1<? super android.view.Surface, kotlin.Unit> onSurfaceReady;
    
    @kotlin.jvm.JvmOverloads()
    public GlPreviewView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.jvm.functions.Function1<android.view.Surface, kotlin.Unit> getOnSurfaceReady() {
        return null;
    }
    
    public final void setOnSurfaceReady(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super android.view.Surface, kotlin.Unit> p0) {
    }
    
    public final void onSurfaceVideoReady(@org.jetbrains.annotations.Nullable()
    android.view.Surface surface) {
    }
    
    public final void setVideoDimensions(int width, int height) {
    }
    
    public final void setLayers(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.MediaLayer> layers) {
    }
    
    public final void setSegments(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.VideoSegment> segments) {
    }
    
    public final void updateLayerDirect(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.MediaLayer layer) {
    }
    
    /**
     * Force an immediate re-render of the current frame.
     * Invalidates the renderer's layer snapshot cache so the next setLayers() call
     * will always push a fresh frame — even if layer values haven't changed since the
     * last render (e.g. after refreshFromEngine() creates new layer objects with the
     * same property values).
     */
    public final void forceRender() {
    }
    
    public final void setCurrentTime(long timeMs) {
    }
    
    public final void setVideoTransform(float scale, float rotation, float dx, float dy) {
    }
    
    public final void setVideoInteracting(boolean interacting) {
    }
    
    public final void setEffect() {
    }
    
    public final void setOverlayVideoSurface(@org.jetbrains.annotations.NotNull()
    java.lang.String layerId, @org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer player) {
    }
    
    @kotlin.jvm.JvmOverloads()
    public GlPreviewView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
}