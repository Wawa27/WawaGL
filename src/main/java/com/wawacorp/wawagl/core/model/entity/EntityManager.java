package com.wawacorp.wawagl.core.model.entity;

import com.wawacorp.wawagl.core.model.entity.Entity;

import java.util.ArrayList;

public final class EntityManager {
    private final static ArrayList<Entity> entities;

    static {
        entities = new ArrayList<>();
    }

    private EntityManager() {

    }

    public static void onLoop() {
        for (Entity entity : entities) entity.onLoop();
    }

    public static void addEntity(Entity entity) {
        entities.add(entity);
    }

    public static void removeEntity(Entity entity) {
        entities.remove(entity);
    }
}
