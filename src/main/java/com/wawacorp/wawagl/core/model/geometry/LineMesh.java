package com.wawacorp.wawagl.core.model.geometry;

import org.joml.Vector3f;

public class LineMesh extends GeometryMesh {
    private Vector3f position;
    private Vector3f direction;

    public LineMesh(Vector3f position, Vector3f direction) {
        this.position = position;
        this.direction = direction;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
}
