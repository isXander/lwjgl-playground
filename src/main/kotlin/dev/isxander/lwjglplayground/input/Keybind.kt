package dev.isxander.lwjglplayground.input

import org.lwjgl.glfw.GLFW

abstract class Keybind(
    val keycodes: List<Int>,
    val modifiers: Int = 0,
    val priority: Int = 0,
) {
    abstract fun onKey(action: Int): Boolean
}

class StateKeybind(
    keycodes: List<Int>,
    modifiers: Int = 0,
    priority: Int = 0,
    val allowDuplicates: Boolean = true
) : Keybind(keycodes, modifiers, priority) {
    var isDown = false
        private set

    override fun onKey(action: Int): Boolean {
        when (action) {
            GLFW.GLFW_PRESS -> isDown = true
            GLFW.GLFW_RELEASE -> isDown = false
        }
        return allowDuplicates
    }
}

class FunctionalKeybind(
    keycodes: List<Int>,
    modifiers: Int = 0,
    val action: Int = GLFW.GLFW_RELEASE,
    priority: Int = 0,
    val function: () -> Boolean
) : Keybind(keycodes, modifiers, priority) {
    override fun onKey(action: Int): Boolean {
        if (this.action == action)
            return function()
        return false
    }
}
