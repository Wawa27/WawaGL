package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.opengl.game.Game;
import com.wawacorp.wawagl.core.opengl.camera.Camera;
import com.wawacorp.wawagl.core.opengl.camera.projection.Orthographic;
import com.wawacorp.wawagl.core.opengl.hud.Hud;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.component.Text;
import org.joml.Vector2f;
import org.joml.Vector2i;

import static java.lang.Thread.sleep;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

public class SnakeGame extends Game {
    public final static int CELL_WIDTH = 32;
    public final static int CELL_HEIGHT = 32;

    public final static Vector2f DIRECTION_UP = new Vector2f(0, 1f/15);
    public final static Vector2f DIRECTION_DOWN = new Vector2f(0, -1f/15);
    public final static Vector2f DIRECTION_RIGHT = new Vector2f(1f/15, 0);
    public final static Vector2f DIRECTION_LEFT = new Vector2f(-1f/15, 0);

    private final GameObject[] gameObjects;
    private final Snake snake;
    private Orange orange;

    private final Hud hud;
    private final Text scoreText;
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
        scoreText = new Text(0, 16, "");
        hud.addComponent(scoreText);
        updateScore();

        orange = new Orange(getRandomCell());

        glEnable(GL_STENCIL_TEST);

        glStencilFunc(GL_ALWAYS, 1, 0xFF);
        glStencilMask(0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);

        new Controller(snake);
    }

    private Vector2f getRandomCell() {
        return new Vector2f((int) (Math.random() * CELL_WIDTH), (int) (Math.random() * CELL_HEIGHT));
    }

    @Override
    public void onLoop() {
        long start = System.currentTimeMillis();

        // Process Input
        pollEvents();

        // Update game
        snake.move();

        if ((Math.abs(snake.getCell().x - orange.getCell().x) < .001f) && (Math.abs(snake.getCell().y - orange.getCell().y) < .001f)) {
            score++;
            updateScore();
            snake.addBody();
            orange = new Orange(getRandomCell());
        }

        // Render
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

        Camera.ACTIVE.unbind();

        apply();

        // Sleep
        try {
            sleep((long) ((start + 16.666 - System.currentTimeMillis()) / 1000f));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateScore() {
        String text = "Score: " + score;
        scoreText.setText(text);
    }

    public static void main(String[] args) {
        new SnakeGame().start();
    }
}
