package com.wawacorp.wawagl.core.manager;

import com.wawacorp.wawagl.core.model.SpriteSheet2D;
import com.wawacorp.wawagl.core.view.hud.nanovg.Font;
import com.wawacorp.wawagl.core.model.Bitmap;
import com.wawacorp.wawagl.core.buffer.texture.Texture2D;
import com.wawacorp.wawagl.core.buffer.texture.Texture2DArray;
import com.wawacorp.wawagl.core.buffer.texture.TextureAtlas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    private AssetManager() { }

    public static Font getFont(String fontName) {
        return fonts.get(fontName);
    }

    public static Texture2D getTexture2D(String relativePath) {
        if (textures.get(relativePath) != null) return textures.get(relativePath);
        Texture2D newTexture = new Texture2D(Bitmap.load(relativePath));
        textures.put(relativePath, newTexture);
        return newTexture;
    }

    public static Texture2DArray getTextureArray(String... paths) {
        Bitmap[] images = new Bitmap[paths.length];
        for (int i = 0; i < paths.length; i++) {
            images[i] = Bitmap.load(paths[i]);
        }
        return new Texture2DArray(images);
    }

    public static Texture2DArray getTextureArraySheet(int[] columns, int[] rows, String... paths) {
        SpriteSheet2D[] spriteSheets = new SpriteSheet2D[paths.length];
        for (int i = 0; i < paths.length; i++) {
            spriteSheets[i] = SpriteSheet2D.load(paths[i], columns[i], rows[i]);
        }
        return new Texture2DArray(spriteSheets);
    }

    public static Texture2DArray getTextureArrayInFolder(String folderPath) {
        File folder = new File(AssetManager.class.getClassLoader().getResource(folderPath).getPath());
        File[] files = folder.listFiles();
        Bitmap[] images = new Bitmap[files.length];
        for (int i = 0; i < files.length; i++) {
            images[i] = Bitmap.load(folderPath + files[i].getName());
        }
        return new Texture2DArray(images);
    }

    public static Texture2DArray getTextureArrayInFolders(String... folderPaths) {
        ArrayList<Bitmap> images = new ArrayList<>();
        for (String folderPath : folderPaths) {
            File folder = new File(AssetManager.class.getClassLoader().getResource(folderPath).getPath());
            File[] files = folder.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    images.add(Bitmap.load(folderPath + files[i].getName()));
                }
            }
        }
        return new Texture2DArray(images.toArray(new Bitmap[images.size()]));
    }

    public static Texture2DArray getTextureArrayInFolder(String folderPath, String... fileNames) {
        Bitmap[] images = new Bitmap[fileNames.length];
        for (int i = 0; i < images.length; i++) {
            images[i] = Bitmap.load(folderPath + fileNames[i]);
        }
        return new Texture2DArray(images);
    }

    @Deprecated
    public static TextureAtlas getTextureAtlas(String relativePath, int cellCount) {
        if (textureAtlas.get(relativePath) != null) return textureAtlas.get(relativePath);
        TextureAtlas newTextureAtlas = new TextureAtlas(Bitmap.load(relativePath), cellCount);
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
