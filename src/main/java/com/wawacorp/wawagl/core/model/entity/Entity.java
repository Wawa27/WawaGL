package com.wawacorp.wawagl.core.model.entity;

import com.wawacorp.wawagl.core.manager.EntityManager;
import com.wawacorp.wawagl.core.view.Movable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Observable;

public class Entity extends Observable implements Movable {
    protected final Vector3f position;
    protected final Vector3f rotation;
    protected final Vector3f scale;

    protected final Matrix4f modelMatrix;
    protected final float[] model;

    public Entity() {
        this(new Vector3f());
    }

    public Entity(Vector3f position) {
        EntityManager.addEntity(this);
        this.position = position;
        rotation = new Vector3f();
        scale = new Vector3f(1, 1, 1);
        model = new float[16];
        modelMatrix = new Matrix4f();
        update();
    }

    public void translate(float x, float y, float z) {
        position.add(x, y, z);
        update();
    }

    public void scale(float x, float y, float z) {
        scale.add(x, y, z);
        update();
    }

    public void rotate(float x, float y, float z) {
        rotation.add(x, y, z);
        update();
    }

    @Override
    public void setTranslation(float x, float y, float z) {
        position.set(x, y, z);
        update();
    }

    @Override
    public void setRotation(float x, float y, float z) {
        rotation.set(x, y, z);
        update();
    }

    @Override
    public void setScale(float x, float y, float z) {
        scale.set(x, y, z);
        update();
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
        return modelMatrix.identity().translate(position).rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z).scale(scale).get(model);
    }

    public void update() {
        setChanged();
        notifyObservers();
    }

    public void onLoop() {

    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    @Override
    public String toString() {
        return modelMatrix.toString();
    }
}
