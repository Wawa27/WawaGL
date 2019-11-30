package com.wawacorp.wawagl.core.view.hud.nanovg;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import java.util.HashMap;

import static org.lwjgl.nanovg.NanoVGGL3.*;

public class NanoVG {
    public final static long CONTEXT = nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES);

    public final static NVGColor COLOR_RED = NVGColor.create().r(255).a(255);

    private static final HashMap<Integer, NVGColor> colors;

    private final static NVGPaint paint;

    static {
        colors = new HashMap<>();
        paint = NVGPaint.create();
    }

    private NanoVG() {}

    public static NVGColor getColor(float r, float g, float b, float a) {
        return NVGColor.create().r(r).g(g).b(b).a(a);
    }

    public static NVGPaint getPaint() {
        return paint;
    }
}
