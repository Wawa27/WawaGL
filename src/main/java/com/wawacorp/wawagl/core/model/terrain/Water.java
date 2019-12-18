package com.wawacorp.wawagl.core.model.terrain;

public class Water extends Terrain {
    private final int width;
    private final int depth;

    public Water(int width, int depth) {
        super(width, depth);
        this.width = width;
        this.depth = depth;

        this.name = "Heightmap";

        vertices = new float[depth * width * 3];
        normals = new float[depth * width * 3];
        indices = new int[depth * width * 6];

        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < width; j++) {
                int vertexOffset = i * width * 3 + j * 3;
                float height = 5;

                vertices[vertexOffset] = -width / 2f + j;
                vertices[vertexOffset + 1] = 0;
                vertices[vertexOffset + 2] = -depth / 2f + i;

                heights[i][j] = height;

                normals[vertexOffset] = 0;
                normals[vertexOffset + 1] = 1;
                normals[vertexOffset + 2] = 0;
            }
        }

        for (int i = 0; i < depth - 1; i++) {
            for (int j = 0; j < width - 1; j++) {
                indices[i * width * 6 + j * 6] = i * width + j;
                indices[i * width * 6 + j * 6 + 1] = (i + 1) * width + j;
                indices[i * width * 6 + j * 6 + 2] = i * width + (j + 1);

                indices[i * width * 6 + j * 6 + 3] = i * width + (j + 1);
                indices[i * width * 6 + j * 6 + 4] = (i + 1) * width + j;
                indices[i * width * 6 + j * 6 + 5] = (i + 1) * width + (j + 1);
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
