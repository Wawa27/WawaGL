package com.wawacorp.wawagl.core.view.buffer.vbo;

import static org.lwjgl.opengl.GL46.*;

public class IntArrayVBO extends VertexBufferObject {
    private int index;
    private int size;
    private int[] data;
    private boolean normalized;

    /**
     *
     * @param data
     * @param size The number of element for each vertex
     * @param index The location of the VBO
     * @param normalized
     */
    public IntArrayVBO(int[] data, int size, int index, boolean normalized) {
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
        glVertexAttribIPointer(index, size, GL_UNSIGNED_INT, 0, 0);
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
