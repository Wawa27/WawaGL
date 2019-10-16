package com.wawacorp.wawagl.demo.hud;

import com.wawacorp.wawagl.core.opengl.game.Game;
import com.wawacorp.wawagl.core.opengl.camera.Camera;
import com.wawacorp.wawagl.core.opengl.hud.Hud;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.NanoVG;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.component.Button;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.component.Text;

import static org.lwjgl.opengl.GL11.*;

public class DemoHUD extends Game {
    private final Hud hud;

    public DemoHUD() {
        super(1920, 1080, "Demo HUD");
        Camera.setActive(Camera.DEFAULT_ORTHOGRAPHIC);
        hud = new Hud();
        hud.addComponent(new Text(0, 16, "Test TEXT"));
        hud.addComponent(new Button(64, 64, 128, 64, "test BUTTON", NanoVG.getColor(0, 1, 0, 1)));
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);

        glStencilFunc(GL_ALWAYS, 1, 0xFF);
        glStencilMask(0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
    }

    @Override
    public void onLoop() {
        Camera.ACTIVE.bind();
        clear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

        hud.draw();

        Camera.ACTIVE.unbind();

        apply();
        pollEvents();
    }

    public static void main(String[] args) {
        new DemoHUD().start();
    }
}
