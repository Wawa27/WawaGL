package com.wawacorp.wawagl.core.controller.event;

import com.wawacorp.wawagl.core.game.Game;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public abstract class ClickListener {
    private final static ArrayList<ClickListener> CLICK_LISTENERS;

    static {
        CLICK_LISTENERS = new ArrayList<>();
        glfwSetMouseButtonCallback(Game.window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                switch (action) {
                    case GLFW_PRESS: {
                        for (ClickListener m : CLICK_LISTENERS) m.onMousePressed(button);
                        break;
                    }
                    case GLFW_RELEASE: {
                        for (ClickListener m : CLICK_LISTENERS) m.onMouseReleased(button);
                        break;
                    }
                    case GLFW_REPEAT: {
                        //TODO: ?
                        break;
                    }
                }
            }
        });
    }

    public ClickListener() {
        CLICK_LISTENERS.add(this);
    }

    public abstract void onMousePressed(int button);

    public abstract void onMouseReleased(int button);
}
