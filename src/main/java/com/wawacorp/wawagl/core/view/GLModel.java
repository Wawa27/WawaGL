package com.wawacorp.wawagl.core.view;

import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.shader.Shader;

public abstract class GLModel implements View, Movable {
    protected final Model model;

    public GLModel(Model model) {
        this.model = model;
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
