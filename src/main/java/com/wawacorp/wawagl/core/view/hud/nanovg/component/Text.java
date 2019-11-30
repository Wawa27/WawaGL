package com.wawacorp.wawagl.core.view.hud.nanovg.component;

import com.wawacorp.wawagl.core.view.hud.HudComponent;
import com.wawacorp.wawagl.core.view.hud.nanovg.Font;
import com.wawacorp.wawagl.core.view.hud.nanovg.NanoVG;

import static org.lwjgl.nanovg.NanoVG.*;

public class Text extends HudComponent {
    private float x;
    private float y;
    private String text;
    private float[] bounds;

    public Text(float x, float y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.bounds = new float[6];
    }

    private final void computeBounds() {
        nvgTextBounds(NanoVG.CONTEXT, x, y, text, bounds);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw() {
        Font.ACTIVE.bind();
        nvgFillColor(NanoVG.CONTEXT, NanoVG.getColor(1, 0, 0, 1));
        nvgFontSize(NanoVG.CONTEXT, 64);
        nvgText(NanoVG.CONTEXT, x, y, text);
    }

    public static void draw(float x, float y, String text) {
        nvgText(NanoVG.CONTEXT, x, y, text);
    }
}
