package com.wawacorp.wawagl.core.camera;

import com.wawacorp.wawagl.core.camera.projection.Projection;
import org.joml.Vector3f;

public class SimpleCamera extends Camera {

    public SimpleCamera(Projection projection) {
        super(projection);
    }

    public void rotate(float x, float y, float z) {
        viewMatrix.rotateXYZ(x, y, z);
        update();
    }

    public void translate(float x, float y, float z) {
        viewMatrix.translate(-x, -y, -z);
        update();
    }
}
