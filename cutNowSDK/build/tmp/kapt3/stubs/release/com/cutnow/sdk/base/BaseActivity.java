package com.cutnow.sdk.base;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bH\u0004J\u0012\u0010\n\u001a\u00020\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014\u00a8\u0006\r"}, d2 = {"Lcom/cutnow/sdk/base/BaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "applyWindowInsets", "", "view", "Landroid/view/View;", "applyTop", "", "applyBottom", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "cutNowSDK_release"})
public abstract class BaseActivity extends androidx.appcompat.app.AppCompatActivity {
    
    public BaseActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * Helper to apply window insets to a view.
     * Useful for setting padding/margins for top status bar or bottom navigation bar.
     */
    protected final void applyWindowInsets(@org.jetbrains.annotations.NotNull()
    android.view.View view, boolean applyTop, boolean applyBottom) {
    }
}