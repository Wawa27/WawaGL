package com.wawacorp.wawagl.core.opengl.model.shape;

import org.joml.Vector3f;

public class Rectangle extends Shape {

    public Rectangle() {
        super("Rectangle", new float[]{
                - 1 / 2f, 1 / 2f, 0,                      // TOP LEFT
                - 1 / 2f, -1 / 2f, 0,                      // BOTTOM LEFT
                + 1 / 2f, -1 / 2f, 0,                     // BOTTOM RIGHT
                + 1 / 2f, 1 / 2f, 0                     // TOP RIGHT
        }, new float[]{
                -1 / 3f, 1 / 3f, 1 / 3f,
                -1 / 3f, -1 / 3f, 1 / 3f,
                1 / 3f, -1 / 3f, 1 / 3f,
                1 / 3f, 1 / 3f, 1 / 3f
        }, new float[]{
                0, 1,
                0, 0,
                1, 0,
                1, 1
        }, new int[]{
                0, 1, 3,
                3, 1, 2
        });
    }
}
