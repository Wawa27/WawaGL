package com.wawacorp.wawagl.demo.nemesis;

import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.multiple.mesh.GLMultipleMesh;

public class Chunk extends GLMultipleMesh {
    public final static int CHUNK_SIZE = 8;
    public final static int MAX_CUBE_COUNT = CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE;
    private final Instance[] instances;

    public Chunk(Instance[] instances, int x, int z) {
        super(new Cube(), Shader.loadShaderRelative("", ""), instances);
        this.instances = instances;
        for (Instance instance : instances) {
            addInstance(instance);
        }
        for (int i = 0; i < instances.length; i++) {
            ((EntityProperty)instances[i].getProperty("model")).getEntity().translate(x * CHUNK_SIZE, 0, z * CHUNK_SIZE);
        }
    }
}
