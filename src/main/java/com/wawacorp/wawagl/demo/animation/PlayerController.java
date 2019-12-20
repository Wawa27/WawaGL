package com.wawacorp.wawagl.demo.animation;

import com.wawacorp.wawagl.core.controller.event.KeyboardListener;
import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.animation.SkeletalAnimation;
import com.wawacorp.wawagl.core.model.entity.Entity;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerController extends KeyboardListener {
    private final Model player;

    public PlayerController(Model player) {
        this.player = player;
    }

    @Override
    public void onKeyPressed(int key) {
        if (key == GLFW_KEY_0) {
            player.startAnimation(0);
        }
        if (key == GLFW_KEY_1) {
            player.startAnimation(1);
        }
        if (key == GLFW_KEY_2) {
            player.startAnimation(2);
        }
        if (key == GLFW_KEY_3) {
            player.startAnimation(3);
        }
        if (key == GLFW_KEY_4) {
            player.startAnimation(4);
        }
        if (key == GLFW_KEY_DELETE) {
            player.stopAnimation();
        }
    }

    @Override
    public void onKeyReleased(int key) {

    }

    @Override
    public void onKeyRepeated(int key) {

    }
}
