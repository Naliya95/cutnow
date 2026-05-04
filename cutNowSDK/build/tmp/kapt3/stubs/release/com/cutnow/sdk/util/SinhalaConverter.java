package com.cutnow.sdk.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\nH\u0002J\u0012\u0010\u000e\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\bH\u0002J\u0010\u0010\u0010\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u00020\u00142\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ\u0010\u0010\u0015\u001a\u00020\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\bJ$\u0010\u0017\u001a\u00020\u00182\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0011\u001a\u00020\u00122\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\b\u00a8\u0006\u001a"}, d2 = {"Lcom/cutnow/sdk/util/SinhalaConverter;", "", "()V", "appendNonSinhala", "", "ssb", "Landroid/text/SpannableStringBuilder;", "text", "", "tf", "Landroid/graphics/Typeface;", "appendSinhalaLegacy", "unicodeText", "font", "convertUnicodeToLegacy", "str", "getAmanthaTypeface", "context", "Landroid/content/Context;", "hasSinhalaUnicode", "", "isSinhalaLegacyFont", "fontName", "unicodeToLegacySpannable", "", "customFontPath", "cutNowSDK_release"})
public final class SinhalaConverter {
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.util.SinhalaConverter INSTANCE = null;
    
    private SinhalaConverter() {
        super();
    }
    
    public final boolean hasSinhalaUnicode(@org.jetbrains.annotations.Nullable()
    java.lang.String text) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.CharSequence unicodeToLegacySpannable(@org.jetbrains.annotations.Nullable()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.String customFontPath) {
        return null;
    }
    
    public final boolean isSinhalaLegacyFont(@org.jetbrains.annotations.Nullable()
    java.lang.String fontName) {
        return false;
    }
    
    private final void appendNonSinhala(android.text.SpannableStringBuilder ssb, java.lang.String text, android.graphics.Typeface tf) {
    }
    
    private final void appendSinhalaLegacy(android.text.SpannableStringBuilder ssb, java.lang.String unicodeText, android.graphics.Typeface font) {
    }
    
    private final java.lang.String convertUnicodeToLegacy(java.lang.String str) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Typeface getAmanthaTypeface(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
}