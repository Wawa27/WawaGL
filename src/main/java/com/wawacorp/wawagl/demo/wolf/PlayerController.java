package com.wawacorp.wawagl.demo.wolf;

import com.wawacorp.wawagl.core.controller.event.KeyboardListener;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.entity.Player;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.LightSceneProperty;
import com.wawacorp.wawagl.core.view.instance.property.MaterialProperty;
import com.wawacorp.wawagl.core.view.single.GLSingleView;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerController extends KeyboardListener {
    private final Entity playerEntity;
    private final Model model;

    public PlayerController(Model model, Entity playerEntity) {
        this.model = model;
        this.playerEntity = playerEntity;
    }

    @Override
    public void onKeyPressed(int key) {
        if (key == GLFW_KEY_W) {
            playerEntity.translate(0, 0, 1);
            model.startAnimation();
        } else if (key == GLFW_KEY_F10) {
            Game.enableDebugMode();
        } else if (key == GLFW_KEY_F6) {
            WolfScene.terrainView = new GLSingleView(
//            new HeightmapTerrain(Bitmap.load("textures/heightmaps/heightmap.png")),
                    new LowPolyHeightmapTerrain((float) (Math.random() * 1000000f), 192, 192, 8),
                    new Instance(
                            new EntityProperty(WolfScene.terrainEntity),
                            new LightSceneProperty("lightscene", WolfScene.lightScene),
                            new MaterialProperty("material", Material.DEFAULT)
                    ), Shader.getColorArrayFlatShader()
            );
        }
    }

    @Override
    public void onKeyReleased(int key) {

    }

    @Override
    public void onKeyRepeated(int key) {

    }
}
