package com.wawacorp.wawagl.core.opengl.shader.bo.texture;

import com.wawacorp.wawagl.core.opengl.shader.ShaderException;
import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Image2D;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

@Deprecated
public class TextureAtlas extends Texture {
    private final static int TARGET = GL_TEXTURE_2D;
    private final int width;
    private final int height;
    private final int cellCount;

    public TextureAtlas(Image2D image2D, int cellCount) {
        super(TARGET);
        this.width = image2D.getWidth();
        this.height = image2D.getHeight();
        this.cellCount = cellCount;

        bind();
        uploadData(image2D);
        generateMipmap();
        setWrappingMode();
        setTextureFiltering();
        unbind();
    }

    private void uploadData(Image2D image) {
        uploadData(image.getWidth(), image.getHeight(), image.getData());
    }

    private void uploadData(int w, int h, ByteBuffer data) {
        glTexImage2D(TARGET, 0, GL_RGBA, w, h, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
    }

    private void setWrappingMode() {
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_S, GL_CLAMP);
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_T, GL_CLAMP);
    }

    private void setTextureFiltering() {
        glTexParameteri(TARGET, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
        glTexParameteri(TARGET, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    private void generateMipmap() {
        glGenerateMipmap(TARGET);
    }

    public int getCellCount() {
        return cellCount;
    }

    @Override
    public void bind() {
        super.bind();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void unbind() {
        super.unbind();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void update() {

    }
}
