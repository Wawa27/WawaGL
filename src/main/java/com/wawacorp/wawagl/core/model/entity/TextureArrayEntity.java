package com.wawacorp.wawagl.core.model.entity;

import org.joml.Vector3f;

public class TextureArrayEntity extends Entity {
    private int layer;

    public TextureArrayEntity() {
        super();
    }

    public TextureArrayEntity(int layer) {
        this.layer = layer;
    }

    public TextureArrayEntity(Vector3f position) {
        super(position);
    }

    @Override
    public void onLoop() {
        super.onLoop();
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
        setChanged();
    }
}
