package com.wawacorp.wawagl.core.model.entity;

import com.wawacorp.wawagl.core.model.terrain.Water;
import org.joml.SimplexNoise;

public class WaterEntity extends Entity {
    private final Water water;
    private float xOffset = 0;
    private float yOffset = 0;

    public WaterEntity(Water water) {
        this.water = water;
    }

    @Override
    public void onLoop() {
        xOffset+=.001;
        for (int i = 0; i < water.getDepth(); i++) {
            for (int j = 0; j < water.getWidth(); j++) {
                int index = i * water.getWidth() * 3 + j * 3 + 1;
                water.setVertex(index, ((1 + SimplexNoise.noise(j/25f, i/25f, xOffset))) / 10f);
            }
        }
        setChanged();
        notifyObservers(water.getVertices());
    }
}
