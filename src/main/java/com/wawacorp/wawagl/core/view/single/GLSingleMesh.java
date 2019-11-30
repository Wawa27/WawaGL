package com.wawacorp.wawagl.core.view.single;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.buffer.IndexBufferObject;
import com.wawacorp.wawagl.core.buffer.VertexArrayObject;
import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.GLMesh;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Observable;
import java.util.Observer;

import static org.lwjgl.opengl.GL11.*;

public class GLSingleMesh extends GLMesh implements Observer {
    private String name;

    protected Shader shader;
    private VertexArrayObject vertexArray;
    private IndexBufferObject indexBuffer;
    protected Material material;
    private final int indexCount;

    protected final Entity entity;

    public GLSingleMesh(Mesh mesh) {
        this(mesh, new Entity());
    }

    public GLSingleMesh(Mesh mesh, Entity entity) {
        super(mesh);
        this.name = mesh.getName();
        this.vertexArray = new VertexArrayObject(mesh);
        this.indexBuffer = new IndexBufferObject(mesh.getIndices());
        this.entity = entity;
        this.material = mesh.getMaterial();
        if (this.material == null) {
            this.material = Material.DEFAULT;
        }
        this.shader = Shader.getMaterialShader();
        this.indexCount = mesh.getIndices().length;
        this.entity.addObserver(this);

        updateAll();
    }

    @Override
    public void draw() {
        bindAll();
        shader.bind();
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
        shader.unbind();
        unbindAll();
    }

    @Override
    public void draw(Shader shader) {
        bindAll();
        shader.bind();
        shader.updateTransform(entity); //TODO: remove this hack
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
        shader.unbind();
        unbindAll();
    }

    public final void bindAll() {
        vertexArray.bind();
        indexBuffer.bind();
        material.bind();
        shader.bind();
    }

    public final void unbindAll() {
        shader.unbind();
        material.unbind();
        indexBuffer.unbind();
        vertexArray.unbind();
    }

    public void updateAll() {
        shader.bind();
        shader.updateTransform(entity);
        shader.setMaterial(material);
        shader.setTexture("texture_kd");
        shader.unbind();
    }

    public void updateTransform() {
        shader.bind();
        shader.updateTransform(entity);
        shader.unbind();
    }

    @Override
    public void update(Observable observable, Object o) {
        updateAll();
    }

    public void scale(float x, float y, float z) {
        entity.scale(x, y, z);
        updateTransform();
    }

    public void translate(float x, float y, float z) {
        entity.translate(x, y, z);
        updateTransform();
    }

    public void rotate(float x, float y, float z) {
        entity.rotate(x, y, z);
        updateTransform();
    }

    public void setScale(float x, float y, float z) {
        entity.setScale(x, y, z);
        updateTransform();
    }

    public void setTranslation(float x, float y, float z) {
        entity.setTranslation(x, y, z);
        updateTransform();
    }

    public void setRotation(float x, float y, float z) {
        entity.setRotation(x, y, z);
        updateTransform();
    }

    public Vector3f getPosition() {
        return entity.getPosition();
    }

    public Vector3f getRotation() {
        return entity.getRotation();
    }

    public Vector3f getScale() {
        return entity.getScale();
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
        updateAll();
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

    public Matrix4f getModelMatrix() {
        return entity.getModelMatrix();
    }

    @Override
    public Shader getShader() {
        return shader;
    }

    @Override
    public String toString() {
        return getName();
    }
}
