package com.koje.framework.view

import android.view.Gravity
import android.widget.RelativeLayout

class RelativeLayoutBuilder(override val view: RelativeLayout) :
    ViewGroupBuilder(view) {

    interface Editor : ViewEditor<RelativeLayoutBuilder>


    fun setGravityCenterVertical() {
        view.gravity = Gravity.CENTER_VERTICAL
    }


    fun setGravityCenter() {
        view.gravity = Gravity.CENTER
    }

    fun setGravityBottomCenter() {
        view.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
    }

    fun setGravityBottomLeft() {
        view.gravity = Gravity.BOTTOM or Gravity.LEFT
    }

    fun setGravityBottomRight() {
        view.gravity = Gravity.BOTTOM or Gravity.RIGHT
    }


    fun setGravityTopCenter() {
        view.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
    }

    fun setGravityTopLeft() {
        view.gravity = Gravity.TOP or Gravity.LEFT
    }

    fun setGravityTopRight() {
        view.gravity = Gravity.TOP or Gravity.RIGHT
    }

    fun setGravityCenterLeft() {
        view.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
    }

    fun setGravityCenterRight() {
        view.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
    }
}