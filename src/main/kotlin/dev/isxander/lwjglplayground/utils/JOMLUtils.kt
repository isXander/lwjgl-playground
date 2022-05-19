package dev.isxander.lwjglplayground.utils

import org.joml.Vector2d
import org.joml.Vector2f
import org.joml.Vector2i

fun Vector2d.toFloat() =
    Vector2f(this.x.toFloat(), this.y.toFloat())
