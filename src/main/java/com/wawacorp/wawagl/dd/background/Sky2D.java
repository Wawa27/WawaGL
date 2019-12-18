package com.wawacorp.wawagl.dd.background;

import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.view.buffer.texture.Texture2D;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.shape.Rectangle;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.TextureProperty;
import com.wawacorp.wawagl.core.view.single.GLSingleView;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Plane with a texture applied to it
 */
public class Sky2D extends GLSingleView {
    private final Vector2f vector2f;
    private final Entity entity;

    public Sky2D(Texture2D texture) {
        super(
                new Rectangle(960, 640),
                new Instance(
                        new TextureProperty("texture0", texture),
                        new EntityProperty("model", new Entity(new Vector3f(0, 0, 1)) {
                            @Override
                            public void onLoop() {

                            }
                        })
                ),
                Shader.loadShader("shaders/vert/skybox2d.glsl", "shaders/frag/skybox2d.glsl")
        );
        this.entity = new Entity() {
            @Override
            public void onLoop() {

            }
        };
        this.entity.translate(Game.width/2f, Game.height/2f, 0);
        getInstance().addProperty(new EntityProperty("model", entity));
        vector2f = new Vector2f();
    }

    public void move(float x, float y) {
        vector2f.add(x/(float) Game.width, y);
        shader.bind();
        shader.setVec2("offset", vector2f);
        shader.unbind();
    }
}
