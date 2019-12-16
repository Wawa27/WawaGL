package com.wawacorp.wawagl.core.model;

import com.wawacorp.wawagl.core.model.animation.Armature;
import com.wawacorp.wawagl.core.model.animation.Bone;
import com.wawacorp.wawagl.core.model.animation.SkeletalAnimation;

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
    protected MaterialTexture texture;

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

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    public void setVertex(int indice, float value) {
        this.vertices[indice] = value;
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

    public void setTexture(MaterialTexture texture) {
        this.texture = texture;
    }

    public MaterialTexture getTexture() {
        return texture;
    }

    public void setArmature(Armature armature) {
        this.armature = armature;
    }

    public Armature getArmature() {
        return armature;
    }

    @Override
    public String toString() {
        return "Mesh: " + name + ", v: " + vertices.length + ", n:" + normals.length + ", tx:" + (texCoords == null ? 0: texCoords.length)+ ", f: " + indices.length;
    }
}
