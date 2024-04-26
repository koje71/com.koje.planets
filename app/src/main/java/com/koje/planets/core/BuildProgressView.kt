package com.koje.planets.core

import com.koje.framework.graphics.ComponentGroup

class BuildProgressView : ComponentGroup(Playground) {

    init {
        addImageComponent {
            image = Playground.picmap
            count = 400
            addProcedure {
                index = 46
            }
        }

        addImageComponent {
            image = Playground.picmap
            count = 400
            addProcedure {
                index = 47
                rotate(Playground.board.selection.buildProgress * 360f)
            }
        }

        addProcedure {
            move(0f, getPosY())
            // move(-0.42f, surface.ratio * 0.5f - 0.08f)
            scale(0.14f)
        }
    }

    fun getPosY(): Float {
        return when(surface.ratio>1.4){
            true -> surface.ratio * -0.5f + 0.07f
            else -> -0.7f + 0.07f
        }
    }
}