package com.wawacorp.wawagl.demo.hordes;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.controller.event.CursorListener;
import com.wawacorp.wawagl.core.controller.event.KeyboardListener;
import com.wawacorp.wawagl.core.game.Game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

public class CameraController extends CursorListener {
    private final TPSCamera camera;

    public CameraController(TPSCamera camera) {
        this.camera = camera;
    }

    @Override
    public void onMouseMoved(double xpos, double ypos) {
        camera.setAngleY((float) (Game.height/2f - ypos));
        camera.setAngleZ((float) (Game.width/2f - xpos));
    }

    private class KeyBoardController extends KeyboardListener {
        @Override
        public void onKeyPressed(int key) {
            if (key == GLFW_KEY_UP) {
            }
        }

        @Override
        public void onKeyReleased(int key) {

        }

        @Override
        public void onKeyRepeated(int key) {

        }
    }
}
