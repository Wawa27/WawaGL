package com.wawacorp.wawagl.core.view.lighting;

import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.view.Environnement;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class DirectionalLight extends Light {
    private Vector4f direction;
    private Vector3f color;

    public DirectionalLight(Mesh mesh, Vector3f position, Vector4f direction, Vector3f color) {
        super(mesh, position, color);
        this.direction = direction.negate();
        this.color = color;
    }

    @Override
    public void draw() {
        super.draw();
    }

    public Vector4f getDirection() {
        return direction;
    }

    public void setDirection(Vector4f direction) {
        this.direction = direction;
        Environnement.getLightScene().update();
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
