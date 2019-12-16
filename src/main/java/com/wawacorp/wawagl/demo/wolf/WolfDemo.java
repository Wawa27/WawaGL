package com.wawacorp.wawagl.demo.wolf;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.game.Game;
import org.joml.Vector4f;

public class WolfDemo extends Game {

    public WolfDemo() {
        super(960, 640, "Wolf Demo");
        setScene(new WolfScene());
        setBackgroundColor(new Vector4f(0, 0, 0, 1));
    }

    public static void main(String[] args) {
        new WolfDemo().start();
    }
}
