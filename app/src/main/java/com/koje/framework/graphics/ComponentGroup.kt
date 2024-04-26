package com.koje.framework.graphics

open class ComponentGroup(surface: Surface) : Component(surface) {

    val components = mutableListOf<Component>()
    val componentsNew = mutableListOf<Component>()

    override fun draw() {
        super.draw()

        if (componentsNew.size > 0) {
            components.addAll(componentsNew)
            componentsNew.clear()
        }

        var minPlane = 1
        var maxPlane = 0
        val iterator = components.iterator()
        while (iterator.hasNext()) {
            val component = iterator.next()
            if (component.death) {
                iterator.remove()
            } else {
                if (component.plane < minPlane) minPlane = component.plane
                if (component.plane > maxPlane) maxPlane = component.plane
            }
        }

        for (i in minPlane..maxPlane) {
            components.forEach {
                if (it.plane == i) {
                    it.prepare()
                    it.draw()
                }
            }
        }
    }

    fun <T : Component> addComponent(member: T, action: T.() -> Unit = {}) {
        member.parent = this
        action.invoke(member)
        componentsNew.add(member)
    }

    fun addImageComponent(action: ImageComponent.() -> Unit) {
        addComponent(ImageComponent(surface), action)
    }

    fun addComponentGroup(action: ComponentGroup.() -> Unit) {
        addComponent(ComponentGroup(surface), action)
    }

}