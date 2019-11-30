package com.wawacorp.wawagl.core.buffer.vbo;

import com.wawacorp.wawagl.core.buffer.BufferObject;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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

    protected void uploadData(float[] data) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length).put(data);
        floatBuffer.flip();
        glBufferData(target, floatBuffer, GL_STATIC_DRAW);
    }

    //todo: buffer should be cached
    protected void uploadData(int[] data) {
        IntBuffer floatBuffer = BufferUtils.createIntBuffer(data.length).put(data);
        floatBuffer.flip();
        glBufferData(target, floatBuffer, GL_STATIC_DRAW);
    }

    @Override
    public void dispose() {
        glDeleteBuffers(handle);
    }
}
