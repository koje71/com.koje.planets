package com.koje.framework

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.koje.planets.R


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        init(this)

    }

    companion object {
        val idReceivers = R.id.receivers
        lateinit var context: Context

        fun init(value: Application) {
            context = value
        }

        fun debugging(): Boolean {
            return true
        }

        fun getString(id: Int): String {
            return context.resources.getString(id)
        }

        fun getBoolean(id: Int): Boolean {
            return context.resources.getBoolean(id)
        }

        fun getDrawable(id: Int): Drawable? {
            return AppCompatResources.getDrawable(context, id)
        }

        fun getColor(id: Int): Int {
            return ContextCompat.getColor(context, id)
        }

    }
}