package com.wawacorp.wawagl.core.opengl.camera.projection;

import org.joml.Matrix4f;

public class Orthographic extends Projection {
    private final static float DEFAULT_LEFT = -0.5f;
    private final static float DEFAULT_RIGHT = 0.5f;
    private final static float DEFAULT_BOTTOM = -0.5f;
    private final static float DEFAULT_TOP = 0.5f;
    private final static float DEFAULT_ZNEAR = 0.01f;
    private final static float DEFAULT_ZFAR = -100f;

    public final static Orthographic DEFAULT = new Orthographic(DEFAULT_LEFT, DEFAULT_RIGHT, DEFAULT_BOTTOM, DEFAULT_TOP, DEFAULT_ZNEAR, DEFAULT_ZFAR);

    public Orthographic(float left, float right, float bottom, float top, float zNear, float zFar) {
        super(new Matrix4f().setOrtho(left, right, bottom, top, zNear, zFar));
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }
}
