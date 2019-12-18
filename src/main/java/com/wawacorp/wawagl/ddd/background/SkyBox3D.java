package com.wawacorp.wawagl.ddd.background;

import com.wawacorp.wawagl.core.manager.AssetManager;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.TextureProperty;
import com.wawacorp.wawagl.core.view.single.GLSingleView;

public class SkyBox3D extends GLSingleView {
    private final static Mesh cube;
    private final Entity entity;

    static {
        cube = new Cube();
    }

    public SkyBox3D(String[] paths) {
        super(cube,
                new Instance(
                        new TextureProperty("texture0", AssetManager.getTextureArray(paths))
                ),
                Shader.loadShaderRelative("skybox3d", "skybox3d"));
        this.entity = new Entity() {
            @Override
            public void onLoop() {

            }
        };
        this.entity.scale(8, 8, 8);
        this.instance.addProperty(new EntityProperty("model", entity));
    }
}
