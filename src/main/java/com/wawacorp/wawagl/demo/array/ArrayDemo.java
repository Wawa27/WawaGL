package com.wawacorp.wawagl.demo.array;

import com.wawacorp.wawagl.core.opengl.camera.Camera;
import com.wawacorp.wawagl.core.opengl.camera.projection.Orthographic;
import com.wawacorp.wawagl.core.opengl.entity.Entity;
import com.wawacorp.wawagl.core.opengl.game.Game;
import com.wawacorp.wawagl.core.opengl.manager.AssetManager;
import com.wawacorp.wawagl.core.opengl.model.shape.Rectangle;
import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Image2D;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.TextureArray;
import com.wawacorp.wawagl.core.opengl.skybox.SkyBox2D;
import com.wawacorp.wawagl.core.opengl.view.View;
import com.wawacorp.wawagl.core.opengl.view.simple.GLTextureArrayMesh;
import com.wawacorp.wawagl.core.opengl.view.simple.GLTextureMesh;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL46.*;

public class ArrayDemo extends Game {
    //    private final SkyBox2D sky;
    private final ArrayList<View> tiles;

    public ArrayDemo() {
        super(960, 960, "Atlas Demo");
//        sky = new SkyBox2D("textures/skybox/simple_sky.png");
        Camera.setActive(new Camera(new Orthographic(-width / 2f, width / 2f, height / 2f, -height / 2f, -.01f, 100f)));
        tiles = new ArrayList<>();
        TextureArray textureArray = AssetManager.getTextureArray(
                "textures/array/tiles/"
        );
        for (int j = 0; j < 6; j++) {
            GLTextureArrayMesh tile = new GLTextureArrayMesh(
                    new Rectangle(),
                    textureArray, j);
            tile.scale(64, 64, 1);
            tile.translate(-320 + j * 64, 0, 0);

            tiles.add(tile);
        }

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void onLoop() {
        Camera.ACTIVE.bind();
        for (View tile : tiles) tile.draw();
        Camera.ACTIVE.unbind();
    }

    public static void main(String[] args) {
        new ArrayDemo().start();
    }
}
