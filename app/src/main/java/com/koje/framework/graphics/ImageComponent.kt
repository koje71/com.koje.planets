package com.koje.framework.graphics

import com.koje.planets.R


open class ImageComponent(surface: Surface) : Component(surface) {

    var index = 0
    var count = 1
    var color = 0

    var radius = 0f
    var offset = 0f

    var image = ImageDrawer(R.drawable.picmap)

    override fun draw() {
        super.draw()
        if (plane >= 0) {
            image.draw(this)
            surface.imageCounter++
        }
    }
}