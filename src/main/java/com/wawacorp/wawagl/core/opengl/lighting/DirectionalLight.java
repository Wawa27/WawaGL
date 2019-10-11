package com.wawacorp.wawagl.core.opengl.lighting;

import com.wawacorp.wawagl.core.opengl.model.model.Mesh;
import com.wawacorp.wawagl.core.opengl.Environnement;
import org.joml.Vector3f;

public class DirectionalLight extends Light {
    private Vector3f direction;
    private Vector3f color;

    public DirectionalLight(Mesh mesh, Vector3f position, Vector3f direction, Vector3f color) {
        super(mesh, position, color);
        this.direction = direction.negate();
        this.color = color;
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void scale(float x, float y, float z) {
        super.scale(x, y, z);
    }

    @Override
    public void translate(float x, float y, float z) {
        super.translate(x, y, z);
    }

    @Override
    public void rotate(float x, float y, float z) {
        super.rotate(x, y, z);
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
        Environnement.getLightScene().update();
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public void setPosition(float x, float y, float z) {
        super.getPosition().set(x, y, z);
        updateTransform();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
