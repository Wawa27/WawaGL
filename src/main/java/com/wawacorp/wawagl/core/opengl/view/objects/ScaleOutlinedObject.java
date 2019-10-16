package com.wawacorp.wawagl.core.opengl.view.objects;

import com.wawacorp.wawagl.core.opengl.view.View;
import com.wawacorp.wawagl.core.opengl.shader.Shader;

/**
 * Object used for drawing an outline of the object.
 */
public class ScaleOutlinedObject extends OutlinedObject {
    private Shader shader;

    public ScaleOutlinedObject(View view) {
        super(view);
        shader = Shader.loadShader("shaders/vert/world_vertex_shader_outline_scale.glsl", "shaders/frag/outline.glsl");
    }

    @Override
    public void drawOutline() {
//        setScale(1.1f, 1.1f, 1.1f);
//        drawable.draw(shader);
//        setScale(1, 1, 1);
    }
}
