package com.wawacorp.wawagl.demo.hordes;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.camera.SimpleCamera;
import com.wawacorp.wawagl.core.camera.projection.Orthographic;
import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.entity.Player;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.utils.AssimpLoader;
import com.wawacorp.wawagl.core.view.GLModel;

public class GameScene extends Scene {
    private final Terrain terrain = new Terrain();
    private final Player player = new Player();
    private final GLModel playerView;

    public GameScene() {
        SimpleCamera camera = new SimpleCamera(Orthographic.DEFAULT);
        camera.rotate((float) Math.asin(1 / Math.sqrt(3)), (float) Math.toRadians(45), 0);
        Camera.setActive(camera);

        Model model = AssimpLoader.loadScene(
                "models/character/character.obj"
        ).getRoot();
        model.getMesh("Cylinder").setMaterial(
                new Material()
        );
        playerView = GLModel.getSingleModel(model, new Entity() {
            @Override
            public void onLoop() {

            }
        });
        player.scale(128, 128, 128);
        new DefaultKeyboardController();
    }

    @Override
    public void onLoop() {
        terrain.draw();
        playerView.draw();
    }
}
