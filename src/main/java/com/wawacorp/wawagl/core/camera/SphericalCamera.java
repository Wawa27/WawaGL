package com.wawacorp.wawagl.core.camera;

import com.wawacorp.wawagl.core.camera.projection.Projection;
import com.wawacorp.wawagl.core.model.entity.Entity;
import org.joml.Vector3f;

import java.util.Observable;
import java.util.Observer;

import static java.lang.Math.*;

public class SphericalCamera extends Camera implements Observer {
    private float inclination;
    private float azimuth;
    private float radius;

    private final Vector3f eye;
    private final Entity entity;
    private final Vector3f up;

    public SphericalCamera(Projection projection, Entity entity, float radius) {
        super(projection);
        eye = new Vector3f();
        this.entity = entity;
        this.radius = radius;
        this.up = new Vector3f(0, 1, 0);
        this.inclination = 0;
        this.azimuth = 0;
        update(null, null);
    }

    @Override
    public void update(Observable observable, Object o) {
        calculateEyePosition();
        viewMatrix.setLookAt(
                eye,
                entity.getPosition(),
                up
        );
        update();
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setInclination(float inclination) {
        this.inclination = inclination;
        update(null, null);
    }

    public void setAzimuth(float azimuth) {
        this.azimuth = azimuth;
        update(null, null);
    }

    public float getInclination() {
        return inclination;
    }

    public float getAzimuth() {
        return azimuth;
    }

    public float getRadius() {
        return radius;
    }

    private void calculateEyePosition() {
        eye.x = (float) (radius * cos(0) * cos(azimuth));
        eye.y = (float) (radius * sin(0) * sin(azimuth));
        eye.z = (float) (radius * cos(0));
    }
}
