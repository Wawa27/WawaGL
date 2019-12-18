package com.wawacorp.wawagl.core.view.buffer.ubo;

import com.wawacorp.wawagl.core.view.buffer.BufferObject;
import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.view.lighting.LightScene;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindBufferBase;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;

public class CameraUBO extends BufferObject {
    // 4 (Float size in bytes) * 16 (4x4 matrix) * 2 (view and projection)
    private final static int SIZE = 4 * 16 * 2;
    private final static int TARGET = GL_UNIFORM_BUFFER;
    private final Camera camera;
    private final FloatBuffer floatBuffer;

    public CameraUBO(Camera camera) {
        super(glGenBuffers());
        floatBuffer = BufferUtils.createFloatBuffer(16);
        this.camera = camera;

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
        glBufferSubData(TARGET, 0, camera.getViewMatrix().get(floatBuffer));
        glBufferSubData(TARGET, 64, camera.getProjectionMatrix().get(floatBuffer));
        glBindBuffer(TARGET, 0);

        LightScene.ACTIVE.update();
    }

    @Override
    public void dispose() {

    }
}
