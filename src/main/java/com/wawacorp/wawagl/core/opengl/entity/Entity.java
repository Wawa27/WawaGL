package com.wawacorp.wawagl.core.opengl.entity;

import com.wawacorp.wawagl.core.opengl.view.Movable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Entity implements Movable {
    protected final Vector3f position;
    protected final Vector3f rotation;
    protected final Vector3f scale;

    protected final Matrix4f modelMatrix;
    protected final float[] model;

    protected boolean hasChanged;

    public Entity() {
        this(new Vector3f());
    }

    public Entity(Vector3f position) {
        this.position = position;
        rotation = new Vector3f();
        scale = new Vector3f(1, 1, 1);
        model = new float[16];
        modelMatrix = new Matrix4f();
        hasChanged = true;
    }

    public void translate(float x, float y, float z) {
        position.add(x, y, z);
        hasChanged = true;
    }

    public void scale(float x, float y, float z) {
        scale.add(x, y, z);
        hasChanged = true;
    }

    public void rotate(float x, float y, float z) {
        rotation.add(x, y, z);
        hasChanged = true;
    }

    @Override
    public void setTranslation(float x, float y, float z) {
        position.set(x, y, z);
        hasChanged = true;
    }

    @Override
    public void setRotation(float x, float y, float z) {
        rotation.set(x, y, z);
        hasChanged = true;
    }

    @Override
    public void setScale(float x, float y, float z) {
        scale.set(x, y, z);
        hasChanged = true;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public float[] getModel() {
        if (!hasChanged) {
            return model;
        }
        hasChanged = false;
        return modelMatrix.identity().rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z).translate(position).scale(scale).get(model);
    }

    @Override
    public String toString() {
        return modelMatrix.toString();
    }
}
