package com.wawacorp.wawagl.core.opengl.shader.bo.texture;

import com.wawacorp.wawagl.core.opengl.shader.bo.BufferObject;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public abstract class Texture extends BufferObject {
    private final int TARGET;

    public Texture(int target) {
        super(glGenTextures());

        TARGET = target;
    }

    //TODO: bind calls on an already bound texture should result in a NO OP!!
    @Override
    public void bind() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, handle);
    }

    @Override
    public void unbind() {
        glBindTexture(TARGET, 0);
    }
}
