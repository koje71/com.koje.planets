package com.koje.framework.utils

import com.koje.framework.events.BooleanNotifier

open class BooleanPreference(val key: String, content: Boolean) :
    BooleanNotifier(Preferences.getBoolean(key, content)) {

    override fun set(value: Boolean) {
        if (value == content) {
            return
        }
        super.set(value)
        Preferences.setBoolean(key, value)
    }

}