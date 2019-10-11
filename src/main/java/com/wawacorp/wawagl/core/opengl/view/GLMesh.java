package com.wawacorp.wawagl.core.opengl.view;

import com.wawacorp.wawagl.core.opengl.model.model.Mesh;
import com.wawacorp.wawagl.core.opengl.shader.Shader;

public abstract class GLMesh implements Drawable, Movable {
    protected final String name;
    protected Shader shader;
    protected final int vertexCount;

    public GLMesh(Mesh mesh) {
        this.vertexCount = mesh.getVertices().length;
        this.name = mesh.getName();
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Shader getShader() {
        return shader;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public String getName() {
        return name;
    }

    @Override
    public abstract void draw();

    @Override
    public abstract void draw(Shader shader);
}
