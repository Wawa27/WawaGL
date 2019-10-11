package com.wawacorp.wawagl.core.opengl.hud.nanovg;

import java.io.IOException;
import java.nio.ByteBuffer;

import static com.wawacorp.wawagl.core.utils.io.FileUtils.loadBytes;
import static org.lwjgl.nanovg.NanoVG.*;

public class Font {
    public static Font ACTIVE;

    public static int DEFAULT_SIZE = 16;
    public static int DEFAULT_ALIGNEMENT = NVG_ALIGN_LEFT | NVG_ALIGN_TOP;

    private final String name;
    private int size;
    private int aligment;
    // We set the data in attribute to keep the gc from collecting it
    private ByteBuffer data;

    private Font(ByteBuffer data, String name, int size, int alignment) {
        this.data = data;
        this.name = name;
        this.size = size;
        this.aligment = alignment;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setAligment(int aligment) {
        this.aligment = aligment;
    }

    public void bind() {
        if (ACTIVE == this) return;
        ACTIVE = this;
        nvgFontSize(NanoVG.CONTEXT, size);
        nvgFontFace(NanoVG.CONTEXT, name);
        nvgTextAlign(NanoVG.CONTEXT, aligment);
        nvgFillColor(NanoVG.CONTEXT, NanoVG.COLOR_RED);
    }

    public static Font load(String path, String fontName) throws IOException {
        ByteBuffer buffer = loadBytes(path);
        int result = nvgCreateFontMem(NanoVG.CONTEXT, fontName, buffer, 0);
        if (result == -1) return null;
        return new Font(buffer, fontName, Font.DEFAULT_SIZE, Font.DEFAULT_ALIGNEMENT);
    }
}
