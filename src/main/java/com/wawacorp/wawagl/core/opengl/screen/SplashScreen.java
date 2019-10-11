package com.wawacorp.wawagl.core.opengl.screen;

import com.wawacorp.wawagl.core.opengl.view.simple.GLSimpleMesh;
import com.wawacorp.wawagl.core.opengl.entity.Entity;
import com.wawacorp.wawagl.core.opengl.model.shape.Rectangle;

public class SplashScreen extends GLSimpleMesh {

    public SplashScreen() {
        super(new Rectangle(), new Entity());
    }
}
