package com.koje.planets.core

import com.koje.framework.graphics.Position

open class Board02 : Board("Beta") {


    override fun createPlanets(result: MutableList<Planet>) {
        with(result) {
            add(
                Planet(
                    board = thisBoard,
                    population = Population(45, 0),
                    position = Position(-0.1f, -0.4f),
                    productionType = Civilian,
                    rotationSpeed = 0.5f,
                    size = 0.15f,
                    team = teamBlue,
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    //civilianCount = 45,
                    position = Position(+0.3f, -0.3f),
                    productionType = Military,
                    rotationSpeed = 0.5f,
                    size = 0.12f,
                    // team = teamBlue,
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    //civilianCount = 45,
                    position = Position(-0.3f, -0.25f),
                    productionType = Civilian,
                    rotationSpeed = 0.4f,
                    size = 0.13f,
                    // team = teamBlue,
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    //civilianCount = 45,
                    position = Position(+0.3f, +0.1f),
                    productionType = Research,
                    rotationSpeed = 0.2f,
                    size = 0.15f,
                    buildProgress = 0.8f,
                    // team = teamBlue,
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    //civilianCount = 45,
                    position = Position(-0.05f, -0.1f),
                    productionType = Military,
                    rotationSpeed = 0.2f,
                    size = 0.14f,
                    // team = teamBlue,
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    position = Position(-0.2f, +0.15f),
                    productionType = Civilian,
                    rotationSpeed = 0.1f,
                    size = 0.12f,
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    population = Population(30, 30),
                    redStrategy = Population(50, 10),
                    position = Position(+0.05f, +0.3f),
                    productionType = Military,
                    rotationSpeed = 0.2f,
                    size = 0.15f,
                    team = teamRed,
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    //civilianCount = 45,
                    position = Position(-0.3f, +0.35f),
                    productionType = Research,
                    rotationSpeed = 0.2f,
                    size = 0.14f,
                    buildProgress = 0.98f,
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    //civilianCount = 45,
                    position = Position(+0.3f, +0.38f),
                    productionType = Research,
                    rotationSpeed = 0.2f,
                    size = 0.13f,
                    buildProgress = 0.5f,
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    team = teamBlue,
                    population = Population(10, 0),
                    redStrategy = Population(45, 1),
                    position = Position(+0.03f, +0.6f),
                    productionType = Military,
                    rotationSpeed = 0.1f,
                    size = 0.14f,
                )
            )
        }

    }

    override fun createPaths(result: MutableList<Path>) {
        with(result) {
            add(Path(thisBoard, 0, 1))
            add(Path(thisBoard, 0, 2))
            add(Path(thisBoard, 1, 3))
            add(Path(thisBoard, 1, 4))
            add(Path(thisBoard, 4, 6))
            add(Path(thisBoard, 6, 5))
            add(Path(thisBoard, 6, 7))
            add(Path(thisBoard, 6, 8))
            add(Path(thisBoard, 6, 9))
        }
    }

    override fun setup() {
        teamRed.level = 2

        // bei einem Angriff auf die Hauptwelt wird alles angegriffen
        rules.add(object : Rule() {
            override fun check(): Boolean {
                return planets[6].attackCount > 0
            }

            override fun action() {
                planets.forEach {
                    if (it.team == teamBlue) {
                        it.redStrategy.civilianCount = 10
                        it.redStrategy.militaryCount = 10
                    } else {
                        it.redStrategy.civilianCount = 1
                        it.redStrategy.militaryCount = 1
                    }
                }
            }
        })

        // wenn planet 9 erobert ist, Militär wieder zurück
        rules.add(object : Rule() {
            override fun check(): Boolean {
                return planets[9].team == teamRed
            }

            override fun action() {
                planets[9].redStrategy.militaryCount = 0
                planets[6].redStrategy.militaryCount = 1
            }
        })

        // kurz nach dem Start werden noch einige Soldaten gebaut und dann
        // wird der Nachbarplanet besiedelt
        rules.add(object : Rule() {
            override fun check(): Boolean {
                return planets[6].population.militaryCount > 35
            }

            override fun action() {
                planets[5].redStrategy.civilianCount = 50
                planets[6].redStrategy.civilianCount = 10
            }
        })


        // die Millitärproduktion wird erhöht
        rules.add(object : Rule() {
            override fun check(): Boolean {
                return planets[5].population.civilianCount > 50
            }

            override fun action() {
                planets[6].redStrategy.civilianCount = 50
                planets[9].redStrategy.civilianCount = 50
            }
        })

        // die Forschung wird erhöht
        rules.add(object : Rule() {
            override fun check(): Boolean {
                return planets[9].population.civilianCount > 50
            }

            override fun action() {
                planets[7].redStrategy.civilianCount = 50
            }
        })

        rules.add(object : Rule() {
            override fun check(): Boolean {
                return planets[7].population.civilianCount > 50
            }

            override fun action() {
                planets[8].redStrategy.civilianCount = 50
            }
        })

        // bei Tech Level 5 wird entgültig angegriffen
        rules.add(object : Rule() {
            override fun check(): Boolean {
                return teamRed.level == 5
            }

            override fun action() {
                planets.forEach {
                    it.redStrategy.civilianCount = 10
                    it.redStrategy.militaryCount = 10
                }
            }
        })
    }

}