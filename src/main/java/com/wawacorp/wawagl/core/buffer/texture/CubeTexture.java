package com.wawacorp.wawagl.core.buffer.texture;

import com.wawacorp.wawagl.core.model.Bitmap;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * TextureFactory Buffer Object (TextureFactory)
 */
public class CubeTexture extends Texture {
    private final static int TARGET = GL_TEXTURE_CUBE_MAP;

    public CubeTexture(Bitmap[] images) {
        super(TARGET);

        bind();
        uploadData(images);
        generateMipmap();
        setWrappingMode();
        setTextureFiltering();
        unbind();
    }

    private void uploadData(Bitmap[] images) {
        for (int i = 0; i < images.length; i++) {
            if (images[i] != null) {
                glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, images[i].getWidth(), images[i].getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, images[i].getData());
            } else {
                glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, 0, 0, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
            }
        }
    }

    private void setWrappingMode() {
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
    }

    private void setTextureFiltering() {
        glTexParameteri(TARGET, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
        glTexParameteri(TARGET, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    private void generateMipmap() {
        glGenerateMipmap(TARGET);
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }
}
