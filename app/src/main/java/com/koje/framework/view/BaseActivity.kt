package com.koje.framework.view

import android.app.Activity
import android.os.Bundle
import android.widget.FrameLayout

abstract class BaseActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(FrameLayoutBuilder(FrameLayout(this))) {
            setup(this)
            createLayout(this)
            setContentView(this.view)
        }
    }

    override fun onResume() {
        super.onResume()
        Receivers.register(findViewById(android.R.id.content))
    }

    override fun onPause() {
        super.onPause()
        Receivers.release(findViewById(android.R.id.content))
    }

    abstract fun createLayout(target: FrameLayoutBuilder)

    open fun setup(target: FrameLayoutBuilder) {

    }
}