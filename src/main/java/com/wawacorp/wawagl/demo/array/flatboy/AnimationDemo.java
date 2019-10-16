package com.wawacorp.wawagl.demo.array.flatboy;

import com.wawacorp.wawagl.core.opengl.camera.Camera;
import com.wawacorp.wawagl.core.opengl.camera.projection.Orthographic;
import com.wawacorp.wawagl.core.opengl.game.Game;
import com.wawacorp.wawagl.core.opengl.hud.Hud;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.scene.Debug;
import com.wawacorp.wawagl.core.opengl.manager.AssetManager;
import com.wawacorp.wawagl.core.opengl.model.shape.Rectangle;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.TextureArray;
import com.wawacorp.wawagl.core.opengl.skybox.SkyBox2D;
import com.wawacorp.wawagl.core.opengl.view.View;
import com.wawacorp.wawagl.core.opengl.view.simple.GLTextureArrayMesh;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.lwjgl.opengl.GL46.*;

public class AnimationDemo extends Game {
    private final SkyBox2D sky;
    private final Character character;
    private Debug debug;
    private long lastFrame;

    public AnimationDemo() {
        super(960, 540, "Animation Demo");
        sky = new SkyBox2D(AssetManager.getTexture2D("assets\\Free Platform Game Assets\\Platform Game Assets\\Background\\png\\1920x1080\\Background\\Background1.png"));
        Camera.setActive(new Camera(new Orthographic(-width / 2f, width / 2f, height / 2f, -height / 2f, -.01f, 100f)));
        character = new Character();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        debug = new Debug("60");
        lastFrame = 1;
        new Controller(character);
    }

    @Override
    public void onLoop() {
        debug.setFps((1f / (System.nanoTime() - lastFrame)) * 1000000 + "ms");
        lastFrame = System.nanoTime();
        Camera.ACTIVE.bind();

        glDisable(GL_STENCIL_TEST);
        sky.draw();
        character.draw();

        glEnable(GL_STENCIL_TEST);
        Camera.ACTIVE.unbind();
    }

    public static void main(String[] args) {
        new AnimationDemo().start();
    }
}
