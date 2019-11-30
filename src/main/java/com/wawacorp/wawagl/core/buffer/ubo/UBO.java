package com.wawacorp.wawagl.core.buffer.ubo;

import com.wawacorp.wawagl.core.buffer.BufferObject;

import java.lang.reflect.Field;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindBufferBase;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;

/**
 * Uniform Block Object
 */
public class UBO extends BufferObject {
    private static int currentUniformBlockLocation;

    private final static int TARGET = GL_UNIFORM_BUFFER;

    protected final int UNIFORM_BLOCK_LOCATION;

    // TOOD: General UBO class

    static {
        currentUniformBlockLocation = 0;
    }

    public UBO() {
        super(glGenBuffers());
        UNIFORM_BLOCK_LOCATION = currentUniformBlockLocation++;
    }

    @Override
    public void bind() {
        glBindBufferBase(TARGET, UNIFORM_BLOCK_LOCATION, handle);
    }

    @Override
    public void unbind() {
        glBindBufferBase(TARGET, UNIFORM_BLOCK_LOCATION, 0);
    }

    @Override
    public void update() {
        glBindBuffer(TARGET, handle);
        glBindBuffer(TARGET, 0);
    }

    public void update(Field field) {

    }

    @Override
    public void dispose() {

    }
}
