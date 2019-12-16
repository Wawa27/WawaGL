package com.wawacorp.wawagl.core.model.shape;


import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.model.Mesh;

public class Shape extends Mesh {

    public Shape(String name, float[] vertices, float[] normals, float[] texCoords, int[] indices) {
        super(name, vertices, normals, texCoords, indices);
    }
}
