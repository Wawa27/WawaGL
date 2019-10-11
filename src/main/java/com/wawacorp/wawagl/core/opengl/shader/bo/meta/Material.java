package com.wawacorp.wawagl.core.opengl.shader.bo.meta;

import org.joml.Vector4f;

public class Material {
    public final static Material DEFAULT = new Material(new Vector4f(1, 1, 1, 1), new Vector4f(1, 1, 1, 1), new Vector4f(1, 1, 1, 1));
    private Vector4f ambient;
    private Vector4f diffuse;
    private Vector4f specular;

    public static class Builder {
        private Vector4f ambientColor;
        private Vector4f diffuseColor;
        private Vector4f specularColor;

        public Builder() {

        }

        public Builder ambient(Vector4f ambient) {
            ambientColor = ambient;
            return this;
        }

        public Builder diffuse(Vector4f diffuse) {
            diffuseColor = diffuse;
            return this;
        }

        public Builder specular(Vector4f specular) {
            specularColor = specular;
            return this;
        }

        public Material build() {
            return new Material(ambientColor, diffuseColor, specularColor);
        }
    }

    public Material(Vector4f ambient, Vector4f diffuse, Vector4f specular) {
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

    @Override
    public String toString() {
        return ambient.toString() + "\r\n" + diffuse.toString();
    }
}
