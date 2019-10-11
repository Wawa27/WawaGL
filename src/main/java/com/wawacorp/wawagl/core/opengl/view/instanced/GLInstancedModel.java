package com.wawacorp.wawagl.core.opengl.view.instanced;

import com.wawacorp.wawagl.core.opengl.model.model.Mesh;
import com.wawacorp.wawagl.core.opengl.model.model.Model;
import com.wawacorp.wawagl.core.opengl.shader.Shader;
import com.wawacorp.wawagl.core.opengl.view.GLModel;

import java.util.ArrayList;

public class GLInstancedModel extends GLModel {
    private String name;

    private ArrayList<GLInstancedMesh> meshes;
    private ArrayList<GLInstancedModel> models;

    public GLInstancedModel(Model model, int instanceCount)  {
        this.name = model.getName();

        meshes = new ArrayList<>();
        for (Mesh m : model.getMeshes()) {
            meshes.add(new GLInstancedMesh(m, instanceCount));
        }

        models = new ArrayList<>();
        for (Model m : model.getModels()) {
            models.add(new GLInstancedModel(m, instanceCount));
        }
    }

    @Override
    public void draw() {
        for (GLInstancedMesh mesh : meshes) {
            mesh.draw();
        }
        for (GLModel model : models) model.draw();
    }

    @Override
    public void draw(Shader shader) {
        for (GLInstancedMesh mesh : meshes) {
            mesh.draw(shader);
        }
        for (GLModel model : models) model.draw(shader);
    }

    @Override
    public void translate(float x, float y, float z) {
        for (GLInstancedMesh mesh : meshes) mesh.translate(x, y, z);
        for (GLInstancedModel model : models) model.translate(x, y, z);
    }

    @Override
    public void rotate(float x, float y, float z) {
        for (GLInstancedMesh mesh : meshes) mesh.rotate(x, y, z);
        for (GLInstancedModel model : models) model.rotate(x, y, z);
    }

    @Override
    public void scale(float x, float y, float z) {
        for (GLInstancedMesh mesh : meshes) mesh.scale(x, y, z);
        for (GLInstancedModel model : models) model.scale(x, y, z);
    }

    @Override
    public void setTranslation(float x, float y, float z) {
        for (GLInstancedMesh mesh : meshes) mesh.setTranslation(x, y, z);
        for (GLInstancedModel model : models) model.setTranslation(x, y, z);
    }

    @Override
    public void setRotation(float x, float y, float z) {
        for (GLInstancedMesh mesh : meshes) mesh.setRotation(x, y, z);
        for (GLInstancedModel model : models) model.setRotation(x, y, z);
    }

    @Override
    public void setScale(float x, float y, float z) {
        for (GLInstancedMesh mesh : meshes) mesh.setScale(x, y, z);
        for (GLInstancedModel model : models) model.setScale(x, y, z);
    }

    public void translate(int instance, float x, float y, float z) {
        for (GLInstancedMesh mesh : meshes) mesh.translate(instance, x, y, z);
        for (GLInstancedModel model : models) model.translate(instance, x, y, z);
    }

    public void rotate(int instance, float x, float y, float z) {
        for (GLInstancedMesh mesh : meshes) mesh.rotate(instance, x, y, z);
        for (GLInstancedModel model : models) model.rotate(instance, x, y, z);
    }

    public void scale(int instance, float x, float y, float z) {
        for (GLInstancedMesh mesh : meshes) mesh.scale(instance, x, y, z);
        for (GLInstancedModel model : models) model.scale(instance, x, y, z);
    }

    public void addInstance(int instance) {
        for (GLInstancedMesh mesh : meshes) mesh.addInstance(instance);
        for (GLInstancedModel model : models) model.addInstance(instance);
    }

    public void removeInstance(int instance, int activeInstance) {
        for (GLInstancedMesh mesh : meshes) mesh.removeInstance(instance, activeInstance);
        for (GLInstancedModel model : models) model.removeInstance(instance, activeInstance);
    }

    public ArrayList<GLInstancedMesh> getMeshes() {
        return meshes;
    }

    public ArrayList<GLInstancedModel> getChildren() {
        return models;
    }

    public String getName() {
        return name;
    }
}
