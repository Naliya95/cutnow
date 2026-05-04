package com.cutnow.sdk.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0087\u0081\u0002\u0018\u0000 \u00192\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002:\u0001\u0019B\u001f\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0006H\u00d6\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nj\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018\u00a8\u0006\u001a"}, d2 = {"Lcom/cutnow/sdk/model/ExportResolution;", "", "Landroid/os/Parcelable;", "label", "", "width", "", "height", "(Ljava/lang/String;ILjava/lang/String;II)V", "getHeight", "()I", "getLabel", "()Ljava/lang/String;", "getWidth", "describeContents", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "P480", "P720", "P1080", "K2", "K4", "Companion", "cutNowSDK_debug"})
@kotlinx.parcelize.Parcelize()
public enum ExportResolution implements android.os.Parcelable {
    /*public static final*/ P480 /* = new P480(null, 0, 0) */,
    /*public static final*/ P720 /* = new P720(null, 0, 0) */,
    /*public static final*/ P1080 /* = new P1080(null, 0, 0) */,
    /*public static final*/ K2 /* = new K2(null, 0, 0) */,
    /*public static final*/ K4 /* = new K4(null, 0, 0) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    private final int width = 0;
    private final int height = 0;
    @org.jetbrains.annotations.NotNull()
    public static final com.cutnow.sdk.model.ExportResolution.Companion Companion = null;
    
    ExportResolution(java.lang.String label, int width, int height) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    public final int getWidth() {
        return 0;
    }
    
    public final int getHeight() {
        return 0;
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.cutnow.sdk.model.ExportResolution> getEntries() {
        return null;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cutnow/sdk/model/ExportResolution$Companion;", "", "()V", "cutNowSDK_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}