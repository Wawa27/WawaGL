package com.wawacorp.wawagl.core.opengl.hud.nanovg.component;

import com.wawacorp.wawagl.core.opengl.hud.HudComponent;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.HudListener;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.NanoVG;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import static org.lwjgl.nanovg.NanoVG.*;

public class Button extends HudComponent {
    private HudListener hudListener;
    private final NVGColor color;
    private final float x;
    private final float y;
    private final float h;
    private final float w;
    private String text;

    public Button(float x, float y, float w, float h, String text, NVGColor color) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.text = text;
        this.color = color;
    }

    public void setHudListener(HudListener hudListener) {

    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw() {
        float cornerRadius = 4.0f;
        float[] tw = new float[4];
        NVGPaint bg = NanoVG.getPaint();

        nvgLinearGradient(NanoVG.CONTEXT, x, y, x + w, y + h, NanoVG.getColor(1, 0, 0, 1), NanoVG.getColor(0, 0, 1, 1), bg);
        nvgBeginPath(NanoVG.CONTEXT);

        nvgRoundedRect(NanoVG.CONTEXT, x, y, w, h, cornerRadius);
        nvgFillColor(NanoVG.CONTEXT, NanoVG.getColor(1, 0, 0, 1));
        nvgFill(NanoVG.CONTEXT);
        nvgStrokeWidth(NanoVG.CONTEXT, 4);
        nvgStrokeColor(NanoVG.CONTEXT, NanoVG.getColor(0, 0, 0, 1));
        nvgStroke(NanoVG.CONTEXT);
        nvgClosePath(NanoVG.CONTEXT);

        nvgBeginPath(NanoVG.CONTEXT);
        nvgTextBounds(NanoVG.CONTEXT, 0, 0, text, tw);

        nvgTextAlign(NanoVG.CONTEXT, NVG_ALIGN_MIDDLE);
        nvgFillColor(NanoVG.CONTEXT, NanoVG.getColor(1, 1, 1, 1));
        nvgText(NanoVG.CONTEXT, x + w * 0.5f - tw[2] * 0.5f, y + h * 0.5f - 1, text);

        nvgClosePath(NanoVG.CONTEXT);
    }

    public static void draw(float x, float y, String text) {
        nvgText(NanoVG.CONTEXT, x, y, text);
    }
}
