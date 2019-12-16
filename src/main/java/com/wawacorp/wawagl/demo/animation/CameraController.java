package com.wawacorp.wawagl.demo.animation;

import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.controller.event.CursorListener;
import com.wawacorp.wawagl.core.game.Game;

import static org.lwjgl.glfw.GLFW.*;

public class CameraController extends CursorListener {
    private final TPSCamera camera;

    public CameraController(TPSCamera camera) {
        this.camera = camera;
        new ScrollListener();
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
            camera.setDistance(camera.getDistance() + (float) yoffset * 8);
        }
    }
}
