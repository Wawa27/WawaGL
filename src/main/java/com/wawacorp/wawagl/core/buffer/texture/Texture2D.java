package com.wawacorp.wawagl.core.buffer.texture;

import com.wawacorp.wawagl.core.manager.AssetManager;
import com.wawacorp.wawagl.core.shader.ShaderException;
import com.wawacorp.wawagl.core.model.Bitmap;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * TextureFactory Buffer Object (TextureFactory)
 */
public class Texture2D extends Texture {
    public final static Texture2D DEFAULT = AssetManager.getTexture2D("textures/default_texture.png");

    private final static int TARGET = GL_TEXTURE_2D;
    private static Texture2D lastTexture;

    private final int width;
    private final int height;

    /**
     * Creates a texture and fill it with the data provided
     * @param bitmap The data
     * @throws ShaderException
     */
    public Texture2D(Bitmap bitmap) {
        super(TARGET);

        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();

        bind();
        uploadData(bitmap);
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

        this.width = w;
        this.height = h;

        bind();
        uploadData(w, h, null);
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
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_T, GL_REPEAT);
    }

    private void setTextureFiltering() {
        glTexParameteri(TARGET, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(TARGET, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    private void generateMipmap() {
        glGenerateMipmap(TARGET);
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public void unbind() {
        super.unbind();
        lastTexture = null;
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }
}
