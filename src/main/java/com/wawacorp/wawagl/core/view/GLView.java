package com.wawacorp.wawagl.core.view;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.buffer.VertexArrayObject;
import com.wawacorp.wawagl.core.view.instance.Instance;

import java.util.Observer;

public abstract class GLView implements View, Observer {
    protected String name;
    protected VertexArrayObject vertexArrayObject;
    protected int vertexCount;
    protected Shader shader;
    protected Instance instance;

    public GLView(String name, VertexArrayObject vertexArrayObject, int vertexCount) {
        this.vertexArrayObject = vertexArrayObject;
        this.name = name;
        this.vertexCount = vertexCount;
    }

    public GLView() {
    }

    public VertexArrayObject getVertexArrayObject() {
        return vertexArrayObject;
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

    public Instance getInstance() {
        return instance;
    }

    @Override
    public abstract void draw();

    @Deprecated
    @Override
    public abstract void draw(Shader shader);
}
