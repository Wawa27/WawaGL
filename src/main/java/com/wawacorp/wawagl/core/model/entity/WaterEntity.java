package com.wawacorp.wawagl.core.model.entity;

import com.wawacorp.wawagl.core.model.terrain.Water;
import com.wawacorp.wawagl.core.utils.MathUtils;
import org.joml.SimplexNoise;
import org.joml.Vector3f;

public class WaterEntity extends Entity {
    private final Water water;
    private float timeOffset = 0;
    private float yOffset = 0;
    private float xOffset = 0;

    public WaterEntity(Water water) {
        this.water = water;
    }

    @Override
    public void onLoop() {
        timeOffset +=.0001f;
        yOffset+=.00009;
        xOffset+=.00009;
        for (int i = 0; i < water.getDepth(); i++) {
            for (int j = 0; j < water.getWidth(); j++) {
                int index = i * water.getWidth() * 3 + j * 3 + 1;
                water.setVertex(index,  (1 + SimplexNoise.noise(j + xOffset, i + yOffset, timeOffset)) / 2f);
            }
        }
        // TODO: move the normal calculation part to the GPU instead
        Vector3f normal = new Vector3f();
        Vector3f p1 = new Vector3f(), p2 = new Vector3f(), p3 = new Vector3f();
        for (int i = 0; i < (water.getDepth() - 1); i++) {
            for (int j = 0; j < (water.getWidth() - 1); j++) {
                int indexP1 = i * water.getWidth() * 3 + j * 3;
                int indexP2 = i * water.getWidth() * 3 + (j + 1) * 3;
                int indexP3 = (i + 1) *  water.getWidth() * 3 + j * 3;

                water.getVertex(indexP1, p1);
                water.getVertex(indexP2, p2);
                water.getVertex(indexP3, p3);

                MathUtils.calculateNormal(
                    p1, p2, p3, normal
                );

                water.setNormal(indexP1, normal);
                water.setNormal(indexP2, normal);
                water.setNormal(indexP3, normal);
            }
        }
        setChanged();
        notifyObservers(water);
    }
}
