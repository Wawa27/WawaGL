package com.wawacorp.wawagl.core.opengl.hud.nanovg;

import org.lwjgl.nanovg.NVGColor;

import static org.lwjgl.nanovg.NanoVGGL3.*;

public class NanoVG {
    public final static long CONTEXT = nvgCreate(NVG_STENCIL_STROKES);

    public final static NVGColor COLOR_RED = NVGColor.create().r(255).a(255);

    private NanoVG() {}

    public static NVGColor getColor(float r, float g, float b, float a) {
        return NVGColor.create().r(r).g(g).b(b).a(a);
    }
}
