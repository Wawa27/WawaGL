package com.wawacorp.wawagl.core.opengl.skybox;

import com.wawacorp.wawagl.core.opengl.manager.AssetManager;
import com.wawacorp.wawagl.core.opengl.model.shape.Rectangle;
import com.wawacorp.wawagl.core.opengl.view.simple.GLSimpleMesh;
import com.wawacorp.wawagl.core.opengl.entity.Entity;
import com.wawacorp.wawagl.core.opengl.shader.Shader;
import org.joml.Vector3f;

/**
 * Plane with a texture applied to it
 */
public class SkyBox2D extends GLSimpleMesh {
    private final static Rectangle plane;

    static {
        plane = new Rectangle();
    }

    public SkyBox2D(String texturePath) {
        super(plane, new Entity(new Vector3f(0, 0, -4)));
        setTexture(AssetManager.getTexture2D(texturePath));
        setShader(Shader.loadShader("shaders/vert/skybox2d.glsl", "shaders/frag/skybox2d.glsl"));
    }
}
