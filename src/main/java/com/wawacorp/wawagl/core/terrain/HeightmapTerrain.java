package com.wawacorp.wawagl.core.terrain;

import com.wawacorp.wawagl.core.model.Bitmap;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class HeightmapTerrain extends Terrain {
    private final int width;
    private final int height;

    public HeightmapTerrain(Bitmap image) {
        super(image.getWidth(), image.getHeight());
        ByteBuffer buffer = image.getData();

        this.width = image.getWidth();
        this.height = image.getHeight();
        this.name = "Heightmap";

        vertices = new float[image.getWidth() * image.getHeight() * 3];
        texCoords = new float[image.getWidth() * image.getHeight() * 2];
        normals = new float[image.getWidth() * image.getHeight() * 3];
        indices = new int[(image.getWidth() - 1) * (image.getHeight() - 1) * 6];

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int rgb = (buffer.get() & 0xff) + (buffer.get() & 0xff) + (buffer.get() & 0xff);
                buffer.get(); // Skip the alpha value

                int vertexOffset = i * image.getWidth() * 3 + j * 3;
                float height = rgb/755f * 16;

                vertices[vertexOffset] = j;
                vertices[vertexOffset + 1] = height;
                vertices[vertexOffset + 2] = i;

                heights[i][j] = height;

                normals[vertexOffset] = 0;
                normals[vertexOffset + 1] = 1;
                normals[vertexOffset + 2] = 0;

                texCoords[i * image.getWidth() * 2 + j * 2] = j / (float) image.getWidth();
                texCoords[i * image.getWidth() * 2 + j * 2 + 1] = i / (float) image.getHeight();
            }
        }

        for (int i = 0; i < (image.getHeight() - 1); i++) {
            for (int j = 0; j < (image.getWidth() - 1); j++) {
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 0] = i * image.getWidth() + j;
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 1] = (i + 1) * image.getWidth() + j;
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 2] = i * image.getWidth() + (j + 1);

                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 3] = i * image.getWidth() + (j + 1);
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 4] = (i + 1) * image.getWidth() + j;
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 5] =(i + 1) * image.getWidth() + (j + 1);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}