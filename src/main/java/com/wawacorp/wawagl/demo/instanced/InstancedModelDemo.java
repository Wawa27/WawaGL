package com.wawacorp.wawagl.demo.instanced;

import com.wawacorp.wawagl.core.utils.io.AssimpLoader;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.view.instanced.GLInstancedMaterialModel;
import org.joml.Vector4f;


public class InstancedModelDemo extends Scene {
    private final GLInstancedMaterialModel CUBE = new GLInstancedMaterialModel(
            AssimpLoader.loadModel("", "")
    );

    public InstancedModelDemo() {
        CUBE.scale(32, 32, 32);
    }

    @Override
    public void onLoop() {
        CUBE.draw();
        CUBE.rotate(.01f, .01f, .01f);
    }

    public static void main(String[] args) {
        Game game = new Game(960, 640, "Nemesis");
        game.setScene(new InstancedModelDemo());
        game.setBackgroundColor(new Vector4f(0, 0, 0, 1));
        game.start();
    }
}
