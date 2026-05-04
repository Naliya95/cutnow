/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.model

import android.net.Uri
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

enum class FilterType { 
    NORMAL, BRIGHT, WARM, COOL, BW,
    VINTAGE, SEPIA, CONTRAST, SATURATION, VIGNETTE, SHARPEN
}

enum class CanvasType {
    NONE, BLUR, COLOR, GRADIENT
}


enum class EffectType {
    NONE,
    BLUR,           // Gaussian blur effect
    GLITCH,         // Digital glitch/distortion
    RGB_SPLIT,      // Chromatic aberration
    VIGNETTE,       // Darkened edges
    PIXELATE,       // Mosaic/pixel effect
    MIRROR,         // Horizontal/vertical mirroring
    SHAKE,          // Camera shake effect
    ZOOM_BLUR,      // Radial blur from center
    BW,             // Black and white
    INVERT,         // Invert colors
    BRIGHTNESS,     // Brightness adjustment
    CONTRAST,       // Contrast adjustment
    RETRO_VHS,      // VHS tape effect
    HORIZONTAL_OPEN, // Horizontal curtains opening effect
    VERTICAL_OPEN,   // Vertical curtains opening effect
    ADJUSTMENT       // Pro Adjustment parameters combined
}

enum class LayerType { TEXT, STICKER, IMAGE, AUDIO, EFFECT, FILTER, VIDEO }
enum class TransitionType { NONE, FADE, SLIDE_LEFT, SLIDE_RIGHT, SLIDE_UP, SLIDE_DOWN, ZOOM_IN, ZOOM_OUT, BLUR, WIPE_UP, WIPE_DOWN, WIPE_LEFT, WIPE_RIGHT, DISSOLVE,
    MIX, GLOW, BLUR_DISSOLVE, ZOOM_BLUR, GLITCH, SPIN, IRIS_CIRCLE, LIGHT_LEAK, GLOWUP }

enum class TextAnimationType {
    NONE,
    // Basic / Legacy
    FADE, FADE_IN, FADE_OUT,
    SLIDE_UP, SLIDE_DOWN, SLIDE_LEFT, SLIDE_RIGHT,
    
    // Pro IN/OUT
    ZOOM_IN, ZOOM_OUT,
    BOUNCE_IN, BOUNCE_OUT,
    ELASTIC_IN, ELASTIC_OUT,
    SPIN_IN, SPIN_OUT,
    SLIDE_UP_BOUNCE, SLIDE_DOWN_BOUNCE,
    POP_IN, POP_OUT,
    
    // Pro LOOP
    PULSE,
    SHAKE,
    WIGGLE,
    FLOAT,
    HEARTBEAT,
    
    // Special
    CURSOR // Typewriter
}

enum class KeyframeProperty { POS_X, POS_Y, SCALE, ROTATION, OPACITY }

enum class TextWarpType {
    NONE,
    ARC,
    WAVE,
    BULGE,
    FLAG,
    TWIST,
    FISHEYE,
    SQUEEZE
}

enum class TextAlignment { LEFT, CENTER, RIGHT }

data class Keyframe(
    val id: String = UUID.randomUUID().toString(),
    val timeMs: Long,
    val posX: Float? = null,
    val posY: Float? = null,
    val scale: Float? = null,
    val rotation: Float? = null,
    val opacity: Float? = null,
    val easeType: String = "LINEAR" // LINEAR, EASE_IN, EASE_OUT, EASE_IN_OUT
) {
    fun toJson(): JSONObject {
        val json = JSONObject()
        json.put("id", id)
        json.put("timeMs", timeMs)
        posX?.let { json.put("posX", it.toDouble()) }
        posY?.let { json.put("posY", it.toDouble()) }
        scale?.let { json.put("scale", it.toDouble()) }
        rotation?.let { json.put("rotation", it.toDouble()) }
        opacity?.let { json.put("opacity", it.toDouble()) }
        json.put("easeType", easeType)
        return json
    }

    companion object {
        fun fromJson(json: JSONObject): Keyframe {
            return Keyframe(
                id = json.optString("id", UUID.randomUUID().toString()),
                timeMs = json.optLong("timeMs"),
                posX = if (json.has("posX")) json.optDouble("posX").toFloat() else null,
                posY = if (json.has("posY")) json.optDouble("posY").toFloat() else null,
                scale = if (json.has("scale")) json.optDouble("scale").toFloat() else null,
                rotation = if (json.has("rotation")) json.optDouble("rotation").toFloat() else null,
                opacity = if (json.has("opacity")) json.optDouble("opacity").toFloat() else null,
                easeType = json.optString("easeType", "LINEAR")
            )
        }

        fun listToJson(keyframes: List<Keyframe>): JSONArray {
            val jsonArray = JSONArray()
            keyframes.forEach { jsonArray.put(it.toJson()) }
            return jsonArray
        }

        fun listFromJson(jsonStr: String): List<Keyframe> {
            val keyframes = mutableListOf<Keyframe>()
            try {
                val array = JSONArray(jsonStr)
                for (i in 0 until array.length()) {
                    keyframes.add(fromJson(array.getJSONObject(i)))
                }
            } catch (e: Exception) {}
            return keyframes
        }
    }
}


/**
 * Root data structure for a video editing project.
 */
data class TimelineProject(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "My Project",
    var thumbnailPath: String? = null,
    val creationDate: Long = System.currentTimeMillis(),
    var lastModified: Long = System.currentTimeMillis(),
    val videoTracks: MutableList<Track> = mutableListOf(),
    val audioTracks: MutableList<Track> = mutableListOf(),
    val overlayTracks: MutableList<Track> = mutableListOf(), // Text, Stickers
    var aspectRatio: Float? = null, // null = source video ratio
    val canvas: CanvasData = CanvasData() // Global project canvas
) {
    fun getTotalTimelineDuration(): Long {
        var maxEnd = 0L
        videoTracks.forEach { track ->
            track.clips.forEach { clip ->
                if (clip.endTimeMs > maxEnd) maxEnd = clip.endTimeMs
            }
        }
        audioTracks.forEach { track ->
            track.clips.forEach { clip ->
                if (clip.endTimeMs > maxEnd) maxEnd = clip.endTimeMs
            }
        }
        overlayTracks.forEach { track ->
            track.clips.forEach { clip ->
                if (clip.endTimeMs > maxEnd) maxEnd = clip.endTimeMs
            }
        }
        return maxEnd
    }

    fun toJson(): JSONObject {
        val json = JSONObject()
        json.put("id", id)
        json.put("name", name)
        json.put("thumbnailPath", thumbnailPath ?: JSONObject.NULL)
        json.put("creationDate", creationDate)
        json.put("lastModified", lastModified)
        json.put("aspectRatio", if (aspectRatio != null) aspectRatio!!.toDouble() else JSONObject.NULL)
        
        val videoArray = JSONArray()
        videoTracks.forEach { videoArray.put(it.toJson()) }
        json.put("videoTracks", videoArray)
        
        val audioArray = JSONArray()
        audioTracks.forEach { audioArray.put(it.toJson()) }
        json.put("audioTracks", audioArray)
        
        val overlayArray = JSONArray()
        overlayTracks.forEach { overlayArray.put(it.toJson()) }
        json.put("overlayTracks", overlayArray)
        
        return json
    }

    companion object {
        fun fromJson(json: JSONObject): TimelineProject {
            val project = TimelineProject(
                id = json.optString("id", UUID.randomUUID().toString()),
                name = json.optString("name", "Untitled Project"),
                thumbnailPath = if (json.isNull("thumbnailPath")) null else json.optString("thumbnailPath"),
                creationDate = json.optLong("creationDate", System.currentTimeMillis()),
                lastModified = json.optLong("lastModified", System.currentTimeMillis()),
                aspectRatio = if (json.has("aspectRatio") && !json.isNull("aspectRatio")) json.optDouble("aspectRatio").toFloat() else null
            )
            
            val videoArray = json.optJSONArray("videoTracks")
            if (videoArray != null) {
                for (i in 0 until videoArray.length()) {
                    project.videoTracks.add(Track.fromJson(videoArray.getJSONObject(i)))
                }
            }
            
            val audioArray = json.optJSONArray("audioTracks")
            if (audioArray != null) {
                for (i in 0 until audioArray.length()) {
                    project.audioTracks.add(Track.fromJson(audioArray.getJSONObject(i)))
                }
            }
            
            val overlayArray = json.optJSONArray("overlayTracks")
            if (overlayArray != null) {
                for (i in 0 until overlayArray.length()) {
                    project.overlayTracks.add(Track.fromJson(overlayArray.getJSONObject(i)))
                }
            }
            
            return project
        }
    }
}

enum class TrackType { VIDEO, AUDIO, OVERLAY }

data class Track(
    val id: String = UUID.randomUUID().toString(),
    val type: TrackType,
    val clips: MutableList<Clip> = mutableListOf(),
    var isMuted: Boolean = false,
    var isLocked: Boolean = false,
    var volume: Float = 1.0f
) {
    fun toJson(): JSONObject {
        val json = JSONObject()
        json.put("id", id)
        json.put("type", type.name)
        json.put("isMuted", isMuted)
        json.put("isLocked", isLocked)
        json.put("volume", volume.toDouble())
        
        val clipsArray = JSONArray()
        clips.forEach { clipsArray.put(it.toJson()) }
        json.put("clips", clipsArray)
        
        return json
    }

    companion object {
        fun fromJson(json: JSONObject): Track {
            val type = TrackType.valueOf(json.optString("type", TrackType.VIDEO.name))
            val track = Track(
                id = json.optString("id", UUID.randomUUID().toString()),
                type = type,
                isMuted = json.optBoolean("isMuted", false),
                isLocked = json.optBoolean("isLocked", false),
                volume = json.optDouble("volume", 1.0).toFloat()
            )
            
            val clipsArray = json.optJSONArray("clips")
            if (clipsArray != null) {
                for (i in 0 until clipsArray.length()) {
                    track.clips.add(Clip.fromJson(clipsArray.getJSONObject(i)))
                }
            }
            
            return track
        }
    }
}

enum class ClipType { VIDEO, AUDIO, IMAGE, TEXT, STICKER, EFFECT }

data class Clip(
    val id: String = UUID.randomUUID().toString(),
    val type: ClipType,
    var filePath: String = "", // URI or path or content
    
    // Timeline Position
    var startTimeMs: Long = 0,
    var durationMs: Long = 0,
    
    // Source Trimming
    var sourceStartTimeMs: Long = 0,
    var sourceDurationMs: Long = 0,
    
    // Properties
    var speed: Float = 1.0f,
    var volume: Float = 1.0f,
    
    // Geometry (for overlays/pip)
    var posX: Float = 0.5f,
    var posY: Float = 0.5f,
    var scaleX: Float = 1.0f,
    var scaleY: Float = 1.0f,
    var rotation: Float = 0f,
    
    // Styles (for text)
    var styleJson: String = "{}",
    
    // Split/Trim properties
    var filterType: FilterType = FilterType.NORMAL,
    var filterIntensity: Float = 1.0f,
    var effectType: EffectType = EffectType.NONE,
    var effectIntensity: Float = 0.5f,
    
    // Video Transition (between this clip and the next)
    var transitionType: TransitionType = TransitionType.NONE,
    var transitionDurationMs: Long = 500L,
    
    var keyframesJson: String = "[]",
    
    var flipHorizontal: Boolean = false,
    var flipVertical: Boolean = false,
    
    // Adjustments
    var adjustmentsJson: String = "{}",
    
    // Canvas (Background)
    var canvasJson: String = "{}"
) {
    val canvas: CanvasData get() = CanvasData.fromJson(canvasJson)
    val adjustments: AdjustmentData get() = AdjustmentData.fromJson(adjustmentsJson)
    val keyframes: List<Keyframe> get() = Keyframe.listFromJson(keyframesJson)
    val isImage: Boolean get() = type == ClipType.IMAGE || type == ClipType.TEXT || type == ClipType.STICKER

    val endTimeMs: Long get() = startTimeMs + durationMs

    fun toJson(): JSONObject {
        val json = JSONObject()
        json.put("id", id)
        json.put("type", type.name)
        json.put("filePath", filePath)
        json.put("startTimeMs", startTimeMs)
        json.put("durationMs", durationMs)
        json.put("sourceStartTimeMs", sourceStartTimeMs)
        json.put("sourceDurationMs", sourceDurationMs)
        json.put("speed", speed.toDouble())
        json.put("volume", volume.toDouble())
        
        json.put("posX", posX.toDouble())
        json.put("posY", posY.toDouble())
        json.put("scaleX", scaleX.toDouble())
        json.put("scaleY", scaleY.toDouble())
        json.put("rotation", rotation.toDouble())
        
        json.put("styleJson", styleJson)
        json.put("filterType", filterType.name)
        json.put("filterIntensity", filterIntensity.toDouble())
        json.put("effectType", effectType.name)
        json.put("effectIntensity", effectIntensity.toDouble())
        json.put("transitionType", transitionType.name)
        json.put("transitionDurationMs", transitionDurationMs)
        json.put("keyframesJson", keyframesJson)
        json.put("adjustmentsJson", adjustmentsJson)
        json.put("canvasJson", canvasJson)
        
        return json
    }


    companion object {
        fun fromJson(json: JSONObject): Clip {
            return Clip(
                id = json.optString("id", UUID.randomUUID().toString()),
                type = ClipType.valueOf(json.optString("type", ClipType.VIDEO.name)),
                filePath = json.optString("filePath", ""),
                startTimeMs = json.optLong("startTimeMs", 0),
                durationMs = json.optLong("durationMs", 0),
                sourceStartTimeMs = json.optLong("sourceStartTimeMs", 0),
                sourceDurationMs = json.optLong("sourceDurationMs", 0),
                speed = json.optDouble("speed", 1.0).toFloat(),
                volume = json.optDouble("volume", 1.0).toFloat(),
                posX = json.optDouble("posX", 0.5).toFloat(),
                posY = json.optDouble("posY", 0.5).toFloat(),
                scaleX = json.optDouble("scaleX", 1.0).toFloat(),
                scaleY = json.optDouble("scaleY", 1.0).toFloat(),
                rotation = json.optDouble("rotation", 0.0).toFloat(),
                styleJson = json.optString("styleJson", "{}"),
                filterType = FilterType.valueOf(json.optString("filterType", FilterType.NORMAL.name)),
                filterIntensity = json.optDouble("filterIntensity", 1.0).toFloat(),
                effectType = EffectType.valueOf(json.optString("effectType", EffectType.NONE.name)),
                effectIntensity = json.optDouble("effectIntensity", 0.5).toFloat(),
                transitionType = TransitionType.valueOf(json.optString("transitionType", TransitionType.NONE.name)),
                transitionDurationMs = json.optLong("transitionDurationMs", 500L),
                keyframesJson = json.optString("keyframesJson", "[]"),
                adjustmentsJson = json.optString("adjustmentsJson", "{}"),
                canvasJson = json.optString("canvasJson", "{}")
            )
        }
    }
}

/**
 * Legacy/UI-level representation of a video clip.
 */
data class VideoSegment(
    val id: String = UUID.randomUUID().toString(),
    val uri: Uri,
    var sourceDurationMs: Long = 0L,
    var trimStartMs: Long = 0L,
    var trimEndMs: Long = 0L,
    var speed: Float = 1.0f,
    var cropRect: CropRect = CropRect(), // Normalized 0-1 coordinates
    var rotation: Int = 0, // 0, 90, 180, 270
    var flipHorizontal: Boolean = false,
    var flipVertical: Boolean = false,
    var filterType: FilterType = FilterType.NORMAL, // Color filter
    var transitionType: TransitionType = TransitionType.NONE,
    var transitionDurationMs: Long = 500L,
    // Transform properties
    var scaleX: Float = 1.0f,
    var scaleY: Float = 1.0f,
    var posX: Float = 0.5f,
    var posY: Float = 0.5f,
    // Image support: true when this segment is a static image (not a video)
    val isImage: Boolean = false,
    // Keyframes
    val keyframes: MutableList<Keyframe> = mutableListOf(),
    // Adjustments
    var adjustments: AdjustmentData = AdjustmentData(),
    // Canvas
    var canvas: CanvasData = CanvasData()
) {
    val durationMs get() = (trimEndMs - trimStartMs).coerceAtLeast(0L)
    /** Timeline/export duration: faster speed = shorter effective duration */
    val effectiveDurationMs get() = (durationMs / speed.coerceAtLeast(0.1f)).toLong().coerceAtLeast(1L)
}

data class AdjustmentData(
    var brightness: Float = 0f,    // -1.0 to 1.0
    var contrast: Float = 1.0f,    // 0.0 to 2.0
    var saturation: Float = 1.0f,  // 0.0 to 2.0
    var brilliance: Float = 0f,    // -1.0 to 1.0
    var sharpen: Float = 0f,       // 0.0 to 1.0
    var highlights: Float = 0f,    // -1.0 to 1.0
    var shadows: Float = 0f,       // -1.0 to 1.0
    var whites: Float = 0f,        // -1.0 to 1.0
    var blacks: Float = 0f,        // -1.0 to 1.0
    var temp: Float = 0f,          // -1.0 to 1.0 (Blue to Yellow)
    var hue: Float = 0f,           // -1.0 to 1.0
    var fade: Float = 0f,          // 0.0 to 1.0
    var vignate: Float = 0f,       // 0.0 to 1.0 (Note: spelled vignate as per user request)
    var grain: Float = 0f,         // 0.0 to 1.0
    var clarity: Float = 0f,       // -1.0 to 1.0 (Local contrast)
    // HSL support
    var hslHue: Float = 0f,
    var hslSaturation: Float = 1.0f,
    var hslLightness: Float = 1.0f
) {
    fun toJson(): String {
        val json = JSONObject()
        json.put("brightness", brightness.toDouble())
        json.put("contrast", contrast.toDouble())
        json.put("saturation", saturation.toDouble())
        json.put("brilliance", brilliance.toDouble())
        json.put("sharpen", sharpen.toDouble())
        json.put("highlights", highlights.toDouble())
        json.put("shadows", shadows.toDouble())
        json.put("whites", whites.toDouble())
        json.put("blacks", blacks.toDouble())
        json.put("temp", temp.toDouble())
        json.put("hue", hue.toDouble())
        json.put("fade", fade.toDouble())
        json.put("vignate", vignate.toDouble())
        json.put("grain", grain.toDouble())
        json.put("clarity", clarity.toDouble())
        json.put("hslHue", hslHue.toDouble())
        json.put("hslSaturation", hslSaturation.toDouble())
        json.put("hslLightness", hslLightness.toDouble())
        return json.toString()
    }

    companion object {
        fun fromJson(jsonStr: String): AdjustmentData {
            if (jsonStr.isEmpty() || jsonStr == "{}") return AdjustmentData()
            return try {
                val json = JSONObject(jsonStr)
                AdjustmentData(
                    brightness = json.optDouble("brightness", 0.0).toFloat(),
                    contrast = json.optDouble("contrast", 1.0).toFloat(),
                    saturation = json.optDouble("saturation", 1.0).toFloat(),
                    brilliance = json.optDouble("brilliance", 0.0).toFloat(),
                    sharpen = json.optDouble("sharpen", 0.0).toFloat(),
                    highlights = json.optDouble("highlights", 0.0).toFloat(),
                    shadows = json.optDouble("shadows", 0.0).toFloat(),
                    whites = json.optDouble("whites", 0.0).toFloat(),
                    blacks = json.optDouble("blacks", 0.0).toFloat(),
                    temp = json.optDouble("temp", 0.0).toFloat(),
                    hue = json.optDouble("hue", 0.0).toFloat(),
                    fade = json.optDouble("fade", 0.0).toFloat(),
                    vignate = json.optDouble("vignate", 0.0).toFloat(),
                    grain = json.optDouble("grain", 0.0).toFloat(),
                    clarity = json.optDouble("clarity", 0.0).toFloat(),
                    hslHue = json.optDouble("hslHue", 0.0).toFloat(),
                    hslSaturation = json.optDouble("hslSaturation", 1.0).toFloat(),
                    hslLightness = json.optDouble("hslLightness", 1.0).toFloat()
                )
            } catch (e: Exception) {
                AdjustmentData()
            }
        }
    }
}

data class CanvasData(
    var type: CanvasType = CanvasType.NONE,
    var blur: Float = 0f, // 0.0 to 1.0
    var color: Int = android.graphics.Color.BLACK,
    var gradientColors: List<Int> = listOf(android.graphics.Color.BLACK, android.graphics.Color.BLACK)
) {
    fun toJson(): String {
        val json = JSONObject()
        json.put("type", type.name)
        json.put("blur", blur.toDouble())
        json.put("color", color)
        
        val gradArray = JSONArray()
        gradientColors.forEach { gradArray.put(it) }
        json.put("gradientColors", gradArray)
        
        return json.toString()
    }

    companion object {
        fun fromJson(jsonStr: String): CanvasData {
            if (jsonStr.isEmpty() || jsonStr == "{}") return CanvasData()
            return try {
                val json = JSONObject(jsonStr)
                val type = CanvasType.valueOf(json.optString("type", CanvasType.NONE.name))
                val blur = json.optDouble("blur", 0.0).toFloat()
                val color = json.optInt("color", android.graphics.Color.BLACK)
                
                val gradientColors = mutableListOf<Int>()
                val gradArray = json.optJSONArray("gradientColors")
                if (gradArray != null) {
                    for (i in 0 until gradArray.length()) {
                        gradientColors.add(gradArray.getInt(i))
                    }
                }
                
                CanvasData(type, blur, color, if (gradientColors.isEmpty()) listOf(android.graphics.Color.BLACK, android.graphics.Color.BLACK) else gradientColors)
            } catch (e: Exception) {
                CanvasData()
            }
        }
    }
}


data class CropRect(
    val left: Float = 0f,
    val top: Float = 0f,
    val right: Float = 1f,
    val bottom: Float = 1f
) {
    val width get() = right - left
    val height get() = bottom - top
}

/**
 * Legacy/UI-level representation of an overlay layer.
 */
data class MediaLayer(
    val id: String = UUID.randomUUID().toString(),
    val type: LayerType,
    var startTimeMs: Long,
    var durationMs: Long,
    val content: String, // Text content or URI
    var transitionIn: TransitionType = TransitionType.NONE,
    var transitionOut: TransitionType = TransitionType.NONE,
    var posX: Float = 0.5f, // Normalized 0-1
    var posY: Float = 0.5f, // Normalized 0-1
    var scale: Float = 1.0f,
    var scaleX: Float = 1.0f,
    var scaleY: Float = 1.0f,
    var rotation: Float = 0f,
    var isSelected: Boolean = false,
    var originalWidth: Float = 0f, // Original view width before scaling
    var originalHeight: Float = 0f, // Original view height before scaling
    // Text styling properties
    var textColor: Int = android.graphics.Color.WHITE,
    var backgroundColor: Int = android.graphics.Color.TRANSPARENT,
    var fontFamily: String = "sans-serif",
    var fontSize: Float = 64f,
    var strokeColor: Int = android.graphics.Color.TRANSPARENT,
    var strokeWidth: Float = 0f,
    var textGradientColors: List<Int>? = null,
    var textWarpType: TextWarpType = TextWarpType.NONE,
    var textWarpIntensity: Float = 0.5f, // 0.0 to 1.0 (default mid)
    var volume: Float = 1.0f, // 0.0 to 1.0
    var sourceStartTimeMs: Long = 0L,
    var sourceDurationMs: Long = 0L,
    var waveformPoints: List<Float>? = null,
    // Effect properties (for EFFECT type layers)
    var effectType: EffectType? = null,
    var effectIntensity: Float = 0.5f, // 0.0 to 1.0
    // Filter properties (for FILTER type layers)
    var filterType: FilterType? = null,
    var filterIntensity: Float = 1.0f, // 0.0 to 1.0
    // Text animation properties (Legacy & Pro)
    // Legacy single field (Migrate to In/Out/Loop)
    var textAnimation: TextAnimationType = TextAnimationType.NONE,
    
    // Pro Animation Fields
    var animationIn: TextAnimationType = TextAnimationType.NONE,
    var animationOut: TextAnimationType = TextAnimationType.NONE,
    var animationLoop: TextAnimationType = TextAnimationType.NONE,
    
    var animInDuration: Long = 500L,
    var animOutDuration: Long = 500L,
    var animLoopDuration: Long = 1000L, // Loop cycle duration
    
    // Legacy durations (mapped to In/Out)
    var textAnimInDurationMs: Long = 500L,
    var textAnimOutDurationMs: Long = 500L,
    var textAnimLoopCursor: Boolean = false,
    // Audio properties
    var speed: Float = 1.0f, // 0.25x to 4.0x
    
    // Flipped state
    var flipHorizontal: Boolean = false,
    var flipVertical: Boolean = false,

    // Background styling
    var textBgPaddingX: Float = -1f, // -1 means use default (fontSize * 0.5)
    var textBgPaddingY: Float = -1f, // -1 means use default (fontSize * 0.5)
    var textBgCornerRadius: Float = -1f, // -1 means use default (padding)
    var textAlignment: TextAlignment = TextAlignment.CENTER,

    // Keyframes
    val keyframes: MutableList<Keyframe> = mutableListOf()
) {
    val endTimeMs get() = startTimeMs + durationMs

    val contentHash: String get() = if (type == LayerType.TEXT) {
        "${content}_${textColor}_${backgroundColor}_${fontFamily}_${fontSize}_${strokeColor}_${strokeWidth}_${textWarpType.name}_${textWarpIntensity}_${textGradientColors?.joinToString()}_${textBgPaddingX}_${textBgPaddingY}_${textBgCornerRadius}_${textAlignment.name}_${animationIn.name}_${animationOut.name}_${animationLoop.name}_${animInDuration}_${animOutDuration}_${animLoopDuration}"
    } else {
        "${content}_${animationIn.name}_${animationOut.name}_${animationLoop.name}_${animInDuration}_${animOutDuration}_${animLoopDuration}"
    }
}
