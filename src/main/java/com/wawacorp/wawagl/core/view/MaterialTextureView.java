package com.wawacorp.wawagl.core.view;

import com.wawacorp.wawagl.core.view.buffer.texture.Texture2D;
import com.wawacorp.wawagl.core.manager.AssetManager;
import com.wawacorp.wawagl.core.model.MaterialTexture;

public class MaterialTextureView implements Bindable {
    private Texture2D ambient;
    private Texture2D diffuse;
    private Texture2D specular;

    public MaterialTextureView() {

    }

    public MaterialTextureView(MaterialTexture materialTexture) {
        this.ambient = AssetManager.getTexture2D(materialTexture.getAmbientPath());
        this.diffuse = AssetManager.getTexture2D(materialTexture.getDiffusePath());
        this.specular = AssetManager.getTexture2D(materialTexture.getSpecularPath());
    }

    public Texture2D getAmbient() {
        return ambient;
    }

    public Texture2D getDiffuse() {
        return diffuse;
    }

    public Texture2D getSpecular() {
        return specular;
    }

    @Override
    public void bind() {
        ambient.bind();
        diffuse.bind();
        specular.bind();
    }

    @Override
    public void unbind() {
        ambient.unbind();
        diffuse.unbind();
        specular.unbind();
    }
}
