package com.koje.framework.view

import android.graphics.drawable.GradientDrawable

class GradientDrawableBuilder(val parent: ViewBuilder, val drawable: GradientDrawable) {

    fun setColor(color: Int) {
        drawable.setColor(color)
    }

    fun setColorId(colorId: Int) {
        setColor(parent.getColorFromID(colorId))
    }

    fun setStrokeId(width: Int, colorId: Int) {
        setStroke(width, parent.getColorFromID(colorId))
    }

    fun setStroke(width: Int, colorId: Int) {
        drawable.setStroke(parent.getDpPx(width), parent.getColorFromID(colorId))
    }

    fun setCornerRadius(value: Int) {
        val px = parent.getDpPx(value).toFloat()
        drawable.cornerRadius = px
    }
}