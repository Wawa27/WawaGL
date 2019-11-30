package com.wawacorp.wawagl.core.model;

import com.wawacorp.wawagl.core.buffer.texture.Texture2D;
import com.wawacorp.wawagl.core.manager.AssetManager;
import com.wawacorp.wawagl.core.view.Bindable;
import org.joml.Vector4f;

public class Material implements Bindable {
    public final static Material DEFAULT = new Material(Texture2D.DEFAULT, new Vector4f(1, 1, 1, 1), new Vector4f(1, 1, 1, 1), new Vector4f(1, 1, 1, 1));

    private Texture2D texturePath;
    private Vector4f ambient;
    private Vector4f diffuse;
    private Vector4f specular;

    public Material() {
        texturePath = Texture2D.DEFAULT;
    }

    public Material(Texture2D texturePath, Vector4f ambient, Vector4f diffuse, Vector4f specular) {
        this();
        this.texturePath = texturePath;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
    }

    public Vector4f getAmbient() {
        return ambient;
    }

    public Vector4f getDiffuse() {
        return diffuse;
    }

    public Vector4f getSpecular() {
        return specular;
    }

    public void setTexturePath(Texture2D texturePath) {
        this.texturePath = texturePath;
    }

    public void setAmbient(Vector4f ambient) {
        this.ambient = ambient;
    }

    public void setDiffuse(Vector4f diffuse) {
        this.diffuse = diffuse;
    }

    public void setSpecular(Vector4f specular) {
        this.specular = specular;
    }

    @Override
    public void bind() {
        texturePath.bind();
    }

    @Override
    public void unbind() {
        texturePath.unbind();
    }

    @Override
    public String toString() {
        return ambient.toString() + "\r\n" + diffuse.toString();
    }
}
