package com.wawacorp.wawagl.core.camera.projection;

import org.joml.Matrix4f;

public class Projection {
    private final Matrix4f projectionMatrix;
    private final float[] projection;

    public Projection(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        projection = new float[16];
    }

    public float[] getProjection() {
        return projectionMatrix.get(projection);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
