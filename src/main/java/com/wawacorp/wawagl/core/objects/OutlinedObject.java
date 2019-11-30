package com.wawacorp.wawagl.core.objects;

import com.wawacorp.wawagl.core.view.View;
import com.wawacorp.wawagl.core.shader.Shader;

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

    @Deprecated
    @Override
    public void draw(Shader shader) {
        view.draw(shader);
    }
}
