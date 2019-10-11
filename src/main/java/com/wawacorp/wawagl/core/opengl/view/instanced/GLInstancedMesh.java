package com.wawacorp.wawagl.core.opengl.view.instanced;

import com.wawacorp.wawagl.core.opengl.entity.Entity;
import com.wawacorp.wawagl.core.opengl.manager.AssetManager;
import com.wawacorp.wawagl.core.opengl.shader.bo.IndexBufferObject;
import com.wawacorp.wawagl.core.opengl.shader.bo.InstancedVAO;
import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Material;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.Texture;
import com.wawacorp.wawagl.core.opengl.model.model.Mesh;
import com.wawacorp.wawagl.core.opengl.shader.Shader;
import com.wawacorp.wawagl.core.opengl.view.GLMesh;

import static org.lwjgl.opengl.GL46.*;

public class GLInstancedMesh extends GLMesh {
    private InstancedVAO vertexArray;
    private IndexBufferObject indexBuffer;

    private Texture texture;
    private Material material;

    private final Entity[] entities;

    public GLInstancedMesh(Mesh mesh, int instanceCount) {
        super(mesh);

        this.entities = new Entity[instanceCount];

        this.vertexArray = new InstancedVAO(mesh);
        this.indexBuffer = new IndexBufferObject(mesh.getIndices());

        if (mesh.getTexturePath() != null) this.texture = AssetManager.getTexture2D(mesh.getTexturePath());
        this.material = mesh.getMaterial();

        this.shader = (texture == null) ? Shader.getInstancedMaterialShader() : Shader.getInstancedTextureShader();
        update();
    }

    @Override
    public void draw() {
        bind();
        glDrawElementsInstanced(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0, entities.length);
        unbind();
    }

    @Override
    public void draw(Shader shader) {
        bind();
        shader.bind();
        glDrawElementsInstanced(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0, entities.length);
        shader.unbind();
        unbind();
    }

    /**
     * Creates a new instance of this mesh at the specified location
     * Results in a NO-OP if {@code instance} is equals or greater than {@link GLInstancedMesh#getMaxInstance()}
     */
    public void addInstance(int activeInstance) {
        if (activeInstance >= entities.length) return;
        entities[activeInstance] = new Entity();
    }

    /**
     * Removes the instance at the specified location
     * @param instance The instance to remove
     * @return True if removed, false otherwise
     */
    public boolean removeInstance(int instance, int activeInstance) {
        if (instance < 0 || instance >= entities.length || entities[instance] == null) return false;
        if (instance > 0)  entities[instance] = entities[activeInstance];
        return true;
    }

    public void bind() {
        vertexArray.bind();
        indexBuffer.bind();

        if (texture != null) texture.bind();

        shader.bind();
    }

    public void unbind() {
        shader.unbind();

        if (texture != null) texture.unbind();

        indexBuffer.unbind();
        vertexArray.unbind();
    }

    public void update() {
        shader.bind();
        shader.updateTransform(entities);
        if (!shader.setMaterial(material)) System.err.println("Error while updating material to shader");
        if (!shader.setTexture("texture_kd")) System.err.println("Error while updating texture to shader");
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
        entities[instance].translate(x, y, z);
        updateTransform();
    }

    public void rotate(int instance, float x, float y, float z) {
        entities[instance].rotate(x, y, z);
        updateTransform();
    }

    public void scale(int instance, float x, float y, float z) {
        entities[instance].scale(x, y, z);
        updateTransform();
    }

    public int getMaxInstance() {
        return entities.length;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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
