package com.wawacorp.wawagl.core.model.shape;

public class InfiniteRectangle extends Shape {

    public InfiniteRectangle() {
        this(1, 1);
    }

    public InfiniteRectangle(float w, float h) {
        super("Rectangle", new float[]{
                -w / 2f, h / 2f, 0,                      // TOP LEFT
                -w / 2f, -h / 2f, 0,                      // BOTTOM LEFT
                +w / 2f, -h / 2f, 0,                     // BOTTOM RIGHT
                +w / 2f, h / 2f, 0,                // TOP RIGHT

                - 3 * w / 2f, h / 2f, 0,                      // TOP LEFT
                -3 * w / 2f, -h / 2f, 0,                      // BOTTOM LEFT
                -w / 2f, -h / 2f, 0,                     // BOTTOM RIGHT
                -w / 2f, h / 2f, 0,                 // TOP RIGHT

                + w / 2f, h / 2f, 0,                      // TOP LEFT
                +w / 2f, -h / 2f, 0,                      // BOTTOM LEFT
                +3 * w / 2f, -h / 2f, 0,                     // BOTTOM RIGHT
                +3 * w / 2f, h / 2f, 0                     // TOP RIGHT
        }, new float[]{
                -1 / 3f, 1 / 3f, 1 / 3f,
                -1 / 3f, -1 / 3f, 1 / 3f,
                1 / 3f, -1 / 3f, 1 / 3f,
                1 / 3f, 1 / 3f, 1 / 3f,

                -1 / 3f, 1 / 3f, 1 / 3f,
                -1 / 3f, -1 / 3f, 1 / 3f,
                1 / 3f, -1 / 3f, 1 / 3f,
                1 / 3f, 1 / 3f, 1 / 3f,

                -1 / 3f, 1 / 3f, 1 / 3f,
                -1 / 3f, -1 / 3f, 1 / 3f,
                1 / 3f, -1 / 3f, 1 / 3f,
                1 / 3f, 1 / 3f, 1 / 3f
        }, new float[]{
                0, 0,
                0, 1,
                1, 1,
                1, 0,

                -1, 0,
                -1, 1,
                0, 1,
                0, 0,

                1, 0,
                1, 1,
                2, 1,
                2, 0
        }, new int[]{
                0, 1, 3,
                3, 1, 2,

                4, 5, 7,
                7, 5, 6,

                8, 9, 11,
                11, 9, 10
        });
    }
}
