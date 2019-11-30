package com.wawacorp.wawagl.dd.background;

import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.buffer.texture.Texture2D;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.shape.InfiniteRectangle;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.single.GLSingleTextureMesh;
import org.joml.Vector2f;

/**
 * Plane with a texture applied to it
 */
public class Sky2D extends GLSingleTextureMesh {
    private Vector2f vector2f;

    public Sky2D(Texture2D texture) {
        super(new InfiniteRectangle(texture.getWidth(), texture.getHeight()), new Entity());
        setTexture(texture);
        setShader(Shader.loadShader("shaders/vert/skybox2d.glsl", "shaders/frag/skybox2d.glsl"));
        vector2f = new Vector2f();
        translate(Game.width/2f, Game.height/2f, 0);
    }

    @Override
    public void draw() {
        super.draw();
    }

    public void move(float x, float y) {
        vector2f.add(x/(float) Game.width, y);
        shader.bind();
        shader.setVec2("offset", vector2f);
        shader.unbind();
    }
}
