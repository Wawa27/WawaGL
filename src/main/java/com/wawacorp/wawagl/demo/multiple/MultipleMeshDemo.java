package com.wawacorp.wawagl.demo.multiple;

import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.multiple.mesh.GLMultipleMesh;
import org.joml.Vector4f;

public class MultipleMeshDemo extends Scene {
    private final GLMultipleMesh CUBE = new GLMultipleMesh(
            new Cube(), Shader.loadShaderRelative("multiple", "multiple_flat_color"), new Instance[16]
    );

    public MultipleMeshDemo() {
        for (int i = 0; i < 8; i++) {
            Instance instance = new Instance();
            CUBE.addInstance(instance);
        }
    }

    @Override
    public void onLoop() {
        CUBE.draw();
    }

    public static void main(String[] args) {
        Game game = new Game(960, 640, "Nemesis");
        game.setScene(new MultipleMeshDemo());
        game.setBackgroundColor(new Vector4f(0, 0, 0, 1));
        game.start();
    }
}
