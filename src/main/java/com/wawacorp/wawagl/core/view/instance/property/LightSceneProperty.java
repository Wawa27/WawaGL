package com.wawacorp.wawagl.core.view.instance.property;

import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.Bindable;
import com.wawacorp.wawagl.core.view.lighting.LightScene;

public class LightSceneProperty extends Property implements Bindable {
    private final LightScene lightScene;

    public LightSceneProperty(String name, LightScene lightScene) {
        super(name);
        this.lightScene = lightScene;
    }

    @Override
    public void bind() {
        lightScene.bind();
    }

    @Override
    public void unbind() {
        lightScene.unbind();
    }

    @Override
    public void upload(Shader shader) {
        
    }
}
