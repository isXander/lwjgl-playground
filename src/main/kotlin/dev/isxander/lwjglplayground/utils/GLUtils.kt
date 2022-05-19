package dev.isxander.lwjglplayground.utils

import dev.isxander.lwjglplayground.LwjglPlayground
import dev.isxander.lwjglplayground.render.buffer.VertexFormats
import dev.isxander.lwjglplayground.render.buffer.draw
import dev.isxander.lwjglplayground.render.texture.Texture
import dev.isxander.lwjglplayground.render.texture.Textures
import org.lwjgl.opengl.GL33.*
import java.awt.Color

fun absoluteX(x: Float) =
    x / LwjglPlayground.window.width

fun absoluteY(y: Float) =
    y / LwjglPlayground.window.height

fun resetColor() {
    glColor4f(1f, 1f, 1f, 1f)
}

fun rgba(color: Color) =
    Quad(
        color.red / 255f,
        color.green / 255f,
        color.blue / 255f,
        color.alpha / 255f,
    )

fun drawRect(x: Float, y: Float, width: Float, height: Float, color: Color) {
    val (r, g, b, a) = rgba(color)

    draw(GL_QUADS, VertexFormats::PositionColor) {
        vertex {
            pos(x, y, 1f)
            color(r, g, b, a)
        }

        vertex {
            pos(x + width, y, 1f)
            color(r, g, b, a)
        }

        vertex {
            pos(x + width, y + height, 1f)
            color(r, g, b, a)
        }

        vertex {
            pos(x, y + height, 1f)
            color(r, g, b, a)
        }
    }

    resetColor()
}

fun drawTexturedRect(x: Float, y: Float, width: Float, height: Float, color: Color, texture: Texture, u1: Float = 0f, v1: Float = 0f, u2: Float = 1f, v2: Float = 1f) {
    val (r, g, b, a) = rgba(color)

    glEnable(GL_TEXTURE_2D)

    texture.bind()

    draw(GL_QUADS, VertexFormats::PositionColorTexture) {
        vertex {
            pos(x, y, 1f)
            color(r, g, b, a)
            tex(u1, v1)
        }

        vertex {
            pos(x + width, y, 1f)
            color(r, g, b, a)
            tex(u2, v1)
        }

        vertex {
            pos(x + width, y + height, 1f)
            color(r, g, b, a)
            tex(u2, v2)
        }

        vertex {
            pos(x, y + height, 1f)
            color(r, g, b, a)
            tex(u1, v2)
        }
    }

    Textures.unbind()

    resetColor()

    glDisable(GL_TEXTURE_2D)
}
