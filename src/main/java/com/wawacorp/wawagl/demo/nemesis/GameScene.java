package com.wawacorp.wawagl.demo.nemesis;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.camera.FPSCamera;
import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.camera.TPSCameraNoClip;
import com.wawacorp.wawagl.core.camera.projection.Perspective;
import com.wawacorp.wawagl.core.model.Bitmap;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.entity.Player;
import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.terrain.HeightmapTerrain;
import com.wawacorp.wawagl.core.terrain.ProceduralTerrain;
import com.wawacorp.wawagl.core.terrain.Terrain;
import com.wawacorp.wawagl.core.utils.io.AssimpLoader;
import com.wawacorp.wawagl.core.view.single.GLSingleMesh;
import com.wawacorp.wawagl.core.view.single.GLSingleModel;
import org.joml.Vector3f;

import java.util.Iterator;

public class GameScene extends Scene {
    private final Terrain world = new HeightmapTerrain(Bitmap.load("textures/heightmaps/test_128x128.png"));
    private final GLSingleMesh worldView;
    private final CameraController cameraController;
    private final Player player;
    private final GLSingleModel playerView;

    public GameScene() {
        player = new Player(); // The player entity
        player.scale(2, 2, 2);
        playerView = new GLSingleModel(
                AssimpLoader.loadModel(
                        "models/character/character.obj", "models/character/"
                ),
                player
        ); // View of player entity
        worldView = new GLSingleMesh(world, new Entity());
        Camera camera = new TPSCamera(player, new Vector3f(0, 8, -8), new Perspective((float) (Math.toRadians(60)), Game.width / (float) Game.height, .01f, -100f));
        Camera.setActive(camera);
        cameraController = new CameraController(camera);
        world.addEntity(player);
    }

    @Override
    public void onLoop() {
        world.update();
        worldView.draw();
        playerView.draw();
    }
}
