package com.wawacorp.wawagl.core.buffer;

import static org.lwjgl.opengl.GL15.*;

public class IndexBufferObject extends BufferObject {
    private final static int TARGET = GL_ELEMENT_ARRAY_BUFFER;

    public IndexBufferObject(int[] indices) {
        super(glGenBuffers());

        bind();
        setIndices(indices);
        unbind();
    }

    @Override
    public void bind() {
        glBindBuffer(TARGET, handle);
    }

    @Override
    public void unbind() {
        glBindBuffer(TARGET, 0);
    }

    private void setIndices(int[] indices) {
        glBufferData(TARGET, indices, GL_STATIC_DRAW);
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }
}
