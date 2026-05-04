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
import com.cutnow.sdk.model.*
import java.util.UUID

class TimelineEngine(private val context: Context) {

    var currentProject: TimelineProject = TimelineProject()
        private set

    private val stateListeners = mutableListOf<ITimelineStateListener>()

    interface ITimelineStateListener {
        fun onProjectLoaded(project: TimelineProject)
        fun onTrackUpdated(track: Track)
        fun onTimelineUpdated()
    }

    fun addStateListener(listener: ITimelineStateListener) {
        stateListeners.add(listener)
    }

    fun createNewProject(name: String) {
        currentProject = TimelineProject(name = name)
        // Ensure default tracks exist
        if (currentProject.videoTracks.isEmpty()) {
            currentProject.videoTracks.add(Track(type = TrackType.VIDEO))
        }
        if (currentProject.audioTracks.isEmpty()) {
            currentProject.audioTracks.add(Track(type = TrackType.AUDIO))
        }
        notifyProjectLoaded()
    }

    fun loadProject(projectId: String) {
        val project = ProjectManager.loadProject(context, projectId)
        if (project != null) {
            currentProject = project
            notifyProjectLoaded()
        }
    }

    fun saveProject() {
        ProjectManager.saveProject(context, currentProject)
    }


    
    fun removeClip(trackType: TrackType, trackIndex: Int, clipId: String) {
        val track = getTrack(trackType, trackIndex) ?: return
        val clipIndex = track.clips.indexOfFirst { it.id == clipId }
        
        if (clipIndex != -1) {
            track.clips.removeAt(clipIndex)
            recalculateTrackTiming(track)
            notifyTrackUpdated(track)
            notifyTimelineUpdated()
        }
    }

    fun moveClip(trackType: TrackType, trackIndex: Int, fromIndex: Int, toIndex: Int) {
        val track = getTrack(trackType, trackIndex) ?: return
        if (fromIndex in track.clips.indices && toIndex in track.clips.indices) {
            val clip = track.clips.removeAt(fromIndex)
            track.clips.add(toIndex, clip)
            recalculateTrackTiming(track)
            notifyTrackUpdated(track)
            notifyTimelineUpdated()
        }
    }

    fun updateClip(trackType: TrackType, trackIndex: Int, updatedClip: Clip) {
        val track = getTrack(trackType, trackIndex) ?: return
        val index = track.clips.indexOfFirst { it.id == updatedClip.id }
        
        if (index != -1) {
            // Update the clip
            track.clips[index] = updatedClip
            
            // If duration changed, we might need to ripple
            recalculateTrackTiming(track)
            notifyTrackUpdated(track)
            notifyTimelineUpdated()
        }
    }
    
    private fun getTrack(trackType: TrackType, trackIndex: Int): Track? {
        val trackList = when (trackType) {
            TrackType.VIDEO -> currentProject.videoTracks
            TrackType.AUDIO -> currentProject.audioTracks
            TrackType.OVERLAY -> currentProject.overlayTracks
        }
        return if (trackIndex in trackList.indices) trackList[trackIndex] else null
    }

    private fun recalculateTrackTiming(track: Track) {
        if (track.type == TrackType.OVERLAY) return
        var currentTime = 0L
        track.clips.forEach { clip ->
            clip.startTimeMs = currentTime
            // effective duration based on speed
            val effectiveDuration = (clip.durationMs / clip.speed).toLong()
            currentTime += effectiveDuration
        }
    }
    
    fun splitClip(trackType: TrackType, trackIndex: Int, splitTimeMs: Long) {
         val track = getTrack(trackType, trackIndex) ?: return
        
        // Find clip spanning splitTimeMs
        // splitTimeMs is GLOBAL timeline time
        val clipIndex = track.clips.indexOfFirst { splitTimeMs > it.startTimeMs && splitTimeMs < it.endTimeMs }
        if (clipIndex == -1) return
        
        val originalClip = track.clips[clipIndex]
        
        // Calculate offset in EFFECTIVE time (timeline time)
        val offsetInEffective = splitTimeMs - originalClip.startTimeMs
        
        // Convert to SOURCE time duration
        val offsetInSource = (offsetInEffective * originalClip.speed).toLong()
        
        // Create second part
        val newClip = originalClip.copy(
            id = UUID.randomUUID().toString(),
            // startTime will be fixed by recalculate
            durationMs = originalClip.durationMs - offsetInSource,
            sourceStartTimeMs = originalClip.sourceStartTimeMs + offsetInSource
        )
        
        // Update first part
        originalClip.durationMs = offsetInSource
        
        // Insert new clip
        track.clips.add(clipIndex + 1, newClip)
        
        recalculateTrackTiming(track)
        notifyTrackUpdated(track)
        notifyTimelineUpdated()
    }

    fun addClip(trackType: TrackType, trackIndex: Int, filePath: String, durationMs: Long) {
        val track = getTrack(trackType, trackIndex) ?: return
            
        val clipType = when (trackType) {
            TrackType.VIDEO -> ClipType.VIDEO
            TrackType.AUDIO -> ClipType.AUDIO
            TrackType.OVERLAY -> ClipType.TEXT 
        }
        
        val newClip = Clip(
            type = clipType,
            filePath = filePath,
            // startTime will be set by recalculate
            durationMs = durationMs
        )
        track.clips.add(newClip)
        
        recalculateTrackTiming(track)
        notifyTrackUpdated(track)
        notifyTimelineUpdated()
    }

    private fun notifyProjectLoaded() {
        stateListeners.forEach { it.onProjectLoaded(currentProject) }
    }

    private fun notifyTrackUpdated(track: Track) {
        stateListeners.forEach { it.onTrackUpdated(track) }
    }
    
    fun notifyTimelineUpdated() {
        stateListeners.forEach { it.onTimelineUpdated() }
    }
}
