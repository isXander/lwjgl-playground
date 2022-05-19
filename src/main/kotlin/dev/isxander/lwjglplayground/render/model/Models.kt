package dev.isxander.lwjglplayground.render.model

object Models {
    val SQUARE = Model(
        floatArrayOf(
            -0.5f, 0.5f, 0f, // top left
            0.5f, 0.5f, 0f, // top right
            0.5f, -0.5f, 0f, // bottom right

            0.5f, -0.5f, 0f, // bottom right
            -0.5f, -0.5f, 0f, // bottom left
            -0.5f, 0.5f, 0f, // top left
        ),
        floatArrayOf(
            0f, 0f,
            1f, 0f,
            1f, 1f,

            1f, 1f,
            0f, 1f,
            0f, 0f,
        )
    )
}
