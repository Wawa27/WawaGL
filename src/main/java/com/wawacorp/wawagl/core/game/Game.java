package com.wawacorp.wawagl.core.game;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.model.entity.EntityManager;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.view.View;
import com.wawacorp.wawagl.core.view.gui.nanovg.Font;
import com.wawacorp.wawagl.core.manager.AssetManager;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.objects.OutlinedObject;
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
public class Game {
    public final static Vector4f DEFAULT_BACKGROUND_COLOR = new Vector4f(1, 1, 1, 1);

    public static int width;
    public static int height;

    public static long window;

    public final static long start = System.currentTimeMillis();

    protected static Scene currentScene;

    protected static int debugMode = 0;

    /**
     * Time passed since the start of the class
     */
    private static long time;

    /**
     * Time elapsed since the last frame
     */
    private static long elapsedTime = System.currentTimeMillis();

    private static long maxFPS = 60;
    private static long timePerFrame = (long) ((1 / (double) maxFPS) * 1e6);

    protected final ArrayList<OutlinedObject> outlinedGameObjects;

    /**
     * Single color shader to draw object's outline
     * TODO: hashmap obect -> shader to have multiple outlines colors
     * TODO: shader animation for outlines (can be cool)
     */
    private Shader outlineShader;

    public static Game context;

    private Font font;

    protected static double framePerSecond;

    protected static long lastTime;

    public Game(int width, int height) {
        this(width, height, "WawaGL");
    }

    public Game(int width, int height, String title) {
        context = this;

        Game.width = width;
        Game.height = height;

        outlinedGameObjects = new ArrayList<>();
        init(); // All GL methods has to be called after this method which initialize the GL context
        setWindowTitle(title);
    }

    public void setScene(Scene scene) {
        Game.currentScene = scene;
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, 4); // the window will stay hidden after creation
        glfwWindowHint(GLFW_STENCIL_BITS, 4);
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

        setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
    }

    public final void start() {
        // TODO: remove this trick (loads the asset manager class)
        font = AssetManager.getFont("");
        glEnable(GL_DEBUG_OUTPUT);
        GLUtil.setupDebugMessageCallback();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

//        glEnable(GL_STENCIL_TEST);
//
//        // OUTLINE
//        outlineShader = Shader.loadShader("shaders/vert/world_vertex_shader_outline_smooth.glsl", "shaders/frag/outline.glsl");
//        glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
        //PICKING
        time = System.currentTimeMillis();
        loop();
        destroy();
    }

    //TODO: disable stencil test if there is not outlined objects
    private void loop() {
        System.out.println("Game loaded in : " + ((System.currentTimeMillis() - time) / 1000f) + " seconds");
        while (!glfwWindowShouldClose(window)) {
            // Input
            pollEvents();
            // Process
            framePerSecond = 1000f / (System.currentTimeMillis() - lastTime);
            lastTime = System.currentTimeMillis();
//            try {
//                if (elapsedTime + timePerFrame - System.currentTimeMillis() > 0) Thread.sleep((long) ((elapsedTime + timePerFrame - System.currentTimeMillis()) / 1e3));
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            elapsedTime = System.currentTimeMillis();
            EntityManager.loop();
//            Animation.runAll();

            Camera.ACTIVE.bind();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT); // P
            currentScene.onLoop(); // P
            Camera.ACTIVE.unbind();
            // Output
            apply(); // O
        }
    }

    private void destroy() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * Sets the background color
     * The default background is the DEFAULT_BACKGROUND_COLOR constant field
     *
     * @param color The background color (RGBA)
     */
    public void setBackgroundColor(Vector4f color) {
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

    public void removeOutlinedObject(View view) {
        outlinedGameObjects.remove(view);
    }

    public static void pollEvents() {
        glfwPollEvents();
    }

    public static void clear(int mask) {
        glClear(mask);
    }

    public static void apply() {
        glfwSwapBuffers(window);
    }

    /**
     * @return The time in {@link java.util.concurrent.TimeUnit.MILLISECONDS} nanoseconds since the game has started
     */
    public static long getTime() {
        return time;
    }

    /**
     * @return The time in {@link java.util.concurrent.TimeUnit.MILLISECONDS} nanoseconds since the last frame
     */
    public static long getElapsedTime() {
        return elapsedTime;
    }

    public static void enableDebugMode() {
        if ((debugMode=(debugMode + 1)%3) == 1) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else if (debugMode == 2) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }
    }

    public static void enableTransparency() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static double getFramePerSecond() {
        return framePerSecond;
    }
}
