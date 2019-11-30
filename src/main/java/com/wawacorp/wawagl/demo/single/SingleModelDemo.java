package com.wawacorp.wawagl.demo.single;

import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.utils.io.AssimpLoader;
import com.wawacorp.wawagl.core.view.single.GLSingleModel;
import org.joml.Vector4f;


public class SingleModelDemo extends Scene {
    private final GLSingleModel cube = new GLSingleModel(
            AssimpLoader.loadModel("models/rubiks/Robik.obj", "models/rubiks/")
    );

    public SingleModelDemo() {
        cube.scale(32, 32, 32);
    }

    @Override
    public void onLoop() {
        cube.draw();
    }

    public static void main(String[] args) {
        Game game = new Game(960, 640, "Nemesis");
        game.setScene(new SingleModelDemo());
        game.setBackgroundColor(new Vector4f(0, 0, 0, 1));
        game.start();
    }
}
