/*
 * Copyright : (c) 2026 AndroPlaza
 * Company : AndroPlaza
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Buddhika
 * Contact : support@androplaza.store
 * Whatsapp : +94711920144
 * All rights reserved.
 */

package com.cutnow.sdk.util

import com.cutnow.sdk.R

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Custom GL Program wrapper for the SDK.
 */
class UtilGlProgram(vertexShaderCode: String, fragmentShaderCode: String) {
    val programId: Int
        get() = _programId
        
    private var _programId: Int = 0

    init {
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        
        _programId = GLES20.glCreateProgram()
        GLES20.glAttachShader(_programId, vertexShader)
        GLES20.glAttachShader(_programId, fragmentShader)
        GLES20.glLinkProgram(_programId)
        
        // Check link status
        val linkStatus = IntArray(1)
        GLES20.glGetProgramiv(_programId, GLES20.GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(_programId)
            throw RuntimeException("Error creating GL program")
        }
    }
    
    fun use() {
        GLES20.glUseProgram(_programId)
    }
    
    fun delete() {
        GLES20.glDeleteProgram(_programId)
    }
    
    fun setSamplerTexIdUniform(name: String, texId: Int, unit: Int, isOes: Boolean = false) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + unit)
        val target = if (isOes) android.opengl.GLES11Ext.GL_TEXTURE_EXTERNAL_OES else GLES20.GL_TEXTURE_2D
        GLES20.glBindTexture(target, texId)
        val handle = GLES20.glGetUniformLocation(_programId, name)
        if (handle != -1) GLES20.glUniform1i(handle, unit)
    }
    
    fun setFloatUniform(name: String, value: Float) {
        val handle = GLES20.glGetUniformLocation(_programId, name)
        if (handle != -1) GLES20.glUniform1f(handle, value)
    }

    fun setFloatVec4Uniform(name: String, x: Float, y: Float, z: Float, w: Float) {
        val handle = GLES20.glGetUniformLocation(_programId, name)
        if (handle != -1) GLES20.glUniform4f(handle, x, y, z, w)
    }
    
    fun setIntUniform(name: String, value: Int) {
        val handle = GLES20.glGetUniformLocation(_programId, name)
        if (handle != -1) GLES20.glUniform1i(handle, value)
    }

    fun setMatrixUniform(name: String, matrix: FloatArray) {
        val handle = GLES20.glGetUniformLocation(_programId, name)
        if (handle != -1) GLES20.glUniformMatrix4fv(handle, 1, false, matrix, 0)
    }

    fun setFloatArrayUniform(name: String, value: FloatArray) {
        val handle = GLES20.glGetUniformLocation(_programId, name)
        if (handle != -1) GLES20.glUniform1fv(handle, value.size, value, 0)
    }
    
    fun bindAttributesAndUniforms() {
        val positionHandle = GLES20.glGetAttribLocation(_programId, "aFramePosition")
        val texCoordHandle = GLES20.glGetAttribLocation(_programId, "aTexSamplingCoord")
        
        // Fullscreen quad covering NDC space (-1..1) with matching texture coordinates (0..1).
        // Triangle strip order for rectangle: bottom-left, bottom-right, top-right, top-left.
        val vertexData = floatArrayOf(
            -1f, -1f, // BL
             1f, -1f, // BR
             1f,  1f, // TR
            -1f,  1f  // TL
        )

        // Texture coordinates mapped to matching vertex order.
        val texData = floatArrayOf(
            0f, 0f, // BL
            1f, 0f, // BR
            1f, 1f, // TR
            0f, 1f  // TL
        )

        if (positionHandle >= 0) {
            val vertexBuffer: FloatBuffer = ByteBuffer
                .allocateDirect(vertexData.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .apply {
                    put(vertexData)
                    position(0)
                }

            GLES20.glEnableVertexAttribArray(positionHandle)
            GLES20.glVertexAttribPointer(
                positionHandle,
                2,
                GLES20.GL_FLOAT,
                false,
                2 * 4,
                vertexBuffer
            )
        }

        if (texCoordHandle >= 0) {
            val texBuffer: FloatBuffer = ByteBuffer
                .allocateDirect(texData.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .apply {
                    put(texData)
                    position(0)
                }

            GLES20.glEnableVertexAttribArray(texCoordHandle)
            GLES20.glVertexAttribPointer(
                texCoordHandle,
                2,
                GLES20.GL_FLOAT,
                false,
                2 * 4,
                texBuffer
            )
        }
    }
    
    private fun loadShader(type: Int, shaderCode: String): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }
}
