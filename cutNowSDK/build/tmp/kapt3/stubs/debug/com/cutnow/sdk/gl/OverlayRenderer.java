package com.cutnow.sdk.gl;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006R\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/cutnow/sdk/gl/OverlayRenderer;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "onOverlayChanged", "Lkotlin/Function1;", "Lcom/cutnow/sdk/gl/TextOverlay;", "", "cutNowSDK_debug"})
public final class OverlayRenderer extends android.widget.FrameLayout {
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function1<? super com.cutnow.sdk.gl.TextOverlay, kotlin.Unit> onOverlayChanged;
    
    @kotlin.jvm.JvmOverloads()
    public OverlayRenderer(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public OverlayRenderer(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
}