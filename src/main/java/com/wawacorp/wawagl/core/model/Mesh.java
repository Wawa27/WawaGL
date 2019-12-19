package com.wawacorp.wawagl.core.model;

import com.wawacorp.wawagl.core.model.animation.Armature;
import org.joml.Vector3f;

import java.io.Serializable;

public class Mesh implements Serializable {
    protected String name;

    protected float[] vertices;
    protected float[] normals;
    protected float[] texCoords;
    protected float[] colors;
    protected int[] indices;

    private Armature armature;

    protected Material material;
    protected MaterialTexture materialTexture;

    public Mesh() {

    }

    public Mesh(String name, float[] vertices, float[] normals, float[] texCoords, int[] indices) {
        this.name = name;
        this.vertices = vertices;
        this.normals = normals;
        this.texCoords = texCoords;
        this.indices = indices;
    }

    public Mesh(String name, float[] vertices, float[] normals, float[] texCoords, int[] indices, Armature armature) {
        this.name = name;
        this.vertices = vertices;
        this.normals = normals;
        this.texCoords = texCoords;
        this.indices = indices;
        this.armature = armature;
    }

    public float[] getVertices() {
        return vertices;
    }


    public float getVertex(int index) {
        return vertices[index];
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    public void setVertex(int indice, float value) {
        this.vertices[indice] = value;
    }

    public void setNormal(int indice, float value) {
        this.normals[indice] = value;
    }

    public void setNormal(int index, Vector3f normal) {
        this.normals[index] = normal.x;
        this.normals[index + 1] = normal.y;
        this.normals[index + 2] = normal.z;
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

    public int[] getIndices() {
        return indices;
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

    public Material getMaterial() {
        return material;
    }

    public void setColors(float[] colors) {
        this.colors = colors;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public void setTexCoords(float[] texCoords) {
        this.texCoords = texCoords;
    }

    public float[] getColors() {
        return colors;
    }

    public MaterialTexture getMaterialTexture() {
        return materialTexture;
    }

    public void setMaterialTexture(MaterialTexture materialTexture) {
        this.materialTexture = materialTexture;
    }

    public void setArmature(Armature armature) {
        this.armature = armature;
    }

    public Armature getArmature() {
        return armature;
    }

    public void getVertex(int index, Vector3f dest) {
        dest.set(vertices[index], vertices[index + 1], vertices[index + 2]);
    }

    public void getNormal(int index, Vector3f dest) {
        dest.set(normals[index], normals[index + 1], normals[index + 2]);
    }

    @Override
    public String toString() {
        return "Mesh: " + name + ", v: " + vertices.length + ", n:" + normals.length + ", tx:" + (texCoords == null ? 0: texCoords.length)+ ", f: " + indices.length;
    }
}
