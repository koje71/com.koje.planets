package com.koje.planets.core

import com.koje.framework.graphics.Position

open class Board01 : Board("Alpha") {


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
                    position = Position(-0.3f, -0.2f),
                    productionType = Civilian,
                    size = 0.15f
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    position = Position(0.2f, -0.2f),
                    rotationSpeed = 1.3f,
                    productionType = Military,
                    size = 0.15f
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    position = Position(0f, 0.1f),
                    productionType = Military,
                    size = 0.2f
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    position = Position(-0.3f, 0.25f),
                    rotationSpeed = 1.1f,
                    size = 0.15f
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    position = Position(0.35f, 0.5f),
                    rotationSpeed = 1.1f,
                    size = 0.15f,
                    productionType = Civilian,
                    population = Population(20, 100),
                    team = teamRed
                )
            )
            add(
                Planet(
                    board = thisBoard,
                    position = Position(0.3f, -0.42f),
                    rotationSpeed = 1.1f,
                    size = 0.15f,
                    buildProgress = 0.75f,
                    productionType = Research,
                )
            )
        }

    }

    override fun createPaths(result: MutableList<Path>) {
        with(result) {
            add(Path(thisBoard, 0, 6))
            add(Path(thisBoard, 0, 1))
            add(Path(thisBoard, 0, 3))
            add(Path(thisBoard, 6, 2))
            add(Path(thisBoard, 2, 5))
            add(Path(thisBoard, 4, 3))
            add(Path(thisBoard, 3, 2))
        }
    }

    override fun setup() {
        teamRed.level = 4

        rules.add(object : Rule() {
            override fun check(): Boolean {
                return planets[5].population.militaryCount < 100
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