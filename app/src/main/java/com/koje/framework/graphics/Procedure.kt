package com.koje.framework.graphics

class Procedure(val action: Procedure.() -> Unit) {

    var progress = 0f
    var progressOld = 0f

    fun sin(angle: Float): Float {
        return Math.sin(Math.toRadians(angle.toDouble())).toFloat()
    }

    fun sin90(): Float {
        return Math.sin(Math.toRadians(90.0 * progress.toDouble())).toFloat()
    }

    fun cos90(): Float {
        return Math.cos(Math.toRadians(90.0 * progress.toDouble())).toFloat()
    }

    fun finish() {
        progress = 1f
    }

    fun prepare() {
        progressOld = progress
    }

    fun passed(border: Float): Boolean {
        return progressOld < border && progress >= border
    }
}