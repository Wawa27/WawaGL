package com.wawacorp.wawagl.core.view.lighting;

import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.model.entity.Entity;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class DirectionalLight extends Light {
    private Vector4f direction;

    public DirectionalLight(Mesh mesh, Entity entity, Vector4f color, Vector4f direction) {
        super(mesh, entity, color);
        this.direction = direction.negate();
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
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
