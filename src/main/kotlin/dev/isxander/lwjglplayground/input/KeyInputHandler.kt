package dev.isxander.lwjglplayground.input

import dev.isxander.lwjglplayground.LwjglPlayground
import org.lwjgl.glfw.GLFW

object KeyInputHandler {
    private val _KEYBINDS = mutableListOf<Keybind>()
    val KEYBINDS: List<Keybind>
        get() = _KEYBINDS

    val CLOSE_KEY = register(FunctionalKeybind(listOf(GLFW.GLFW_KEY_ESCAPE)) {
        LwjglPlayground.stop()
        true
    })

    val TOGGLE_FULLSCREEN_KEY = register(FunctionalKeybind(listOf(GLFW.GLFW_KEY_F11)) {
        LwjglPlayground.window.fullscreen = !LwjglPlayground.window.fullscreen
        true
    })

    val RESIZABLE_KEY = register(FunctionalKeybind(listOf(GLFW.GLFW_KEY_R)) {
        println("resizable")
        LwjglPlayground.window.resizable = !LwjglPlayground.window.resizable
        true
    })

    val TITLE_KEY = register(FunctionalKeybind(listOf(GLFW.GLFW_KEY_T)) {
        LwjglPlayground.window.title = "e"
        true
    })

    val VSYNC_KEY = register(FunctionalKeybind(listOf(GLFW.GLFW_KEY_V)) {
        LwjglPlayground.window.vsync = !LwjglPlayground.window.vsync
        true
    })

    val UP_KEY = register(StateKeybind(listOf(GLFW.GLFW_KEY_W, GLFW.GLFW_KEY_UP)))
    val DOWN_KEY = register(StateKeybind(listOf(GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_DOWN)))
    val LEFT_KEY = register(StateKeybind(listOf(GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_LEFT)))
    val RIGHT_KEY = register(StateKeybind(listOf(GLFW.GLFW_KEY_D, GLFW.GLFW_KEY_RIGHT)))

    init {
        _KEYBINDS.sortByDescending { it.priority }
    }

    fun onKeyPress(keycode: Int, action: Int, mods: Int) {
        val availableKeys =
            KEYBINDS.filter { it.keycodes.contains(keycode) && it.modifiers == mods }
        availableKeys.all { !it.onKey(action) }
    }

    private fun <T : Keybind> register(keybind: T): T {
        _KEYBINDS += keybind
        return keybind
    }
}
