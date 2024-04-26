package com.koje.framework.utils

class IntPreference(val key: String, content: Int) :
    com.koje.framework.events.IntNotifier(Preferences.getInt(key, content)) {

    override fun set(value: Int) {
        if (value == content) {
            return
        }
        super.set(value)
        Preferences.setInt(key, value)
    }

}