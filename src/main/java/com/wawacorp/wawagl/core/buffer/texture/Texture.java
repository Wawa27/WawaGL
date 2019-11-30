package com.wawacorp.wawagl.core.buffer.texture;

import com.wawacorp.wawagl.core.buffer.BufferObject;

import static org.lwjgl.opengl.GL11.*;

public abstract class Texture extends BufferObject {
    private final int TARGET;

    public Texture(int target) {
        super(glGenTextures());

        TARGET = target;
    }

    //TODO: bind calls on an already bound texture should result in a NO OP!!
    @Override
    public void bind() {
        glBindTexture(TARGET, handle);
    }

    @Override
    public void unbind() {
        glBindTexture(TARGET, 0);
    }
}
