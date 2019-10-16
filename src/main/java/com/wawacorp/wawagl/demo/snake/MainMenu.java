package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.opengl.camera.Camera;
import com.wawacorp.wawagl.core.opengl.camera.projection.Orthographic;
import com.wawacorp.wawagl.core.opengl.entity.Entity;
import com.wawacorp.wawagl.core.opengl.game.Game;
import com.wawacorp.wawagl.core.opengl.hud.Hud;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.NanoVG;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.component.Button;
import com.wawacorp.wawagl.core.opengl.manager.AssetManager;
import com.wawacorp.wawagl.core.opengl.model.shape.Rectangle;
import com.wawacorp.wawagl.core.opengl.scene.Scene;
import com.wawacorp.wawagl.core.opengl.view.simple.GLTextureMesh;

import static com.wawacorp.wawagl.core.opengl.game.Game.*;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

public class MainMenu extends Scene {
    private final GLTextureMesh background;
    private final Hud hud;
    private boolean isClicked = false;

    public MainMenu() {
        hud = new Hud();
        hud.addComponent(new Button(width/2f - 64, 128, 128, 64, "Play", NanoVG.getColor(1, 1, 1, 1)));
        Entity entity = new Entity();
        entity.scale(Game.width, Game.height, 1);
        background = new GLTextureMesh(new Rectangle(), entity, AssetManager.getTexture2D("assets/SnakeGame_PNG/StartScreen.png"));
        Camera.setActive(new Camera(new Orthographic(-width/2f, width/2f, height/2f, -height/2f, 0.01f, -100f)));
    }

    @Override
    public void onLoop() {
        while (!isClicked && !glfwWindowShouldClose(window)) {
            Camera.ACTIVE.bind();
            clear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
            background.draw();
            clear(GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
            hud.draw();
            Camera.ACTIVE.unbind();
            apply();
            pollEvents();
        }
    }
}
