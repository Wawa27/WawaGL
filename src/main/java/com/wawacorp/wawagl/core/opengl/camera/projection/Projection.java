package com.wawacorp.wawagl.core.opengl.camera.projection;

import org.joml.Matrix4f;

public abstract class Projection {
    private final Matrix4f projectionMatrix;
    private final float[] projection;

    public Projection(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        projection = new float[16];
    }

    public float[] getProjection() {
        return projectionMatrix.get(projection);
    }

    public abstract float getWidth();

    public abstract float getHeight();
}
