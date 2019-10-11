package com.wawacorp.wawagl.core.opengl.view;

import com.wawacorp.wawagl.core.opengl.shader.Shader;

public interface Drawable {

    /**
     * Draws the object with it's shader
     */
    void draw();

    /**
     * Draws the object with the specified shader
     */
    void draw(Shader shader);
}
