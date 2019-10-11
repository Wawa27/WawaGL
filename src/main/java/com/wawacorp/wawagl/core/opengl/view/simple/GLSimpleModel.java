package com.wawacorp.wawagl.core.opengl.view.simple;

import com.wawacorp.wawagl.core.opengl.model.model.Mesh;
import com.wawacorp.wawagl.core.opengl.model.model.Model;
import com.wawacorp.wawagl.core.opengl.entity.Entity;
import com.wawacorp.wawagl.core.opengl.shader.Shader;
import com.wawacorp.wawagl.core.opengl.view.GLModel;
import org.joml.Vector3f;

import java.util.ArrayList;

public class GLSimpleModel extends GLModel {
    private String name;

    private ArrayList<GLSimpleMesh> meshes;
    private ArrayList<GLSimpleModel> models;

    public GLSimpleModel(Model model) {
        this(model, new Vector3f());
    }

    public GLSimpleModel(Model model, Vector3f position) {
        this.name = model.getName();

        meshes = new ArrayList<>();
        for (Mesh m : model.getMeshes()) {
            meshes.add(new GLSimpleMesh(m, new Entity(position)));
        }

        models = new ArrayList<>();
        for (Model m : model.getModels()) {
            models.add(new GLSimpleModel(m, position));
        }
    }

    @Override
    public void draw() {
        for (GLSimpleMesh mesh : meshes) mesh.draw();
        for (GLModel model : models) model.draw();
    }

    @Override
    public void draw(Shader shader) {
        for (GLSimpleMesh mesh : meshes) mesh.draw(shader);
        for (GLSimpleModel model : models) model.draw(shader);
    }

    @Override
    public void translate(float x, float y, float z) {
        for (GLSimpleMesh mesh : meshes) mesh.translate(x, y, z);
        for (GLSimpleModel model : models) model.translate(x, y, z);
    }

    @Override
    public void rotate(float x, float y, float z) {
        for (GLSimpleMesh mesh : meshes) mesh.rotate(x, y, z);
        for (GLSimpleModel model : models) model.rotate(x, y, z);
    }

    @Override
    public void scale(float x, float y, float z) {
        for (GLSimpleMesh mesh : meshes) mesh.scale(x, y, z);
        for (GLSimpleModel model : models) model.scale(x, y, z);
    }

    @Override
    public void setTranslation(float x, float y, float z) {
        for (GLSimpleMesh mesh : meshes) mesh.setTranslation(x, y, z);
        for (GLSimpleModel model : models) model.setTranslation(x, y, z);
    }

    @Override
    public void setRotation(float x, float y, float z) {
        for (GLSimpleMesh mesh : meshes) mesh.setRotation(x, y, z);
        for (GLSimpleModel model : models) model.setRotation(x, y, z);
    }

    @Override
    public void setScale(float x, float y, float z) {
        for (GLSimpleMesh mesh : meshes) mesh.setScale(x, y, z);
        for (GLSimpleModel model : models) model.setScale(x, y, z);
    }

    public ArrayList<GLSimpleMesh> getMeshes() {
        return meshes;
    }

    public ArrayList<GLSimpleModel> getModels() {
        return models;
    }

    public String getName() {
        return name;
    }

    public final ArrayList<GLSimpleMesh> getAllMeshes(ArrayList<GLSimpleMesh> meshes) {
        meshes.addAll(this.meshes);
        for(GLSimpleModel model : models) {
            model.getAllMeshes(meshes);
        }

        return meshes;
    }

    @Override
    public String toString() {
        return name;
    }
}
