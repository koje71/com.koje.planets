package com.koje.framework.events

open class IntNotifier(content: Int) : Notifier<Int>(content) {

    fun increase() {
        set(content + 1)
    }

    fun increase(count: Int) {
        set(content + count)
    }

    fun decrease() {
        set(content - 1)
    }
}