package com.wawacorp.wawagl.core.opengl.model.shape;

public class Cube extends Shape {

    public Cube() {
        super("Cube", new float[]{
                -0.5f, 0.5f, 0,                      // TOP LEFT
                -0.5f, -0.5f, 0,                      // BOTTOM LEFT
                0.5f, -0.5f, 0,                     // BOTTOM RIGHT
                0.5f, 0.5f, 0                      // TOP RIGHT
        }, new float[]{
                -1/3f,  1/3f, 1/3f,
                -1/3f, -1/3f, 1/3f,
                1/3f, -1/3f, 1/3f,
                1/3f, 1/3f, 1/3f
        }, new float[]{
                0, 0,
                0, 1,
                1, 0,
                1, 1
        }, new int[]{
                0, 1, 3,
                3, 1, 2
        });
    }
}
