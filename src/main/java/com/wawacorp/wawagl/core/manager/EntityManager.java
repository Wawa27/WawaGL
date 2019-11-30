package com.wawacorp.wawagl.core.manager;

import com.wawacorp.wawagl.core.model.entity.Entity;

import java.util.ArrayList;

@Deprecated
public class EntityManager {
    private final static ArrayList<Entity> entities;

    static {
        entities = new ArrayList<>();
    }

    private EntityManager() {

    }

    public static void addEntity(Entity entity) {
        entities.add(entity);
    }

    public static void onLoop() {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.onLoop();
        }
    }
}
