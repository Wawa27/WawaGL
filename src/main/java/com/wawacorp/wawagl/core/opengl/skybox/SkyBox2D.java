package com.wawacorp.wawagl.core.opengl.skybox;

import com.wawacorp.wawagl.core.opengl.game.Game;
import com.wawacorp.wawagl.core.opengl.manager.AssetManager;
import com.wawacorp.wawagl.core.opengl.model.shape.Rectangle;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.Texture;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.Texture2D;
import com.wawacorp.wawagl.core.opengl.view.simple.GLSimpleMesh;
import com.wawacorp.wawagl.core.opengl.entity.Entity;
import com.wawacorp.wawagl.core.opengl.shader.Shader;
import com.wawacorp.wawagl.core.opengl.view.simple.GLTextureMesh;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Plane with a texture applied to it
 */
public class SkyBox2D extends GLTextureMesh {
    private final static Rectangle plane;
    private Vector2f vector2f;

    static {
        plane = new Rectangle();
    }

    public SkyBox2D(Texture2D texture) {
        super(new Rectangle(texture.getWidth(), texture.getHeight()));
        setTexture(texture);
        setShader(Shader.loadShader("shaders/vert/skybox2d.glsl", "shaders/frag/skybox2d.glsl"));
        vector2f = new Vector2f();
    }

    @Override
    public void draw() {
        super.draw();
        vector2f.add(0.0001f, 0);
        shader.bind();
        shader.setVec2("offset", vector2f);
        shader.unbind();
    }
}
