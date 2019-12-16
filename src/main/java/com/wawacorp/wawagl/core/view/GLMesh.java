package com.wawacorp.wawagl.core.view;

import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.instance.Instance;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public abstract class GLMesh implements View, Observer {
    protected final Mesh mesh;
    protected final String name;
    protected Shader shader;
    protected final int vertexCount;
    protected final int indexCount;

    public GLMesh(Mesh mesh) {
        this.mesh = mesh;
        this.vertexCount = mesh.getVertices().length;
        this.indexCount = mesh.getIndices().length;
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

    @Deprecated
    @Override
    public abstract void draw(Shader shader);
}
