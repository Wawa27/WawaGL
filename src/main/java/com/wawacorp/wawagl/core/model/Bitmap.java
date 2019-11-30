package com.wawacorp.wawagl.core.model;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Bitmap {
    public final static Bitmap RED = new Bitmap(1, 1, BufferUtils.createByteBuffer(4).put(new byte[] {(byte) 255, 0, 0, (byte) 255}).flip());
    public final static Bitmap GREEN = new Bitmap(1, 1, BufferUtils.createByteBuffer(4).put(new byte[] {0, (byte) 255, 0, (byte) 255}).flip());
    public final static Bitmap BLUE = new Bitmap(1, 1, BufferUtils.createByteBuffer(4).put(new byte[] {0, 0, (byte) 255, (byte) 255}).flip());
    public final static Bitmap BLACK = new Bitmap(1, 1, BufferUtils.createByteBuffer(4).put(new byte[] {0, 0, 0, (byte) 255}).flip());
    public final static Bitmap TRANSPARENT = new Bitmap(1, 1, BufferUtils.createByteBuffer(4).put(new byte[] {0, 0, 0, 0}).flip());

    private int width;
    private int height;
    private ByteBuffer data;

    public Bitmap(int width, int height, ByteBuffer data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public static Bitmap load(String relativePath) {
        if (relativePath == null) return null;
        InputStream stream = Bitmap.class.getClassLoader().getResourceAsStream(relativePath);
        String[] ext = relativePath.split("\\.");
        return loadJPEG(stream);
    }

    //TODO: 32 bit depth only, should support 16bits too
    private static Bitmap loadPNG(InputStream stream) {
        PNGDecoder decoder;

        try {
            decoder = new PNGDecoder(stream);
            ByteBuffer buf = BufferUtils.createByteBuffer(Float.BYTES * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * Float.BYTES, PNGDecoder.Format.RGBA);
            buf.flip();

            return new Bitmap(decoder.getWidth(), decoder.getHeight(), buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bitmap loadJPEG(InputStream stream) {
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
            return new Bitmap(img.getWidth(), img.getHeight(), byteBuffer);
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
