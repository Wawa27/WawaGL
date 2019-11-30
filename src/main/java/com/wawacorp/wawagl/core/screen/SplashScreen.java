package com.wawacorp.wawagl.core.screen;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.manager.AssetManager;
import com.wawacorp.wawagl.core.model.shape.Rectangle;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.view.single.GLSingleTextureMesh;
import org.joml.Vector3f;

public class SplashScreen extends Scene {
    private final GLSingleTextureMesh textureMesh;

    static {
    }

    public SplashScreen() {
        textureMesh = new GLSingleTextureMesh(new Rectangle(Game.width, Game.height), new Entity(new Vector3f(Game.width/2f, Game.height/2f, 0)), AssetManager.getTexture2D("textures/WawaGL.png"));
    }

    @Override
    public void onLoop() {
        Camera.ACTIVE.bind();
        textureMesh.draw();
        Camera.ACTIVE.unbind();
    }
}
