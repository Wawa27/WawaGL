package com.wawacorp.wawagl.demo.animation;

import com.wawacorp.wawagl.core.controller.event.KeyboardListener;
import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.animation.SkeletalAnimation;
import com.wawacorp.wawagl.core.model.entity.Entity;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class PlayerController extends KeyboardListener {
    private final Model player;

    public PlayerController(Model player) {
        this.player = player;
    }

    @Override
    public void onKeyPressed(int key) {
        if (key == GLFW_KEY_W) {
            player.startAnimation();
        }
    }

    @Override
    public void onKeyReleased(int key) {

    }

    @Override
    public void onKeyRepeated(int key) {

    }
}
