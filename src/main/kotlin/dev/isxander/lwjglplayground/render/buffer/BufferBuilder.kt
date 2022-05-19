package dev.isxander.lwjglplayground.render.buffer

import org.lwjgl.opengl.GL11

fun <T : VertexFormat> draw(mode: Int, format: () -> T, builder: BufferBuilder<T>.() -> Unit) {
    BufferBuilder(mode, format).apply(builder).draw()
}

class BufferBuilder<T : VertexFormat>(private val mode: Int, private val formatBuilder: () -> T) {
    private val vertices = mutableListOf<T>()

    val vertex: T
        get() {
            val format = formatBuilder()
            vertices += format
            return format
        }

    fun vertex(builder: T.() -> Unit) {
        vertices += formatBuilder().apply(builder)
    }

    fun draw() {
        GL11.glBegin(mode)
        vertices.forEach(VertexFormat::vertex)
        GL11.glEnd()
    }
}
