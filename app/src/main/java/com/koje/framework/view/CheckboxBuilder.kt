package com.koje.framework.view

import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.View
import android.widget.CheckBox

class CheckboxBuilder(override val view: CheckBox) :
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

    fun setLabelGravityCenter() {
        view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        setHeightWrapContent()
    }
}