package com.wawacorp.wawagl.core.view.buffer.texture;

import com.wawacorp.wawagl.core.model.Bitmap;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

@Deprecated
public class TextureAtlas extends Texture {
    private final static int TARGET = GL_TEXTURE_2D;
    private final int width;
    private final int height;
    private final int cellCount;

    public TextureAtlas(Bitmap bitmap, int cellCount) {
        super(TARGET);
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.cellCount = cellCount;

        bind();
        uploadData(bitmap);
        generateMipmap();
        setWrappingMode();
        setTextureFiltering();
        unbind();
    }

    private void uploadData(Bitmap image) {
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
