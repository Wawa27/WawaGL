package com.wawacorp.wawagl.core.view.single;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.buffer.IndexBufferObject;
import com.wawacorp.wawagl.core.buffer.VertexArrayObject;
import com.wawacorp.wawagl.core.buffer.texture.TextureAtlas;
import com.wawacorp.wawagl.core.view.GLMesh;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class GLTextureAtlasMesh extends GLMesh {
    private String name;

    protected Shader shader;

    private VertexArrayObject vertexArray;
    private IndexBufferObject indexBuffer;

    private TextureAtlas texture;
    private int textureRow;
    private int textureColumn;

    protected final Entity entity;

    public GLTextureAtlasMesh(Mesh mesh, Entity entity, TextureAtlas texture, int textureColumn, int textureRow) {
        this(mesh, entity, texture, Shader.getTextureAtlasShader(), textureColumn, textureRow);
    }

    public GLTextureAtlasMesh(Mesh mesh, TextureAtlas texture, int textureColumn, int textureRow) {
        this(mesh, new Entity(), texture, Shader.getTextureAtlasShader(), textureColumn, textureRow);
    }

    public GLTextureAtlasMesh(Mesh mesh, Entity entity, TextureAtlas texture, Shader shader, int textureColumn, int textureRow) {
        super(mesh);
        this.name = mesh.getName();
        this.textureColumn = textureColumn;
        this.textureRow = textureRow;
        vertexArray = new VertexArrayObject(mesh);
        indexBuffer = new IndexBufferObject(mesh.getIndices());
        this.entity = entity;
        this.texture = texture;
        this.shader = shader;
        update();
    }

    @Override
    public void draw() {
        bind();
        shader.bind();
        glDrawElements(GL_TRIANGLES, vertexCount*3, GL_UNSIGNED_INT, 0);
        shader.unbind();
        unbind();
    }

    @Override
    public void draw(Shader shader) {
        bind();
        shader.bind();
        shader.updateTransform(entity); //TODO: remove this hack
        glDrawElements(GL_TRIANGLES, vertexCount*3, GL_UNSIGNED_INT, 0);
        shader.unbind();
        unbind();
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
        shader.updateTransform(entity);
        shader.setTexture("texture_kd");
        shader.setTextureAtlas(texture, getOffset());
        shader.unbind();
    }

    public void updateTransform() {
        shader.bind();
        shader.updateTransform(entity);
        shader.unbind();
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

    public void setShader(Shader shader) {
        this.shader = shader;
        update();
    }

    public Vector2f getOffset() {
        return new Vector2f(((float) (texture.getWidth() / texture.getCellCount()) * textureColumn), ((float) (texture.getHeight() / texture.getCellCount()) * textureRow));
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public String getName() {
        return name;
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
