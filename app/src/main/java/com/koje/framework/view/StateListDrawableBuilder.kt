package com.koje.framework.view

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet

class StateListDrawableBuilder(val parent: ViewBuilder, val drawable: StateListDrawable) {

    fun addStatePressedGradient(action: GradientDrawableBuilder.() -> Unit) {
        val result = GradientDrawable()
        GradientDrawableBuilder(parent, result).action()
        drawable.addState(intArrayOf(android.R.attr.state_pressed), result)
    }

    fun addStateWildcardGradient(action: GradientDrawableBuilder.() -> Unit) {
        val result = GradientDrawable()
        GradientDrawableBuilder(parent, result).action()
        drawable.addState(StateSet.WILD_CARD, result)
    }

}