package com.wawacorp.wawagl.core.view.instanced;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.entity.TextureArrayEntity;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.buffer.IndexBufferObject;
import com.wawacorp.wawagl.core.buffer.InstancedVAO;
import com.wawacorp.wawagl.core.buffer.texture.Texture2DArray;
import com.wawacorp.wawagl.core.view.GLMesh;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static org.lwjgl.opengl.GL46.*;

public class GLInstancedTextureArrayMesh extends GLMesh implements Observer {
    private InstancedVAO vertexArray;
    private IndexBufferObject indexBuffer;
    private Texture2DArray texture;

    private final List<TextureArrayEntity> entities;

    public GLInstancedTextureArrayMesh(Mesh mesh, Texture2DArray texture2DArray, int maxInstance) {
        this(mesh, texture2DArray, Shader.getInstancedTextureArrayShader(), maxInstance);
    }

    public GLInstancedTextureArrayMesh(Mesh mesh, Texture2DArray texture2DArray, Shader shader, int maxInstance) {
        this(mesh, texture2DArray, shader, new ArrayList<>(maxInstance));
    }

    public GLInstancedTextureArrayMesh(Mesh mesh, Texture2DArray texture2DArray, Shader shader, List<TextureArrayEntity> entities) {
        super(mesh);
        this.entities = entities;
        this.vertexArray = new InstancedVAO(mesh);
        this.indexBuffer = new IndexBufferObject(mesh.getIndices());
        this.texture = texture2DArray;
        this.shader = shader;
        for (TextureArrayEntity textureArrayEntity : entities) {
            textureArrayEntity.addObserver(this);
        }
        update();
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    @Override
    public void draw() {
        bind();
        shader.bind();
        glDrawElementsInstanced(GL_TRIANGLES, vertexCount * 3, GL_UNSIGNED_INT, 0, entities.size());
        shader.unbind();
        unbind();
    }

    @Override
    public void draw(Shader shader) {
        bind();
        shader.bind();
        glDrawElementsInstanced(GL_TRIANGLES, vertexCount * 3, GL_UNSIGNED_INT, 0, entities.size());
        shader.unbind();
        unbind();
    }

    public void addInstance(TextureArrayEntity textureArrayEntity) {
        entities.add(textureArrayEntity);
        textureArrayEntity.addObserver(this);
        update();
    }

    public void bind() {
        vertexArray.bind();
        indexBuffer.bind();
        texture.bind();
    }

    public void unbind() {
        texture.unbind();
        indexBuffer.unbind();
        vertexArray.unbind();
    }

    public void update() {
        shader.bind();
        shader.updateTransform(entities);
        if (!shader.setTexture("texture_kd")) System.err.println("Error while updating texture to shader");
        shader.updateLayers(entities);
        shader.unbind();
    }

    public void updateTransform() {
        shader.bind();
        shader.updateTransform(entities);
        shader.unbind();
    }
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

    public void setTexture(Texture2DArray texture) {
        this.texture = texture;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public String getName() {
        return name;
    }
}
