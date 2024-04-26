package com.koje.framework.utils

import android.content.Context
import android.content.SharedPreferences
import com.koje.framework.App

object Preferences {

    fun get(): SharedPreferences {
        return get("prefs")
    }

    fun get(name: String): SharedPreferences {
        return App.context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun getEditor(): SharedPreferences.Editor {
        return get().edit()
    }

    fun getString(name: String, default: String): String {
        val result = get().getString(name, default) + ""
        return result
    }

    fun setString(name: String, value: String) {
        Logger.debug(this, "$name setString:$value")
        getEditor().putString(name, value).commit()
    }

    fun getBoolean(name: String, default: Boolean): Boolean {
        return get().getBoolean(name, default)
    }

    fun setBoolean(name: String, value: Boolean) {
        getEditor().putBoolean(name, value).commit()
    }

    fun getInt(name: String, default: Int): Int {
        return get().getInt(name, default)
    }

    fun setInt(name: String, value: Int) {
        getEditor().putInt(name, value).commit()
    }

    fun getLong(name: String, default: Long): Long {
        return get().getLong(name, default)
    }

    fun setLong(name: String, value: Long) {
        getEditor().putLong(name, value).commit()
    }
}