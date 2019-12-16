package com.wawacorp.wawagl.demo.animation;

import com.wawacorp.wawagl.core.animation.Animation;
import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.camera.projection.Perspective;
import com.wawacorp.wawagl.core.model.AScene;
import com.wawacorp.wawagl.core.model.MaterialTexture;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.utils.AssimpLoader;
import com.wawacorp.wawagl.core.view.GLMesh;
import com.wawacorp.wawagl.core.view.GLModel;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

public class AnimationDemo extends Scene {
    private TPSCamera camera;
    private GLModel model;
    private Entity entity;

    public AnimationDemo() {
        AScene scene = AssimpLoader.loadScene("models/guy/source/guy.fbx", "models/guy/textures/");
        Model rootModel = scene.getRoot();
        entity = new Entity();
        MaterialTexture materialTexture = new MaterialTexture();
        materialTexture.setAmbientPath("models/guy/textures/guy_tex.tga");
        materialTexture.setSpecularPath("models/guy/textures/guy_tex.tga");
        materialTexture.setDiffusePath("models/guy/textures/guy_tex.tga");
        for (Mesh mesh : rootModel.getAllMeshes()) {
            mesh.setTexture(materialTexture);
        }
        this.model = GLModel.getSingleModel(rootModel, entity);
        new PlayerController(rootModel);
        camera = new TPSCamera(Perspective.DEFAULT, entity, 8, (float) Math.PI / 8, 0, 0);
        Camera.setActive(camera);
        new CameraController(camera);
    }

    @Override
    public void onLoop() {
        glfwSetCursorPos(Game.window, Game.width/2f, Game.height/2f);
        Animation.loop();
        model.draw();
    }

    public static void main(String[] args) {
        Game game = new Game(960, 640, "SingleMeshDemo");
        game.setScene(new AnimationDemo());
        game.setBackgroundColor(new Vector4f(1, 0, 0, 1));
        game.start();
    }
}
