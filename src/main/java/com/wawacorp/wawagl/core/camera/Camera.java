package com.wawacorp.wawagl.core.camera;

import com.wawacorp.wawagl.core.camera.projection.Projection;
import com.wawacorp.wawagl.core.buffer.ubo.CameraUBO;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.view.Bindable;
import com.wawacorp.wawagl.core.view.Movable;
import org.joml.Matrix4f;

public abstract class Camera extends Entity implements Bindable {
    public static Camera ACTIVE;

    static {
        ACTIVE = SimpleCamera.DEFAULT_ORTHOGRAPHIC;
    }

    private CameraUBO ubo;

    protected final Projection projection;
    protected final Matrix4f viewMatrix;

    private final Matrix4f viewProjectionMatrix;

    public Camera(Projection projection) {
        this.projection = projection;
        viewProjectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();

        ubo = new CameraUBO(this);
    }

    public abstract void forward();

    public abstract void backward();

    public abstract void left();

    public abstract void right();

    public abstract void up();

    public abstract void down();

    public abstract void mouse(float x, float y);

    public abstract void setPosition(float x, float y, float z);

    @Override
    public void bind() {
        ubo.bind();
    }

    @Override
    public void unbind() {
        ubo.unbind();
    }

    public void update() {
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

    public Matrix4f getNormalMatrix(Matrix4f model) {
        return viewMatrix.mul(model, new Matrix4f()).transpose().invert();
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
}
