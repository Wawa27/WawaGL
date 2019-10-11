package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Material;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Orange extends GameObject {
    public static Material SNAKE_HEAD_MATERIAL;
    private final Vector2i cell;

    static {
        SNAKE_HEAD_MATERIAL = new Material(new Vector4f(1,165 / 255.0f,0, 1), new Vector4f(1, 1, 1, 1), new Vector4f(1, 1, 1, 1));
    }

    public Orange(Vector2i position) {
        super(position.x, position.y, 0);
        cell = position;
        setMaterial(SNAKE_HEAD_MATERIAL);
    }

    public Vector2i getCell() {
        return cell;
    }
}
