package com.koje.framework.view

import android.os.Handler
import android.os.Looper
import android.widget.ScrollView

class ScrollViewBuilder(override val view: ScrollView) :
    ViewGroupBuilder(view) {

    interface Editor : ViewEditor<ScrollViewBuilder>

    fun addOnScrolledChangeListener(action: () -> Unit) {
        view.viewTreeObserver.addOnScrollChangedListener(action)
    }

    fun scroll(value: Int) {
        view.scrollTo(0, value)
    }

    fun setFillViewPort() {
        view.isFillViewport = true
    }


    /**
     * Scrollt mit einer Verzögerung an die angegebene Position. Das wird benötigt wenn
     * eine ScrollView direkt nach der Erstellung gescrollt werden soll und die internen
     * Views noch nicht vollständig berechnet wurden.
     */
    fun scrollDelayed(value: Int) {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            scroll(value)
        }, 100)
    }

}