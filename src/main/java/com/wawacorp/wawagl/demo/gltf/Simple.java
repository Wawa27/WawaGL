package com.wawacorp.wawagl.demo.gltf;

import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.utils.GLTFUtils;
import de.javagl.jgltf.model.v2.GltfModelV2;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.io.IOException;

public class Simple extends Scene {

    public Simple() throws IOException {
        GltfModelV2 modelV2 = GLTFUtils.load("models/gltf_cube/cube.glb");
        modelV2.getSceneModels().get(0).getNodeModels().get(0).getMatrix();
    }

    @Override
    public void onLoop() {

    }

    public static void main(String[] args) throws IOException {
        Game game = new Game(960, 640, "SingleMeshDemo");
        game.setScene(new Simple());
        game.setBackgroundColor(new Vector4f(1, 0, 0, 1));
        game.start();
    }
}
