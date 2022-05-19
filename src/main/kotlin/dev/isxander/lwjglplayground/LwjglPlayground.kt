package dev.isxander.lwjglplayground

import dev.isxander.lwjglplayground.gameplay.Player
import dev.isxander.lwjglplayground.input.KeyInputHandler
import dev.isxander.lwjglplayground.render.Timer
import dev.isxander.lwjglplayground.render.Window
import dev.isxander.lwjglplayground.render.buffer.VertexFormats
import dev.isxander.lwjglplayground.render.buffer.draw
import dev.isxander.lwjglplayground.render.texture.Textures
import org.joml.Vector4f
import org.lwjgl.*
import org.lwjgl.glfw.*
import org.lwjgl.opengl.*

import org.lwjgl.glfw.Callbacks.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL33.*

object LwjglPlayground {
    val TARGET_UPS = 20
    val TARGET_FPS = -1

    lateinit var window: Window
        private set

    private var stopping = false

    fun run() {
        println("LWJGL ${Version.getVersion()}")

        init()
        startLoop()

        // loop finished, close
        glfwFreeCallbacks(window.handle)
        glfwDestroyWindow(window.handle)

        // terminate glfw
        glfwTerminate()
        glfwSetErrorCallback(null)!!.free()
    }

    private fun init() {
        // print error messages to System.err
        GLFWErrorCallback.createPrint(System.err).set()

        // init GLFW
        if (!glfwInit())
            error("Unable to initialize GLFW")

        window = Window()
        window.setupWindow()

        Timer.init()
    }

    private fun startLoop() {
        // makes OpenGL bindings available for use
        GL.createCapabilities()

        Textures // calls clinit which registers all the textures

        // reset color
        glClearColor(1f, 0f, 0f, 0f)

        setupOrtho()

        var delta: Float
        var accumulator = 0f
        val interval = 1f / TARGET_UPS
        var alpha: Float

        // run rendering loop until close
        while (!glfwWindowShouldClose(window.handle)) {
            if (stopping) {
                glfwSetWindowShouldClose(window.handle, true)
                // nothing else to stop
                continue
            }

            delta = Timer.delta
            accumulator += delta

            // poll window events for callbacks
            glfwPollEvents()

            while (accumulator >= interval) {
                update()
                Timer.updateUPS()
                accumulator -= interval
            }

            alpha = accumulator / interval

            render(alpha)
            Timer.updateFPS()

            Timer.update()

            if (!window.vsync && TARGET_FPS != -1) {
                // sync
                val lastLoopTime = Timer.lastLoopTime
                var now = Timer.time
                val targetTime = 1f / TARGET_FPS

                while (now - lastLoopTime < targetTime) {
                    Thread.yield()
                    Thread.sleep(1)
                    now = Timer.time
                }
            }
        }
    }

    private fun render(alpha: Float) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT) // clear fb
        glPushMatrix()

        glEnable(GL_TEXTURE_2D)
        Textures.BACKGROUND.bind()
        draw(GL_QUADS, VertexFormats::PositionColorTexture) {
            val size = 64f
            vertex {
                pos(0, window.height, 0)
                tex(0f, window.height / size)
                color(64, 64, 64)
            }

            vertex {
                pos(window.width, window.height, 0)
                tex(window.width / size, window.height / size)
                color(64, 64, 64)
            }

            vertex {
                pos(window.width, 0, 0)
                tex(window.width / size, 0f)
                color(64, 64, 64)
            }

            vertex {
                pos(0, 0, 0)
                tex(0f, 0f)
                color(64, 64, 64)
            }
        }
        Textures.unbind()
        glDisable(GL_TEXTURE_2D)

        Player.render(alpha)

        glPopMatrix()
        glfwSwapBuffers(window.handle) // swap color buffers
    }

    private fun update() {
        Player.updatePosition(Vector4f(
            if (KeyInputHandler.DOWN_KEY.isDown) 10f else 0f,
            if (KeyInputHandler.UP_KEY.isDown) 10f else 0f,
            if (KeyInputHandler.LEFT_KEY.isDown) 10f else 0f,
            if (KeyInputHandler.RIGHT_KEY.isDown) 10f else 0f,
        ))

        Player.update()
    }

    fun stop() {
        // let the loop know that it needs to start shutting everything down now
        stopping = true
    }

    fun setupOrtho() {
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()
        glOrtho(0.0, window.width.toDouble(), window.height.toDouble(), 0.0, 1.0, -1.0)
        glMatrixMode(GL_MODELVIEW)
    }
}
