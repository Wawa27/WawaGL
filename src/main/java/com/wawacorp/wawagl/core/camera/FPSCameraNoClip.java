package com.wawacorp.wawagl.core.camera;

import com.wawacorp.wawagl.core.camera.projection.Projection;
import com.wawacorp.wawagl.core.terrain.Terrain;
import org.joml.Vector3f;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class FPSCameraNoClip extends Camera {
    // position of camera
    private final Vector3f eye;
    // direction of camera
    private final Vector3f center;
    // up vector
    private final Vector3f up;
    private float angleX;
    private float angleY;
    private final Terrain terrain;
    private boolean noClip = false;

    public FPSCameraNoClip(Terrain terrain, Projection projection) {
        super(projection);
        this.terrain = terrain;
        angleX = (float) Math.PI;
        eye = new Vector3f(0, 0, 0);
        center = new Vector3f(eye.add(getDirection(), new Vector3f()));
        up = new Vector3f(0, 1, 0);
        update();
    }

    public void mouse(float x, float y) {
        angleX += x;
        angleY += y;
        center.set(getDirection());
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
        if (noClip) {
             direction = new Vector3f(direction.x, 0, direction.z).normalize();
        }
        eye.add(direction);
        detection();
        update();
    }

    public void backward() {
        Vector3f direction = getDirection();
        if (noClip) {
            direction = new Vector3f(direction.x, 0, direction.z).normalize();
        }
        eye.add(direction);
        detection();
        update();
    }

    public void left() {
        Vector3f direction = getRight();
        if (noClip) {
            direction = new Vector3f(direction.x, 0, direction.z).normalize();
        }
        eye.sub(direction);
        detection();
        update();
    }

    public void right() {
        Vector3f direction = getRight();
        if (noClip) {
            direction = new Vector3f(direction.x, 0, direction.z).normalize();
        }
        eye.add(direction);
        detection();
        update();
    }

    public void detection() {
        float terrainHeightAtPlayerPosition = terrain.getHeight((int) eye.x, (int) eye.z);
        if (terrainHeightAtPlayerPosition != Float.NEGATIVE_INFINITY) {
            if (terrain.getHeight((int) eye.x, (int) eye.z) > eye.y) {
                eye.y = terrain.getHeight((int) eye.x, (int) eye.z);
            }
        }
    }

    public void up() {
        eye.add(up);
        update();
    }

    public void down() {
        eye.sub(up);
        update();
    }

    public void setPosition(float x, float y, float z) {
        eye.set(x, y, z);
        update();
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
                eye,
                center.add(eye, new Vector3f()),
                up
        );
        super.update();
    }
}
