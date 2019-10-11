package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.opengl.shader.Shader;
import com.wawacorp.wawagl.core.opengl.view.Drawable;
import org.joml.Vector2i;

import java.util.ArrayList;

public class Snake implements Drawable {
    private final SnakeHead head;
    private final ArrayList<SnakeBody> body;
    private final Vector2i position;

    private Vector2i direction = SnakeGame.DIRECTION_UP;

    public Snake() {
        position = new Vector2i(4, 4);
        head = new SnakeHead(position.x, position.y);
        body = new ArrayList<>();
    }

    @Override
    public void draw() {
        head.draw();
        for(SnakeBody snakeBody : body) snakeBody.draw();
    }

    public void move() {
        for (int i = body.size() - 1; i >= 0; i--) {
            body.get(i).move();
        }
        position.add(direction);
        head.setTranslation(position.x, position.y, 0);
    }

    public void setDirection(Vector2i direction) {
        this.direction = direction;
    }

    public void addBody() {
        if (body.size() == 0) {
            body.add(new SnakeBody(position, position.sub(direction, new Vector2i())));
        } else {
            body.add(new SnakeBody(body.get(body.size() - 1).getCell(), position.sub(direction, new Vector2i())));
        }
    }

    public Vector2i getCell() {
        return position;
    }

    @Override
    public void draw(Shader shader) {

    }
}
