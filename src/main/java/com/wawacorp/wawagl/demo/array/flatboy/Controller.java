package com.wawacorp.wawagl.demo.array.flatboy;

import com.wawacorp.wawagl.core.glfw.event.KeyboardListener;

import static org.lwjgl.glfw.GLFW.*;

public class Controller extends KeyboardListener {
    private final Character character;
    private boolean isRunning = false;

    public Controller(Character character) {
        this.character = character;
    }

    @Override
    public void onKeyPressed(int key) {
        switch (key) {
            case GLFW_KEY_SPACE: {
                if (isRunning) {
                    character.jump();
                }
                else {
                    isRunning = true;
                    character.run();
                }
                break;
            }
        }
    }

    @Override
    public void onKeyReleased(int key) {
        switch (key) {
            case GLFW_KEY_RIGHT: {
                character.idle();
                break;
            }
        }
    }
}
