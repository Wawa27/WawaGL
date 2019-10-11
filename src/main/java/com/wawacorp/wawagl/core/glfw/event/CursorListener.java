package com.wawacorp.wawagl.core.glfw.event;

import com.wawacorp.wawagl.core.opengl.game.Game;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;

public abstract class CursorListener {
    private final static ArrayList<CursorListener> CURSOR_LISTENERS;

    static {
        CURSOR_LISTENERS = new ArrayList<>();
        glfwSetCursorPosCallback(Game.window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                for (CursorListener m : CURSOR_LISTENERS) m.onMouseMoved(xpos, ypos);
            }
        });
    }

    public CursorListener() {
        CURSOR_LISTENERS.add(this);
    }

    public abstract void onMouseMoved(double xpos, double ypos);
}
