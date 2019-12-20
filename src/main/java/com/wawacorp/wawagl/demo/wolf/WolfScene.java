package com.wawacorp.wawagl.demo.wolf;

import com.sun.source.tree.Tree;
import com.wawacorp.wawagl.core.animation.Animation;
import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.camera.projection.Perspective;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.AScene;
import com.wawacorp.wawagl.core.model.Bitmap;
import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.entity.EntityManager;
import com.wawacorp.wawagl.core.model.entity.WaterEntity;
import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.model.shape.Rectangle;
import com.wawacorp.wawagl.core.model.terrain.HeightmapTerrain;
import com.wawacorp.wawagl.core.model.terrain.Terrain;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.model.terrain.Water;
import com.wawacorp.wawagl.core.utils.AssimpLoader;
import com.wawacorp.wawagl.core.utils.MathUtils;
import com.wawacorp.wawagl.core.view.GLModel;
import com.wawacorp.wawagl.core.view.GLView;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.LightSceneProperty;
import com.wawacorp.wawagl.core.view.instance.property.MaterialProperty;
import com.wawacorp.wawagl.core.view.lighting.DirectionalLight;
import com.wawacorp.wawagl.core.view.lighting.LightScene;
import com.wawacorp.wawagl.core.view.single.GLNormalView;
import com.wawacorp.wawagl.core.view.single.GLSingleView;
import com.wawacorp.wawagl.ddd.background.SkyBox3D;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

public class WolfScene extends Scene {
    public final static LightScene lightScene = new LightScene();

    private final TPSCamera camera;

    public final static Entity terrainEntity = new Entity(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)) {
        @Override
        public void onLoop() { }
    };

    public static Terrain terrain =new LowPolyHeightmapTerrain(8, 192, 192, 8);
    public static GLSingleView terrainView = new GLSingleView(
            terrain,
            new Instance(
                    new EntityProperty(terrainEntity),
                    new LightSceneProperty("lightscene", lightScene),
                    new MaterialProperty("material", Material.DEFAULT)
            ), Shader.getColorArrayFlatShader()
    );

    private final static Entity treeEntity = new Entity() {
        @Override
        public void onLoop() {

        }
    };
    public static GLModel treeView = GLModel.getSingleModel(
            AssimpLoader.loadScene("models/tree_2/tree.fbx", "").getRoot(),
            treeEntity
    );

    private final Water water = new Water(192, 192);
    private final WaterEntity waterEntity = new WaterEntity(water);
    private final GLSingleView waterView = new GLSingleView(
            water,
            new Instance(
                    new EntityProperty(waterEntity),
                    new MaterialProperty("material", new Material(
                            "water",
                            new Vector4f(0, 0, 3, 1),
                            new Vector4f(1.6f, 1.6f, 2, 1),
                            new Vector4f(3, 3, 3, 1)
                    )),
                    new LightSceneProperty("lightscene", lightScene)
            ), Shader.getWaterShader()
    );

    private final GLView rectangleView = new GLSingleView(
            new Rectangle(),
            new Instance(
                    new EntityProperty(new Entity(new Vector3f(), new Vector3f(64, 1, 64), new Vector3f((float) Math.PI/2f, 0, 0))),
                    new MaterialProperty("", Material.DEFAULT),
                    new LightSceneProperty("lightScene", lightScene)
            ),
            Shader.getMaterialShader()
    );

    DirectionalLight pointLight = new DirectionalLight(
            new Cube(),
            new Entity(new Vector3f(0, 1024, 0), new Vector3f(64, 64, 64)) {
                @Override
                public void onLoop() {
                }
            },
            new Vector4f(1, 1, 1, 1),
            new Vector4f(0, -1, 0, 1)
    );

    public WolfScene() {
        camera = new TPSCamera(Perspective.DEFAULT, terrainEntity, 8, (float) Math.PI / 8, 0, 0);
        Camera.setActive(camera);
        lightScene.addLight(pointLight);
        new CameraController(camera);
        terrain.addTree(treeEntity);
    }

    @Override
    public void onLoop() {
        glfwSetCursorPos(Game.window, Game.width / 2f, Game.height / 2f);
        Animation.loop();
        EntityManager.loop();
        terrainView.draw();
        treeView.draw();
        rectangleView.draw();
    }
}
