package com.wawacorp.wawagl.core.view.instance.property;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.shader.Shader;

import java.util.Observable;
import java.util.Observer;

public class EntityProperty extends Property implements Observer {
    private final Entity entity;

    public EntityProperty() {
        this("model", new Entity() {
            @Override
            public void onLoop() {

            }
        });
    }

    public EntityProperty(String name) {
        this(name, new Entity() {
            @Override
            public void onLoop() {

            }
        });

    }

    public EntityProperty(Entity entity) {
        this("model", entity);
    }

    public EntityProperty(String name, Entity entity) {
        super(name);
        this.entity = entity;
        this.entity.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        setChanged();
        notifyObservers(o);
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public void upload(Shader shader) {
        shader.uploadEntity(name, entity);
    }
}
