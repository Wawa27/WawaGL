package com.wawacorp.wawagl.core.opengl.model.shape;

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

    public Rectangle(float w, float h) {
        super("Rectangle", new float[]{
                - w / 2f, h / 2f, 0,                      // TOP LEFT
                - w / 2f, -h / 2f, 0,                      // BOTTOM LEFT
                + w / 2f, -h / 2f, 0,                     // BOTTOM RIGHT
                + w / 2f, h / 2f, 0                     // TOP RIGHT
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
