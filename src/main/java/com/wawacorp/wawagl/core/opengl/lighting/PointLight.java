package com.wawacorp.wawagl.core.opengl.lighting;

import com.wawacorp.wawagl.core.opengl.model.model.Mesh;
import org.joml.Vector3f;

public class PointLight extends Light {
    private Vector3f color;

    public PointLight(Mesh mesh, Vector3f position, Vector3f color) {
        super(mesh, position, color);
        this.color = color;
    }

    public Vector3f getColor() {
        return color;
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

    @Override
    public String toString() {
        return super.toString();
    }
}
