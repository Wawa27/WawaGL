package com.wawacorp.wawagl.core.view.lighting;

import com.wawacorp.wawagl.core.model.Mesh;
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
    public String toString() {
        return super.toString();
    }
}
