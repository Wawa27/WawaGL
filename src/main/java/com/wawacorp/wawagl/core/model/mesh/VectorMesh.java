package com.wawacorp.wawagl.core.model.mesh;

import com.wawacorp.wawagl.core.model.Mesh;
import org.joml.Vector3f;

public class VectorMesh extends Mesh {
    private Vector3f position;
    private Vector3f normal;

    public VectorMesh(Vector3f position, Vector3f normal) {
        this.position = position;
        this.normal = normal;
    }
}
