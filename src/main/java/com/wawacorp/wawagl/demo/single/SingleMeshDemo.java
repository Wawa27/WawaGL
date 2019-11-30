package com.wawacorp.wawagl.demo.single;

import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.view.single.GLSingleMesh;
import org.joml.Vector4f;


public class SingleMeshDemo extends Scene {
    private final GLSingleMesh cube = new GLSingleMesh(
            new Cube()
    );

    public SingleMeshDemo() {
        cube.scale(32, 32, 32);
    }

    @Override
    public void onLoop() {
        cube.draw();
    }

    public static void main(String[] args) {
        Game game = new Game(960, 640, "Nemesis");
        game.setScene(new SingleMeshDemo());
        game.setBackgroundColor(new Vector4f(0, 0, 0, 1));
        game.start();
    }
}
