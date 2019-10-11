package com.wawacorp.wawagl.core.opengl.camera.projection;

import com.wawacorp.wawagl.core.opengl.game.Game;
import org.joml.Matrix4f;

public class Perspective extends Projection {
    private final static float DEFAULT_FOVY = (float) Math.toRadians(90);
    private final static float DEFAULT_ASPECT = (float) Game.width / Game.height;
    private final static float DEFAULT_ZNEAR = 0.01f;
    private final static float DEFAULT_ZFAR = -100f;

    public final static Perspective DEFAULT = new Perspective(DEFAULT_FOVY, DEFAULT_ASPECT, DEFAULT_ZNEAR, DEFAULT_ZFAR);

    public Perspective(float fovy, float aspect, float zNear, float zFar) {
        super(new Matrix4f().setPerspective(fovy, aspect, zNear, zFar));
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }
}
