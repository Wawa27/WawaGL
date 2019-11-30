package com.wawacorp.wawagl.core.scene;

public abstract class Scene {
    private Scene nextScene;

    // TODO: Game should contains scenes
    // TODO: when the scene's main loop ends, it start the next scene
    // TODO: if there is no next scene, exits the Game
    public Scene() {

    }

    public final void start() {
        onLoop();
        nextScene.start();
    }

    /**
     * Sets the scene that will be started when this scene ends
     * @param scene
     */
    public final void setNextScene(Scene scene) {
        nextScene = scene;
    }

    public abstract void onLoop();
}
