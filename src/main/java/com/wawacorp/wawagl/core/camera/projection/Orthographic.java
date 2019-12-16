package com.wawacorp.wawagl.core.camera.projection;

import com.wawacorp.wawagl.core.game.Game;
import org.joml.Matrix4f;

public class Orthographic extends Projection {
    private final static float DEFAULT_LEFT = -Game.width/2f;
    private final static float DEFAULT_RIGHT = Game.width/2f;
    private final static float DEFAULT_BOTTOM = -Game.height/2f;
    private final static float DEFAULT_TOP = Game.height/2f;
    private final static float DEFAULT_ZNEAR = DEFAULT_LEFT * 2;
    private final static float DEFAULT_ZFAR = DEFAULT_RIGHT * 2;

    public final static Orthographic DEFAULT = new Orthographic(DEFAULT_LEFT, DEFAULT_RIGHT, DEFAULT_BOTTOM, DEFAULT_TOP, DEFAULT_ZNEAR, DEFAULT_ZFAR);

    public Orthographic(float left, float right, float bottom, float top, float zNear, float zFar) {
        super(new Matrix4f().setOrtho(left, right, bottom, top, zNear, zFar));
    }
}
