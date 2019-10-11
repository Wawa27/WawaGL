package com.wawacorp.wawagl.core.opengl.shader.bo.vbo;

import static org.lwjgl.opengl.GL33.*;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class InstancedVBO extends VertexBufferObject {
    private int index;
    private int size;
    private float[] data;
    private final int vertexPerInstance;

    /**
     *
     * @param data
     * @param size
     * @param index
     * @param vertexPerInstance The number of vertex per instance, 0 means the whole vertex array is for a single instance
     */
    public InstancedVBO(float[] data, int size, int index, int vertexPerInstance)  {
        super(GL_ARRAY_BUFFER, glGenBuffers());
        this.data = data;
        this.index = index;
        this.size = size;
        this.vertexPerInstance = vertexPerInstance;
    }

    @Override
    public void init() {
        bind();
        uploadData(data);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glVertexAttribDivisor(index, vertexPerInstance);
        glEnableVertexAttribArray(index);
        unbind();
    }

    @Override
    public void update() {

    }
}
