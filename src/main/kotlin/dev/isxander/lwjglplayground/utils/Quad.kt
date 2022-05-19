package dev.isxander.lwjglplayground.utils

import java.io.Serializable

data class Quad<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
) : Serializable {
    /**
     * Returns string representation of the [Quad] including its [first], [second], [third] and [fourth] values.
     */
    override fun toString(): String = "($first, $second, $third, $fourth)"
}

fun <T> Quad<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)
