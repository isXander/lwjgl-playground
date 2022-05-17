package dev.isxander.lwjglplayground.utils

import org.lwjgl.glfw.GLFW
import org.lwjgl.system.MemoryUtil

val Boolean.glfw: Int
    get() = when (this) {
        true -> GLFW.GLFW_TRUE
        false -> GLFW.GLFW_FALSE
    }