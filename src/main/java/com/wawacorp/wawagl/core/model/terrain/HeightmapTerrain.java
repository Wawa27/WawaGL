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

                vertices[vertexOffset] = (-image.getWidth() / 2f) + j; // x value
                vertices[vertexOffset + 1] = height * 1; // y value
                vertices[vertexOffset + 2] = (-image.getHeight() / 2f) + i; // z value

                heights[i][j] = height;

                normals[vertexOffset] = 0;
                normals[vertexOffset + 1] = 1;
                normals[vertexOffset + 2] = 0;

                texCoords[i * image.getWidth() * 2 + j * 2] = j / (float) image.getWidth();
                texCoords[i * image.getWidth() * 2 + j * 2 + 1] = i / (float) image.getHeight();
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

        System.out.println(Arrays.toString(indices));
    }

    public void calculateColor(int vertex1Index, int vertex2Index, int vertex3Index) {
        float heights = (vertices[vertex1Index + 1] + vertices[vertex2Index + 1] + vertices[vertex3Index + 1]) / 3;

        if (heights < 16) {
            colors[vertex1Index] = 0;
            colors[vertex1Index + 1] = 0;
            colors[vertex1Index + 2] = 1;

            colors[vertex2Index] = 0;
            colors[vertex2Index + 1] = 0;
            colors[vertex2Index + 2] = 1;

            colors[vertex3Index] = 0;
            colors[vertex3Index + 1] = 0;
            colors[vertex3Index + 2] = 1;
        }
        if (heights > 16) {
            colors[vertex1Index] = 0;
            colors[vertex1Index + 1] = 1;
            colors[vertex1Index + 2] = 0;

            colors[vertex2Index] = 0;
            colors[vertex2Index + 1] = 1;
            colors[vertex2Index + 2] = 0;

            colors[vertex3Index] = 0;
            colors[vertex3Index + 1] = 1;
            colors[vertex3Index + 2] = 0;
        } else if (heights > 64) {
            colors[vertex1Index] = 1;
            colors[vertex1Index + 1] = 1;
            colors[vertex1Index + 2] = 1;

            colors[vertex2Index] = 1;
            colors[vertex2Index + 1] = 1;
            colors[vertex2Index + 2] = 1;

            colors[vertex3Index] = 1;
            colors[vertex3Index + 1] = 1;
            colors[vertex3Index + 2] = 1;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}