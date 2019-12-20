package com.wawacorp.wawagl.demo.single;

import com.wawacorp.wawagl.core.animation.Animation;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.animation.Bone;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.utils.AssimpLoader;
import com.wawacorp.wawagl.core.view.GLModel;
import org.joml.Vector4f;

import java.awt.*;

public class SingleModelDemo extends Scene {
    private final GLModel model;

    public SingleModelDemo() {
        Model model = AssimpLoader.loadScene("models/guy/source/guyv2.fbx").getRoot();
        this.model = GLModel.getSingleModel(model, new Entity() {
            @Override
            public void onLoop() {

            }
        });
//        model.getSkeletalAnimation()[0].setAnimationEndListener(model.getSkeletalAnimation()[0]::start);
//        model.getSkeletalAnimation()[0].start();
    }

    @Override
    public void onLoop() {
        model.draw();
        Animation.loop();
    }

    public static void main(String[] args) {
        Game game = new Game(960, 640, "SingleModelDemo");
        game.setScene(new SingleModelDemo());
        game.setBackgroundColor(new Vector4f(.33f, .33f, .33f, 1));
        game.start();
    }
}
