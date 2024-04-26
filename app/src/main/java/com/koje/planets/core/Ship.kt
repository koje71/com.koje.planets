package com.koje.planets.core

import com.koje.framework.graphics.ComponentGroup
import kotlin.math.max

class Ship(
    val path: Path,
    val target: Planet,
    var crew: Int,
    var type: String,
    var team: Team
) : ComponentGroup(Playground) {

    var position = 0f
    var posY = 0f

    private var forward = path.planet2 == target
    private val rotation = if (forward) 90f else 270f

    init {
        plane = 1

        addImageComponent {
            image = Playground.picmap
            count = 400
            index = getShipImage()
        }

        if (type == Military) {
            addImageComponent {
                image = Playground.picmap
                count = 400
                index = getShipImage() + 1
            }
        }

        addProcedure {
            move()
        }

        onStart()
    }

    fun move() {
        val oldPosition = position
        position += 0.0001f * surface.loopTime
        if (position > path.length) {
            onArrive()
        } else {
            checkEnemyContact(oldPosition)
        }

        val posX = if (forward) position else path.length - position
        move(posX, posY)
        var scale = 0.1f


        // increase on starting
        var source = if (target == path.planet2) path.planet1 else path.planet2
        if (position < source.size) {
            scale = max(0.07f, 0.1f * (position) / source.size)
        }

        // decrease on landing
        if (path.length - position < target.size) {
            scale = max(0.07f, 0.1f * (path.length - position) / target.size)
        }

        // increase big ships
        scale *= getExtraScale()


        scale(scale)
        rotate(rotation)
    }

    private fun onStart() {

    }

    private fun onArrive() {
        death = true


        when {
            target.team == target.board.teamNone -> onArriveNeutralPlanet()
            target.team == team -> onArriveFriendsPlanet()
            else -> onArriveEnemiesPlanet()
        }

        Playground.reloadControls.set(Any())
    }

    private fun onArriveNeutralPlanet() {
        target.team = team
        onArriveFriendsPlanet()
    }

    private fun onArriveFriendsPlanet() {
        when (type) {
            Civilian -> target.population.civilianCount += crew
            Military -> target.population.militaryCount += crew
        }
    }

    private fun onArriveEnemiesPlanet() {
        target.attackCount++
        if (type == Civilian) {
            target.increase(type, crew)
            return
        }

        with(target.population) {
            while (militaryCount > 0) {
                civilianCount = max(civilianCount - 10, 0)
                if (crew == 0) {
                    return
                }
                when (Playground.random.nextBoolean()) {
                    true -> militaryCount-- // ein gegner wurde besiegt
                    else -> crew-- // ein mitglied der crew wurde besiegt
                }
            }
        }

        target.team = team
        target.increase(Military, crew)
    }

    private fun getShipImage(): Int {
        return when {
            crew <= 1 -> 126
            crew <= 2 -> 146
            crew <= 5 -> 166
            crew <= 10 -> 186
            crew <= 20 -> 206
            else -> 206
        }
    }

    private fun checkEnemyContact(oldPosition: Float) {
        path.components.forEach {
            if (it is Ship) {
                checkEnemyContact(oldPosition, it)
            }
        }
    }

    private fun checkEnemyContact(oldPosition: Float, other: Ship) {
        if (other.team == team) {
            return
        }

        if (other.crew * crew == 0) {
            return
        }

        if (oldPosition >= path.length - other.position) {
            return
        }

        if (position <= path.length - other.position) {
            return
        }

        if (type == Military && other.type == Civilian) {
            other.explode()
            return
        }

        if (type == Civilian && other.type == Military) {
            explode()
            return
        }

        while (other.crew > 0) {
            if (crew == 0) {
                explode()
                return
            }
            when (Playground.random.nextBoolean()) {
                true -> other.crew-- // ein gegner wurde besiegt
                else -> crew-- // ein mitglied der crew wurde besiegt
            }
        }

        other.explode()
    }

    fun explode() {
        addImageComponent {
            addImageComponent {
                image = Playground.picmap
                count = 400
                index = 108

                addProcedure {
                    scale(3.2f)
                }
            }
        }

        val directionY = when (Playground.random.nextBoolean()) {
            true -> 1f
            else -> -1f
        }

        posY = 0f

        addProcedure {
            progress += 0.0005f * surface.loopTime
            posY += 0.00005f * directionY * Playground.loopTime
            if (progress > 1.0f) {
                death = true
            }

            scale(1f - progress)
        }
    }

    private fun getExtraScale(): Float {
        return 0.65f * when (getShipImage()) {
            186 -> 1.2f
            206 -> 1.5f
            else -> 1.0f
        }
    }


}