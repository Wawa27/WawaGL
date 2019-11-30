package com.wawacorp.wawagl.core.view.instanced;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.buffer.IndexBufferObject;
import com.wawacorp.wawagl.core.buffer.InstancedVAO;
import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.view.GLMesh;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static org.lwjgl.opengl.GL46.*;

public class GLInstancedMaterialMesh extends GLMesh implements Observer {
    private InstancedVAO vertexArray;
    private IndexBufferObject indexBuffer;
    private Material material;

    private final List<Entity> entities;

    public GLInstancedMaterialMesh(Mesh mesh, int maxInstance) {
        this(mesh, Shader.getInstancedMaterialShader(), maxInstance);
    }

    public GLInstancedMaterialMesh(Mesh mesh, Shader shader, int maxInstance) {
        this(mesh, Material.DEFAULT, shader, new ArrayList<>(maxInstance));
    }

    public GLInstancedMaterialMesh(Mesh mesh, Material material, Shader shader, int maxInstance) {
        this(mesh, material, shader, new ArrayList<>(maxInstance));
    }

    public GLInstancedMaterialMesh(Mesh mesh, Material material, Shader shader, List<Entity> entities) {
        super(mesh);
        this.entities = entities;
        this.vertexArray = new InstancedVAO(mesh);
        this.material = material;
        this.indexBuffer = new IndexBufferObject(mesh.getIndices());
        this.shader = shader;
        for (Entity entity : entities) {
            entity.addObserver(this);
        }
        update();
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    @Override
    public void draw() {
        bind();
        glDrawElementsInstanced(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0, entities.size());
        unbind();
    }

    @Override
    public void draw(Shader shader) {
        bind();
        shader.bind();
        glDrawElementsInstanced(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0, entities.size());
        shader.unbind();
        unbind();
    }

    public void addInstance(Entity entity) {
        entities.add(entity);
        update();
    }

    public void bind() {
        vertexArray.bind();
        indexBuffer.bind();
        shader.bind();
    }

    public void unbind() {
        shader.unbind();
        indexBuffer.unbind();
        vertexArray.unbind();
    }

    public void update() {
        shader.bind();
        shader.updateTransform(entities);
        shader.setMaterial(material);
        shader.unbind();
    }

    public void updateTransform() {
        shader.bind();
        shader.updateTransform(entities);
        shader.unbind();
    }

    //TODO: cache commands instead of updating the shader for every calls
    @Override
    public void translate(float x, float y, float z) {
        for (Entity e : entities) e.translate(x, y, z);
        updateTransform();
    }

    @Override
    public void rotate(float x, float y, float z) {
        for (Entity e : entities) e.rotate(x, y, z);
        updateTransform();
    }

    @Override
    public void scale(float x, float y, float z) {
        for (Entity e : entities) e.scale(x, y, z);
        updateTransform();
    }

    @Override
    public void setTranslation(float x, float y, float z) {
        for (Entity e : entities) e.setTranslation(x, y, z);
        updateTransform();
    }

    @Override
    public void setRotation(float x, float y, float z) {
        for (Entity e : entities) e.setRotation(x, y, z);
        updateTransform();
    }

    @Override
    public void setScale(float x, float y, float z) {
        for (Entity e : entities) e.setScale(x, y, z);
        updateTransform();
    }

    public void translate(int instance, float x, float y, float z) {
        entities.get(instance).translate(x, y, z);
        updateTransform();
    }

    public void rotate(int instance, float x, float y, float z) {
        entities.get(instance).rotate(x, y, z);
        updateTransform();
    }

    public void scale(int instance, float x, float y, float z) {
        entities.get(instance).scale(x, y, z);
        updateTransform();
    }

    public int getMaxInstance() {
        return entities.size();
    }

    public Entity getInstance(int i) {
        return entities.get(i);
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Material getMaterial() {
        return material;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public String getName() {
        return name;
    }
}
