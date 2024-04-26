package com.koje.animals.core

import com.koje.framework.graphics.ComponentGroup
import com.koje.planets.core.Playground

class Background : ComponentGroup(Playground) {

    var angle = 0f

    init {
        // herkunft: https://unsplash.com/de/fotos/NkQD-RHhbvY
        addImageComponent {
            image = Playground.picmap
            index = 3
            count = 4

            addProcedure {
                move(0f, 0f)
                if (Playground.ratio >= 1.4f) {
                    scale(Playground.ratio * 2)
                } else {
                    scale(1.4f / Playground.ratio * 2)
                }

                angle += surface.loopTime * 0.001f
                if (angle > 360) {
                    angle -= 360
                }
                rotate(angle)
            }
        }

        addImageComponent {
            image = Playground.picmap
            index = 7
            count = 16

            addProcedure {
                move(0f, 0f)
                if (Playground.ratio >= 1.4f) {
                    scale(Playground.ratio)
                } else {
                    scale(1.4f / Playground.ratio)
                }
            }
        }
    }
}