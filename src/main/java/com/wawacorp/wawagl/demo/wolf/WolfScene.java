package com.wawacorp.wawagl.demo.wolf;

import com.wawacorp.wawagl.core.animation.Animation;
import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.camera.projection.Perspective;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.entity.EntityManager;
import com.wawacorp.wawagl.core.model.entity.WaterEntity;
import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.model.shape.Rectangle;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.model.terrain.Water;
import com.wawacorp.wawagl.core.utils.AssimpLoader;
import com.wawacorp.wawagl.core.view.GLModel;
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
    private final Entity entity = new Entity() {
        @Override
        public void onLoop() {

        }
    };
    private final Entity playerEntity = new Entity() {
        @Override
        public void onLoop() {

        }
    };
    private final GLModel model = GLModel.getSingleModel(
            AssimpLoader.loadScene("models/new_char/new_char.fbx").getRoot(),
            playerEntity
    );
    private final Entity terrainEntity = new Entity(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)) {
        @Override
        public void onLoop() {

        }
    };
    private final GLSingleView terrain = new GLSingleView(
            new LowPolyHeightmapTerrain(96, 96),
            new Instance(
                    new EntityProperty(terrainEntity),
                    new LightSceneProperty("lightscene", lightScene)
            ), Shader.getColorArrayFlatShader()
    );

    private final Water water = new Water(32, 32);
    private final WaterEntity waterEntity = new WaterEntity(water);
    private final GLSingleView waterView = new GLSingleView(
            water,
            new Instance(
                    new EntityProperty(waterEntity),
                    new MaterialProperty("material", new Material(
                            "water",
                            new Vector4f(1, 1, 1f, .6f),
                            new Vector4f(0, 0, 1f, .6f),
                            new Vector4f(1, 1, 1f, .6f)
                    )),
                    new LightSceneProperty("lightscene", lightScene)
            ), Shader.getWaterShader()
    );

    private final SkyBox3D skyBox3D = new SkyBox3D(
            new String[]{
                    "textures/skybox/hw_morning/morning_bk.tga",
                    "textures/skybox/hw_morning/morning_dn.tga",
                    "textures/skybox/hw_morning/morning_ft.tga",
                    "textures/skybox/hw_morning/morning_lf.tga",
                    "textures/skybox/hw_morning/morning_rt.tga",
                    "textures/skybox/hw_morning/morning_up.tga",
            }
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

    GLModel treeView = GLModel.getSingleModel(AssimpLoader.loadScene("models/Tree Low Poly.fbx").getRoot(), new Entity(new Vector3f(0, 800, 0)) {
        @Override
        public void onLoop() {

        }
    });

    private GLSingleView sand = new GLSingleView(new Cube(), new Instance(
            new EntityProperty("model", new Entity(new Vector3f(0, -6000, 0), new Vector3f(10240, 5120, 10240)) {
                @Override
                public void onLoop() {

                }
            }),
            new MaterialProperty("material", new Material(
                    "sand",
                    new Vector4f(1, 1, 1, 1),
                    new Vector4f(237 / 255f, 201 / 255f, 175 / 255f, 1),
                    new Vector4f(1, 1, 1, 1)
            )),
            new LightSceneProperty("lightScene", lightScene)
    ), Shader.getMaterialFlatShader());

    private final GLNormalView heightmapNormal = new GLNormalView(
            waterView
    );

    public WolfScene() {
        camera = new TPSCamera(Perspective.DEFAULT, entity, 8, (float) Math.PI / 8, 0, 0);
        Camera.setActive(camera);
        lightScene.addLight(pointLight);
        new CameraController(camera);
        new PlayerController(model.getModel(), playerEntity);
        waterEntity.scale(512, 128, 512);
        waterEntity.translate(0, -256, 0);
        terrainEntity.scale(64, 64, 64);
    }

    @Override
    public void onLoop() {
        glfwSetCursorPos(Game.window, Game.width / 2f, Game.height / 2f);
        Animation.loop();
        EntityManager.loop();
        sand.draw();
        waterView.draw();
        pointLight.draw();
    }
}
