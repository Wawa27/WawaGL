package com.wawacorp.wawagl.core.camera;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.entity.Player;
import com.wawacorp.wawagl.core.camera.projection.Projection;
import org.joml.Vector3f;

import java.util.Observable;
import java.util.Observer;

public class TPSCamera extends Camera implements Observer {
    protected final Entity player;

    // position of camera = position of player + distance * angle
    protected final Vector3f position;

    /**
     * Distance from the player
     */
    protected float distance;

    /**
     * Angle from the player and the camera
     */
    protected float angleZ;
    protected float angleY;

    // direction of camera
    protected final Vector3f center;
    // up vector
    protected final Vector3f up;

    protected final Vector3f rotation;

    protected int height;

    protected boolean floatAround;

    public TPSCamera(Projection projection, Entity player, float distance, float angleZ, float angleY) {
        this(projection, player, distance, angleZ, angleY, 2);
    }

    public TPSCamera(Projection projection, Entity player, float distance, float angleZ, float angleY, int height) {
        super(projection);
        this.player = player;
        this.position = new Vector3f();
        this.rotation = new Vector3f();
        player.addObserver(this);
        this.distance = distance;
        this.angleZ = angleZ;
        this.angleY = angleY;
        center = new Vector3f();
        up = new Vector3f(0, 1, 0);
        this.height = height;
        update(null, null);
    }

    @Override
    public void update(Observable observable, Object o) {
        updateCameraPosition();
        viewMatrix.setLookAt(
                position,
                new Vector3f(
                        player.getPosition().x,
                        player.getPosition().y + height,
                        player.getPosition().z
                ),
                up
        );
        updateView();
    }

    public float calculateHorizontalOffset() {
        return distance * (float) Math.cos(angleZ);
    }

    public float calculateVerticalOffset() {
        return distance * (float) Math.sin(angleZ);
    }

    public void updateCameraPosition() {
        float verticalOffset = calculateVerticalOffset();
        float horizontalOffset = calculateHorizontalOffset();
        float cameraAngleY = player.getRotation().y + angleY;
        float offsetX = (float) (horizontalOffset * Math.sin(cameraAngleY));
        float offsetZ = (float) (horizontalOffset * Math.cos(cameraAngleY));
        position.x = player.getPosition().x - offsetX;
        position.y = player.getPosition().y + verticalOffset;
        position.z = player.getPosition().z - offsetZ;
    }

    public void setAngleY(float angleY) {
        this.angleY = angleY;
        update(null, null);
    }

    public void setAngleZ(float angleZ) {
        if (angleZ > Math.PI / 4) {
            angleZ = (float) Math.PI / 4;
        }
        if (angleZ < -Math.PI / 4) {
            angleZ = (float) -Math.PI / 4;
        }
        this.angleZ = angleZ;
        update(null, null);
    }

    public void setDistance(float distance) {
        if (distance < 1f) distance = 1;
        this.distance = distance;
        update(null, null);
    }

    public float getAngleY() {
        return angleY;
    }

    public float getAngleZ() {
        return angleZ;
    }

    public float getDistance() {
        return distance;
    }

    @Override
    public void onLoop() {
        if (floatAround) {
            angleY += .0001f;
            update(null, null);
        }
    }

    public void setFloatAround(boolean floatAround) {
        this.floatAround = floatAround;
    }
}
