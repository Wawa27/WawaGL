package com.wawacorp.wawagl.demo.hordes;

import com.wawacorp.wawagl.core.game.Game;
import org.joml.Vector4f;

public class Hordes extends Game {

    public Hordes() {
        super(960, 640);
        setScene(new GameScene());
        setBackgroundColor(new Vector4f(0, 0, 0, 1));
    }

    public static void main(String[] args) {
        new Hordes().start();
    }
}
