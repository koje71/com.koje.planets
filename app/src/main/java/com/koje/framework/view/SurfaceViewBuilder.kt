package com.koje.framework.view

import android.opengl.GLSurfaceView
import com.koje.framework.graphics.Surface

class SurfaceViewBuilder(override val view: GLSurfaceView) :
    ViewBuilder(view) {

    init {
        view.setEGLContextClientVersion(2)
    }

    interface Editor : ViewEditor<SurfaceViewBuilder>

    fun setSurface(surface: Surface) {
        view.setRenderer(surface)
        view.setOnTouchListener(surface)
    }

}