package com.wawacorp.wawagl.core.opengl.screen;

import com.wawacorp.wawagl.core.opengl.view.simple.GLSimpleModel;
import com.wawacorp.wawagl.core.opengl.model.model.Model;

public abstract class LoadingScreen extends GLSimpleModel {
    private final static Model model;

    static {
        model = new Model("Loading Screen");
    }

    public LoadingScreen() {
        super(model);
    }


}
