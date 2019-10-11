package com.wawacorp.wawagl.core.opengl.skybox;

import com.wawacorp.wawagl.core.opengl.model.model.Mesh;
import com.wawacorp.wawagl.core.opengl.model.shape.Cube;
import com.wawacorp.wawagl.core.opengl.view.simple.GLSimpleMesh;
import com.wawacorp.wawagl.core.opengl.entity.Entity;
import com.wawacorp.wawagl.core.opengl.shader.Shader;

public class SkyBox3D extends GLSimpleMesh {
    private final static Mesh cube;

    static {
        cube = new Cube();
    }

    public static class Builder {
        private String[] paths;

        public Builder() {
            paths = new String[6];
        }

        public Builder withRight(String right) {
            paths[0] = right;
            return this;
        }

        public Builder withLeft(String left) {
            paths[1] = left;
            return this;
        }

        public Builder withTop(String top) {
            paths[2] = top;
            return this;
        }

        public Builder withBottom(String bottom) {
            paths[3] = bottom;
            return this;
        }

        public Builder withBack(String back) {
            paths[4] = back;
            return this;
        }

        public Builder withFront(String front) {
            paths[5] = front;
            return this;
        }

        public SkyBox3D build() {
            return new SkyBox3D(paths);
        }
    }

    private SkyBox3D(String[] paths) {
        super(cube, new Entity());
        setShader(Shader.loadShader("", ""));
        scale(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
}
