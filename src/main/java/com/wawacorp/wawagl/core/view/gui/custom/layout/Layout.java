package com.wawacorp.wawagl.core.view.gui.custom.layout;

import com.wawacorp.wawagl.core.view.gui.custom.component.Component;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Layout {
    protected final ArrayList<Component> components;
    protected final Vector3f position;

    public Layout() {
        components = new ArrayList<>();
        position = new Vector3f();
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public void update() {

    }
}
