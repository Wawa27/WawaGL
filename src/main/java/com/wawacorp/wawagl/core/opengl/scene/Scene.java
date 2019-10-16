package com.wawacorp.wawagl.core.opengl.scene;

public abstract class Scene {

    public Scene() {

    }

    public void start() {
        onLoop();
    }

    public abstract void onLoop();
}
