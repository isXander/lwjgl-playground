package dev.isxander.lwjglplayground.input

import org.lwjgl.glfw.GLFW

data class Keybind(val keycodes: List<Int>, val modifiers: Int = 0, val action: Int = GLFW.GLFW_RELEASE, val priority: Int = 0, val function: () -> Boolean)