package com.koje.framework.utils

import com.koje.framework.events.StringNotifier

class StringPreference(val key: String, content: String) :
    StringNotifier(Preferences.getString(key, content)) {

    override fun set(value: String) {
        if (value == content) {
            return
        }
        super.set(value)
        Preferences.setString(key, value)
    }

}