package com.koje.planets.core

import android.view.MotionEvent
import com.koje.framework.graphics.ComponentGroup
import com.koje.framework.graphics.Position
import kotlin.math.min

class BuildControlBar(val type: String) : ComponentGroup(Playground) {

    init {
        addBackground()
        addBuildTypeSymbol()
        addScale()
        addTargetIndicator()

        addProcedure {
            move(getPosX(), getPosY())
            scale(0.05f)
        }
    }

    fun getPosX(): Float {
        return when (type) {
            Civilian -> -0.47f
            Military -> 0.47f
            else -> 0f
        }

    }

    fun addBackground() {
        addImageComponent {
            image = Playground.picmap
            count = 64
            index = 28
            plane = -1

            val xPos = when (type) {
                Civilian -> -0.36f
                Military -> 0.36f
                else -> 0f
            }
            addProcedure {
                scale(20f)
                move(xPos, 0.375f)
            }
        }
    }

    fun getPosY(): Float {
        return when(surface.ratio>1.4){
            true -> surface.ratio * -0.5f + 0.16f
            else -> -0.7f + 0.16f
        }
    }

    open fun onTouch(position: Position, event: MotionEvent): Boolean {

        if (type == Civilian) {
            if (position.x < -0.5 || position.x > -0.4f) {
                return false
            }
        }

        if (type == Military) {
            if (position.x < 0.4 || position.x > 0.5f) {
                return false
            }
        }

        if (position.y < getPosY() || position.y > getPosY() + 0.75f) {
            return false
        }

        val result = ((position.y - getPosY()) * 67).toInt()
        val strategy = Playground.board.selection.blueStrategy
        when (type) {
            Civilian -> strategy.civilianCount = result
            Military -> strategy.militaryCount = result
        }

        Playground.reloadControls.set(Any())
        return true
    }

    fun addBuildTypeSymbol() {
        addImageComponent {
            image = Playground.picmap
            count = 400
            index = when (type) {
                Civilian -> 8
                Military -> 7
                else -> 0
            }

            addProcedure {
                move(0f, 57 * 0.3f)
                scale(1.5f)
            }
        }
    }

    fun addTargetIndicator() {

        addComponentGroup {
            addImageComponent {
                image = Playground.picmap
                count = 400
                index = 90
                plane = -1

                addProcedure {
                    val strategy = Playground.board.selection.blueStrategy
                    if (type == Civilian) {
                        move(0.0f, min(50, strategy.civilianCount) * 0.3f)
                        // rotate(90f)
                    }
                    if (type == Military) {
                        move(-0.0f, min(50, strategy.militaryCount) * 0.3f)
                        //  rotate(270f)
                    }
                    scale(2.0f)
                }
            }

            addImageComponent {
                image = Playground.picmap
                count = 400
                index = 106

                addProcedure {
                    val strategy = Playground.board.selection.blueStrategy
                    if (type == Civilian) {
                        move(0.7f, min(50, strategy.civilianCount) * 0.3f)
                        rotate(90f)
                    }
                    if (type == Military) {
                        move(-0.7f, min(50, strategy.militaryCount) * 0.3f)
                        rotate(270f)
                    }
                    scale(1.5f)
                }
            }
        }

    }

    fun addScale() {
        for (i in 0..50) {
            addImageComponent {
                image = Playground.picmap
                count = 400

                addProcedure {
                    val population = Playground.board.selection.population

                    index = when (Playground.board.selection.team) {
                        Playground.board.teamBlue -> 87
                        Playground.board.teamRed -> 88
                        else -> 0
                    }

                    val count = when (type) {
                        Civilian -> population.civilianCount
                        Military -> population.militaryCount
                        else -> 0
                    }

                    if (count <= i) {
                        index = 86
                    }

                    move(0f, i * 0.3f)
                }
            }
        }

        addImageComponent {
            image = Playground.picmap
            count = 400
            index = 106

            addProcedure {
                val population = Playground.board.selection.population

                val count = when (type) {
                    Civilian -> population.civilianCount
                    Military -> population.militaryCount
                    else -> 0
                }

                plane = when (count > 51) {
                    true -> 1
                    else -> -1
                }

                move(0f, 52 * 0.3f)
                rotate(180f)
            }
        }
    }
}