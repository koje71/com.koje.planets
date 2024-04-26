package com.koje.framework.view

import android.graphics.Bitmap
import android.widget.ImageView


class ImageViewBuilder(override val view: ImageView) :
    ViewBuilder(view) {

    interface Editor : ViewEditor<ImageViewBuilder>

    fun setDrawableId(value: Int) {
        view.setImageResource(value)
    }

    fun setImageBitmap(bitmap: Bitmap) {
        view.setImageBitmap(bitmap)
    }

    fun setScaleTypeFitXY() {
        view.scaleType = ImageView.ScaleType.FIT_XY
    }

    fun setScaleTypeFitCenter() {
        view.scaleType = ImageView.ScaleType.FIT_CENTER
    }

    fun setScaleTypeCenterCrop() {
        view.scaleType = ImageView.ScaleType.CENTER_CROP
    }

}