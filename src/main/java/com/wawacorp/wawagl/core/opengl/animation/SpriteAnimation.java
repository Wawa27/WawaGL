package com.wawacorp.wawagl.core.opengl.animation;

import com.wawacorp.wawagl.core.opengl.camera.Camera;
import com.wawacorp.wawagl.core.opengl.game.Game;
import com.wawacorp.wawagl.core.opengl.shader.Shader;
import com.wawacorp.wawagl.core.opengl.view.simple.GLTextureArrayMesh;

import java.util.concurrent.TimeUnit;

public class SpriteAnimation extends Animation {
    private GLTextureArrayMesh sprite;
    private int[] animation;
    private long timePerSprite;
    private int layer;

    public SpriteAnimation(GLTextureArrayMesh sprite, int[] animation, long timePerSprite) {
        super((timePerSprite + 1) * animation.length, TimeUnit.MILLISECONDS);
        this.sprite = sprite;
        this.animation = animation;
        this.timePerSprite = timePerSprite;
        this.layer = 0;
    }

    @Override
    protected void onStart() {
        this.layer = 0;
        updateShader(sprite.getShader(), animation[0]);
    }

    @Override
    protected void loop() {
        if (getElapsedTime() > (layer + 1) * timePerSprite) {
            if (layer + 1 >= animation.length) return;
            updateShader(sprite.getShader(), animation[++layer]);
        }
    }

    private void updateShader(Shader shader, int layer) {
        shader.bind();
        shader.setTextureArrayLayer(layer);
        shader.unbind();
    }
}
