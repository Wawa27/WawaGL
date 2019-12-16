package com.wawacorp.wawagl.core.model;

public class MaterialTexture {
    private String ambientPath;
    private String diffusePath;
    private String specularPath;

    public MaterialTexture() {

    }

    public void setAmbientPath(String ambientPath) {
        this.ambientPath = ambientPath;
    }

    public void setDiffusePath(String diffusePath) {
        this.diffusePath = diffusePath;
    }

    public void setSpecularPath(String specularPath) {
        this.specularPath = specularPath;
    }

    public String getAmbientPath() {
        return ambientPath;
    }

    public String getDiffusePath() {
        return diffusePath;
    }

    public String getSpecularPath() {
        return specularPath;
    }
}
