package com.wawacorp.wawagl.core.view.buffer.vbo;

import com.wawacorp.wawagl.core.view.buffer.BufferObject;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL15.*;

public abstract class VertexBufferObject extends BufferObject {
    private final int target;

    public VertexBufferObject(int target, int handle) {
        super(handle);
        this.target = target;
    }

    public abstract void init();

    @Override
    public void bind() {
        glBindBuffer(target, handle);
    }

    @Override
    public void unbind() {
        glBindBuffer(target, 0);
    }

    /**
     * Creates a new buffer in the GPU and uploads the data to its location
     * @param data
     */
    protected void uploadData(float[] data) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length).put(data);
        floatBuffer.flip();
        glBufferData(target, floatBuffer, GL_STREAM_DRAW);
    }

    /**
     * Updates the buffer with the given data
     * @param data
     */
    protected void updateData(float[] data) {
        glBufferSubData(target, 0, data);
    }

    /**
     * Creates a new buffer in the GPU and uploads the data to its location
     * @param data
     */
    protected void uploadData(int[] data) {
        IntBuffer floatBuffer = BufferUtils.createIntBuffer(data.length).put(data);
        floatBuffer.flip();
        glBufferData(target, floatBuffer, GL_STATIC_DRAW);
    }

    /**
     * Updates the buffer with the given data
     * @param data
     */
    protected void updateData(int[] data) {
        glBufferSubData(target, 0, data);
    }

    @Override
    public void dispose() {
        glDeleteBuffers(handle);
    }
}
