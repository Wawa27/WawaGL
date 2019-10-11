package com.wawacorp.wawagl.core.opengl.camera;

import com.wawacorp.wawagl.core.opengl.camera.projection.Orthographic;
import com.wawacorp.wawagl.core.opengl.view.Bindable;
import com.wawacorp.wawagl.core.opengl.view.Movable;
import com.wawacorp.wawagl.core.opengl.shader.bo.ubo.ViewProjectionUBO;
import com.wawacorp.wawagl.core.opengl.camera.projection.Perspective;
import com.wawacorp.wawagl.core.opengl.camera.projection.Projection;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

// TODO: Should extends GLSimpleMesh ?
public class Camera implements Bindable, Movable {
    public static Camera ACTIVE;

    public static Camera DEFAULT_ORTHOGRAPHIC = new Camera(Orthographic.DEFAULT);
    public static Camera DEFAULT_PERSPECTIVE = new Camera(Perspective.DEFAULT);

    private final Projection projection;
    private final Matrix4f matrix;
    private ViewProjectionUBO ubo;

    private final Vector3f position;
    private final Vector3f direction;
    private final Vector3f up;

    static {
        ACTIVE = DEFAULT_ORTHOGRAPHIC;
    }

    public Camera(Projection projection) {
        super();
        this.projection = projection;

        this.position = new Vector3f(0.0f, 0, 0);
        this.direction = new Vector3f(0.0f, 0, -1f);
        this.up = new Vector3f(0.0f, 1.0f, 0.0f);

        this.matrix = new Matrix4f();
        ubo = new ViewProjectionUBO(this);
        update();
    }

    @Override
    public void bind() {
        ubo.bind();
    }

    @Override
    public void unbind() {
        ubo.unbind();
    }

    public void translate(float x, float y, float z) {
        position.add(x, y, z);
        update();
    }

    public void setTranslate(int component, float value) {
        position.set(component, value);
        update();
    }

    public void rotateX(float angle) {
        position.rotateX(angle);
        update();
    }

    public void rotateY(float angle) {
        position.rotateY(angle);
        update();
    }

    public void rotateZ(float angle) {
        position.rotateZ(angle);
        update();
    }

    @Override
    public void scale(float x, float y, float z) {

    }

    @Override
    public void rotate(float x, float y, float z) {

    }

    @Override
    public void setTranslation(float x, float y, float z) {

    }

    @Override
    public void setScale(float x, float y, float z) {

    }

    @Override
    public void setRotation(float x, float y, float z) {

    }

    public void update() {
        this.matrix.setLookAt(position, direction, up);
        ubo.update();
    }

    public Matrix4f getMatrix() {
        return matrix;
    }

    public Matrix3f getNormalMatrix() {
        return new Matrix3f(matrix).transpose().invert();
    }

    public static void setActive(Camera camera) {
        ACTIVE = camera;
    }

    public static Camera newPerspectiveCamera() {
        return new Camera(Perspective.DEFAULT);
    }

    public float[] getProjectionMatrix() {
        return projection.getProjection();
    }
}
