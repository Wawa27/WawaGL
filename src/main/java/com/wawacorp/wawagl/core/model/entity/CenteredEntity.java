package com.wawacorp.wawagl.core.model.entity;

import org.joml.Vector3f;

import java.util.Observable;
import java.util.Observer;

public class CenteredEntity extends Entity implements Observer {
    private final Entity center;
    private final Vector3f distance;

    /**
     *
     * @param center
     * @param distance The distance between the center entity and this
     */
    public CenteredEntity(Entity center, Vector3f distance) {
        this.center = center;
        this.distance = distance;
        this.center.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        hasChanged();
    }

    @Override
    public float[] getModel() {
        return modelMatrix.identity().translate(center.getPosition()).translate(distance).rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z).scale(scale).get(model);
    }
}