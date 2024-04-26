package com.koje.framework.events

open class BooleanNotifier(content: Boolean) :
    Notifier<Boolean>(content) {

    fun switch() {
        set(!get())
    }
}