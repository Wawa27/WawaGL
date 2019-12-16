package com.wawacorp.wawagl.core.view.instance.property;

import com.wawacorp.wawagl.core.view.buffer.texture.Texture;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.Bindable;

public class TextureProperty extends Property implements Bindable {
    private final Texture texture;

    public TextureProperty(String name, Texture texture) {
        super(name);
        this.texture = texture;
    }

    @Override
    public void upload(Shader shader) {
        shader.uploadTexture(0, texture);
    }

    @Override
    public void bind() {
        texture.bind();
    }

    @Override
    public void unbind() {
        texture.unbind();
    }
}
