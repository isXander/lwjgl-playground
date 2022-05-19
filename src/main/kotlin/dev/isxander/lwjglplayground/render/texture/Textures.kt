package dev.isxander.lwjglplayground.render.texture

import org.lwjgl.opengl.GL11

object Textures {
    val STEVE = Texture("steve.png")
    val BACKGROUND = Texture("background.png")

    fun unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
    }
}
