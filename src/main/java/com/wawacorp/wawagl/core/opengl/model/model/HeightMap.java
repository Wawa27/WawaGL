package com.wawacorp.wawagl.core.opengl.model.model;

import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Image2D;

import java.nio.ByteBuffer;

public class HeightMap extends Mesh {

    //TODO: center the mesh (not start at 0, 0)
    //TODO: add Texture/Material
    public HeightMap(String path, String texturePath) {
        Image2D image = Image2D.load(path);
        ByteBuffer buffer = image.getData();

        this.name = "Heightmap";
        this.texturePath = texturePath;

        vertices = new float[image.getWidth() * image.getHeight() * 3];
        indices = new int[(image.getWidth() - 1) * (image.getHeight() - 1) * 2 * 3];
        normals = new float[image.getWidth()  * image.getHeight() * 3];
        texCoords = new float[image.getWidth() * image.getHeight()  * 2];

        float minX = -image.getWidth() / 2f;
        float minZ = -image.getHeight() / 2f;

        height:
        for (int i = 0; i < image.getHeight(); i++) {
            width:
            for (int j = 0; j < image.getWidth(); j++) {
                int rgb = (buffer.get() & 0xff) + (buffer.get() & 0xff) + (buffer.get() & 0xff);
                buffer.get(); // Alpha value
                vertices[i * image.getWidth() * 3 + j * 3] = minX + j;
                vertices[i * image.getWidth() * 3 + j * 3 + 1] = rgb/765f * 32;
                vertices[i * image.getWidth() * 3 + j * 3 + 2] = minZ + i;
                //todo: remove redondant operations

                texCoords[i * image.getWidth() * 2 + j * 2] = j % 2;
                texCoords[i * image.getWidth() * 2 + j * 2 + 1] = i % 2;
            }
        }

        height:
        for (int i = 0; i < image.getHeight() - 1; i++) {
            width:
            for (int j = 0; j < image.getWidth() - 1; j++) {
                indices[i * (image.getWidth() - 1) * 6 + j * 6] =       i * image.getWidth() + j;
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 1] =   (i + 1) * image.getWidth() + j;
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 2] =   i * image.getWidth() + j + 1;

                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 3] =   i * image.getWidth() + j + 1;
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 4] =   (i + 1) * image.getWidth() + j;
                indices[i * (image.getWidth() - 1) * 6 + j * 6 + 5] =   (i + 1) * image.getWidth() + j + 1;
            }
        }

        height:
        for (int i = 1; i < image.getHeight() - 1; i++) {
            width:
            for (int j = 1; j < image.getWidth() - 1; j++) {
                normals[i * image.getWidth() * 3 + j * 3] = 0;
                normals[i * image.getWidth() * 3 + j * 3 + 1] = 1;
                normals[i * image.getWidth() * 3 + j * 3 + 2] = 0;
            }
        }
    }
}