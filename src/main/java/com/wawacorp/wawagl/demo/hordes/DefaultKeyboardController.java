package com.wawacorp.wawagl.demo.hordes;

import com.wawacorp.wawagl.core.controller.event.KeyboardListener;
import com.wawacorp.wawagl.core.game.Game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F12;

public class DefaultKeyboardController extends KeyboardListener {

    @Override
    public void onKeyPressed(int key) {
        if (key == GLFW_KEY_F12) Game.enableDebugMode();
    }

    @Override
    public void onKeyReleased(int key) {

    }

    @Override
    public void onKeyRepeated(int key) {

    }
}
