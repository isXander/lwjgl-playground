package dev.isxander.lwjglplayground.render.buffer

import org.joml.Vector2d
import org.joml.Vector3d
import org.joml.Vector4f
import org.lwjgl.opengl.GL33.*

interface VertexFormat {
    fun vertex()
}

object VertexFormats {
    class Position : VertexFormat {
        private lateinit var pos: Vector3d

        fun pos(vec3d: Vector3d): Position {
            pos = vec3d
            return this
        }

        fun pos(x: Double, y: Double, z: Double) =
            pos(Vector3d(x, y, z))

        fun pos(x: Float, y: Float, z: Float) =
            pos(x.toDouble(), y.toDouble(), z.toDouble())

        fun pos(x: Int, y: Int, z: Int) =
            pos(x.toDouble(), y.toDouble(), z.toDouble())

        override fun vertex() {
            glVertex3d(pos.x, pos.y, pos.z)
        }
    }

    class PositionColor : VertexFormat {
        private lateinit var pos: Vector3d
        private lateinit var color: Vector4f

        fun pos(vec3d: Vector3d): PositionColor {
            pos = vec3d
            return this
        }

        fun pos(x: Double, y: Double, z: Double) =
            pos(Vector3d(x, y, z))

        fun pos(x: Float, y: Float, z: Float) =
            pos(x.toDouble(), y.toDouble(), z.toDouble())

        fun pos(x: Int, y: Int, z: Int) =
            pos(x.toDouble(), y.toDouble(), z.toDouble())

        fun color(rgba: Vector4f): PositionColor {
            color = rgba
            return this
        }

        fun color(r: Float, g: Float, b: Float, a: Float = 1f) =
            color(Vector4f(r, g, b, a))

        fun color(r: Int, g: Int, b: Int, a: Int = 255) =
            color(r / 255f, g / 255f, b / 255f, a / 255f)

        override fun vertex() {
            glColor4f(color.x, color.y, color.z, color.w)
            glVertex3d(pos.x, pos.y, pos.z)
        }
    }

    class PositionTexture : VertexFormat {
        private lateinit var pos: Vector3d
        private lateinit var tex: Vector2d

        fun pos(vec3d: Vector3d): PositionTexture {
            pos = vec3d
            return this
        }

        fun pos(x: Double, y: Double, z: Double) =
            pos(Vector3d(x, y, z))

        fun pos(x: Float, y: Float, z: Float) =
            pos(x.toDouble(), y.toDouble(), z.toDouble())

        fun pos(x: Int, y: Int, z: Int) =
            pos(x.toDouble(), y.toDouble(), z.toDouble())

        fun tex(vec2d: Vector2d): PositionTexture {
            tex = vec2d
            return this
        }

        fun tex(u: Double, v: Double) =
            tex(Vector2d(u, v))

        fun tex(u: Float, v: Float) =
            tex(u.toDouble(), v.toDouble())

        fun tex(u: Int, v: Int) =
            tex(u.toDouble(), v.toDouble())

        override fun vertex() {
            glTexCoord2d(tex.x, tex.y)
            glVertex3d(pos.x, pos.y, pos.z)
        }
    }

    class PositionColorTexture : VertexFormat {
        private lateinit var pos: Vector3d
        private lateinit var color: Vector4f
        private lateinit var tex: Vector2d

        fun pos(vec3d: Vector3d): PositionColorTexture {
            pos = vec3d
            return this
        }

        fun pos(x: Double, y: Double, z: Double) =
            pos(Vector3d(x, y, z))

        fun pos(x: Float, y: Float, z: Float) =
            pos(x.toDouble(), y.toDouble(), z.toDouble())

        fun pos(x: Int, y: Int, z: Int) =
            pos(x.toDouble(), y.toDouble(), z.toDouble())

        fun color(rgba: Vector4f): PositionColorTexture {
            color = rgba
            return this
        }

        fun color(r: Float, g: Float, b: Float, a: Float = 1f) =
            color(Vector4f(r, g, b, a))

        fun color(r: Int, g: Int, b: Int, a: Int = 255) =
            color(r / 255f, g / 255f, b / 255f, a / 255f)

        fun tex(vec2d: Vector2d): PositionColorTexture {
            tex = vec2d
            return this
        }

        fun tex(u: Double, v: Double) =
            tex(Vector2d(u, v))

        fun tex(u: Float, v: Float) =
            tex(u.toDouble(), v.toDouble())

        fun tex(u: Int, v: Int) =
            tex(u.toDouble(), v.toDouble())

        override fun vertex() {
            glColor4f(color.x, color.y, color.z, color.w)
            glTexCoord2d(tex.x, tex.y)
            glVertex3d(pos.x, pos.y, pos.z)
        }
    }
}
