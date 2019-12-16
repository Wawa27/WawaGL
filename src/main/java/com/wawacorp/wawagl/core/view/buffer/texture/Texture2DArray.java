package com.wawacorp.wawagl.core.view.buffer.texture;

import com.wawacorp.wawagl.core.model.Bitmap;
import com.wawacorp.wawagl.core.model.SpriteSheet2D;

import static org.lwjgl.opengl.GL46.*;

/**
 * TextureFactory Buffer Object (TextureFactory)
 */
public class Texture2DArray extends Texture {
    private final static int TARGET = GL_TEXTURE_2D_ARRAY;
    private final int imageCount;

    public Texture2DArray(SpriteSheet2D image) {
        super(TARGET);
        this.imageCount = image.getColumnCount() * image.getRowCount();
        bind();
        uploadData(image,0, image.getWidth() / image.getColumnCount(),image.getHeight() / image.getRowCount(), image.getColumnCount(), image.getRowCount());
        setWrappingMode();
        setTextureFiltering();
        unbind();
    }

    public Texture2DArray(SpriteSheet2D[] images) {
        super(TARGET);
        int imageCount = 0;
        int maxWidth = 0, maxHeight = 0;
        for (int i = 0; i < images.length; i++) {
            if (images[i].getWidth() / images[i].getColumnCount() > maxWidth) maxWidth = images[i].getWidth() / images[i].getColumnCount();
            if (images[i].getHeight() / images[i].getRowCount() > maxHeight) maxHeight = images[i].getHeight() / images[i].getRowCount();
            imageCount += images[i].getColumnCount() * images[i].getRowCount();
        }
        this.imageCount = imageCount;

        int zoffset = 0;

        bind();
        for (int i = 0; i < images.length; i++) {
            uploadData(images[i], zoffset, maxWidth, maxHeight, images[i].getColumnCount(), images[i].getRowCount());
            zoffset += images[i].getColumnCount() * images[i].getRowCount();
        }
        setWrappingMode();
        setTextureFiltering();
        unbind();
    }

    public Texture2DArray(Bitmap[] images) {
        super(TARGET);
        imageCount = images.length;
        bind();
        uploadData(images);
        setWrappingMode();
        setTextureFiltering();
        unbind();
    }

    private void uploadData(Bitmap[] images) {
        int maxWidth = 0, maxHeight = 0;
        for (int i = 0; i < images.length; i++) {
            Bitmap image = images[i];
            if (image.getWidth() > maxWidth) maxWidth = image.getWidth();
            if (image.getHeight() > maxHeight) maxHeight = image.getHeight();
        }
        glTexStorage3D(TARGET, 1, GL_RGBA8, maxWidth, maxHeight, imageCount);
        for (int i = 0; i < images.length; i++) {
            glTexSubImage3D(TARGET, 0, 0, 0, i, maxWidth, maxHeight, 1, GL_RGBA, GL_UNSIGNED_BYTE, images[i].getData());
        }
    }

    private void uploadData(Bitmap image, int zoffset, int width, int height, int column, int row) {
        glTexStorage3D(TARGET, 1, GL_RGBA8, width, height, imageCount);
        glPixelStorei(GL_UNPACK_ROW_LENGTH, width * column);
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < column; x++) {
                System.out.println(zoffset + y * column + x);
                glTexSubImage3D(TARGET, 0, 0, 0, zoffset + y * column + x, width, height, 1, GL_RGBA, GL_UNSIGNED_BYTE, image.getData().position(y * x * width * 4 + x * width * 4));
            }
        }
        glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);
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
