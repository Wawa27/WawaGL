package com.wawacorp.wawagl.core.model.shape;

public class Rectangle extends Shape {

    public Rectangle() {
        this(1, 1);
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
                0, 0,
                0, 1,
                1, 1,
                1, 0
        }, new int[]{
                0, 1, 3,
                3, 1, 2
        });
    }
}
