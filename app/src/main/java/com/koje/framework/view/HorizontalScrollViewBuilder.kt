package com.koje.framework.view

import android.widget.HorizontalScrollView

class HorizontalScrollViewBuilder(override val view: HorizontalScrollView) :
    ViewGroupBuilder(view) {

    interface Editor : ViewEditor<HorizontalScrollViewBuilder>

    fun addOnScrolledChangeListener(action: () -> Unit) {
        view.viewTreeObserver.addOnScrollChangedListener(action)
    }

    fun setFillViewPort() {
        view.isFillViewport = true
    }


}