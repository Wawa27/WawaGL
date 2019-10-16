package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Material;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

public class SnakeHead extends GameObject {
    public static Material SNAKE_HEAD_MATERIAL;
    private final Vector2f cell;

    static {
        SNAKE_HEAD_MATERIAL = new Material(new Vector4f(1,0,0, 1), new Vector4f(1, 1, 1, 1), new Vector4f(1, 1, 1, 1));
    }

    public SnakeHead(float x, float y) {
        super(x, y, 0);
        setMaterial(SNAKE_HEAD_MATERIAL);
        cell = new Vector2f(4, 4);
    }

    public void move(Vector2f direction) {
        cell.add(direction);
        setTranslation(cell.x, cell.y, 0);
    }

    public Vector2f getCell() {
        return cell;
    }
}
