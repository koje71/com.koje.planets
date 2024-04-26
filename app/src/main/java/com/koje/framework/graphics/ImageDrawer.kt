package com.koje.framework.graphics

import android.graphics.Color
import android.opengl.GLES20.GL_ARRAY_BUFFER
import android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER
import android.opengl.GLES20.GL_FLOAT
import android.opengl.GLES20.GL_TEXTURE0
import android.opengl.GLES20.GL_TEXTURE_2D
import android.opengl.GLES20.GL_TRIANGLES
import android.opengl.GLES20.GL_UNSIGNED_SHORT
import android.opengl.GLES20.glActiveTexture
import android.opengl.GLES20.glBindBuffer
import android.opengl.GLES20.glBindTexture
import android.opengl.GLES20.glDisableVertexAttribArray
import android.opengl.GLES20.glDrawElements
import android.opengl.GLES20.glEnableVertexAttribArray
import android.opengl.GLES20.glGetAttribLocation
import android.opengl.GLES20.glGetUniformLocation
import android.opengl.GLES20.glUniform1f
import android.opengl.GLES20.glUniform1i
import android.opengl.GLES20.glUniform3f
import android.opengl.GLES20.glUniform4f
import android.opengl.GLES20.glUniformMatrix4fv
import android.opengl.GLES20.glUseProgram
import android.opengl.GLES20.glVertexAttribPointer
import com.koje.framework.App

class ImageDrawer(val drawable: Int) : Drawer() {

    var textureDataHandle = 0
    var positionLoc = 0
    var textureCoordsLoc = 0


    override fun init() {
        initProgramHandle()
        initBuffers()

        textureDataHandle = loadTexture(App.context, drawable)
    }


    /**
     * Ein Ausschnitt aus einer ImageMap wird gezeichnet. "count" und "index" steuern
     * die Zeichnung eines Bildausschnitts.
     */
    fun draw(component: ImageComponent) {
        glUseProgram(programHandle)
        glBindBuffer(GL_ARRAY_BUFFER, vboHandle)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboHandle)

        setupMatrix(component)
        setupTexture()
        setupImagePart(component)
        setupColor(component)
        setupOpacity(component)
        setupPosition()
        setupRotation(component)

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureDataHandle);
        glDrawElements(GL_TRIANGLES, iboCount, GL_UNSIGNED_SHORT, 0)
        glDisableVertexAttribArray(positionLoc)
        glDisableVertexAttribArray(textureCoordsLoc)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    private fun setupMatrix(component: ImageComponent) {
        val modelMatrixLoc = glGetUniformLocation(programHandle, "modelMatrix")
        glUniformMatrix4fv(modelMatrixLoc, 1, false, component.matrix, 0)
    }

    private fun setupPosition() {
        positionLoc = glGetAttribLocation(programHandle, "position")
        glEnableVertexAttribArray(positionLoc)
        glVertexAttribPointer(
            positionLoc, 3, GL_FLOAT, false,
            5 * bytesPerFloat, 0
        )
    }

    private fun setupOpacity(component: ImageComponent) {
        val opacityLoc = glGetUniformLocation(programHandle, "opacityArg")
        val opacity = component.realOpacity()

        glUniform1f(opacityLoc, opacity)
    }

    private fun setupTexture() {
        val textureLoc = glGetUniformLocation(programHandle, "texture")
        glUniform1i(textureLoc, 0)

        textureCoordsLoc = glGetAttribLocation(programHandle, "textureCoords")
        glEnableVertexAttribArray(textureCoordsLoc)
        glVertexAttribPointer(
            textureCoordsLoc, 2, GL_FLOAT, false,
            5 * bytesPerFloat, 3 * bytesPerFloat
        )
    }

    private fun setupImagePart(component: ImageComponent) {
        val selectorLoc = glGetUniformLocation(programHandle, "selector")
        val selectorC = Math.sqrt(component.count.toDouble()).toFloat()
        var selectorX = (1 / selectorC) * (component.index % selectorC.toInt())
        val selectorY = (1 / selectorC) * (component.index / selectorC.toInt())
        glUniform3f(selectorLoc, selectorX, selectorY, selectorC)
    }

    private fun setupRotation(component: ImageComponent) {
        val selectorLoc = glGetUniformLocation(programHandle, "rotation")
        glUniform3f(selectorLoc, component.radius, component.offset, 0f)
    }

    private fun setupColor(component: ImageComponent) {
        val colorLoc = glGetUniformLocation(programHandle, "color")
        val colorR = Color.red(component.color).toFloat() / 255
        val colorG = Color.green(component.color).toFloat() / 255
        val colorB = Color.blue(component.color).toFloat() / 255
        val colotA = Color.alpha(component.color).toFloat() / 255
        glUniform4f(colorLoc, colorR, colorG, colorB, colotA)
    }

    override fun createVertexShader(): String {
        return """
            uniform mat4 modelMatrix;
            attribute vec4 position;
            attribute vec2 textureCoords;
            varying vec2 textureCoordsVar;

            void main(){
              textureCoordsVar = textureCoords;
              gl_Position = modelMatrix * position;
            }
            """.trimIndent()
    }

    override fun createFragmentShader(): String {
        return """                
            precision mediump float;
            uniform sampler2D texture;
            uniform vec3 selector;
            uniform vec3 rotation;
            uniform vec4 color;
            varying vec2 textureCoordsVar;
            uniform float opacityArg;
            
            void main(){
              vec4 result;
               
              float x = selector[0] + textureCoordsVar[0] / selector[2];
              float y = selector[1] + textureCoordsVar[1] / selector[2];
              
              x += rotation[1]/360.0/selector[2];
              result = texture2D(texture, vec2(x,y));
              
              if(color.a > 0.5){
                 result.r = color[0];
                 result.g = color[1];
                 result.b = color[2];
              }

              float opacity = opacityArg;
              if(rotation[0] > 0.0){
                float distA = abs(textureCoordsVar[0] -0.5);
                float distB = abs(textureCoordsVar[1] -0.5);
                float dist = sqrt(distA * distA + distB * distB);
                if(dist > 0.45){
                   opacity = 0.0;
                }
              }

              result.r *= opacity;
              result.g *= opacity;
              result.b *= opacity;
              result.a *= opacity;

              gl_FragColor = result;
            }
            """.trimIndent()
    }

}