package com.wawacorp.wawagl.core.model.entity;

import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.terrain.Water;
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
        xOffset+=.01;
        for (int i = 0; i < water.getDepth(); i++) {
            for (int j = 0; j < water.getWidth(); j++) {
                int index = i * water.getWidth() * 3 + j * 3 + 1;
                water.setVertex(index, ((1.8f + SimplexNoise.noise(xOffset + j/25f, yOffset + i/25f)) * 1.4f));
            }
        }
        setChanged();
        notifyObservers(water.getVertices());
    }
}
