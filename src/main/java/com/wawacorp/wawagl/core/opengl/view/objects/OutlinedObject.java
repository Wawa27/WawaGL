package com.wawacorp.wawagl.core.opengl.view.objects;

import com.wawacorp.wawagl.core.opengl.view.View;
import com.wawacorp.wawagl.core.opengl.shader.Shader;

public abstract class OutlinedObject implements View {
    protected final View view;

    public OutlinedObject(View view) {
        this.view = view;
    }

    public abstract void drawOutline();

    @Override
    public void draw() {
        view.draw();
    }

    @Override
    public void draw(Shader shader) {
        view.draw(shader);
    }
}
