package com.koje.planets.core

import com.koje.framework.graphics.ComponentGroup

class PowerBalanceBar : ComponentGroup(Playground) {

    var blueCiv = 0
    var blueMil = 0
    var redCiv = 0
    var redMil = 0

    init {

        addComponentGroup {
            for (i in 0..49) {
                addImageComponent {
                    image = Playground.picmap
                    count = 400

                    addProcedure {
                        val part = (blueCiv + blueMil + redCiv + redMil) / 50.0 * i
                        index = when {
                            part < blueCiv -> 50
                            part < blueCiv + blueMil -> 30
                            part < blueCiv + blueMil + redMil -> 31
                            part < blueCiv + blueMil + redMil + redCiv -> 51
                            else -> 0
                        }
                        move(i * 0.33f, 0f)
                    }
                }
            }

            addProcedure {
                blueCiv = 0
                blueMil = 0
                redCiv = 0
                redMil = 0

                with(Playground.board) {
                    planets.forEach {
                        when (it.team) {
                            teamBlue -> {
                                blueCiv += it.population.civilianCount
                                blueMil += it.population.militaryCount
                            }

                            teamRed -> {
                                redCiv += it.population.civilianCount
                                redMil += it.population.militaryCount
                            }
                        }
                    }
                }

                move(-0.365f, getPosY())
                scale(0.045f)
            }
        }
    }

    fun getPosY(): Float {
        return when(surface.ratio>1.4){
            true -> surface.ratio * -0.5f + 0.16f
            else -> -0.7f + 0.16f
        }
    }
}