package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.glfw.event.KeyboardListener;

import static com.wawacorp.wawagl.demo.snake.SnakeGame.*;
import static org.lwjgl.glfw.GLFW.*;

public class Controller extends KeyboardListener {
    private final Snake snake;

    public Controller(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void onKeyPressed(int key) {
        switch (key) {
            case GLFW_KEY_UP: {
                snake.setDirection(DIRECTION_UP);
                break;
            }
            case GLFW_KEY_LEFT: {
                snake.setDirection(DIRECTION_LEFT);
                break;
            }
            case GLFW_KEY_RIGHT: {
                snake.setDirection(DIRECTION_RIGHT);
                break;
            }
            case GLFW_KEY_DOWN: {
                snake.setDirection(DIRECTION_DOWN);
                break;
            }
        }
    }

    @Override
    public void onKeyReleased(int key) {

    }
}
