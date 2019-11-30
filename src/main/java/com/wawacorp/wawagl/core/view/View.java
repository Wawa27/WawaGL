package com.wawacorp.wawagl.core.view;

import com.wawacorp.wawagl.core.shader.Shader;

public interface View {

    /**
     * Draws the object with it's shader
     */
    void draw();

    /**
     * Draws the object with the specified shader
     */
    @Deprecated
    void draw(Shader shader);
}
