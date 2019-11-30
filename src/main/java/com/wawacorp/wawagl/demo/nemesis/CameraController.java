package com.wawacorp.wawagl.demo.nemesis;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.controller.event.CursorListener;
import com.wawacorp.wawagl.core.controller.event.KeyboardListener;
import com.wawacorp.wawagl.core.camera.FPSCamera;
import com.wawacorp.wawagl.core.game.Game;

import static org.lwjgl.glfw.GLFW.*;

public class CameraController {
    private final Camera camera;
    private double lastX;
    private double lastY;
    private double sensitivity = .01;

    public CameraController(Camera camera) {
        this.camera = camera;
        new KeyBoardController();
        new MouseController();
    }

    private final class KeyBoardController extends KeyboardListener {

        @Override
        public void onKeyPressed(int key) {
            switch (key) {
                case GLFW_KEY_W: {
                    camera.forward();
                    break;
                }
                case GLFW_KEY_S: {
                    camera.backward();
                    break;
                }
                case GLFW_KEY_A: {
                    camera.left();
                    break;
                }
                case GLFW_KEY_D: {
                    camera.right();
                    break;
                }
                case GLFW_KEY_SPACE: {
                    camera.up();
                    break;
                }
                case GLFW_KEY_F11: {
                    Game.enableDebugMode();
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
                    camera.forward();
                    break;
                }
                case GLFW_KEY_S: {
                    camera.backward();
                    break;
                }
                case GLFW_KEY_A: {
                    camera.left();
                    break;
                }
                case GLFW_KEY_D: {
                    camera.right();
                    break;
                }
                case GLFW_KEY_SPACE: {
                    camera.up();
                    break;
                }
                case GLFW_KEY_F11: {
                    Game.enableDebugMode();
                }
            }
        }
    }

    private final class MouseController extends CursorListener {

        @Override
        public void onMouseMoved(double xpos, double ypos) {
            if (lastX != 0) {
                camera.mouse((float) ((Game.width/2f - xpos) * sensitivity), (float) ((Game.height/2f - ypos) * sensitivity));
                glfwSetCursorPos(Game.window, Game.width/2f, Game.height/2f);
            }
            lastX = xpos;
            lastY = ypos;
        }
    }
}
