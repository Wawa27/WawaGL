package com.wawacorp.wawagl.core.view.buffer.vbo;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class FloatArrayVBO extends VertexBufferObject {
    private int index;
    private int size;
    private float[] data;
    private boolean normalized;

    /**
     *
     * @param data
     * @param size The number of element for each vertex
     * @param index The location of the VBO
     * @param normalized
     */
    public FloatArrayVBO(float[] data, int size, int index, boolean normalized) {
        super(GL_ARRAY_BUFFER, glGenBuffers());
        this.data = data;
        this.index = index;
        this.size = size;
        this.normalized = normalized;
    }

    @Override
    public void init() {
        bind();
        uploadData(data);
        glVertexAttribPointer(index, size, GL_FLOAT, normalized, 0, 0);
        glEnableVertexAttribArray(index);
        unbind();
    }

    @Override
    public void update() {
        bind();
        updateData(data);
        unbind();
    }
}
