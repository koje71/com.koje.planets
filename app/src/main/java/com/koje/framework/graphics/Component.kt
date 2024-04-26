package com.koje.framework.graphics

import android.opengl.Matrix
import java.lang.Float.max
import java.lang.Float.min

open class Component(val surface: Surface) {

    var procedures = mutableListOf<Procedure>()
    var proceduresNew = mutableListOf<Procedure>()
    var opacity = 1f
    var plane = 1
    var matrix = FloatArray(16)
    var death = false
    var parent: ComponentGroup? = null

    open fun prepare() {
    }

    fun realOpacity(): Float {
        if (parent != null) {
            return min(opacity, parent!!.realOpacity())
        } else {
            return opacity
        }
    }

    open fun draw() {
        if (parent != null) matrix = parent!!.matrix.clone()

        if (proceduresNew.size > 0) {
            procedures.addAll(proceduresNew)
            proceduresNew.clear()
        }
        val iterator = procedures.iterator()
        while (iterator.hasNext()) {
            val procedure = iterator.next()
            if (procedure.progress >= 1f) {
                iterator.remove()
            } else {
                procedure.prepare()
                procedure.action.invoke(procedure)
            }
        }

    }

    fun move(target: Position) {
        Matrix.translateM(matrix, 0, target.x, target.y, 0f)
    }

    fun move(xPos: Float, yPos: Float) {
        Matrix.translateM(matrix, 0, xPos, yPos, 0f)
    }

    fun rotate(angle: Float) {
        Matrix.rotateM(matrix, 0, angle, 0f, 0f, -1f)
    }

    fun scale(size: Float) {
        scale(size, size)
    }

    fun scale(sizeX: Float, sizeY: Float) {
        Matrix.scaleM(matrix, 0, max(0f, sizeX), max(0f, sizeY), 0f)
    }

    fun addProcedure(startPos: Float = 0f, action: Procedure.() -> Unit) {
        val result = Procedure(action)
        result.progress = startPos
        proceduresNew.add(result)
    }


}