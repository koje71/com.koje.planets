package com.koje.planets.core

import com.koje.framework.App
import com.koje.framework.graphics.ComponentGroup
import com.koje.framework.graphics.Position
import com.koje.planets.R
import kotlin.math.abs
import kotlin.math.min
import kotlin.random.Random

class Planet(
    val board: Board,
    var position: Position = Position(),
    var productionType: String = Research,
    var rotationSpeed: Float = 1f,
    var buildProgress: Float = 0.0f,
    var size: Float = 0.2f,
    var team: Team = board.teamNone,
    var redStrategy: Population = Population(0, 0),
    var blueStrategy: Population = Population(0, 0),
    val population: Population = Population(0, 0)
) : ComponentGroup(Playground) {

    val thisPlanet = this
    var paths = mutableListOf<Path>()
    var buildType = Military
    var startProgress = Random.nextFloat()
    var attackCount = 0


    init {
        calculateRatio()
        plane = 2

        addProcedure {
            if (productionType == Research) {
                research()
            } else {
                buildPeople()
            }
            buildShip()
            move(position)

        }

        addTeamIndicator()
        addLandscape()
        addSelection()
        addTypeIndicator()

    }

    fun calculateRatio() {
        //nexus: 1.3775
        //if (Playground.ratio < 1.7f) {
        //    position.y = position.y * Playground.ratio / 1.7f
        //}
    }

    private fun addTypeIndicator() {
        addImageComponent {
            image = Playground.picmap
            count = 400
            color = App.getColor(R.color.planetType)

            addProcedure {
                index = when (productionType) {
                    Civilian -> 8
                    Military -> 7
                    else -> 9
                }
                scale(size * 0.6f)
            }
        }
    }

    private fun addTeamIndicator() {
        addImageComponent {
            image = Playground.picmap
            count = 400

            addProcedure {
                index = when (team) {
                    board.teamBlue -> 10
                    board.teamRed -> 11
                    else -> 12
                }
                scale(size * 1.25f)
            }
        }
    }

    private fun addLandscape() {
        addImageComponent {
            image = Playground.picmap
            count = 400

            radius = 0.5f
            offset = Random.nextFloat() * 720
            index = listOf(0).random(Playground.random)

            addProcedure {

                scale(size)
                rotate(15f)

                offset += rotationSpeed * 0.03f * surface.loopTime
                if (offset > 720) {
                    offset -= 720
                }
            }
        }
    }


    private fun addSelection() {
        var angle = 0f
        addImageComponent {
            image = Playground.picmap
            count = 400
            index = 6

            addProcedure {
                angle += 0.003f * surface.loopTime
                plane = if (board.selection == thisPlanet) 1 else -1
                scale(size * 1.3f)
                rotate(angle)
            }
        }
    }

    val researchSpeed = 0.000003f
    private fun research() {
        with(population) {
            if (civilianCount == 0) {
                return
            }

            var speed = researchSpeed
            speed += speed * min(civilianCount, 100) * 0.02f

            buildProgress += speed * surface.loopTime
            if (buildProgress >= 1f) {
                buildProgress -= 1f
                team.level++
            }
        }
    }

    val peopleBuildSpeed = 0.0001f

    private fun buildPeople() {
        with(population) {
            if (civilianCount == 0) {
                return
            }

            var speed = peopleBuildSpeed
            speed += speed * min(civilianCount, 100) * 0.1f

            buildProgress += speed * surface.loopTime
            if (buildProgress >= 1f) {
                buildProgress -= 1f
                buildPeopleFinish()
            }
        }
    }

    private fun buildPeopleFinish() {
        increase(productionType, 1)
        if (team.color == Blue) {
            Playground.reloadControls.set(Any())
        }
    }


    val shipBuildSpeed = 0.0015f

    fun increase(type: String, count: Int) {
        when (type) {
            Civilian -> {
                population.civilianCount += count
            }

            Military -> {
                population.militaryCount += count
            }
        }
    }

    fun buildShip() {
        startProgress += shipBuildSpeed * surface.loopTime
        if (startProgress >= 1f) {
            startProgress -= 1f
            buildShipFinish()
        }
    }

    fun buildShipFinish() {
        buildType = if (buildType == Civilian) Military else Civilian

        var localNeeds = getNeeds(team, buildType)
        if (localNeeds >= 0) {
            return
        }

        if (paths.size == 0) {
            return
        }

        var targetPath = paths[0]
        var targetNeeds = localNeeds
        paths.forEach {
            val target = it.getOther(this)
            if (target.hasNeeds(team, buildType)) {
                val needs = target.getNeeds(team, buildType)
                if (needs > targetNeeds) {
                    targetNeeds = needs
                    targetPath = it
                }
            }
        }

        val crew = min(getCrewSize(team.level), abs(targetNeeds - localNeeds))
        if (crew < 1) {
            return
        }


        targetPath.addComponent(
            Ship(
                path = targetPath,
                target = targetPath.getOther(this),
                team = team,
                type = buildType,
                crew = crew
            )
        )

        when (buildType) {
            Civilian -> population.civilianCount -= crew
            Military -> population.militaryCount -= crew
        }
    }

    fun getCrewSize(level: Int): Int {
        return when (level) {
            1 -> 1
            2 -> 2
            3 -> 5
            4 -> 10
            5 -> 20
            6 -> 50
            7 -> 100
            8 -> 500
            else -> 1000
        }
    }

    fun getNeeds(team: Team, type: String): Int {
        val strategy = getStrategy(team)

        if (type == Civilian) {
            return when (team == this.team) {
                true -> strategy.civilianCount - population.civilianCount
                else -> when (this.team != board.teamNone) {
                    true -> -1000
                    else -> strategy.civilianCount
                }
            }
        }

        if (type == Military) {
            return when (team == this.team) {
                true -> strategy.militaryCount - population.militaryCount
                else -> strategy.militaryCount
            }
        }

        return 0
    }

    fun hasNeeds(team: Team, type: String): Boolean {
        val strategy = getStrategy(team)

        return when (type) {
            Civilian -> strategy.civilianCount > 0
            Military -> strategy.militaryCount > 0
            else -> false
        }
    }

    fun getStrategy(team: Team): Population {
        return when (team) {
            board.teamBlue -> blueStrategy
            board.teamRed -> redStrategy
            else -> Population(0, 0)
        }
    }
}