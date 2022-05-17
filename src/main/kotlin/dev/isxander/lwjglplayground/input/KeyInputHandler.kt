package dev.isxander.lwjglplayground.input

import dev.isxander.lwjglplayground.LwjglPlayground
import org.lwjgl.glfw.GLFW

object KeyInputHandler {
    private val _KEYBINDS = mutableListOf<Keybind>()
    val KEYBINDS: List<Keybind>
        get() = _KEYBINDS

    val CLOSE_KEY = register(Keybind(listOf(GLFW.GLFW_KEY_ESCAPE)) {
        GLFW.glfwSetWindowShouldClose(LwjglPlayground.window.handle, true)
        true
    })

    val TOGGLE_FULLSCREEN_KEY = register(Keybind(listOf(GLFW.GLFW_KEY_F11)) {
        LwjglPlayground.window.fullscreen = !LwjglPlayground.window.fullscreen
        true
    })

    val RESIZABLE_KEY = register(Keybind(listOf(GLFW.GLFW_KEY_R)) {
        println("resizable")
        LwjglPlayground.window.resizable = !LwjglPlayground.window.resizable
        true
    })

    val TITLE_KEY = register(Keybind(listOf(GLFW.GLFW_KEY_T)) {
        LwjglPlayground.window.title = "e"
        true
    })

    val VISIBLE_KEY = register(Keybind(listOf(GLFW.GLFW_KEY_V)) {
        LwjglPlayground.window.visible = !LwjglPlayground.window.visible
        true
    })

    fun setupCallbacks() {
        _KEYBINDS.sortByDescending { it.priority }

        GLFW.glfwSetKeyCallback(LwjglPlayground.window.handle) { window, key, scancode, action, modifiers ->
            val availableKeys = KEYBINDS.filter { it.keycodes.contains(key) && it.action == action && it.modifiers == modifiers }
            val iter = availableKeys.iterator()
            while (iter.hasNext() && !iter.next().function()) {}
        }
    }

    private fun register(keybind: Keybind): Keybind {
        _KEYBINDS += keybind
        return keybind
    }
}