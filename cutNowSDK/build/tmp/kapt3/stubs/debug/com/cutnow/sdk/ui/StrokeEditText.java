package com.cutnow.sdk.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0015\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201H\u0015R$\u0010\n\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR$\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u000f@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R$\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u000f@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R$\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u000f@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0012\"\u0004\b\u001a\u0010\u0014R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u0010\u001f\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\f\"\u0004\b!\u0010\u000eR$\u0010\"\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u000f@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0012\"\u0004\b$\u0010\u0014R$\u0010%\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\f\"\u0004\b\'\u0010\u000eR(\u0010)\u001a\u0004\u0018\u00010(2\b\u0010\t\u001a\u0004\u0018\u00010(@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-\u00a8\u00062"}, d2 = {"Lcom/cutnow/sdk/ui/StrokeEditText;", "Landroidx/appcompat/widget/AppCompatEditText;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "value", "bgColor", "getBgColor", "()I", "setBgColor", "(I)V", "", "bgCornerRadius", "getBgCornerRadius", "()F", "setBgCornerRadius", "(F)V", "bgPaddingX", "getBgPaddingX", "setBgPaddingX", "bgPaddingY", "getBgPaddingY", "setBgPaddingY", "bgPaint", "Landroid/graphics/Paint;", "bgRect", "Landroid/graphics/RectF;", "strokeColor", "getStrokeColor", "setStrokeColor", "strokeWidth", "getStrokeWidth", "setStrokeWidth", "textAlpha", "getTextAlpha", "setTextAlpha", "", "textGradientColors", "getTextGradientColors", "()[I", "setTextGradientColors", "([I)V", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "cutNowSDK_debug"})
public final class StrokeEditText extends androidx.appcompat.widget.AppCompatEditText {
    private float strokeWidth = 0.0F;
    private int strokeColor = android.graphics.Color.TRANSPARENT;
    private float bgPaddingX = 0.0F;
    private float bgPaddingY = 0.0F;
    private float bgCornerRadius = 0.0F;
    private int bgColor = android.graphics.Color.TRANSPARENT;
    @org.jetbrains.annotations.Nullable()
    private int[] textGradientColors;
    private int textAlpha = 255;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint bgPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.RectF bgRect = null;
    
    @kotlin.jvm.JvmOverloads()
    public StrokeEditText(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    public final float getStrokeWidth() {
        return 0.0F;
    }
    
    public final void setStrokeWidth(float value) {
    }
    
    public final int getStrokeColor() {
        return 0;
    }
    
    public final void setStrokeColor(int value) {
    }
    
    public final float getBgPaddingX() {
        return 0.0F;
    }
    
    public final void setBgPaddingX(float value) {
    }
    
    public final float getBgPaddingY() {
        return 0.0F;
    }
    
    public final void setBgPaddingY(float value) {
    }
    
    public final float getBgCornerRadius() {
        return 0.0F;
    }
    
    public final void setBgCornerRadius(float value) {
    }
    
    public final int getBgColor() {
        return 0;
    }
    
    public final void setBgColor(int value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final int[] getTextGradientColors() {
        return null;
    }
    
    public final void setTextGradientColors(@org.jetbrains.annotations.Nullable()
    int[] value) {
    }
    
    public final int getTextAlpha() {
        return 0;
    }
    
    public final void setTextAlpha(int value) {
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"RtlHardcoded"})
    protected void onDraw(@org.jetbrains.annotations.NotNull()
    android.graphics.Canvas canvas) {
    }
    
    @kotlin.jvm.JvmOverloads()
    public StrokeEditText(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public StrokeEditText(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
}