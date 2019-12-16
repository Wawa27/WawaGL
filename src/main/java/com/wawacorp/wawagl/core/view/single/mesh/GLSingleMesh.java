package com.wawacorp.wawagl.core.view.single.mesh;

import com.wawacorp.wawagl.core.model.FlatColor;
import com.wawacorp.wawagl.core.model.entity.AttachedEntity;
import com.wawacorp.wawagl.core.view.MaterialTextureView;
import com.wawacorp.wawagl.core.view.buffer.vbo.FloatArrayVBO;
import com.wawacorp.wawagl.core.view.buffer.IndexBufferObject;
import com.wawacorp.wawagl.core.view.buffer.VertexArrayObject;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.GLMesh;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.FlatColorProperty;
import com.wawacorp.wawagl.core.view.instance.property.MaterialProperty;
import com.wawacorp.wawagl.core.view.instance.property.MaterialTextureProperty;
import org.joml.Vector3f;

import java.util.Observable;

import static org.lwjgl.opengl.GL11.*;

public class GLSingleMesh extends GLMesh {
    private final String name;

    protected Shader shader;
    protected VertexArrayObject vertexArray;
    protected IndexBufferObject indexBuffer;
    //    protected final FloatArrayVBO positionBufferObject;
    protected final Instance instance;

    public GLSingleMesh(Mesh mesh, Instance instance) {
        this(mesh, instance, Shader.getFlatColorShader());
    }

    public GLSingleMesh(Mesh mesh, Instance instance, Shader shader) {
        super(mesh);
        this.name = mesh.getName();
        this.vertexArray = new VertexArrayObject(mesh);
        this.indexBuffer = new IndexBufferObject(mesh.getIndices());
        this.instance = instance;
        this.shader = shader;
        this.instance.addObserver(this);
        update();
    }

    @Override
    public final void draw() {
        bind();
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
        unbind();
    }

    @Override
    public final void draw(Shader shader) {
        bind();
        instance.upload(shader);
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
        unbind();
    }

    public final void bind() {
        vertexArray.bind();
        indexBuffer.bind();
        instance.bind();
        shader.bind();
    }

    public final void unbind() {
        shader.unbind();
        instance.unbind();
        indexBuffer.unbind();
        vertexArray.unbind();
    }

    public void update() {
        shader.bind();
        instance.upload(shader);
        shader.unbind();
    }

    @Override
    public void update(Observable observable, Object o) {
//        if (o instanceof float[]) { // vertices
//            vertexArray.bind();
//            positionBufferObject.update();
//            vertexArray.unbind();
//        }
        update();
    }

    public void setShader(Shader shader) {
        this.shader = shader;
        update();
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public String getName() {
        return name;
    }

    public Instance getInstance() {
        return instance;
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
