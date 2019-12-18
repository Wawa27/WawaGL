package com.wawacorp.wawagl.core.view.instance.property;

import com.wawacorp.wawagl.core.model.FlatColor;
import com.wawacorp.wawagl.core.shader.Shader;

@Deprecated
public class FlatColorProperty extends Property {
    private final FlatColor color;

    public FlatColorProperty() {
        this(FlatColor.DEFAULT);
    }

    public FlatColorProperty(FlatColor color) {
        this("color", color);
    }

    public FlatColorProperty(String name, FlatColor color) {
        super(name);
        this.color = color;
    }

    @Override
    public void upload(Shader shader) {
        shader.uploadFlatColor(name, color);
    }
}
