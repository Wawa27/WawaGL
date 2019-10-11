package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Material;
import org.joml.Vector2i;
import org.joml.Vector4f;

public class SnakeBody extends GameObject {
    public static Material SNAKE_HEAD_MATERIAL;
    private final Vector2i cell;
    private final Vector2i parent;

    static {
        SNAKE_HEAD_MATERIAL = new Material(new Vector4f(0,0,1, 1), new Vector4f(1, 1, 1, 1), new Vector4f(1, 1, 1, 1));
    }

    public SnakeBody(Vector2i parent, Vector2i position) {
        super(position.x, position.y, 0);
        this.parent = parent;
        this.cell = position;
        setMaterial(SNAKE_HEAD_MATERIAL);
    }

    public void move() {
        cell.set(parent);
        setTranslation(cell.x, cell.y, 0);
    }

    public Vector2i getCell() {
        return cell;
    }
}
