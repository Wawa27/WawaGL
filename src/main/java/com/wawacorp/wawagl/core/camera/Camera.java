package com.wawacorp.wawagl.core.camera;

import com.wawacorp.wawagl.core.camera.projection.Projection;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.view.buffer.ubo.CameraUBO;
import com.wawacorp.wawagl.core.view.Bindable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public abstract class Camera extends Entity implements Bindable {
    public static Camera ACTIVE;

    static {
        ACTIVE = FPSCamera.DEFAULT;
    }

    private CameraUBO ubo;

    protected final Projection projection;
    protected final Matrix4f viewMatrix;

    private final Matrix4f viewProjectionMatrix;

    /**
     * Creates a new Camera
     * Must call {#link update(#a, #a)} method to upload it to the shader
     * @param projection
     */
    public Camera(Projection projection) {
        this.projection = projection;
        viewProjectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();

        ubo = new CameraUBO(this);
    }

    @Override
    public void bind() {
        ubo.bind();
    }

    @Override
    public void unbind() {
        ubo.unbind();
    }

    public void updateView() {
        ubo.update();
    }

    public Matrix4f getViewProjectionMatrix() {
        return viewProjectionMatrix;
    }

    public static void setActive(Camera camera) {
        ACTIVE = camera;
    }

    public Projection getProjection() {
        return projection;
    }

    public Matrix4f getProjectionMatrix() {
        return projection.getProjectionMatrix();
    }

    /**
     * Returns the vector in view space
     * @param vector
     * @return
     */
    public Vector4f getPositionViewSpace(Vector4f vector) {
        return vector.mul(viewMatrix);
    }

    /**
     * Returns the vector in view space
     * @param vector
     * @return
     */
    public Vector4f getVectorViewSpace(Vector4f vector) {
        return vector.mul(viewMatrix.transpose(new Matrix4f()).invert());
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
}
