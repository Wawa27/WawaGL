package com.wawacorp.wawagl.demo.nemesis;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.camera.projection.Perspective;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.entity.Player;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.utils.AssimpLoader;
import com.wawacorp.wawagl.core.view.GLModel;

import java.util.ArrayList;

public class GameScene extends Scene {
    private final ArrayList<Chunk> chunks;
    private final PlayerController playerController;
    private final Player player;
    private final GLModel playerView;

    public GameScene() {
        player = new Player(); // The player entity
        player.scale(2, 2, 2);
        playerView = GLModel.getSingleModel(
                AssimpLoader.loadScene(
                        "models/character/character.obj"
                ).getRoot(), new Entity()
        ); // View of player entity
        chunks = new ArrayList<>();
        for (int i = -8; i < 8; i++) {
            for (int j = -8; j < 8; j++) {
                chunks.add(ChunkGenerator.generate(j, i));
            }
        }
        Camera camera = new TPSCamera(new Perspective((float) (Math.toRadians(60)), Game.width / (float) Game.height, .01f, -100f), player, 8, (float)Math.PI/4, 0);
        Camera.setActive(camera);
        playerController = new PlayerController(player);
    }

    @Override
    public void onLoop() {
        playerView.draw();
        for (Chunk chunk : chunks) chunk.draw();
    }
}
