package com.wawacorp.wawagl.core.opengl.shader.bo.texture;

import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Image2D;

import static org.lwjgl.opengl.GL46.*;

/**
 * TextureFactory Buffer Object (TextureFactory)
 */
public class TextureArray extends Texture {
    private final static int TARGET = GL_TEXTURE_2D_ARRAY;
    private final int imageCount;

    public TextureArray(Image2D[] images) {
        super(TARGET);
        imageCount = images.length;

        bind();
        uploadData(images);
        setWrappingMode();
        setTextureFiltering();
        unbind();
    }

    private void uploadData(Image2D[] images) {
        Image2D image2D = images[0];
        if (image2D == null) {
            System.err.println("null image");
            return;
        }
        glTexStorage3D(TARGET, 1, GL_RGBA8, image2D.getWidth(), image2D.getHeight(), imageCount);
        for (int i = 0; i < images.length; i++) {
            glTexSubImage3D(TARGET, 0, 0, 0, i, image2D.getWidth(), image2D.getHeight(), 1, GL_RGBA, GL_UNSIGNED_BYTE, images[i].getData());
        }
    }

    private void setWrappingMode() {
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    }

    private void setTextureFiltering() {
        glTexParameteri(TARGET, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(TARGET, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    public int getImageCount() {
        return imageCount;
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }
}
