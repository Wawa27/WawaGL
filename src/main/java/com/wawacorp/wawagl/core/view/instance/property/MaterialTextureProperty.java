package com.wawacorp.wawagl.core.view.instance.property;

import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.Bindable;
import com.wawacorp.wawagl.core.view.MaterialTextureView;

public class MaterialTextureProperty extends Property implements Bindable {
    private final MaterialTextureView materialTexture;

    public MaterialTextureProperty(String name, MaterialTextureView materialTexture) {
        super(name);
        this.materialTexture = materialTexture;
    }

    @Override
    public void upload(Shader shader) {
        shader.uploadTexture(0, materialTexture.getAmbient());
        shader.uploadTexture(1, materialTexture.getDiffuse());
        shader.uploadTexture(2, materialTexture.getSpecular());
    }

    @Override
    public void bind() {
        materialTexture.getAmbient().bind();
        materialTexture.getDiffuse().bind();
        materialTexture.getSpecular().bind();
    }

    @Override
    public void unbind() {
        materialTexture.getAmbient().bind();
        materialTexture.getDiffuse().bind();
        materialTexture.getSpecular().bind();
    }
}
