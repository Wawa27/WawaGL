package com.wawacorp.wawagl.demo.nemesis;

import com.wawacorp.wawagl.core.game.Game;
import org.joml.Vector4f;

public class Nemesis extends Game {

    public Nemesis(int width, int height) {
        super(width, height, "Nemesis");
    }

    public static void main(String[] args) {
        Game game = new Nemesis(960, 540);
        game.setScene(new GameScene());
        game.setBackgroundColor(new Vector4f(.51f, .51f, .51f, 1));
        game.start();
    }
}