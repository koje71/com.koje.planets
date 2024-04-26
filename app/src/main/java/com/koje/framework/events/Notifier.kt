package com.koje.framework.events

import android.os.Handler
import android.os.Looper

open class Notifier<T>(var content: T) {

    val receivers = mutableListOf<Receiver<T>>()
    var useMainThread = true

    open fun set(value: T) {
        synchronized(this) {
            if (content == value) {
                return
            }
            content = value

            if (useMainThread) {
                Handler(Looper.getMainLooper()).post {
                    push()
                }
            } else {
                push()
            }
        }
    }

    fun push() {
        receivers.forEach {
            it.receive(content)
        }
    }

    fun get(): T {
        return content
    }

    fun contains(value: T): Boolean {
        return content == value
    }


}