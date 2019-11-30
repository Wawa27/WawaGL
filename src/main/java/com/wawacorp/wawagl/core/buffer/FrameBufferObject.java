package com.wawacorp.wawagl.core.buffer;

import com.wawacorp.wawagl.core.view.View;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.buffer.texture.Texture2D;
import com.wawacorp.wawagl.core.utils.FixedSizeArrayList;

import static org.lwjgl.opengl.GL30.*;

public abstract class FrameBufferObject extends BufferObject implements View {
    public final static int MAX_ATTACHMENT_COUNT = 32;
    private final static int TARGET = GL_FRAMEBUFFER;
    /**
     * Holds the index (GL_COLOR_ATTACHMENT0 + i) of unused attachments.
     * The list has a max size of GL_MAX_COLOR_ATTACHMENTS.
     * TODO: FixedSizeHashMap
     */
    protected FixedSizeArrayList<Texture2D> attachment;

    protected RenderBufferObject depth;

    //TODO: add stencil

    public FrameBufferObject() {
        super(glGenFramebuffers());
        attachment = new FixedSizeArrayList<>(MAX_ATTACHMENT_COUNT);
    }

    /**
     * Check if the frame buffer is ready to use
     * @return True if the frame buffer can be used, false otherwise
     */
    public boolean check() {
        return glCheckFramebufferStatus(TARGET) == GL_FRAMEBUFFER_COMPLETE;
    }

    /**
     * Adds a color attachment to the frame buffer
     * @param texture2D An empty texture to write into
     * @return True if the texture was added, false otherwise
     */
    public boolean addTexture(Texture2D texture2D) {
        bind();
        glFramebufferTexture2D(TARGET, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture2D.getHandle(), 0);
        attachment.add(texture2D);
        glDrawBuffer(GL_COLOR_ATTACHMENT0);
        boolean result = check();
        unbind();
        return result;
    }

    /**
     *
     * @param rbo
     * @return
     */
    public boolean addRenderObject(RenderBufferObject rbo) {
        return false;
    }

    public Texture2D getTexture(int location) {
        return attachment.get(location);
    }

    /**
     * Adds a depth buffer to the frame buffer.
     * Only one buffer may be used at the same time.
     * Removes the old depth buffer if there is already one.
     * @return True if the depth buffer was set, false otherwise.
     */
    public boolean addDepthBuffer() {
        this.depth = new RenderBufferObject(GL_DEPTH_COMPONENT);
        bind();
        glFramebufferRenderbuffer(TARGET, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, this.depth.getHandle());
        unbind();
        return check();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public abstract void draw();

    @Override
    public abstract void draw(Shader shader);

    @Override
    public void bind() {
        glBindFramebuffer(TARGET, handle);
    }

    @Override
    public void unbind() {
        glBindFramebuffer(TARGET, 0);
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {
        glDeleteFramebuffers(handle);
    }
}
