package com.wawacorp.wawagl.core.model;

import org.joml.Vector4f;

public class FlatColor {
    public final static FlatColor DEFAULT = new FlatColor(new Vector4f(1, 1, 1, 1));

    public final static FlatColor WHITE = new FlatColor(new Vector4f(1, 1, 1, 1));
    public final static FlatColor BLACK = new FlatColor(new Vector4f(0, 0, 0, 1));

    public final static FlatColor RED = new FlatColor(new Vector4f(1, 0, 0, 1));
    public final static FlatColor GREEN = new FlatColor(new Vector4f(0, 1, 0, 1));
    public final static FlatColor BLUE = new FlatColor(new Vector4f(0, 0, 1, 1));

    public final static FlatColor YELLOW = new FlatColor(new Vector4f(1, 1, 0, 1));

    private final Vector4f ambient;

    public FlatColor() {
        this(new Vector4f());
    }

    public FlatColor(Vector4f ambient) {
        this.ambient = ambient;
    }

    public void setAmbient(float r, float g, float b, float a) {
        ambient.set(r, g, b, a);
    }

    public Vector4f getAmbient() {
        return ambient;
    }

    @Override
    public String toString() {
        return ambient.toString();
    }
}
