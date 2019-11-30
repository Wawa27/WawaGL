package com.wawacorp.wawagl.core.view.single;

import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.GLModel;
import org.joml.Vector3f;

import java.util.ArrayList;

public class GLSingleModel extends GLModel {
    private String name;

    private ArrayList<GLSingleMesh> meshes;
    private ArrayList<GLSingleModel> models;

    public GLSingleModel(Model model) {
        this(model, new Entity());
    }

    @Deprecated
    public GLSingleModel(Model model, Vector3f position) {
        super(model);
        this.name = model.getName();

        meshes = new ArrayList<>();
        for (Mesh m : model.getMeshes()) {
            meshes.add(new GLSingleMesh(m, new Entity(position)));
        }

        models = new ArrayList<>();
        for (Model m : model.getModels()) {
            models.add(new GLSingleModel(m, position));
        }
    }

    public GLSingleModel(Model model, Entity entity) {
        super(model);
        this.name = model.getName();

        meshes = new ArrayList<>();
        for (Mesh m : model.getMeshes()) {
            meshes.add(new GLSingleMesh(m, entity));
        }

        models = new ArrayList<>();
        for (Model m : model.getModels()) {
            models.add(new GLSingleModel(m, entity));
        }
    }

    @Override
    public void draw() {
        for (GLSingleMesh mesh : meshes) mesh.draw();
        for (GLModel model : models) model.draw();
    }

    @Override
    public void draw(Shader shader) {
        for (GLSingleMesh mesh : meshes) mesh.draw(shader);
        for (GLSingleModel model : models) model.draw(shader);
    }

    @Override
    public void translate(float x, float y, float z) {
        for (GLSingleMesh mesh : meshes) mesh.translate(x, y, z);
        for (GLSingleModel model : models) model.translate(x, y, z);
    }

    @Override
    public void rotate(float x, float y, float z) {
        for (GLSingleMesh mesh : meshes) mesh.rotate(x, y, z);
        for (GLSingleModel model : models) model.rotate(x, y, z);
    }

    @Override
    public void scale(float x, float y, float z) {
        for (GLSingleMesh mesh : meshes) mesh.scale(x, y, z);
        for (GLSingleModel model : models) model.scale(x, y, z);
    }

    @Override
    public void setTranslation(float x, float y, float z) {
        for (GLSingleMesh mesh : meshes) mesh.setTranslation(x, y, z);
        for (GLSingleModel model : models) model.setTranslation(x, y, z);
    }

    @Override
    public void setRotation(float x, float y, float z) {
        for (GLSingleMesh mesh : meshes) mesh.setRotation(x, y, z);
        for (GLSingleModel model : models) model.setRotation(x, y, z);
    }

    @Override
    public void setScale(float x, float y, float z) {
        for (GLSingleMesh mesh : meshes) mesh.setScale(x, y, z);
        for (GLSingleModel model : models) model.setScale(x, y, z);
    }

    public ArrayList<GLSingleMesh> getMeshes() {
        return meshes;
    }

    public ArrayList<GLSingleModel> getModels() {
        return models;
    }

    public String getName() {
        return name;
    }

    public final ArrayList<GLSingleMesh> getAllMeshes(ArrayList<GLSingleMesh> meshes) {
        meshes.addAll(this.meshes);
        for(GLSingleModel model : models) {
            model.getAllMeshes(meshes);
        }

        return meshes;
    }

    @Override
    public String toString() {
        return name;
    }
}
