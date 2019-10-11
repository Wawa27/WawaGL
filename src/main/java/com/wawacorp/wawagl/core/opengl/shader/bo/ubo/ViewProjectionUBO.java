package com.wawacorp.wawagl.core.opengl.shader.bo.ubo;

import com.wawacorp.wawagl.core.opengl.shader.bo.BufferObject;
import com.wawacorp.wawagl.core.opengl.camera.Camera;
import com.wawacorp.wawagl.core.opengl.Environnement;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindBufferBase;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;

public class ViewProjectionUBO extends BufferObject {
    // 4 (Float size in bytes) * 16 (4x4 matrix) * 2 (view and projection)
    private final static int SIZE = 4 * 16 * 2;
    private final static int TARGET = GL_UNIFORM_BUFFER;
    private final Camera CAMEREA;

    public ViewProjectionUBO(Camera camera) {
        super(glGenBuffers());
        CAMEREA = camera;

        glBindBuffer(TARGET, handle);
        glBufferData(TARGET, SIZE, GL_STREAM_DRAW);
        glBindBuffer(TARGET, 0);

        update();
    }

    @Override
    public void bind() {
        glBindBufferBase(TARGET, 0, handle);
    }

    @Override
    public void unbind() {
        glBindBufferBase(TARGET, 0, 0);
    }

    public void update() {
        glBindBuffer(TARGET, handle);
        //TODO: Single cached float buffer
        glBufferSubData(TARGET, 0, CAMEREA.getMatrix().get(new float[16]));
        glBufferSubData(TARGET, 64, CAMEREA.getProjectionMatrix());
        glBindBuffer(TARGET, 0);

        Environnement.getLightScene().update();
    }

    @Override
    public void dispose() {

    }
}
