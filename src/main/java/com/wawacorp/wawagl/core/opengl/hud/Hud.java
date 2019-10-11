package com.wawacorp.wawagl.core.opengl.hud;

import com.wawacorp.wawagl.core.opengl.game.Game;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.NanoVG;

import java.util.ArrayList;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;

public class Hud {
    private final ArrayList<HudComponent> components;

    public Hud() {
        components = new ArrayList<>();
    }

    public void draw() {
        nvgBeginFrame(NanoVG.CONTEXT, Game.width, Game.height, 1);
        for (HudComponent component : components) {
            component.draw();
        }
        nvgEndFrame(NanoVG.CONTEXT);
    }

    public void addComponent(HudComponent component) {
        components.add(component);
    }
}
