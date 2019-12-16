package com.wawacorp.wawagl.core.view.lighting;

import com.wawacorp.wawagl.core.model.FlatColor;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.FlatColorProperty;
import com.wawacorp.wawagl.core.view.single.mesh.GLSingleMesh;
import org.joml.Vector3f;

public class Light {
    private Vector3f color;
    private GLSingleMesh mesh;
    protected final Instance instance;
    protected final Entity entity;

    //TODO: Lights meshes should have the colors of the lights in the shader
    @Deprecated
    public Light(Mesh mesh, Vector3f position, Vector3f color) {
        this.entity = new Entity();
        entity.translate(position.x, position.y, position.z);
        this.instance = new Instance(new FlatColorProperty(new FlatColor()), new EntityProperty(entity));
        this.mesh = mesh == null ? null : new GLSingleMesh(mesh, instance);
        this.color = color;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
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
