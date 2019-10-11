package com.wawacorp.wawagl.core.opengl.entity;

public class CenteredEntity extends Entity {
    private final Entity center;

    public CenteredEntity(Entity center) {
        this.center = center;
    }
}