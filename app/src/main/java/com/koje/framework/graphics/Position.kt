package com.koje.framework.graphics

import kotlin.math.abs
import kotlin.math.sqrt

class Position(var x: Float = 0f, var y: Float = 0f) {

    fun copyFrom(other: Position) {
        x = other.x
        y = other.y
    }

    fun copy(): Position {
        return Position(x, y)
    }

    fun set(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun isNull(): Boolean {
        return x == 0f && y == 0f
    }

    fun add(other: Position): Position {
        return Position(x + other.x, y + other.y)
    }

    fun distanceTo(other: Position): Float {
        val dx = distanceBetween(x, other.x)
        val dy = distanceBetween(y, other.y)
        return sqrt(dx * dx + dy * dy)
    }

    fun distanceTo(otherX: Float, otherY: Float): Float {
        val dx = distanceBetween(x, otherX)
        val dy = distanceBetween(y, otherY)
        return sqrt(dx * dx + dy * dy)
    }

    fun distanceBetween(a: Float, b: Float): Float {
        return abs(a - b)
    }

    override fun toString(): String {
        return "{x:$x,y:$y}"
    }
}