package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.opengl.manager.AssetManager;
import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Material;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.Texture2D;
import org.joml.Vector4f;

public class Ground extends GameObject {
    public static Material GROUND_TEXTURE;

    static {
        GROUND_TEXTURE = new Material(new Vector4f(139/255.0f,69/255.0f,19/255.0f, 1), new Vector4f(1, 1, 1, 1), new Vector4f(1, 1, 1, 1));
    }

    public Ground(float x, float y) {
        super(x, y, 0);
        setMaterial(GROUND_TEXTURE);
    }
}
