package com.wawacorp.wawagl.core.view.hud;

import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.view.hud.nanovg.NanoVG;

import java.util.ArrayList;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.opengl.GL11.*;

public class Hud {
    private final ArrayList<HudComponent> components;

    public Hud() {
        components = new ArrayList<>();
    }

    public void draw() {
        glEnable(GL_STENCIL_TEST);
        nvgBeginFrame(NanoVG.CONTEXT, Game.width, Game.height, 1);
        for (HudComponent component : components) {
            component.draw();
        }
        nvgEndFrame(NanoVG.CONTEXT);
        glDisable(GL_STENCIL_TEST);
    }

    public void addComponent(HudComponent component) {
        components.add(component);
    }
}
