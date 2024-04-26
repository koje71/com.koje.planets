package com.koje.framework.view

import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.core.content.ContextCompat

class TextViewBuilder(override val view: TextView) :
    ViewBuilder(view) {

    interface Editor : ViewEditor<TextViewBuilder>

    val typefaceDafault = view.typeface

    fun setFontId(id: Int) {
        view.typeface = view.context.resources.getFont(id)
    }

    fun setIncludeFontPadding(value: Boolean) {
        view.includeFontPadding = value
    }

    fun setText(value: String) {
        view.setText(value)
    }

    fun setTextId(value: Int) {
        view.setText(view.context.resources.getText(value))
    }

    fun setTextColorID(value: Int) {
        view.setTextColor(ContextCompat.getColor(view.context, value))
    }

    fun setTextSizePX(value: Float) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
    }

    /**
     * Die Schriftgröße wird relativ rur Bildschirmbreite gesetzt. Bei einer Bildschirmbreite
     * von 1080 Pixel und value=0.01f ist die Schriftgröße 10,8 Pixel. Das Layout sieht dann
     * unabhängig von allen Bildschirmgrößen immer gleich aus.
     */
    fun setTextSizeRE(value: Float) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, value * getScreenWithPX())
    }

    fun setTextSizeSP(value: Float) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, value)
    }

    fun setTextSizeSP(value: Int) {
        setTextSizeSP(value.toFloat())
    }

    fun setGravityBottomCenter() {
        view.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
    }

    fun setGravityTopCenter() {
        view.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
    }

    fun setGravityCenter() {
        view.gravity = Gravity.CENTER
    }

    fun setGravityRight() {
        view.gravity = Gravity.RIGHT
    }

    fun setGravityLeft() {
        view.gravity = Gravity.LEFT
    }

}