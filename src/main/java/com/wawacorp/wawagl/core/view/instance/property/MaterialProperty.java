package com.wawacorp.wawagl.core.view.instance.property;

import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.shader.Shader;

public class MaterialProperty extends Property {
    private final Material material;

    public MaterialProperty(String name) {
        this(name, Material.DEFAULT);
    }

    public MaterialProperty() {
        this("material", Material.DEFAULT);
    }

    @Override
    public void upload(Shader shader) {
        shader.uploadMaterial(name, material);
    }

    public MaterialProperty(String name, Material material) {
        super(name);
        this.material = material;
    }
}
