package com.wawacorp.wawagl.demo.snake;

import com.wawacorp.wawagl.core.opengl.model.shape.Rectangle;
import com.wawacorp.wawagl.core.opengl.view.simple.GLMaterialMesh;

public abstract class GameObject extends GLMaterialMesh {

    public GameObject(float x, float y, float z) {
        super(new Rectangle());
        translate(x, y, z);
    }
}
