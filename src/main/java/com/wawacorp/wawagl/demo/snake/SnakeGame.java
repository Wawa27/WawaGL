package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.opengl.game.Game;
import com.wawacorp.wawagl.core.opengl.camera.Camera;
import com.wawacorp.wawagl.core.opengl.camera.projection.Orthographic;
import com.wawacorp.wawagl.core.opengl.hud.Hud;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.component.ImmutableText;
import org.joml.Vector2i;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

public class SnakeGame extends Game {
    public final static int CELL_WIDTH = 32;
    public final static int CELL_HEIGHT = 32;

    public final static Vector2i DIRECTION_UP = new Vector2i(0, 1);
    public final static Vector2i DIRECTION_DOWN = new Vector2i(0, -1);
    public final static Vector2i DIRECTION_RIGHT = new Vector2i(1, 0);
    public final static Vector2i DIRECTION_LEFT = new Vector2i(-1, 0);

    private final GameObject[] gameObjects;
    private final Snake snake;
    private Orange orange;

    private final Hud hud;
    private final ImmutableText scoreText;
    private int score = 0;

    public SnakeGame() {
        super(480, 480, "Snake");
        Camera.setActive(new Camera(new Orthographic(0, CELL_WIDTH, 0, CELL_HEIGHT, 0.01f, -100f)));
        gameObjects = new GameObject[CELL_WIDTH * CELL_HEIGHT];
        for (int y = 0; y < CELL_HEIGHT; y++) {
            for (int x = 0; x < CELL_WIDTH; x++) {
                gameObjects[y * CELL_WIDTH + x] = new Ground(x + 0.5f, y + 0.5f);
            }
        }
        snake = new Snake();
        hud = new Hud();
        scoreText = new ImmutableText(0, 16, "");
        hud.addComponent(scoreText);
        updateScore();

        orange = new Orange(getRandomCell());

        glEnable(GL_STENCIL_TEST);

        glStencilFunc(GL_ALWAYS, 1, 0xFF);
        glStencilMask(0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);

        new Controller(snake);
    }

    private Vector2i getRandomCell() {
        return new Vector2i((int) (Math.random() * CELL_WIDTH), (int) (Math.random() * CELL_HEIGHT));
    }

    @Override
    public void onLoop() {
        Camera.ACTIVE.bind();
        clear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

        glDisable(GL_DEPTH_TEST);
        for (GameObject gameObject : gameObjects) {
            if (gameObject != null) gameObject.draw();
        }
        orange.draw();
        snake.draw();
        glEnable(GL_DEPTH_TEST);

        hud.draw();

        snake.move();
        if (snake.getCell().equals(orange.getCell())) {
            score++;
            updateScore();
            snake.addBody();
            orange = new Orange(getRandomCell());
        }
        Camera.ACTIVE.unbind();

        apply();
        sleep(1);
    }

    public void updateScore() {
        String text = "Score: " + score;
        scoreText.setText(text);
    }

    public void sleep(int seconds) {
        long time = System.currentTimeMillis();
        while ((System.currentTimeMillis() - time) < seconds * 100) {
            pollEvents();
            if (glfwWindowShouldClose(window)) break;
        }
    }

    public static void main(String[] args) {
        new SnakeGame().start();
    }
}
