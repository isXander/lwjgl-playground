package dev.isxander.lwjglplayground.gameplay

import dev.isxander.lwjglplayground.render.texture.Textures
import dev.isxander.lwjglplayground.utils.drawRect
import dev.isxander.lwjglplayground.utils.drawTexturedRect
import dev.isxander.lwjglplayground.utils.toFloat
import org.joml.Vector2d
import org.joml.Vector4f
import org.lwjgl.opengl.GL33.*
import java.awt.Color

object Player {
    var velocity = Vector2d()
    var position = Vector2d(150.0, 150.0)
    private var lastPosition = position

    private var goingLeft = false

    val HITBOX = Vector2d(300.0, 300.0)
    val DRAG = 0.5

    fun updatePosition(input: Vector4f) {
        lastPosition = Vector2d(position)

        // input = up, down, left, right
        velocity.y += input.x
        velocity.y -= input.y
        velocity.x -= input.z
        velocity.x += input.w

        if (input.z != 0f || input.w != 0f)
            goingLeft = input.z > input.w
    }

    fun update() {
        lastPosition = Vector2d(position)

        position.x += velocity.x
        position.y += velocity.y

        velocity.div(1.0 + DRAG)
    }

    fun render(deltaTime: Float) {
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        val positionTopLeft = getPositionLerped(deltaTime)
            .sub(Vector2d(HITBOX).div(2.0))
            .toFloat()

        drawTexturedRect(positionTopLeft.x, positionTopLeft.y, HITBOX.x.toFloat(), HITBOX.y.toFloat(), Color.WHITE, Textures.STEVE, u1 = if (goingLeft) 1f else 0f, u2 = if (goingLeft) 0f else 1f)

        glDisable(GL_BLEND)
    }

    fun getPositionLerped(deltaTime: Float) =
        Vector2d(lastPosition).lerp(position, deltaTime.toDouble())
}
