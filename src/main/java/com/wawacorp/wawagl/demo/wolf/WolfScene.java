package com.wawacorp.wawagl.demo.wolf;

import com.wawacorp.wawagl.core.animation.Animation;
import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.camera.projection.Perspective;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.FlatColor;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.utils.AssimpLoader;
import com.wawacorp.wawagl.core.view.GLModel;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.FlatColorProperty;
import com.wawacorp.wawagl.core.view.instance.property.LightSceneProperty;
import com.wawacorp.wawagl.core.view.lighting.LightScene;
import com.wawacorp.wawagl.core.view.lighting.PointLight;
import com.wawacorp.wawagl.core.view.single.mesh.GLSingleMesh;
import com.wawacorp.wawagl.ddd.background.SkyBox3D;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

public class WolfScene extends Scene {
    private final TPSCamera camera;
    private final Entity entity = new Entity();
    private final Entity playerEntity = new Entity();
    private final GLModel model = GLModel.getSingleModel(
            AssimpLoader.loadScene("models/new_char/new_char.fbx").getRoot(),
            playerEntity
    );
    private final GLSingleMesh floor = new GLSingleMesh(
            new Cube(),
            new Instance(
                    new EntityProperty(new Entity(new Vector3f(0, -1.075f, 0), new Vector3f(10, .01f, 10))),
                    new FlatColorProperty(FlatColor.RED)
            ), Shader.getFlatColorShader()
    );
    private final SkyBox3D skyBox3D = new SkyBox3D(
            new String[] {
                    "textures/skybox/hw_morning/morning_bk.tga",
                    "textures/skybox/hw_morning/morning_dn.tga",
                    "textures/skybox/hw_morning/morning_ft.tga",
                    "textures/skybox/hw_morning/morning_lf.tga",
                    "textures/skybox/hw_morning/morning_rt.tga",
                    "textures/skybox/hw_morning/morning_up.tga",
            }
    );
    public final static LightScene lightScene = new LightScene();

    public WolfScene() {
        camera = new TPSCamera(Perspective.DEFAULT, entity, 8, (float) Math.PI / 8, 0, 0);
        Camera.setActive(camera);
        new CameraController(camera);
        new PlayerController(model.getModel(), playerEntity);
//        entity.scale(2, 2, 2);
//        model.getModel().getSkeletalAnimation()[0].start();
//        model.getModel().getSkeletalAnimation()[0].setAnimationEndListener(model.getModel().getSkeletalAnimation()[0]::start);
        lightScene.addLight(new PointLight(null, new Vector3f(0, 1, 0), new Vector3f(1, 1, 1)));
        floor.getInstance().addProperty(new LightSceneProperty("LightScene", lightScene));
    }

    @Override
    public void onLoop() {
        glfwSetCursorPos(Game.window, Game.width/2f, Game.height/2f);
        Animation.loop();
        model.draw();
        floor.draw();
    }
}
