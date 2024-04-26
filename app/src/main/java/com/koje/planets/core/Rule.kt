package com.koje.planets.core

abstract class Rule {

    abstract fun check(): Boolean
    abstract fun action()

}