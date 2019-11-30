package com.wawacorp.wawagl.demo.controller;

import com.wawacorp.wawagl.core.controller.event.KeyboardListener;
import com.wawacorp.wawagl.core.controller.event.ScrollListener;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.scene.Scene;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class ControllerDemo extends Scene {

    public ControllerDemo() {
        new KeyboardListener() {
            @Override
            public void onKeyPressed(int key) {
                System.out.println("key pressed : " + key);

                if (key == GLFW_KEY_ESCAPE) {
                    glfwSetWindowShouldClose(Game.window, true);
                }
            }

            @Override
            public void onKeyReleased(int key) {
                System.out.println("key released : " + key);
            }

            @Override
            public void onKeyRepeated(int key) {
                System.out.println("key repeated : " + key);
            }
        };

        new ScrollListener() {
            @Override
            public void onScroll(double xoffset, double yoffset) {
                System.out.println(xoffset + " : " + yoffset);
            }
        };
    }

    @Override
    public void onLoop() {

    }

    public static void main(String[] args) {
        Game game = new Game(960, 640, "Nemesis");
        game.setScene(new ControllerDemo());
        game.setBackgroundColor(new Vector4f(0, 0, 0, 1));
        game.start();
    }
}
