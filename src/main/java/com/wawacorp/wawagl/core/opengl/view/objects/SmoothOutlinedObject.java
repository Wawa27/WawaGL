package com.wawacorp.wawagl.core.opengl.view.objects;

import com.wawacorp.wawagl.core.opengl.view.View;
import com.wawacorp.wawagl.core.opengl.shader.Shader;

/**
 * Object used for drawing an outline of the object using it's normals.
 * It's normals MUST be smoothed to have a clean outline otherwise use : {@link ScaleOutlinedObject}
 */
public class SmoothOutlinedObject extends OutlinedObject {
    private Shader shader;

    public SmoothOutlinedObject(View view) {
        super(view);
        shader = Shader.loadShader("shaders/vert/world_vertex_shader_outline_smooth.glsl", "shaders/frag/outline.glsl");
    }

    public void draw() {
        view.draw();
    }

    public void draw(Shader shader) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drawOutline() {
        view.draw(shader);
    }
}
