package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Material;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

import java.util.Vector;

public class SnakeBody extends GameObject {
    public static Material SNAKE_HEAD_MATERIAL;
    private final Vector2f cell;
    private final Vector2f parent;

    static {
        SNAKE_HEAD_MATERIAL = new Material(new Vector4f(0,0,1, 1), new Vector4f(1, 1, 1, 1), new Vector4f(1, 1, 1, 1));
    }

    public SnakeBody(Vector2f parent, Vector2f position) {
        super(position.x, position.y, 0);
        this.parent = parent;
        this.cell = position;
        setMaterial(SNAKE_HEAD_MATERIAL);
    }

    public void move() {
        cell.add((parent.x - cell.x) / 15f, (parent.y - cell.y) /  15f);
        setTranslation(cell.x, cell.y, 0);
    }

    public Vector2f getCell() {
        return cell;
    }
}
