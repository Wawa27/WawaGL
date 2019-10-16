package com.wawacorp.wawagl.core.opengl.manager;

import com.wawacorp.wawagl.core.opengl.hud.nanovg.Font;
import com.wawacorp.wawagl.core.opengl.model.model.Model;
import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Image2D;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.Texture;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.Texture2D;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.TextureArray;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.TextureAtlas;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class AssetManager {
    private final static HashMap<String, Font> fonts;
    private final static HashMap<String, Texture2D> textures;
    private final static HashMap<String, TextureAtlas> textureAtlas;

    public final static Font DEFAULT_FONT;

    static {
        fonts = new HashMap<>();
        textures = new HashMap<>();
        textureAtlas = new HashMap<>();

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

    public static TextureArray getTextureArray(String... paths) {
        Image2D[] images = new Image2D[paths.length];
        for (int i = 0; i < paths.length; i++) {
            images[i] = Image2D.load(paths[i]);
        }
        return new TextureArray(images);
    }

    public static TextureArray getTextureArray(Image2D... images) {
        return new TextureArray(images);
    }

    public static TextureArray getTextureArray(String folderPath) {
        File folder = new File(AssetManager.class.getClassLoader().getResource(folderPath).getPath());
        File[] files = folder.listFiles();
        Image2D[] images = new Image2D[files.length];
        for (int i = 0; i < files.length; i++) {
            images[i] = Image2D.load(folderPath + files[i].getName());
        }
        return new TextureArray(images);
    }

    public static TextureArray getTextureArrayInFolder(String folderPath, String... fileNames) {
        Image2D[] images = new Image2D[fileNames.length];
        for (int i = 0; i < images.length; i++) {
            images[i] = Image2D.load(folderPath + fileNames[i]);
        }
        return new TextureArray(images);
    }

    @Deprecated
    public static TextureAtlas getTextureAtlas(String relativePath, int cellCount) {
        if (textureAtlas.get(relativePath) != null) return textureAtlas.get(relativePath);
        TextureAtlas newTextureAtlas = new TextureAtlas(Image2D.load(relativePath), cellCount);
        textureAtlas.put(relativePath, newTextureAtlas);
        return newTextureAtlas;
    }

    public static Font loadFont(String path, String fontName) throws IOException {
        if (fonts.get(fontName) != null) return fonts.get(fontName);
        Font newFont;
        newFont = Font.load(path, fontName);
        fonts.put(fontName, newFont);
        return newFont;
    }
}
