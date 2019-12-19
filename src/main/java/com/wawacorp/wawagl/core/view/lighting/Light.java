package com.wawacorp.wawagl.core.view.lighting;

import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.MaterialProperty;
import com.wawacorp.wawagl.core.view.single.GLSingleView;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Light {
    private Vector4f color;
    private GLSingleView mesh;
    protected final Instance instance;
    protected final Entity entity;

    //TODO: Lights meshes should have the colors of the lights in the shader
    public Light(Mesh mesh, Entity entity, Vector4f color) {
        this.color = color;
        this.entity = entity;
        this.instance = new Instance(
                new EntityProperty(entity),
                new MaterialProperty("material", new Material(
                "light",
                new Vector4f(1, 0, 0, 1),
                new Vector4f(1, 0, 0, 1),
                new Vector4f(1, 0, 0, 1)
        )));
        this.mesh = mesh == null ? null : new GLSingleView(mesh, instance, Shader.getMaterialShader());
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public void draw() {
        if (mesh != null) mesh.draw();
    }

    public void setShader(Shader shader) {
        mesh.setShader(shader);
    }

    public String getName() {
        return mesh.getName();
    }

    public Instance getInstance() {
        return instance;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
