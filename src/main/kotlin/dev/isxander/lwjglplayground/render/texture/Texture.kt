package dev.isxander.lwjglplayground.render.texture

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL33.*
import javax.imageio.ImageIO

class Texture(path: String) {
    val id: Int
    val width: Int
    val height: Int

    init {
        val input = Texture::class.java.getResourceAsStream("/assets/$path")

        val bi = input.use(ImageIO::read)
        width = bi.width
        height = bi.height

        val rawPixels = bi.getRGB(0, 0, width, height, null, 0, width)

        val pixels = BufferUtils.createByteBuffer(width * height * 4)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pix = rawPixels[x * width + y]
                // RGBA
                pixels.put(((pix shr 16) and 0xFF).toByte())
                pixels.put(((pix shr 8) and 0xFF).toByte())
                pixels.put(((pix shr 0) and 0xFF).toByte())
                pixels.put(((pix shr 24) and 0xFF).toByte())
            }
        }

        pixels.flip()

        id = glGenTextures()

        glBindTexture(GL_TEXTURE_2D, id)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexImage2D(
            GL_TEXTURE_2D,
            0,
            GL_RGBA,
            width,
            height,
            0,
            GL_RGBA,
            GL_UNSIGNED_BYTE,
            pixels,
        )
    }

    fun bind() {
        glBindTexture(GL_TEXTURE_2D, id)
    }
}
