package com.wawacorp.wawagl.core.model;

import org.joml.Vector4f;

public class Material {
    public final static Material DEFAULT = new Material("DEFAULT", new Vector4f(1, 1, 1, 1), new Vector4f(1, 1, 1, 1), new Vector4f(1, 1, 1, 1));
    private String name;

    private Vector4f ambient;
    private Vector4f diffuse;
    private Vector4f specular;

    public Material() {
        this(
                "DEFAULT",
                new Vector4f(1, 1, 1, 1),
                new Vector4f(1, 1, 1, 1),
                new Vector4f(1, 1, 1, 1)
        );
    }

    public Material(String name, Vector4f ambient, Vector4f diffuse, Vector4f specular) {
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

    public void setAmbient(Vector4f ambient) {
        this.ambient = ambient;
    }

    public void setDiffuse(Vector4f diffuse) {
        this.diffuse = diffuse;
    }

    public void setSpecular(Vector4f specular) {
        this.specular = specular;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Material: " + name + " \r\n Ambient: " + ambient.toString() + "\r\n Diffuse: " + diffuse.toString() + "\r\n Specular: " + specular.toString();
    }
}
