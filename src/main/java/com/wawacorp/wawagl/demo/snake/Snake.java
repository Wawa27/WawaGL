package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.opengl.shader.Shader;
import com.wawacorp.wawagl.core.opengl.view.View;
import org.joml.Vector2f;

import java.util.ArrayList;

public class Snake implements View {
    private final SnakeHead head;
    private final ArrayList<SnakeBody> body;

    private Vector2f currentDirection = SnakeGame.DIRECTION_UP;
    private Vector2f nextDirection = SnakeGame.DIRECTION_UP;

    private int moveCount = 0;

    public Snake() {
        head = new SnakeHead(4, 4);
        body = new ArrayList<>();
    }

    @Override
    public void draw() {
        head.draw();
        for(SnakeBody snakeBody : body) snakeBody.draw();
    }

    public void move() {
        if (moveCount == 15) {
            currentDirection = nextDirection;
            moveCount = 0;
        }

        for (int i = body.size() - 1; i >= 0; i--) {
            body.get(i).move();
        }
        head.move(currentDirection);

        moveCount++;
    }

    public void addBody() {
        if (body.size() == 0) {
            body.add(new SnakeBody(head.getCell(), head.getCell().sub(currentDirection.mul(15, new Vector2f()), new Vector2f())));
        } else {
            body.add(new SnakeBody(body.get(body.size() - 1).getCell(), body.get(body.size() - 1).getCell().sub(currentDirection.mul(15, new Vector2f()), new Vector2f())));
        }
    }

    public void setCurrentDirection(Vector2f currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void setNextDirection(Vector2f nextDirection) {
        this.nextDirection = nextDirection;
    }

    public Vector2f getCell() {
        return head.getCell();
    }

    @Override
    public void draw(Shader shader) {

    }
}
