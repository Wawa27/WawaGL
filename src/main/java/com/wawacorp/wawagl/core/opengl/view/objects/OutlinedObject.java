package com.wawacorp.wawagl.core.opengl.view.objects;

import com.wawacorp.wawagl.core.opengl.view.Drawable;
import com.wawacorp.wawagl.core.opengl.shader.Shader;

public abstract class OutlinedObject implements Drawable {
    protected final Drawable drawable;

    public OutlinedObject(Drawable drawable) {
        this.drawable = drawable;
    }

    public abstract void drawOutline();

    @Override
    public void draw() {
        drawable.draw();
    }

    @Override
    public void draw(Shader shader) {
        drawable.draw(shader);
    }
}
