package com.wawacorp.wawagl.core.camera.projection;

import com.wawacorp.wawagl.core.game.Game;
import org.joml.Matrix4f;

public class Perspective extends Projection {
    private final static float DEFAULT_FOVY = (float) Math.toRadians(60);
    private final static float DEFAULT_ASPECT = Game.width / (float) Game.height;
    private final static float DEFAULT_ZNEAR = .1f;
    private final static float DEFAULT_ZFAR = -1000f;

    public final static Perspective DEFAULT = new Perspective(DEFAULT_FOVY, DEFAULT_ASPECT, DEFAULT_ZNEAR, DEFAULT_ZFAR);

    public Perspective(float fovy, float aspect, float zNear, float zFar) {
        super(new Matrix4f().setPerspective(fovy, aspect, zNear, zFar));
        System.out.println(getProjectionMatrix());
    }
}
