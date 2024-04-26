package com.koje.framework.view

import android.content.res.ColorStateList
import com.google.android.material.slider.RangeSlider

class SliderBuilder(override val view: RangeSlider) :
    ViewBuilder(view) {

    fun setRange(min: Float, max: Float) {
        view.valueFrom = min
        view.valueTo = max
    }

    fun setOnChangeListener(action: (value: Float) -> Unit) {
        view.addOnChangeListener { v: RangeSlider, value: Float, b: Boolean ->
            action.invoke(value)
        }
    }

    fun setValue(current: Float) {
        //      view.setValue(current)
    }

    fun setStepSize(value: Float) {
        view.stepSize = value
    }

    fun setTrackColor(colorId: Int) {
        view.trackTintList = ColorStateList.valueOf(getColorFromID(colorId))
    }

    fun setThumbColor(colorId: Int) {
        view.thumbTintList = ColorStateList.valueOf(getColorFromID(colorId))
    }
}