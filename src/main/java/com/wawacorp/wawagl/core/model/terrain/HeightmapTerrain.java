package com.wawacorp.wawagl.core.model.terrain;

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
        colors = new float[image.getWidth() * image.getHeight() * 3];

        // Calculate positions, normals, and texture coordinates
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int rgb = (buffer.get() & 0xff) + (buffer.get() & 0xff) + (buffer.get() & 0xff);
                buffer.get(); // Skip the alpha value

                int vertexOffset = i * image.getWidth() * 3 + j * 3;
                float height = rgb / 755f;

                setVertex(vertexOffset, (-image.getWidth() / 2f) + j, 0, (-image.getHeight() / 2f) + i);

                heights[i][j] = height;

                setNormal(vertexOffset, 0, 1, 0);

                texCoords[i * image.getWidth() * 2 + j * 2] = j / (float) image.getWidth();
                texCoords[i * image.getWidth() * 2 + j * 2 + 1] = i / (float) image.getHeight();

                setColor(vertexOffset, 1, 0, 0);
            }
        }

        // Calculate the faces (2 triangles)
        for (int i = 0; i < (image.getHeight() - 1); i++) {
            for (int j = 0; j < (image.getWidth() - 1); j++) {
                indices[i * (image.getWidth() - 1) * 6 + j * 6] = i * image.getWidth() + j;
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 1] = (i + 1) * image.getWidth() + j;
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 2] = i * image.getWidth() + (j + 1);

                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 3] = i * image.getWidth() + (j + 1);
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 4] = (i + 1) * image.getWidth() + j;
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 5] = (i + 1) * image.getWidth() + (j + 1);

                calculateColor(
                        i * image.getWidth() * 3 + j * 3,
                        (i + 1) * image.getWidth() * 3 + j * 3,
                        i * image.getWidth() * 3 + (j + 1) * 3
                );
            }
        }
    }

    public void calculateColor(int vertex1Index, int vertex2Index, int vertex3Index) {
        float heights = (vertices[vertex1Index + 1] + vertices[vertex2Index + 1] + vertices[vertex3Index + 1]) / 3;
        setColor(vertex1Index, 1, 0, 0);
        setColor(vertex2Index, 1, 0, 0);
        setColor(vertex3Index, 1, 0, 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}