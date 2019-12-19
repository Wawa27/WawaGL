package com.wawacorp.wawagl.demo.wolf;

import com.wawacorp.wawagl.core.model.terrain.Terrain;
import org.joml.SimplexNoise;
import org.joml.Vector3f;

public class LowPolyHeightmapTerrain extends Terrain {
    private final int width;
    private final int height;

    public LowPolyHeightmapTerrain(int depth, int width) {
        super(width, depth);

        this.width = width;
        this.height = depth;
        this.name = "Heightmap";

        vertices = new float[(width - 1) * (depth - 1) * 18];
        normals = new float[(width - 1) * (depth - 1) * 18];
        colors = new float[(width - 1) * (depth - 1) * 18];
        float height;
        Vector3f n1 = new Vector3f(), n2 = new Vector3f();
        Vector3f normal = new Vector3f();
        float maxHeight = 8;

        // Calculate the faces (2 triangles)
        for (int i = 0; i < (depth - 1); i++) {
            for (int j = 0; j < (width - 1); j++) {
                // FIRST TRIANGLE
                // P1
                vertices[i * (width - 1) * 18 + j * 18] = -width / 2f + j;
                vertices[i * (width - 1) * 18 + j * 18 + 1] = getElevation(i, j, maxHeight);
                vertices[i * (width - 1) * 18 + j * 18 + 2] = -depth / 2f + i;

                // P2
                vertices[i * (width - 1) * 18 + j * 18 + 3] = -width / 2f + j;
                vertices[i * (width - 1) * 18 + j * 18 + 4] = getElevation(i + 1, j, maxHeight);
                vertices[i * (width - 1) * 18 + j * 18 + 5] = -depth / 2f + i + 1;

                // P3
                vertices[i * (width - 1) * 18 + j * 18 + 6] = -width / 2f + j + 1;
                vertices[i * (width - 1) * 18 + j * 18 + 7] = getElevation(i, j + 1, maxHeight);
                vertices[i * (width - 1) * 18 + j * 18 + 8] = -depth / 2f + i;

                n1.set(-width / 2f + j, getElevation(i, j, maxHeight), -depth / 2f + i); // P1
                n1.sub(-width / 2f + j + 1, getElevation(i, j + 1, maxHeight), -depth / 2f + i, n2).negate(); // -(P1 - P3) = -(P31) = (P13)
                n1.sub(-width / 2f + j, getElevation(i + 1, j, maxHeight), -depth / 2f + i + 1).negate(); // -(P1 - P2) = -(P21) = (P12)
                n2.cross(n1, normal); // (N) = (P12) x (P13)
                normal.negate().normalize();

                normals[i * (width - 1) * 18 + j * 18] = normal.x;
                normals[i * (width - 1) * 18 + j * 18 + 1] = normal.y;
                normals[i * (width - 1) * 18 + j * 18 + 2] = normal.z;

                normals[i * (width - 1) * 18 + j * 18 + 3] = normal.x;
                normals[i * (width - 1) * 18 + j * 18 + 4] = normal.y;
                normals[i * (width - 1) * 18 + j * 18 + 5] = normal.z;

                normals[i * (width - 1) * 18 + j * 18 + 6] = normal.x;
                normals[i * (width - 1) * 18 + j * 18 + 7] = normal.y;
                normals[i * (width - 1) * 18 + j * 18 + 8] = normal.z;

                // SECOND TRIANGLE
                // P3
                vertices[i * (width - 1) * 18 + j * 18 + 9] = -width / 2f + j + 1;
                vertices[i * (width - 1) * 18 + j * 18 + 10] = getElevation(i, j + 1, maxHeight);
                vertices[i * (width - 1) * 18 + j * 18 + 11] = -depth / 2f + i;

                // P2
                vertices[i * (width - 1) * 18 + j * 18 + 12] = -width / 2f + j;
                vertices[i * (width - 1) * 18 + j * 18 + 13] = getElevation(i + 1, j, maxHeight);
                vertices[i * (width - 1) * 18 + j * 18 + 14] = -depth / 2f + i + 1;

                // P4
                vertices[i * (width - 1) * 18 + j * 18 + 15] = -width / 2f + j + 1;
                vertices[i * (width - 1) * 18 + j * 18 + 16] = getElevation(i + 1, j + 1, maxHeight);
                vertices[i * (width - 1) * 18 + j * 18 + 17] = -depth / 2f + i + 1;

                n1.set(-width / 2f + j + 1, getElevation(i + 1, j + 1, maxHeight), -depth / 2f + i + 1); // P4
                n1.sub(-width / 2f + j, getElevation(i + 1, j, maxHeight), -depth / 2f + i + 1, n2).negate(); // -(P4 - P2) = -(P24) = (P42)
                n1.sub(-width / 2f + j + 1, getElevation(i, j + 1, maxHeight), -depth / 2f + i).negate(); // -(P4 - P3) = -(P34) = (P43)
                n2.cross(n1, normal);
                normal.negate().normalize();

                normals[i * (width - 1) * 18 + j * 18 + 9] = normal.x;
                normals[i * (width - 1) * 18 + j * 18 + 10] = normal.y;
                normals[i * (width - 1) * 18 + j * 18 + 11] = normal.z;

                normals[i * (width - 1) * 18 + j * 18 + 12] = normal.x;
                normals[i * (width - 1) * 18 + j * 18 + 13] = normal.y;
                normals[i * (width - 1) * 18 + j * 18 + 14] = normal.z;

                normals[i * (width - 1) * 18 + j * 18 + 15] = normal.x;
                normals[i * (width - 1) * 18 + j * 18 + 16] = normal.y;
                normals[i * (width - 1) * 18 + j * 18 + 17] = normal.z;

                calculateColor(
                        width,
                        j, i
                );
            }
        }
    }

    public void calculateColor(int width, int j, int i) {
        float firstTriangleHeight =
                Math.max(
                        Math.max(vertices[i * (width - 1) * 18 + j * 18 + 1], vertices[i * (width - 1) * 18 + j * 18 + 4]),
                        vertices[i * (width - 1) * 18 + j * 18 + 7]);

        if (firstTriangleHeight < 8) {
            colors[i * (width - 1) * 18 + j * 18] = 0;
            colors[i * (width - 1) * 18 + j * 18 + 1] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 2] = 0;

            colors[i * (width - 1) * 18 + j * 18 + 3] = 0;
            colors[i * (width - 1) * 18 + j * 18 + 4] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 5] = 0;

            colors[i * (width - 1) * 18 + j * 18 + 6] = 0;
            colors[i * (width - 1) * 18 + j * 18 + 7] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 8] = 0;
        } else if (firstTriangleHeight >= 8) {
            colors[i * (width - 1) * 18 + j * 18] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 1] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 2] = 1;

            colors[i * (width - 1) * 18 + j * 18 + 3] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 4] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 5] = 1;

            colors[i * (width - 1) * 18 + j * 18 + 6] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 7] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 8] = 1;
        }

        float secondTriangleHeight =
                Math.max(
                        Math.max(vertices[i * (width - 1) * 18 + j * 18 + 10], vertices[i * (width - 1) * 18 + j * 18 + 13]),
                        vertices[i * (width - 1) * 18 + j * 18 + 16]);

        if (secondTriangleHeight < 8) {
            colors[i * (width - 1) * 18 + j * 18 + 9] = 0;
            colors[i * (width - 1) * 18 + j * 18 + 10] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 11] = 0;

            colors[i * (width - 1) * 18 + j * 18 + 12] = 0;
            colors[i * (width - 1) * 18 + j * 18 + 13] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 14] = 0;

            colors[i * (width - 1) * 18 + j * 18 + 15] = 0;
            colors[i * (width - 1) * 18 + j * 18 + 16] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 17] = 0;
        } else if (secondTriangleHeight >= 8) {
            colors[i * (width - 1) * 18 + j * 18 + 9] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 10] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 11] = 1;

            colors[i * (width - 1) * 18 + j * 18 + 12] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 13] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 14] = 1;

            colors[i * (width - 1) * 18 + j * 18 + 15] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 16] = 1;
            colors[i * (width - 1) * 18 + j * 18 + 17] = 1;
        }
    }

    public float getElevation(float i, float j, float maxHeight) {
        float distance = (float) Math.sqrt(
                Math.abs(j - this.width / 2f) * Math.abs(j - this.width / 2f) +
                        Math.abs(i - this.height / 2f) * Math.abs(i - this.height / 2f)
        ); // [0 -> w^1/2]
        i /= 25f;
        j /= 25f;
        distance += (SimplexNoise.noise(i, j) / 2f) * this.width / 1.9f;
        float radius = this.width / 2f;
        float delta = distance / radius; // [0 -> 1]
        float gradiant = delta * delta; // [0 -> 1]
        gradiant = 1 - gradiant; // [1 -> 0]

        float height = (float) Math.pow(
                (1 * ((SimplexNoise.noise(i, j) + 1)) / 2f) +
                        (.5f * ((SimplexNoise.noise(2 * i, 2 * j) + 1) / 2f)) +
                        (.25f * ((SimplexNoise.noise(4 * i, 4 * j) + 1) / 2f)) +
                        (.125f * ((SimplexNoise.noise(8 * i, 8 * j) + 1) / 2f)) +
                        ((0.125f / 2f) * ((SimplexNoise.noise(16 * i, 16 * j) + 1) / 2f))
                , 1.3f) * maxHeight;
        height *= gradiant;

        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}