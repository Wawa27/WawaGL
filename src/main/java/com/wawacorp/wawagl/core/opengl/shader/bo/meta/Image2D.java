package com.wawacorp.wawagl.core.opengl.shader.bo.meta;

import de.matthiasmann.twl.utils.PNGDecoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Image2D {
    private int width;
    private int height;
    private ByteBuffer data;

    public Image2D(int width, int height, ByteBuffer data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public static Image2D load(String relativePath) {
        if (relativePath == null) return null;
        InputStream stream = Image2D.class.getClassLoader().getResourceAsStream(relativePath);
        String[] ext = relativePath.split("\\.");
        switch (ext[ext.length - 1]) {
            case "png": {
                return loadPNG(stream);
            }

            case "tga" :
            case "jpeg" :
            case "jpg" : {
                return loadJPEG(stream);
            }
        }

        return null;
    }

    //TODO: 32 bit depth only, should support 16bits too
    private static Image2D loadPNG(InputStream stream) {
        PNGDecoder decoder;
        try {
            decoder = new PNGDecoder(stream);
            ByteBuffer buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();

            return new Image2D(decoder.getWidth(), decoder.getHeight(), buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Image2D loadJPEG(InputStream stream) {
        try {
            BufferedImage img = ImageIO.read(stream);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(img.getWidth() * img.getHeight() * 4);
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    int argb = img.getRGB(j, i);
                    Color color = new Color(argb, true);

                    byteBuffer.put((byte) color.getRed()).put((byte) color.getGreen()).put((byte) color.getBlue()).put((byte) color.getAlpha());
                }
            }
            byteBuffer.flip();
            return new Image2D(img.getWidth(), img.getHeight(), byteBuffer);
        } catch (IOException e) {
            System.err.println("Error while trying to import a JPEG Texture2D : " + stream);
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ByteBuffer getData() {
        return data;
    }
}
