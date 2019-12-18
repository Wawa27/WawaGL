package com.wawacorp.wawagl.core.view.single;

import com.wawacorp.wawagl.core.view.buffer.IndexBufferObject;
import com.wawacorp.wawagl.core.view.buffer.VertexArrayObject;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.GLView;
import com.wawacorp.wawagl.core.view.buffer.vbo.VertexBufferObject;
import com.wawacorp.wawagl.core.view.instance.Instance;

import java.util.Observable;

import static org.lwjgl.opengl.GL11.*;

public class GLSingleView extends GLView {
    private final String name;
    protected VertexBufferObject positionBuffer;
    protected final Mesh mesh;

    public GLSingleView(Mesh mesh, Instance instance) {
        this(mesh, instance, Shader.getColorArrayShader());
    }

    public GLSingleView(Mesh mesh, Instance instance, Shader shader) {
        this.name = mesh.getName();
        this.vertexArrayObject = new VertexArrayObject(mesh);
        if (mesh.getIndices() != null) {
            this.positionBuffer = vertexArrayObject.getVBO(0);
        }
        this.instance = instance;
        this.shader = shader;
        this.instance.addObserver(this);
        if (mesh.getIndices() != null) this.vertexCount = mesh.getIndices().length;
        else this.vertexCount = mesh.getVertices().length / 3;
        this.mesh = mesh;
        update();
    }

    @Override
    public final void draw() {
        bind();
        if (mesh.getIndices() != null) {
            glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        } else {
            glDrawArrays(GL_TRIANGLES, 0, vertexCount);
        }
        unbind();
    }

    @Deprecated
    @Override
    public final void draw(Shader shader) {
        bind();
        instance.upload(shader);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        unbind();
    }

    public final void bind() {
        vertexArrayObject.bind();
        instance.bind();
        shader.bind();
    }

    public final void unbind() {
        shader.unbind();
        instance.unbind();
        vertexArrayObject.unbind();
    }

    public void update() {
        shader.bind();
        instance.upload(shader);
        shader.unbind();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof float[]) { // vertices
            positionBuffer.bind();
            positionBuffer.updateData((float[]) o);
            positionBuffer.unbind();
        }
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
