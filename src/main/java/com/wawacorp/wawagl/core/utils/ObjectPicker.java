package com.wawacorp.wawagl.core.utils;

import com.wawacorp.wawagl.core.opengl.view.GLMesh;

import java.util.ArrayList;

public class ObjectPicker {
    protected final ArrayList<GLMesh> selectableGameItems;

    public ObjectPicker() {
        selectableGameItems = new ArrayList<>();
    }

    public void addMesh(GLMesh mesh) {
        selectableGameItems.add(mesh);
    }

    public GLMesh onPicked() {
        return null;
    }
}
