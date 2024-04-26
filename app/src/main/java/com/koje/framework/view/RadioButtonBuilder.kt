package com.koje.framework.view

import android.content.res.ColorStateList
import android.util.TypedValue
import android.widget.RadioButton

class RadioButtonBuilder(override val view: RadioButton) :
    ViewBuilder(view) {

    fun setChecked(value: Boolean) {
        view.isChecked = value
    }

    fun setColor(resId: Int) {
        view.setButtonTintList(ColorStateList.valueOf(getColorFromID(resId)))
        view.setTextColor(getColorFromID(resId))
    }

    fun setText(value: String) {
        view.text = value
    }

    fun setFontId(id: Int) {
        view.typeface = view.context.resources.getFont(id)
    }

    fun setTextSizeSP(value: Float) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, value)
    }

    fun setOnCheckedChangeListener(action: (isChecked: Boolean) -> Unit) {
        view.setOnCheckedChangeListener { buttonView, isChecked ->
            action.invoke(isChecked)
        }
    }

}