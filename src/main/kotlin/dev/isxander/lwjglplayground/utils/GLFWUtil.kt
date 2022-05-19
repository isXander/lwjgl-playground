package dev.isxander.lwjglplayground.utils

import org.lwjgl.glfw.GLFW

val Boolean.glfw: Int
    get() = when (this) {
        true -> GLFW.GLFW_TRUE
        false -> GLFW.GLFW_FALSE
    }

const val NULL = 0L

fun Long.isGlfwNull() = this == NULL
