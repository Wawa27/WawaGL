package com.wawacorp.wawagl.core.buffer;

import com.wawacorp.wawagl.core.game.Game;

import static org.lwjgl.opengl.GL30.*;

public class RenderBufferObject extends BufferObject {
    private final static int TARGET = GL_RENDERBUFFER;

    public RenderBufferObject(int internalFormat) {
        super(glGenRenderbuffers());

        bind();
        glRenderbufferStorage(GL_RENDERBUFFER, internalFormat, Game.width, Game.height);
        unbind();
    }

    @Override
    public void bind() {
        glBindRenderbuffer(TARGET, handle);
    }

    @Override
    public void unbind() {
        glBindRenderbuffer(TARGET, 0);
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {
        glDeleteFramebuffers(handle);
    }
}
