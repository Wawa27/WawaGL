package com.wawacorp.wawagl.demo.single;

import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.camera.projection.Perspective;
import com.wawacorp.wawagl.core.model.FlatColor;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.shape.Rectangle;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.FlatColorProperty;
import com.wawacorp.wawagl.core.view.instance.property.MaterialProperty;
import com.wawacorp.wawagl.core.view.lighting.LightScene;
import com.wawacorp.wawagl.core.view.single.GLNormalView;
import com.wawacorp.wawagl.core.view.single.GLSingleView;
import com.wawacorp.wawagl.demo.wolf.LowPolyHeightmapTerrain;
import org.joml.Vector4f;

public class SingleMeshDemo extends Scene {
    private final Instance instance;
    private final Entity cubeEntity;
    private final GLSingleView cube;

    public SingleMeshDemo() {
        cubeEntity = new Entity() {
            @Override
            public void onLoop() {

            }
        };
        instance = new Instance(
                new EntityProperty("model", cubeEntity),
                new MaterialProperty("material"),
                new FlatColorProperty("color", FlatColor.GREEN)
        );
        Mesh mesh = new Rectangle(4, 4);
        cube = new GLSingleView(mesh, instance);
        LightScene lightScene = new LightScene();
        cubeEntity.rotate(0, .0001f, 0);
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
