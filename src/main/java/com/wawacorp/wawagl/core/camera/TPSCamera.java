package com.wawacorp.wawagl.core.camera;

import com.wawacorp.wawagl.core.model.entity.Player;
import com.wawacorp.wawagl.core.camera.projection.Projection;
import org.joml.Vector3f;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class TPSCamera extends Camera {
    // direction of camera
    protected final Vector3f center;
    // up vector
    protected final Vector3f up;

    protected float angleX;
    protected float angleY;

    protected final Player player;
    protected final Vector3f distance;

    public TPSCamera(Player player, Vector3f distance, Projection projection) {
        super(projection);
        this.player = player;
        this.distance = distance;
        angleX = (float) Math.PI;
        center = new Vector3f();
        up = new Vector3f(0, 1, 0);
        update();
    }

    public void mouse(float x, float y) {
        angleX += x;
        angleY += y;
        center.set(getDirection());
        distance.set(getDirection());
        update();
    }

    public Vector3f getRight() {
        return new Vector3f(
                (float) sin(angleX - 3.14f / 2.0f),
                0,
                (float) cos(angleX - 3.14f / 2.0f)
        );
    }

    public void forward() {
        Vector3f direction = getDirection();
        Vector3f directionNoClip = new Vector3f(direction.x, 0, direction.z).normalize();
        player.getPosition().add(directionNoClip);
        update();
        player.update();
    }

    public void backward() {
        Vector3f direction = getDirection();
        Vector3f directionNoClip = new Vector3f(direction.x, 0, direction.z).normalize();
        player.getPosition().sub(directionNoClip);
        update();
        player.update();
    }

    public void left() {
        Vector3f direction = getRight();
        Vector3f directionNoClip = new Vector3f(direction.x, 0, direction.z).normalize();
        player.getPosition().sub(directionNoClip);
        update();
        player.update();
    }

    public void right() {
        Vector3f direction = getRight();
        Vector3f directionNoClip = new Vector3f(direction.x, 0, direction.z).normalize();
        player.getPosition().add(directionNoClip);
        update();
        player.update();
    }

    public void up() {
        player.getPosition().add(up);
        update();
        player.update();
    }

    @Override
    public void down() {
        player.getPosition().sub(up);
        player.update();
        update();
    }

    @Override
    public void setPosition(float x, float y, float z) {
        player.setTranslation(x, y, z);
        update();
        player.update();
    }

    public Vector3f getDirection() {
        return new Vector3f(
                (float) (cos(angleY) * sin(angleX)),
                (float) (sin(angleY)),
                (float) (cos(angleY) * cos(angleX))
        );
    }

    @Override
    public void update() {
        viewMatrix.setLookAt(
                player.getPosition().add(distance, new Vector3f()),
                center.add(player.getPosition().add(distance, new Vector3f()), new Vector3f()),
                up
        );
        super.update();
    }
}
