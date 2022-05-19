package dev.isxander.lwjglplayground.render

import dev.isxander.lwjglplayground.LwjglPlayground
import dev.isxander.lwjglplayground.gameplay.Player
import dev.isxander.lwjglplayground.input.KeyInputHandler
import dev.isxander.lwjglplayground.utils.*
import org.lwjgl.glfw.GLFW
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import kotlin.properties.Delegates

class Window {
    var handle by Delegates.notNull<Long>()
        private set

    private var setup = false

    var vsync = true
        set(value) {
            GLFW.glfwSwapInterval(if (value) 1 else 0)

            field = value
        }

    var title = "LWJGL Playground"
        set(value) {
            GLFW.glfwSetWindowTitle(handle, value)

            field = value
        }

    var visible = false
        set(value) {
            when (value) {
                true -> GLFW.glfwShowWindow(handle)
                false -> GLFW.glfwHideWindow(handle)
            }

            field = value
        }

    var resizable = true
        set(value) {
            if (fullscreen && value)
                error("cannot make window resizable in fullscreen")

            GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, value.glfw)
            field = value
        }

    var fullscreen = false
        set(value) {
            visible = false

            field = value
            resizable = !value
            GLFW.glfwSetWindowAttrib(handle, GLFW.GLFW_DECORATED, (!value).glfw)

            if (value) {
                val videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())!!
                val width = videoMode.width()
                val height = videoMode.height()

                GLFW.glfwSetWindowSize(handle, width, height)
                GLFW.glfwSetWindowPos(handle, 0, 0)
            } else {
                GLFW.glfwSetWindowSize(handle, 1280, 720)
                centerWindow()
            }

            visible = true
        }

    var width = 1280
        private set

    var height = 720
        private set

    fun setupWindow() {
        if (setup)
            error("Cannot setup window twice!")

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, false.glfw)
        resizable = true

        handle = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)

        if (handle.isGlfwNull())
            error("Failed to create GLFW window!")

        centerWindow()

        GLFW.glfwMakeContextCurrent(handle)
        vsync = true

        GLFW.glfwSetWindowSizeCallback(handle, this::onWindowResize)
        GLFW.glfwSetKeyCallback(handle, this::onKeyPress)

        visible = true
        setup = true
    }

    private fun onKeyPress(windowHandle: Long, keycode: Int, scancode: Int, action: Int, mods: Int) {
        if (windowHandle != handle)
            return

        KeyInputHandler.onKeyPress(keycode, action, mods)
    }

    private fun onWindowResize(windowHandle: Long, width: Int, height: Int) {
        if (windowHandle != handle)
            return

        this.width = width
        this.height = height

        LwjglPlayground.setupOrtho()
    }

    fun centerWindow() {
        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            // put window size into pWidth & pHeight
            GLFW.glfwGetWindowSize(handle, pWidth, pHeight)

            val videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())!!

            // center window
            GLFW.glfwSetWindowPos(
                handle,
                (videoMode.width() - pWidth.get(0)) / 2,
                (videoMode.height() - pHeight.get(0)) / 2,
            )
        }
    }
}
