package com.koje.framework.view

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.koje.framework.App
import com.koje.framework.events.Receiver

object Receivers {

    fun register(view: View) {
        val receivers = view.getTag(App.idReceivers)
        if (receivers != null) {
            (receivers as List<Receiver<*>>).forEach {
                it.register()
            }
        }
        if (view is ViewGroup) {
            view.children.forEach {
                register(it)
            }
        }
    }

    fun release(view: View) {
        val receivers = view.getTag(App.idReceivers)
        if (receivers != null) {
            (receivers as List<Receiver<*>>).forEach {
                it.release()
            }
        }
        if (view is ViewGroup) {
            view.children.forEach {
                release(it)
            }
        }
    }

}