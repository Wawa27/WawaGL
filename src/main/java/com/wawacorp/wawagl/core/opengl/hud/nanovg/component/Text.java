package com.wawacorp.wawagl.core.opengl.hud.nanovg.component;

import com.wawacorp.wawagl.core.opengl.hud.HudComponent;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.Font;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.NanoVG;
import com.wawacorp.wawagl.core.opengl.manager.AssetManager;

import static org.lwjgl.nanovg.NanoVG.nvgText;

public class Text extends HudComponent {
    private float x;
    private float y;
    private StringBuilder text;
    private Font font;

    public Text(float x, float y, StringBuilder text) {
        this(x, y, text, AssetManager.DEFAULT_FONT);
    }

    public Text(float x, float y, StringBuilder text, Font font) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
    }

    @Override
    public void draw() {
        Font.ACTIVE.bind();
        nvgText(NanoVG.CONTEXT, x, y, text.toString());
    }
}
