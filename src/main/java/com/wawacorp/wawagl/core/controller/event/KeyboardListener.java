package com.wawacorp.wawagl.core.controller.event;

import com.wawacorp.wawagl.core.game.Game;
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
                    case GLFW_REPEAT: {
                        for (KeyboardListener c : KEYBOARD_CONTROLLERS) c.onKeyRepeated(key);
                        break;
                    }
                    default: {
                        //
                    }
                }
            }
        });
    }

    public KeyboardListener() {
        KEYBOARD_CONTROLLERS.add(this);
    }

    /**
     * This method is called every time a key is PRESSED
     * @param key the key pressed
     */
    public abstract void onKeyPressed(int key);

    /**
     * This method is called every time a key is RELEASED
     * @param key the key pressed
     */
    public abstract void onKeyReleased(int key);


    public abstract void onKeyRepeated(int key);
}
