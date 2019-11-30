package com.wawacorp.wawagl.core.camera;

import com.wawacorp.wawagl.core.camera.projection.Projection;
import com.wawacorp.wawagl.core.model.entity.Player;
import org.joml.Vector3f;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class TPSCameraNoClip extends TPSCamera {

    public TPSCameraNoClip(Player player, Vector3f distance, Projection projection) {
        super(player, distance, projection);
    }
    public void forward() {
        Vector3f direction = getDirection();
        player.getPosition().add(direction);
        player.update();
        update();
    }

    public void backward() {
        Vector3f direction = getDirection();
        player.getPosition().sub(direction);
        player.update();
        update();
    }

    public void left() {
        Vector3f direction = getRight();
        player.getPosition().sub(direction);
        player.update();
        update();
    }

    @Override
    public void down() {
        player.getPosition().sub(up);
        player.update();
        update();
    }
}
