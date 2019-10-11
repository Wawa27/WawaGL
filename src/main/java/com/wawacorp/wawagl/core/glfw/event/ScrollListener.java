package com.wawacorp.wawagl.core.glfw.event;

import com.wawacorp.wawagl.core.opengl.game.Game;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public abstract class ScrollListener {
    private final static ArrayList<ScrollListener> SCROLL_LISTENERS;

    static {
        SCROLL_LISTENERS = new ArrayList<>();

        glfwSetScrollCallback(Game.window, new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                for (ScrollListener m : SCROLL_LISTENERS) m.onScroll(xoffset, yoffset);
            }
        });
    }

    public ScrollListener() {
        SCROLL_LISTENERS.add(this);
    }

    public abstract void onScroll(double xoffset, double yoffset);
}
