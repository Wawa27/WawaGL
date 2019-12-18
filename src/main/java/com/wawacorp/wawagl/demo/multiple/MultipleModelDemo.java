package com.wawacorp.wawagl.demo.multiple;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.utils.AssimpLoader;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.view.GLModel;
import com.wawacorp.wawagl.core.view.instance.Instance;
import org.joml.Vector4f;

import java.util.ArrayList;

public class MultipleModelDemo extends Scene {
    private final GLModel CUBE = GLModel.getSingleModel(
            AssimpLoader.loadScene("").getRoot(),
            new Entity() {
                @Override
                public void onLoop() {

                }
            }
    );
    private final Entity entity;
    private final ArrayList<Instance> instances;

    public MultipleModelDemo() {
        entity = new Entity() {
            @Override
            public void onLoop() {

            }
        };
        instances = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Entity entity = new Entity() {
                @Override
                public void onLoop() {

                }
            };
            entity.translate(i, 0, 0);
            entity.scale(32, 32, 32);
            instances.add(new Instance(entity));
        }
    }

    @Override
    public void onLoop() {
        CUBE.draw();
    }

    public static void main(String[] args) {
        Game game = new Game(960, 640, "Nemesis");
        game.setScene(new MultipleModelDemo());
        game.setBackgroundColor(new Vector4f(0, 0, 0, 1));
        game.start();
    }
}
