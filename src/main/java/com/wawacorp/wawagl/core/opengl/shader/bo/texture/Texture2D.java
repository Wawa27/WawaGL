package com.wawacorp.wawagl.core.opengl.shader.bo.texture;

import com.wawacorp.wawagl.core.opengl.manager.AssetManager;
import com.wawacorp.wawagl.core.opengl.shader.ShaderException;
import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Image2D;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * TextureFactory Buffer Object (TextureFactory)
 */
public class Texture2D extends Texture {
    public final static Texture2D DEFAULT = AssetManager.getTexture2D("textures/colors/white.png");

    private final static int TARGET = GL_TEXTURE_2D;
    private static Texture2D lastTexture;

    /**
     * Creates a texture and fill it with the data provided
     * @param image2D The data
     * @throws ShaderException
     */
    public Texture2D(Image2D image2D) {
        super(TARGET);

        bind();
        uploadData(image2D);
        generateMipmap();
        setWrappingMode();
        setTextureFiltering();
        unbind();
    }

    /**
     * Creates an empty texture
     * @param w
     * @param h
     */
    public Texture2D(int w, int h) {
        super(TARGET);

        bind();
        uploadData(w, h, null);
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

    @Override
    public void update() {

    }

    @Override
    public void bind() {
        if (lastTexture == this) {
            System.out.println("texture saved");
            return;
        }
        super.bind();
        lastTexture = this;
    }

    @Override
    public void unbind() {
        super.unbind();
        lastTexture = null;
    }

    @Override
    public void dispose() {

    }
}
