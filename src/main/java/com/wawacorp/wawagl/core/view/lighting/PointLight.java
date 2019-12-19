package com.wawacorp.wawagl.core.view.lighting;

import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.model.entity.Entity;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class PointLight extends Light {

    public PointLight(Mesh mesh, Entity entity, Vector4f color) {
        super(mesh, entity, color);
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
