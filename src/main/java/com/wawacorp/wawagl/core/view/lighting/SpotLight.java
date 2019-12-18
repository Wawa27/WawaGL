package com.wawacorp.wawagl.core.view.lighting;

import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.shader.ShaderException;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class SpotLight extends Light {
    private Vector3f direction;
    private Vector3f color;
    private float cutoff;

    public SpotLight(Mesh mesh, Entity entity, Vector3f color, Vector3f direction, float cutoff) throws ShaderException {
        super(mesh, entity, color);
        this.direction = direction;
        this.color = color;
        this.cutoff = cutoff;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }

    public float getCutoff() {
        return cutoff;
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
