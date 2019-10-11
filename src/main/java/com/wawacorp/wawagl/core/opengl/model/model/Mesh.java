package com.wawacorp.wawagl.core.opengl.model.model;

import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Material;

import java.io.Serializable;
import java.util.ArrayList;

public class Mesh implements Serializable {
    protected String name;

    protected float[] vertices;
    protected float[] normals;
    protected float[] texCoords;
    protected int[] indices;

    protected Material material;
    protected String texturePath;

    private ArrayList<Mesh> meshes;

    public Mesh() {
        meshes = new ArrayList<>();
    }

    public Mesh(String name, float[] vertices, float[] normals, float[] texCoords, int[] indices) {
        this.name = name;
        this.vertices = vertices;
        this.normals = normals;
        this.texCoords = texCoords;
        this.indices = indices;

        meshes = new ArrayList<>();
    }

    public float[] getVertices() {
        return vertices;
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    public float[] getNormals() {
        return normals;
    }

    public void setNormals(float[] normals) {
        this.normals = normals;
    }

    public float[] getTexCoords() {
        return texCoords;
    }

    public void setTexCoords(float[] texCoords) {
        this.texCoords = texCoords;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public Material getMaterial() {
        return material;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void addMesh(Mesh mesh) {
        meshes.add(mesh);
    }

    public ArrayList<Mesh> getMeshes() {
        return meshes;
    }

    @Override
    public String toString() {
        return "Mesh: " + name + ", v: " + vertices.length + ", n:" + normals.length + ", tx:" + (texCoords == null ? 0: texCoords.length)+ ", f: " + indices.length + " texturePath: " + texturePath;
    }
}
