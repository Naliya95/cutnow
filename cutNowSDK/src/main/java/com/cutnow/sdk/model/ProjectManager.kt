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

import com.cutnow.sdk.R

import android.content.Context
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

object ProjectManager {

    private const val PROJECTS_DIR = "projects"
    private const val PROJECT_EXT = ".json"

    fun saveProject(context: Context, project: TimelineProject) {
        val projectsDir = File(context.filesDir, PROJECTS_DIR)
        if (!projectsDir.exists()) {
            projectsDir.mkdirs()
        }

        val file = File(projectsDir, "${project.id}$PROJECT_EXT")
        project.lastModified = System.currentTimeMillis()
        
        try {
            val jsonString = project.toJson().toString()
            FileOutputStream(file).use { out ->
                out.write(jsonString.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw IOException("Failed to save project: ${e.message}")
        }
    }

    fun loadProject(context: Context, projectId: String): TimelineProject? {
        val projectsDir = File(context.filesDir, PROJECTS_DIR)
        val file = File(projectsDir, "$projectId$PROJECT_EXT")
        
        if (!file.exists()) return null
        
        return try {
            val jsonString = FileInputStream(file).bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            TimelineProject.fromJson(jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun listProjects(context: Context): List<TimelineProject> {
        val projectsDir = File(context.filesDir, PROJECTS_DIR)
        val projects = mutableListOf<TimelineProject>()
        
        if (projectsDir.exists()) {
            projectsDir.listFiles()?.forEach { file ->
                if (file.name.endsWith(PROJECT_EXT)) {
                    try {
                        val jsonString = FileInputStream(file).bufferedReader().use { it.readText() }
                        val jsonObject = JSONObject(jsonString)
                        projects.add(TimelineProject.fromJson(jsonObject))
                    } catch (e: Exception) {
                        // Skip corrupted files
                    }
                }
            }
        }
        return projects.sortedByDescending { it.lastModified }
    }
    
    fun deleteProject(context: Context, projectId: String): Boolean {
        val projectsDir = File(context.filesDir, PROJECTS_DIR)
        val file = File(projectsDir, "$projectId$PROJECT_EXT")
        return if (file.exists()) file.delete() else false
    }

    fun renameProject(context: Context, projectId: String, newName: String) {
        val project = loadProject(context, projectId)
        if (project != null) {
            project.name = newName
            saveProject(context, project)
        }
    }

    fun duplicateProject(context: Context, projectId: String) {
        val project = loadProject(context, projectId)
        if (project != null) {
            val newProject = project.copy(
                id = java.util.UUID.randomUUID().toString(),
                name = "${project.name} (Copy)",
                lastModified = System.currentTimeMillis()
            )
            saveProject(context, newProject)
        }
    }
}
