package com.koje.framework.view

import android.widget.FrameLayout
import androidx.core.view.children
import com.koje.framework.anim.FadeInAnimator
import com.koje.framework.anim.FadeOutAnimator


open class FrameLayoutBuilder(override val view: FrameLayout) :
    ViewGroupBuilder(view) {

    interface Editor : ViewEditor<FrameLayoutBuilder>

    var loaded: Editor? = null


    fun replaceWithFade(entry: Editor) {
        if (loaded == entry) {
            return
        }
        loaded = entry

        view.children.forEach {
            Receivers.release(it)
            FadeOutAnimator(it, view).start()
        }
        with(FrameLayout(view.context)) {
            entry.edit(FrameLayoutBuilder(this))
            Receivers.register(this)
            FadeInAnimator(this, view).start()
        }
    }

}