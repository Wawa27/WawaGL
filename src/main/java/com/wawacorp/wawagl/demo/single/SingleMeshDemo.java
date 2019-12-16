package com.wawacorp.wawagl.demo.single;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.camera.projection.Orthographic;
import com.wawacorp.wawagl.core.model.FlatColor;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.FlatColorProperty;
import com.wawacorp.wawagl.core.view.instance.property.MaterialProperty;
import com.wawacorp.wawagl.core.view.single.mesh.GLSingleMesh;
import org.joml.Vector4f;

public class SingleMeshDemo extends Scene {
    private final Instance instance;
    private final Entity cubeEntity;
    private final GLSingleMesh cube;

    public SingleMeshDemo() {
        cubeEntity = new Entity();
        instance = new Instance(
                new EntityProperty("model", cubeEntity),
                new MaterialProperty("material"),
                new FlatColorProperty("color", FlatColor.GREEN)
        );
        cube = new GLSingleMesh(new Cube(), instance);
    }

    @Override
    public void onLoop() {
        cube.draw();
    }

    public static void main(String[] args) {
        Game game = new Game(960, 640, "SingleMeshDemo");
        game.setScene(new SingleMeshDemo());
        game.setBackgroundColor(new Vector4f(1, 0, 0, 1));
        game.start();
    }
}
