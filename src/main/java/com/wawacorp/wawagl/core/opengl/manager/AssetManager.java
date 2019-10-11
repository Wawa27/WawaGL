package com.wawacorp.wawagl.core.opengl.manager;

import com.wawacorp.wawagl.core.opengl.hud.nanovg.Font;
import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Image2D;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.Texture;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.Texture2D;

import java.io.IOException;
import java.util.HashMap;

public class AssetManager {
    private final static HashMap<String, Font> fonts;
    private final static HashMap<String, Texture2D> textures;

    public final static Font DEFAULT_FONT;

    static {
        fonts = new HashMap<>();
        textures = new HashMap<>();

        try {
            DEFAULT_FONT = loadFont("fonts/sunday/sunday.ttf", "sunday");
            DEFAULT_FONT.bind();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't load default font");
        }
    }

    private AssetManager() {
    }

    public static Font getFont(String fontName) {
        return fonts.get(fontName);
    }

    public static Texture2D getTexture2D(String relativePath) {
        if (textures.get(relativePath) != null) return textures.get(relativePath);
        Texture2D newTexture = new Texture2D(Image2D.load(relativePath));
        textures.put(relativePath, newTexture);
        return newTexture;
    }

    public static Font loadFont(String path, String fontName) throws IOException {
        if (fonts.get(fontName) != null) return fonts.get(fontName);
        Font newFont;
        newFont = Font.load(path, fontName);
        fonts.put(fontName, newFont);
        return newFont;
    }
}
