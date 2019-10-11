package com.wawacorp.wawagl.core.glfw.event;

import com.wawacorp.wawagl.core.opengl.game.Game;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public abstract class KeyboardListener {
    private final static ArrayList<KeyboardListener> KEYBOARD_CONTROLLERS;

    static {
        KEYBOARD_CONTROLLERS = new ArrayList<>();
        glfwSetKeyCallback(Game.window, new GLFWKeyCallbackI() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                switch (action) {
                    case GLFW_PRESS: {
                        for (KeyboardListener c : KEYBOARD_CONTROLLERS) c.onKeyPressed(key);
                        break;
                    }
                    case GLFW_RELEASE: {
                        for (KeyboardListener c : KEYBOARD_CONTROLLERS) c.onKeyReleased(key);
                        break;
                    }
                }
            }
        });
    }

    public KeyboardListener() {
        KEYBOARD_CONTROLLERS.add(this);
    }

    /**
     * This method is called everytime a key is PRESSED
     * @param key the key pressed
     */
    public abstract void onKeyPressed(int key);

    /**
     * This method is called everytime a key is RELEASED
     * @param key the key pressed
     */
    public abstract void onKeyReleased(int key);
}
