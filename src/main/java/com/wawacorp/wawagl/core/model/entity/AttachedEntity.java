package com.wawacorp.wawagl.core.model.entity;

import org.joml.Vector3f;

import java.util.Observable;
import java.util.Observer;

/**
 * Entity which reproduce every actions its parent makes
 */
public class AttachedEntity extends Entity implements Observer {
    private final Entity parent;
    private final Vector3f distance;

    /**
     *
     * @param parent The parent entity to attach to
     * @param distance The distance between the center entity and this
     */
    public AttachedEntity(Entity parent, Vector3f distance) {
        this.parent = parent;
        this.distance = distance;
        this.parent.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        hasChanged();
        update();
    }

    @Override
    public float[] getModel() {
        return modelMatrix.identity().translate(parent.getPosition()).translate(distance).rotateXYZ(parent.rotation).scale(parent.scale).get(model);
    }

    @Override
    public void onLoop() {

    }
}