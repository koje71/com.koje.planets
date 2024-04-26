package com.koje.planets.core

import com.koje.framework.graphics.ComponentGroup
import com.koje.framework.graphics.Position
import com.koje.framework.utils.Logger
import kotlin.math.abs
import kotlin.math.atan

class Path(val board: Board, index1: Int, index2: Int) : ComponentGroup(Playground) {

    var thisPath = this
    val planet1 = board.planets[index1]
    val planet2 = board.planets[index2]

    var angle = calculateAngle()
    var length = planet1.position.distanceTo(planet2.position)

    fun getOther(source: Planet): Planet {
        return when (source == planet1) {
            true -> planet2
            else -> planet1
        }
    }

    init {
        plane = 1
        planet1.paths.add(this)
        planet2.paths.add(this)

        createDots()

        addProcedure {
            move(planet1.position)
            rotate(angle)
        }
    }


    fun createDots() {
        var step = 0
        Logger.info(this, "length = $length")
        var lengthCounter = length
        while (lengthCounter > 0) {
            addImageComponent {
                image = Playground.picmap
                count = 400
                index = 66
                val position = Position(step * 0.09f, 0f)

                addProcedure {
                    move(position)
                    scale(0.1f)
                }
            }
            lengthCounter -= 0.09f
            step++
        }
    }

    fun calculateAngle(): Float {
        val x1 = planet1.position.x
        val x2 = planet2.position.x
        val y1 = planet1.position.y
        val y2 = planet2.position.y

        val deltaX = abs(x1 - x2).toDouble()
        val deltaY = abs(y1 - y2).toDouble()
        val angleN = Math.toDegrees(atan(deltaY / deltaX)).toFloat()

        return when {
            y1 >= y2 -> when {
                x1 >= x2 -> 180 - angleN
                else -> angleN
            }

            else -> when {
                x1 >= x2 -> 180 + angleN
                else -> 360f - angleN
            }
        }
    }
}