package com.wawacorp.wawagl.core.model.terrain;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.Mesh;

import java.util.ArrayList;

public abstract class Terrain extends Mesh {
    protected final float[][] heights;
    protected final ArrayList<Entity> entities;
    protected final ArrayList<Entity> trees;

    public Terrain(int x, int y) {
        heights = new float[y][x];
        entities = new ArrayList<>();
        trees = new ArrayList<>();
    }

    public float getHeight(int x, int z) {
        if (z >= 0 && z < heights.length) {
            if (x >= 0 && x < heights[z].length) {
                return heights[z][x];
            }
        }
        return Float.NEGATIVE_INFINITY;
    }

    public void update() {
        for (Entity entity : entities) {
            float terrainHeightAtPlayerPosition = getHeight((int) entity.getPosition().x, (int) entity.getPosition().z);
            if (terrainHeightAtPlayerPosition != Float.NEGATIVE_INFINITY) {
                if (terrainHeightAtPlayerPosition > entity.getPosition().y) {
                    entity.getPosition().set(1, terrainHeightAtPlayerPosition + .1f);
                }
            }
         }
    }

    public void addTree(Entity entity) {
        trees.add(entity);

        float terrainHeightAtPlayerPosition = getHeight((int) entity.getPosition().x, (int) entity.getPosition().z);
        if (terrainHeightAtPlayerPosition != Float.NEGATIVE_INFINITY) {
            if (terrainHeightAtPlayerPosition > entity.getPosition().y) {
                entity.getPosition().set(1, terrainHeightAtPlayerPosition + .1f);
            }
        }
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public float[][] getHeights() {
        return heights;
    }
}
