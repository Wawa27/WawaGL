package com.wawacorp.wawagl.core.view.instance.property;

import com.wawacorp.wawagl.core.shader.Shader;

import java.util.Observable;
import java.util.Observer;

public abstract class Property extends Observable {
    protected final String name;

    public Property(String name) {
        this.name = name;
    }

    public abstract void upload(Shader shader);

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
