package com.cutnow.sdk.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B/\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J8\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u0019J\t\u0010\u001a\u001a\u00020\u0005H\u00d6\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u00d6\u0003J\"\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050 2\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u0005J\t\u0010#\u001a\u00020\u0005H\u00d6\u0001J\t\u0010$\u001a\u00020%H\u00d6\u0001J\u0019\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006+"}, d2 = {"Lcom/cutnow/sdk/model/ExportSettings;", "Landroid/os/Parcelable;", "resolution", "Lcom/cutnow/sdk/model/ExportResolution;", "frameRate", "", "bitrateMbps", "targetAspectRatio", "", "(Lcom/cutnow/sdk/model/ExportResolution;IILjava/lang/Float;)V", "bitrate", "getBitrate", "()I", "getBitrateMbps", "getFrameRate", "getResolution", "()Lcom/cutnow/sdk/model/ExportResolution;", "getTargetAspectRatio", "()Ljava/lang/Float;", "Ljava/lang/Float;", "component1", "component2", "component3", "component4", "copy", "(Lcom/cutnow/sdk/model/ExportResolution;IILjava/lang/Float;)Lcom/cutnow/sdk/model/ExportSettings;", "describeContents", "equals", "", "other", "", "getAdjustedDimensions", "Lkotlin/Pair;", "sourceWidth", "sourceHeight", "hashCode", "toString", "", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "cutNowSDK_release"})
@kotlinx.parcelize.Parcelize()
public final class ExportSettings implements android.os.Parcelable {
    @org.jetbrains.annotations.NotNull()
    private final com.cutnow.sdk.model.ExportResolution resolution = null;
    private final int frameRate = 0;
    private final int bitrateMbps = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Float targetAspectRatio = null;
    
    public ExportSettings(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.ExportResolution resolution, int frameRate, int bitrateMbps, @org.jetbrains.annotations.Nullable()
    java.lang.Float targetAspectRatio) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.ExportResolution getResolution() {
        return null;
    }
    
    public final int getFrameRate() {
        return 0;
    }
    
    public final int getBitrateMbps() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getTargetAspectRatio() {
        return null;
    }
    
    public final int getBitrate() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Pair<java.lang.Integer, java.lang.Integer> getAdjustedDimensions(int sourceWidth, int sourceHeight) {
        return null;
    }
    
    public ExportSettings() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.ExportResolution component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.ExportSettings copy(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.ExportResolution resolution, int frameRate, int bitrateMbps, @org.jetbrains.annotations.Nullable()
    java.lang.Float targetAspectRatio) {
        return null;
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
}