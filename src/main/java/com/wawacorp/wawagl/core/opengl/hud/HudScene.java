package com.wawacorp.wawagl.core.opengl.hud;

import java.util.ArrayList;
import java.util.List;

public class HudScene extends HudComponent {
    public final static int MAX_COMPONENT = 64;
    protected final HudComponent[] components;

    public HudScene() {
        components = new HudComponent[MAX_COMPONENT];
    }

    public HudScene(HudComponent... components) {
        this.components = components;
    }

    @Override
    public void draw() {
        for (HudComponent component : components) component.draw();
    }
}
