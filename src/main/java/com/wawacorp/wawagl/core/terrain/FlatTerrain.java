package com.wawacorp.wawagl.core.terrain;

import org.joml.SimplexNoise;

public class FlatTerrain extends Terrain {
    private final int width;
    private final int depth;

    public FlatTerrain(int width, int depth) {
        super(width, depth);
        this.width = width;
        this.depth = depth;

        this.name = "Flat Terrain";

        vertices = new float[depth * width * 3];
        texCoords = new float[depth * width * 2]; // TODO: check if needed
        normals = new float[depth * width * 3];
        indices = new int[depth * width * 6];

        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < width; j++) {
                int vertexOffset = i * width * 3 + j * 3;
                float height = 0;

                vertices[vertexOffset] = j;
                vertices[vertexOffset + 1] = height;
                vertices[vertexOffset + 2] = i;

                heights[i][j] = height;

                normals[vertexOffset] = 0;
                normals[vertexOffset + 1] = 1;
                normals[vertexOffset + 2] = 0;

                texCoords[i * width * 2 + j * 2] = j / (float) width;
                texCoords[i * width * 2 + j * 2 + 1] = i / (float) depth;
            }
        }

        for (int i = 0; i < depth - 1; i++) {
            for (int j = 0; j < width - 1; j++) {
                indices[i * width * 6 + j * 6] = i * width + j;
                indices[i * width * 6 + j * 6 + 1] = (i + 1) * width + j;
                indices[i * width * 6 + j * 6 + 2] = i * width + (j + 1);

                indices[i * width * 6 + j * 6 + 3] = i * width + (j + 1);
                indices[i * width * 6 + j * 6 + 4] = (i + 1) * width + j;
                indices[i * width * 6 + j * 6 + 5] =(i + 1) * width + (j + 1);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public void update() {
        super.update();
    }
}
