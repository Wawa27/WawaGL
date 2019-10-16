package com.wawacorp.wawagl.core.opengl.view;

import com.wawacorp.wawagl.core.opengl.shader.Shader;

public abstract class GLModel implements View, Movable {

    public GLModel() {

    }

    @Override
    public abstract void draw();

    @Override
    public abstract void draw(Shader shader);

    @Override
    public abstract void translate(float x, float y, float z);

    @Override
    public abstract void scale(float x, float y, float z);

    @Override
    public abstract void rotate(float x, float y, float z);

    @Override
    public abstract void setTranslation(float x, float y, float z);

    @Override
    public abstract void setScale(float x, float y, float z);

    @Override
    public abstract void setRotation(float x, float y, float z);
}
