package dev.isxander.lwjglplayground.render.model

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15.*

class Model(vertices: FloatArray, texCoords: FloatArray) {
    val drawCount: Int
    val vertexId: Int
    val textureId: Int

    init {
        drawCount = vertices.size / 3



        vertexId = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vertexId)
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW)

        textureId = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, textureId)
        glBufferData(GL_ARRAY_BUFFER, createBuffer(texCoords), GL_STATIC_DRAW)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun render() {
        glEnableClientState(GL_VERTEX_ARRAY)
        glEnableClientState(GL_TEXTURE_COORD_ARRAY)

        glBindBuffer(GL_ARRAY_BUFFER, vertexId)
        glVertexPointer(3, GL_FLOAT, 0, 0)

        glBindBuffer(GL_ARRAY_BUFFER, textureId)
        glTexCoordPointer(2, GL_FLOAT, 0, 0)

        glDrawArrays(GL_TRIANGLES, 0, drawCount)

        glBindBuffer(GL_ARRAY_BUFFER, 0)

        glDisableClientState(GL_VERTEX_ARRAY)
        glDisableClientState(GL_TEXTURE_COORD_ARRAY)
    }

    private fun createBuffer(data: FloatArray) =
        BufferUtils.createFloatBuffer(data.size).apply {
            put(data)
            flip()
        }
}
