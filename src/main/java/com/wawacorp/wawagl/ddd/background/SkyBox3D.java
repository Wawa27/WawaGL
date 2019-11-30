package com.wawacorp.wawagl.ddd.background;

import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.view.single.GLSingleMesh;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.shader.Shader;

public class SkyBox3D extends GLSingleMesh {
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

    public SkyBox3D(String[] paths) {
        super(cube, new Entity());
        setShader(Shader.loadShader("", ""));
        scale(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
}
