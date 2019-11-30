package com.wawacorp.wawagl.core.camera;

import com.wawacorp.wawagl.core.camera.projection.Orthographic;
import com.wawacorp.wawagl.core.camera.projection.Perspective;
import com.wawacorp.wawagl.core.camera.projection.Projection;
import com.wawacorp.wawagl.core.model.Mesh;

public class SimpleCamera extends Camera {
    public static final SimpleCamera DEFAULT_ORTHOGRAPHIC = new SimpleCamera(Orthographic.DEFAULT);
    public static final SimpleCamera DEFAULT_PERSPECTIVE = new SimpleCamera(Perspective.DEFAULT);

    public SimpleCamera(Projection projection) {
        super(projection);
        update();
    }

    public static SimpleCamera newPerspectiveCamera() {
        return new SimpleCamera(Perspective.DEFAULT);
    }

    @Override
    public void forward() {

    }

    @Override
    public void backward() {

    }

    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    @Override
    public void up() {

    }

    @Override
    public void down() {

    }

    @Override
    public void mouse(float x, float y) {

    }

    @Override
    public void setPosition(float x, float y, float z) {
        viewMatrix.setTranslation(x, y, z);
        update();
    }
}
