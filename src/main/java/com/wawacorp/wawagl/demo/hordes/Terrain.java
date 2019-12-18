package com.wawacorp.wawagl.demo.hordes;

import com.wawacorp.wawagl.core.model.FlatColor;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.FlatColorProperty;
import com.wawacorp.wawagl.core.view.multiple.mesh.GLMultipleView;
import org.joml.Vector4f;

public class Terrain extends GLMultipleView {
    private final static int MAX_INSTANCE = 256;
    private final Instance[][] instances;

    public Terrain() {
        super(new Cube(), Shader.getMultipleFlatColorShader(), new Instance[256]);
        instances = new Instance[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Entity entity = new Entity() {
                    @Override
                    public void onLoop() {

                    }
                };
                entity.scale(32, 32, 32);
                entity.translate(-32 * 16 + j * 64 + 32, 0, 64 * 8 - i * 64 - 32);
                FlatColor random = new FlatColor(new Vector4f((float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random()));
                instances[i][j] = new Instance(new EntityProperty(entity), new FlatColorProperty(random));
                addInstance(instances[i][j]);
            }
        }
    }
}
