package com.wawacorp.wawagl.demo.nemesis;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.controller.event.CursorListener;
import com.wawacorp.wawagl.core.controller.event.KeyboardListener;
import com.wawacorp.wawagl.core.camera.FPSCamera;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.entity.Player;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerController {
    private final Player player;

    public PlayerController(Player player) {
        this.player = player;
        new KeyBoardController();
    }

    private final class KeyBoardController extends KeyboardListener {

        @Override
        public void onKeyPressed(int key) {
            switch (key) {
                case GLFW_KEY_W: {
                    player.forward();
                    break;
                }
                case GLFW_KEY_S: {
                    player.backward();
                    break;
                }
                case GLFW_KEY_A: {
                    player.left();
                    break;
                }
                case GLFW_KEY_D: {
                    player.right();
                    break;
                }
                case GLFW_KEY_SPACE: {
                    player.up();
                    break;
                }
                case GLFW_KEY_F11: {
                    Game.enableDebugMode();
                    break;
                }
            }
        }

        @Override
        public void onKeyReleased(int key) {

        }

        @Override
        public void onKeyRepeated(int key) {
            switch (key) {
                case GLFW_KEY_W: {
                    player.forward();
                    break;
                }
                case GLFW_KEY_S: {
                    player.backward();
                    break;
                }
                case GLFW_KEY_A: {
                    player.left();
                    break;
                }
                case GLFW_KEY_D: {
                    player.right();
                    break;
                }
                case GLFW_KEY_SPACE: {
                    break;
                }
                case GLFW_KEY_F11: {
                    Game.enableDebugMode();
                    break;
                }
            }
        }
    }
}
