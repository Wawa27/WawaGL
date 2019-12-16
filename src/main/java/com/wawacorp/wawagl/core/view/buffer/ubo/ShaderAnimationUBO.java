package com.wawacorp.wawagl.core.view.buffer.ubo;

import com.wawacorp.wawagl.core.view.Environnement;
import com.wawacorp.wawagl.core.view.buffer.BufferObject;
import com.wawacorp.wawagl.core.game.Game;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindBufferBase;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;

public class ShaderAnimationUBO extends BufferObject {
    private final static int SIZE = 4;
    private final static int TARGET = GL_UNIFORM_BUFFER;
    private final static long[] time = new long[1];

    public ShaderAnimationUBO() {
        super(glGenBuffers());

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
        time[0] = Game.getTime();

        glBindBuffer(TARGET, handle);
        glBufferSubData(TARGET, 0, time);
        glBindBuffer(TARGET, 0);

        Environnement.getLightScene().update();
    }

    @Override
    public void dispose() {

    }
}
