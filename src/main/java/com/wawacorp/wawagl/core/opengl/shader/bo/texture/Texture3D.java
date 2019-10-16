package com.wawacorp.wawagl.core.opengl.shader.bo.texture;

import com.wawacorp.wawagl.core.opengl.shader.ShaderException;
import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Image2D;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * TextureFactory Buffer Object (TextureFactory)
 */
public class Texture3D extends Texture {
    private final static int TARGET = GL_TEXTURE_CUBE_MAP;

    public Texture3D(Image2D[] images) {
        super(TARGET);

        bind();
        uploadData(images);
        generateMipmap();
        setWrappingMode();
        setTextureFiltering();
        unbind();
    }

    private void uploadData(Image2D[] images) {
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
