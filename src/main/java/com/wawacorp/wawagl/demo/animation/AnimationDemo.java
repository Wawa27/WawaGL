package com.wawacorp.wawagl.demo.animation;

import com.wawacorp.wawagl.core.animation.Animation;
import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.camera.projection.Perspective;
import com.wawacorp.wawagl.core.model.*;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.model.terrain.HeightmapTerrain;
import com.wawacorp.wawagl.core.model.terrain.Terrain;
import com.wawacorp.wawagl.core.utils.AssimpLoader;
import com.wawacorp.wawagl.core.view.GLModel;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.FlatColorProperty;
import com.wawacorp.wawagl.core.view.single.GLBoneView;
import com.wawacorp.wawagl.core.view.single.GLSingleView;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

public class AnimationDemo extends Scene {
    private TPSCamera camera;
    private GLModel model;
    private Entity entity;
    private ArrayList<GLBoneView> glBoneViews;

    public AnimationDemo() {
        Entity terrainEntity = new Entity() {
            @Override
            public void onLoop() {

            }
        };
        terrainEntity.scale(10, 1, 10);
        AScene scene = AssimpLoader.loadScene("models/tree_2/tree.fbx", "");
        Model rootModel = scene.getRoot();
        entity = new Entity() {
            @Override
            public void onLoop() {

            }
        };
        this.model = GLModel.getSingleModel(rootModel, entity);
        new PlayerController(rootModel);
        camera = new TPSCamera(Perspective.DEFAULT, entity, 8, (float) Math.PI / 8, 0, 0);
        Camera.setActive(camera);
        new CameraController(camera);
        glBoneViews = new ArrayList<>();
        for (Mesh mesh : model.getModel().getAllMeshes()) {
            if (mesh.getArmature() != null) glBoneViews.add(new GLBoneView(model, mesh.getArmature().getBones()[0]));
        }
    }

    @Override
    public void onLoop() {
        glfwSetCursorPos(Game.window, Game.width / 2f, Game.height / 2f);
        Animation.loop();
        model.draw();
        for (GLBoneView boneView : glBoneViews) boneView.draw();
    }

    public static void main(String[] args) {
        Game game = new Game(960, 640, "SingleMeshDemo");
        game.setScene(new AnimationDemo());
        game.setBackgroundColor(new Vector4f(1, 0, 0, 1));
        game.start();
    }
}
