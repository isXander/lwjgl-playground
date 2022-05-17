package dev.isxander.lwjglplayground

import dev.isxander.lwjglplayground.input.KeyInputHandler
import org.lwjgl.*
import org.lwjgl.glfw.*
import org.lwjgl.opengl.*

import org.lwjgl.glfw.Callbacks.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*

object LwjglPlayground {
    lateinit var window: Window
        private set

    var fullscreen = false
        private set

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
    }

    private fun startLoop() {
        // makes OpenGL bindings available for use
        GL.createCapabilities()

        // reset color
        glClearColor(1f, 0f, 0f, 0f)

        // run rendering loop until close
        while (!glfwWindowShouldClose(window.handle)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT) // clear fb

            glfwSwapBuffers(window.handle) // swap color buffers

            // poll window events for callbacks
            glfwPollEvents()
        }
    }
}