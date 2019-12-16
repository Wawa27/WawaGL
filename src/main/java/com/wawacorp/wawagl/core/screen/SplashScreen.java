package com.wawacorp.wawagl.core.screen;

import com.wawacorp.wawagl.core.view.buffer.texture.Texture2D;
import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.shape.Rectangle;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.TextureProperty;
import com.wawacorp.wawagl.core.view.single.mesh.GLSingleMesh;

public class SplashScreen extends Scene {
    private final GLSingleMesh textureMesh;

    static {

    }

    public SplashScreen() {
        textureMesh = new GLSingleMesh(
                new Rectangle(Game.width, Game.height),
                new Instance()
        );
        textureMesh.getInstance().addProperty(new EntityProperty("model", new Entity()));
        textureMesh.getInstance().addProperty(new TextureProperty("texture.ambient", Texture2D.DEFAULT));
    }

    @Override
    public void onLoop() {
        Camera.ACTIVE.bind();
        textureMesh.draw();
        Camera.ACTIVE.unbind();
    }
}
