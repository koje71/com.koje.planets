package com.koje.framework.utils

import android.util.Log
import com.koje.framework.App

object Logger {

    fun debug(source: Any, message: String) {
        if (!App.debugging()) return
        Log.d(source.toString(), message)
    }

    fun error(source: Any, message: String) {
        if (!App.debugging()) return
        Log.e(source.toString(), message)
    }

    fun info(source: Any, message: String) {
        if (!App.debugging()) return
        Log.i(source.toString(), message)
    }

    fun abort(message: String) {
        throw RuntimeException(message)
    }

}