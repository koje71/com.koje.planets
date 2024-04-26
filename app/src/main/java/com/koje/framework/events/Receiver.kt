package com.koje.framework.events

import android.os.Handler
import android.os.Looper
import com.koje.framework.utils.Logger

class Receiver<T>(
    val notifier: com.koje.framework.events.Notifier<T>,
    val action: (arg: T) -> Unit
) {

    var content: T? = null

    fun register() {
        Logger.info(this, "register")
        synchronized(notifier) {
            if (!notifier.receivers.contains(this)) {
                notifier.receivers.add(this)
                receive(notifier.content)
            }
        }
    }

    fun release() {
        Logger.info(this, "release")
        synchronized(notifier) {
            if (notifier.receivers.contains(this)) {
                notifier.receivers.remove(this)
            }
        }
    }

    fun receive(value: T) {
        if (content == value) {
            return
        }

        Handler(Looper.getMainLooper()).post {
            action.invoke(value)
            content = value
        }
    }

}