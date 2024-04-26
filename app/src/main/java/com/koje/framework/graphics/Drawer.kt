package com.koje.framework.graphics

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20.GL_ARRAY_BUFFER
import android.opengl.GLES20.GL_COMPILE_STATUS
import android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER
import android.opengl.GLES20.GL_FRAGMENT_SHADER
import android.opengl.GLES20.GL_NEAREST
import android.opengl.GLES20.GL_STATIC_DRAW
import android.opengl.GLES20.GL_TEXTURE_2D
import android.opengl.GLES20.GL_TEXTURE_MAG_FILTER
import android.opengl.GLES20.GL_TEXTURE_MIN_FILTER
import android.opengl.GLES20.GL_VERTEX_SHADER
import android.opengl.GLES20.glAttachShader
import android.opengl.GLES20.glBindBuffer
import android.opengl.GLES20.glBindTexture
import android.opengl.GLES20.glBufferData
import android.opengl.GLES20.glCompileShader
import android.opengl.GLES20.glCreateProgram
import android.opengl.GLES20.glCreateShader
import android.opengl.GLES20.glGenBuffers
import android.opengl.GLES20.glGenTextures
import android.opengl.GLES20.glGetShaderInfoLog
import android.opengl.GLES20.glGetShaderiv
import android.opengl.GLES20.glLinkProgram
import android.opengl.GLES20.glShaderSource
import android.opengl.GLES20.glTexParameteri
import android.opengl.GLUtils
import com.koje.framework.utils.Logger
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

abstract class Drawer() {

    val bytesPerFloat = 4
    val bytesPerShort = 2

    var programHandle = 0
    var iboHandle = 0
    var iboCount = 0
    var vboHandle = 0

    abstract fun createVertexShader(): String
    abstract fun createFragmentShader(): String

    open fun init() {

    }

    fun initBuffers() {
        iboCount = 0

        val vboData = mutableListOf<Float>()
        val iboData = mutableListOf<Short>()

        fun addPoint(vx: Float, vy: Float, tx: Float, ty: Float) {
            vboData.add(vx)
            vboData.add(vy)
            vboData.add(0f)
            vboData.add(tx)
            vboData.add(ty)

            iboData.add(iboCount.toShort())
            iboCount++
        }

        addPoint(-0.5f, -0.5f, 0f, 1f) // ul
        addPoint(-0.5f, 0.5f, 0f, 0f) // or
        addPoint(0.5f, 0.5f, 1f, 0f)

        addPoint(-0.5f, -0.5f, 0f, 1f) // ul
        addPoint(0.5f, 0.5f, 1f, 0f) // or
        addPoint(0.5f, -0.5f, 1f, 1f)

        val buffers = intArrayOf(0, 0)
        glGenBuffers(2, buffers, 0);

        initVbo(buffers[0], vboData)
        initIbo(buffers[1], iboData)
    }

    fun loadTexture(context: Context, resourceId: Int): Int {
        val handles = IntArray(1)
        glGenTextures(1, handles, 0)

        val result = handles[0]
        if (result == 0) {
            Logger.abort("texture loading error")
        }

        glBindTexture(GL_TEXTURE_2D, result)
        glTexParameteri(
            GL_TEXTURE_2D,
            GL_TEXTURE_MIN_FILTER,
            GL_NEAREST
        )
        glTexParameteri(
            GL_TEXTURE_2D,
            GL_TEXTURE_MAG_FILTER,
            GL_NEAREST
        )

        val options = BitmapFactory.Options()
        options.inScaled = false
        val bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options)
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)
        bitmap.recycle()

        return result
    }

    fun initProgramHandle() {
        val result = glCreateProgram()
        glAttachShader(result, createShader(GL_VERTEX_SHADER, createVertexShader()))
        glAttachShader(result, createShader(GL_FRAGMENT_SHADER, createFragmentShader()))
        glLinkProgram(result)
        programHandle = result
    }


    fun initVbo(handle: Int, data: List<Float>) {
        val buffer = createFloatBuffer(data.toFloatArray())
        glBindBuffer(GL_ARRAY_BUFFER, handle)
        glBufferData(
            GL_ARRAY_BUFFER,
            data.size * bytesPerFloat,
            buffer,
            GL_STATIC_DRAW
        )
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        vboHandle = handle
    }

    fun initIbo(handle: Int, data: List<Short>) {
        val buffer = createShortBuffer(data.toShortArray())
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle)
        glBufferData(
            GL_ELEMENT_ARRAY_BUFFER,
            data.size * bytesPerShort,
            buffer,
            GL_STATIC_DRAW
        )
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
        iboHandle = handle
    }

    fun createShader(type: Int, source: String): Int {
        val result = glCreateShader(type)
        glShaderSource(result, source)
        glCompileShader(result)

        val compiled = IntArray(1)
        glGetShaderiv(result, GL_COMPILE_STATUS, compiled, 0)
        if (compiled[0] == 0) {
            Logger.abort("shader compile error: " + glGetShaderInfoLog(result))
        }
        return result
    }

    fun createFloatBuffer(data: FloatArray): FloatBuffer {
        val result = ByteBuffer
            .allocateDirect(data.size * bytesPerFloat)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        result.put(data)
        result.position(0)
        return result
    }

    fun createShortBuffer(data: ShortArray): ShortBuffer {
        val result = ByteBuffer
            .allocateDirect(data.size * bytesPerShort)
            .order(ByteOrder.nativeOrder())
            .asShortBuffer()
        result.put(data)
        result.position(0)
        return result
    }
}
