package com.koje.framework.view

import android.opengl.GLSurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.slider.RangeSlider

open class ViewGroupBuilder(override val view: ViewGroup) :
    ViewBuilder(view) {

    fun addCheckbox(action: CheckboxBuilder.() -> Unit) {
        val result = CheckBox(view.context)
        view.addView(result)
        CheckboxBuilder(result).action()
    }

    fun addRadioButton(action: RadioButtonBuilder.() -> Unit) {
        val result = RadioButton(view.context)
        view.addView(result)
        RadioButtonBuilder(result).action()
    }

    fun addSlider(action: SliderBuilder.() -> Unit) {
        val result = RangeSlider(view.context)
        view.addView(result)
        SliderBuilder(result).action()
    }

    fun addSeekBar(action: SeekBarBuilder.() -> Unit) {
        val result = SeekBar(view.context)
        view.addView(result)
        SeekBarBuilder(result).action()
    }

    fun addFrameLayout(action: FrameLayoutBuilder.() -> Unit) {
        val result = FrameLayout(view.context)
        view.addView(result)
        FrameLayoutBuilder(result).action()
    }

    fun addImageView(action: ImageViewBuilder.() -> Unit) {
        val result = ImageView(view.context)
        view.addView(result)
        ImageViewBuilder(result).action()
    }

    fun addHorizontalScrollView(action: HorizontalScrollViewBuilder.() -> Unit) {
        val result = HorizontalScrollView(view.context)
        view.addView(result)
        HorizontalScrollViewBuilder(result).action()
    }

    fun addLinearLayout(action: LinearLayoutBuilder.() -> Unit) {
        val result = LinearLayout(view.context)
        view.addView(result)
        LinearLayoutBuilder(result).action()
    }

    fun addRelativeLayout(action: RelativeLayoutBuilder.() -> Unit) {
        val result = RelativeLayout(view.context)
        view.addView(result)
        RelativeLayoutBuilder(result).action()
    }

    fun addTextView(action: TextViewBuilder.() -> Unit) {
        val result = TextView(view.context)
        view.addView(result)
        TextViewBuilder(result).action()
    }

    fun addScrollView(action: ScrollViewBuilder.() -> Unit) {
        val result = ScrollView(view.context)
        view.addView(result)
        ScrollViewBuilder(result).action()
    }

    fun addSurfaceView(action: SurfaceViewBuilder.() -> Unit) {
        val result = GLSurfaceView(view.context)
        view.addView(result)
        SurfaceViewBuilder(result).action()
    }

    fun addView(action: ViewBuilder.() -> Unit) {
        val result = View(view.context)
        view.addView(result)
        ViewBuilder(result).action()
    }

    fun removeAllViews() {
        view.removeAllViews()
    }
}