package com.wawacorp.wawagl.core.opengl.shader.bo;

public abstract class BufferObject {
    protected final int handle;

    public BufferObject(int handle) {
        this.handle = handle;
    }

    /**
     * Index of the buffer object
     * @return The index of the buffer, should be higher or equals to 0
     */
    public int getHandle() {
        return handle;
    }

    /**
     * Binds the buffer
     * //TODO: NOOP if already bound
     */
    public abstract void bind();

    /**
     * Unbinds the buffer
     */
    public abstract void unbind();

    /**
     * Updates the buffer object
     */
    public abstract void update();

    /**
     * Dispose the buffer object (on the gpu)
     */
    public abstract void dispose();
}
