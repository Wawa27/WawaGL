package com.wawacorp.wawagl.core.camera.projection;

import com.wawacorp.wawagl.core.game.Game;
import org.joml.Matrix4f;

public class Orthographic2D extends Projection {
    private final static float DEFAULT_LEFT = 0;
    private final static float DEFAULT_RIGHT = Game.width;
    private final static float DEFAULT_BOTTOM = 0;
    private final static float DEFAULT_TOP = Game.height;
    private final static float DEFAULT_ZNEAR = 0.01f;
    private final static float DEFAULT_ZFAR = -100f;

    public final static Orthographic DEFAULT = new Orthographic(DEFAULT_LEFT, DEFAULT_RIGHT, DEFAULT_BOTTOM, DEFAULT_TOP, DEFAULT_ZNEAR, DEFAULT_ZFAR);

    public Orthographic2D(float left, float right, float bottom, float top, float zNear, float zFar) {
        super(new Matrix4f().setOrtho(left, right, bottom, top, zNear, zFar));
    }
}
