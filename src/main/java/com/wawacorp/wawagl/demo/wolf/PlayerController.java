package com.wawacorp.wawagl.demo.wolf;

import com.wawacorp.wawagl.core.controller.event.KeyboardListener;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.entity.Player;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F10;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class PlayerController extends KeyboardListener {
    private final Entity playerEntity;
    private final Model model;

    public PlayerController(Model model, Entity playerEntity) {
        this.model = model;
        this.playerEntity = playerEntity;
    }

    @Override
    public void onKeyPressed(int key) {
        if (key == GLFW_KEY_W) {
            playerEntity.translate(0, 0, 1);
            model.startAnimation();
        } else if (key == GLFW_KEY_F10) {
            Game.enableDebugMode();
        }
    }

    @Override
    public void onKeyReleased(int key) {

    }

    @Override
    public void onKeyRepeated(int key) {

    }
}
