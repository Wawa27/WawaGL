package com.wawacorp.wawagl.core.opengl.hud.nanovg.component;

import com.wawacorp.wawagl.core.opengl.hud.HudComponent;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.Font;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.NanoVG;

import static org.lwjgl.nanovg.NanoVG.*;

public class ImmutableText extends HudComponent {
    private float x;
    private float y;
    private String text;

    public ImmutableText(float x, float y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw() {
        Font.ACTIVE.bind();
        nvgText(NanoVG.CONTEXT, x, y, text);
    }

    public static void draw(float x, float y, String text) {
        nvgText(NanoVG.CONTEXT, x, y, text);
    }
}
