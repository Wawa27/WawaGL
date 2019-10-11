package com.wawacorp.wawagl.core.opengl.game;

import com.wawacorp.wawagl.core.opengl.view.Drawable;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.Font;
import com.wawacorp.wawagl.core.opengl.manager.AssetManager;
import com.wawacorp.wawagl.core.opengl.shader.Shader;
import com.wawacorp.wawagl.core.opengl.view.objects.OutlinedObject;
import org.joml.Vector4f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * TODO: Singleton because we can only have a single Context
 */
public abstract class Game {
    public final static Vector4f DEFAULT_BACKGROUND_COLOR = new Vector4f(1, 0, 0, 1);

    public static int width;
    public static int height;

    public static long window;

    /**
     * Time passed since the start of the class
     */
    private static long time;

    protected final ArrayList<OutlinedObject> outlinedGameObjects;

    /**
     * Single color shader to draw object's outline
     * TODO: hashmap obect -> shader to have multiple outlines colors
     * TODO: shader animation for outlines (can be cool)
     */
    private Shader outlineShader;

    public static Game context;

    private Font font;

    public Game(int width, int height) {
        this(width, height, "WawaGL");
    }

    public Game(int width, int height, String title) {
        context = this;

        time = System.currentTimeMillis();

        Game.width = width;
        Game.height = height;

        outlinedGameObjects = new ArrayList<>();
        init(); // All GL methods has to be called after this method which initialize the GL context
        setWindowTitle(title);
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, 4); // the window will stay hidden after creation
        setWindowResizable(false);

        window = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(window);
        GL.createCapabilities();
    }

    public final void start() {
        // TODO: remove this trick (loads the asset manager class)
        font = AssetManager.getFont("");
        glEnable(GL_DEBUG_OUTPUT);
        GLUtil.setupDebugMessageCallback();
        setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_STENCIL_TEST);

        // OUTLINE
        outlineShader = Shader.loadShader("shaders/vert/world_vertex_shader_outline_smooth.glsl", "shaders/frag/outline.glsl");
        glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
        //PICKING
        loop();
        destroy();
    }

    //TODO: disable stencil test if there is not outlined objects
    private void loop() {
        System.out.println("Game loaded in : " + ((System.currentTimeMillis() - time) / 1000f) + " seconds");
        glActiveTexture(0);
        while (!glfwWindowShouldClose(window)) {
            onLoop();
        }
    }

    private void destroy() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * This method is called every frame
     */
    public abstract void onLoop();

    /**
     * Sets the background color
     * The default background is the DEFAULT_BACKGROUND_COLOR constant field
     *
     * @param color The background color (RGBA)
     */
    public final void setBackgroundColor(Vector4f color) {
        glClearColor(color.x, color.y, color.z, color.w);
    }

    /**
     * Set the application icon
     */
    public final void setIcon() {

    }

    /**
     * @param state
     */
    public void setVSYNC(boolean state) {

    }

    public void hideCursor() {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public void setWindowTitle(String title) {
        glfwSetWindowTitle(window, title);
    }

    public void setWindowResizable(boolean state) {
        glfwWindowHint(GLFW_RESIZABLE, state ? GLFW_TRUE : GLFW_FALSE);
    }

    /**
     * Get the cursor position
     *
     * @return A double array of length 2 with the x and y values
     */
    public static double[] getCursorPos() {
        double[] xPos = new double[1];
        double[] yPos = new double[1];
        glfwGetCursorPos(window, xPos, yPos);
        return new double[]{xPos[0], yPos[0]};
    }

    public static Game getContext() {
        return context;
    }

    public void addOutlinedObject(OutlinedObject gameObject) {
        outlinedGameObjects.add(gameObject);
    }

    public void removeOutlinedObject(Drawable drawable) {
        outlinedGameObjects.remove(drawable);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void clear(int mask) {
        glClear(mask);
    }

    public void apply() {
        glfwSwapBuffers(window);
    }

    /**
     * @return The time in {@link java.util.concurrent.TimeUnit.NANOSECONDS} nanoseconds since the game has started
     */
    public static long getTime() {
        return System.currentTimeMillis() - time;
    }
}
