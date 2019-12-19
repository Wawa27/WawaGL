package com.wawacorp.wawagl.demo.wolf;

import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.controller.event.CursorListener;
import com.wawacorp.wawagl.core.controller.event.KeyboardListener;
import com.wawacorp.wawagl.core.controller.event.ScrollListener;
import com.wawacorp.wawagl.core.game.Game;

import java.awt.image.Kernel;
import java.security.Key;

import static org.lwjgl.glfw.GLFW.*;

public class CameraController extends CursorListener {
    private final TPSCamera camera;

    public CameraController(TPSCamera camera) {
        this.camera = camera;
        new ScrollListener();
        new KeyboardListener();
    }

    @Override
    public void onMouseMoved(double xpos, double ypos) {
        if (glfwGetMouseButton(Game.window, GLFW_MOUSE_BUTTON_MIDDLE) == GLFW_TRUE) {
            camera.setAngleY(camera.getAngleY() + (float) (Game.width/2f - xpos) / 30f);
            camera.setAngleZ(camera.getAngleZ() + -(float) (Game.height/2f - ypos) / 30f);
        }
    }

    private final class ScrollListener extends com.wawacorp.wawagl.core.controller.event.ScrollListener {
        @Override
        public void onScroll(double xoffset, double yoffset) {
            camera.setDistance(camera.getDistance() + (float) yoffset * 128);
        }
    }

    private final class KeyboardListener extends com.wawacorp.wawagl.core.controller.event.KeyboardListener {
        @Override
        public void onKeyPressed(int key) {
            if (key == GLFW_KEY_F8) {
                camera.setFloatAround(true);
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
