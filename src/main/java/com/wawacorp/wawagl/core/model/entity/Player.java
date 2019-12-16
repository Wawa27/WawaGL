package com.wawacorp.wawagl.core.model.entity;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Player extends Entity {

    public Player() {

    }

    public void forward() {
        position.add(new Vector3f(0, 0, 1).mul(new Matrix3f().rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z)));
        update();
    }

    public void backward() {
        position.add(new Vector3f(0, 0, -1).mul(new Matrix3f().rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z)));
        update();
    }

    public void up() {
        position.add(new Vector3f(0, 1, 0).mul(new Matrix3f().rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z)));
        update();
    }

    public void left() {
        rotate(0, .1f, 0);
    }

    public void right() {
        rotate(0, -.1f, 0);
    }

    @Override
    public void onLoop() {
        
    }
}
