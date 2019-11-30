package com.wawacorp.wawagl.core.model;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class SpriteSheet2D extends Bitmap {
    private final int columnCount;
    private final int rowCount;

    public SpriteSheet2D(int width, int height, ByteBuffer data, int columnCount, int rowCount) {
        super(width, height, data);
        this.columnCount = columnCount;
        this.rowCount = rowCount;
    }

    public static SpriteSheet2D load(@Nonnull String relativePath, int columnCount, int rowCount) {
        if (relativePath == null) return null;
        InputStream stream = SpriteSheet2D.class.getClassLoader().getResourceAsStream(relativePath);
        String[] ext = relativePath.split("\\.");
        switch (ext[ext.length - 1]) {
            case "png": {
                return loadPNG(stream, columnCount, rowCount);
            }
            case "tga" :
            case "jpeg" :
            case "jpg" : {
                return loadJPEG(stream, columnCount, rowCount);
            }
        }
        return null;
    }

    //TODO: 32 bit depth only, should support 16bits too
    private static SpriteSheet2D loadPNG(InputStream stream, int columnCount, int rowCount) {
        PNGDecoder decoder;

        try {
            decoder = new PNGDecoder(stream);
            ByteBuffer buf = BufferUtils.createByteBuffer(Float.BYTES * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * Float.BYTES, PNGDecoder.Format.RGBA);
            buf.flip();

            return new SpriteSheet2D(decoder.getWidth(), decoder.getHeight(), buf, columnCount, rowCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SpriteSheet2D loadJPEG(InputStream stream, int rowCount, int columnCount) {
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
            return new SpriteSheet2D(img.getWidth(), img.getHeight(), byteBuffer, columnCount, rowCount);
        } catch (IOException e) {
            System.err.println("Error while trying to import a JPEG Texture2D : " + stream);
        }
        return null;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }
}
