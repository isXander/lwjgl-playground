package dev.isxander.lwjglplayground.render

import org.lwjgl.glfw.GLFW


/**
 * The timer class is used for calculating delta time and also FPS and UPS
 * calculation.
 *
 * @author Heiko Brumme
 */
object Timer {
    /**
     * Getter for the last loop time.
     *
     * @return System time of the last loop
     */
    /**
     * System time since last loop.
     */
    var lastLoopTime = 0.0
        private set

    /**
     * Used for FPS and UPS calculation.
     */
    private var timeCount = 0f

    /**
     * Frames per second.
     */
    private var fps = 0

    /**
     * Counter for the FPS calculation.
     */
    private var fpsCount = 0

    /**
     * Updates per second.
     */
    private var ups = 0

    /**
     * Counter for the UPS calculation.
     */
    private var upsCount = 0

    /**
     * Initializes the timer.
     */
    fun init() {
        lastLoopTime = time
    }

    /**
     * Returns the time elapsed since `glfwInit()` in seconds.
     *
     * @return System time in seconds
     */
    val time: Double
        get() = GLFW.glfwGetTime()

    /**
     * Returns the time that have passed since the last loop.
     *
     * @return Delta time in milliseconds
     */
    val delta: Float
        get() {
            val time = time
            val delta = (time - lastLoopTime).toFloat()
            lastLoopTime = time
            timeCount += delta
            return delta
        }

    /**
     * Updates the FPS counter.
     */
    fun updateFPS() {
        fpsCount++
    }

    /**
     * Updates the UPS counter.
     */
    fun updateUPS() {
        upsCount++
    }

    /**
     * Updates FPS and UPS if a whole second has passed.
     */
    fun update() {
        if (timeCount > 1f) {
            fps = fpsCount
            fpsCount = 0
            ups = upsCount
            upsCount = 0
            timeCount -= 1f
        }
    }

    /**
     * Getter for the FPS.
     *
     * @return Frames per second
     */
    val FPS: Int
        get() = if (fps > 0) fps else fpsCount

    /**
     * Getter for the UPS.
     *
     * @return Updates per second
     */
    val UPS: Int
        get() = if (ups > 0) ups else upsCount
}
