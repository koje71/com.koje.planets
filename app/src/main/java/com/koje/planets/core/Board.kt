package com.koje.planets.core

import android.view.MotionEvent
import com.koje.framework.graphics.ComponentGroup
import com.koje.framework.graphics.Position

abstract class Board(val name: String) : ComponentGroup(Playground) {

    val thisBoard = this
    val teamBlue = Team(color = Blue)
    val teamRed = Team(color = Red)
    val teamGreen = Team(color = Green)
    val teamNone = Team()
    val planets = createPlanets()
    val paths = createPaths()
    var selection = planets[0]
    var totalTime = 4000f
    var rules = mutableListOf<Rule>()

    init {
        setup()
        planets.forEach { addComponent(it) }
        paths.forEach { addComponent(it) }


        addProcedure {
            onDraw()

            val iterator = rules.iterator()
            while (iterator.hasNext()) {
                val rule = iterator.next()
                if (rule.check()) {
                    iterator.remove()
                    rule.action()
                }
            }
            totalTime += Playground.loopTime
        }
    }

    open fun setup() {

    }

    open fun onDraw() {

    }


    open fun createPlanets(): MutableList<Planet> {
        with(mutableListOf<Planet>()) {
            createPlanets(this)
            return this
        }
    }

    abstract fun createPlanets(result: MutableList<Planet>)

    open fun createPaths(): MutableList<Path> {
        with(mutableListOf<Path>()) {
            createPaths(this)
            return this
        }
    }

    abstract fun createPaths(result: MutableList<Path>)


    open fun onTouch(position: Position, event: MotionEvent) {

        planets.forEach {
            if (it.position.distanceTo(position) < it.size / 2f) {
                selection = it
                Playground.reloadControls.set(Any())
            }
        }
    }
}