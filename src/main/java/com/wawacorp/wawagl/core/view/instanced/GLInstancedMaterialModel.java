package com.wawacorp.wawagl.core.view.instanced;

import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.GLMesh;
import com.wawacorp.wawagl.core.view.GLModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GLInstancedMaterialModel extends GLModel implements Observer {
    public final static int DEFAULT_MAX_INSTANCE = 4092;
    private final ArrayList<GLMesh> meshes;

    public GLInstancedMaterialModel(Model model) {
        super(model);
        meshes = new ArrayList<>();

        for (Mesh mesh : model.getMeshes()) {
            meshes.add(new GLInstancedMaterialMesh(mesh, DEFAULT_MAX_INSTANCE));
        }
    }

    public GLInstancedMaterialModel(Model model, Material material) {
        super(model);
        meshes = new ArrayList<>();

        for (Mesh mesh : model.getMeshes()) {
            meshes.add(new GLInstancedMaterialMesh(mesh, material, Shader.getInstancedMaterialShader(), DEFAULT_MAX_INSTANCE));
        }
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void draw(Shader shader) {

    }

    @Override
    public void translate(float x, float y, float z) {

    }

    @Override
    public void scale(float x, float y, float z) {

    }

    @Override
    public void rotate(float x, float y, float z) {

    }

    @Override
    public void setTranslation(float x, float y, float z) {

    }

    @Override
    public void setScale(float x, float y, float z) {

    }

    @Override
    public void setRotation(float x, float y, float z) {

    }
}
