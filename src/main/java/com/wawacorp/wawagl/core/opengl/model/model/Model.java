package com.wawacorp.wawagl.core.opengl.model.model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Model {
    private String name;

    private ArrayList<Mesh> meshes;
    private ArrayList<Model> models;

    public Model(String name) {
        this(name, new ArrayList<>());
    }

    public Model(String name, ArrayList<Mesh> meshes) {
        this.name = name;

        this.meshes = meshes;
        models = new ArrayList<>();
    }

    public void addMesh(Mesh mesh) { meshes.add(mesh); }

    public void addModel(Model child) {
        models.add(child);
    }

    public ArrayList<Mesh> getMeshes() { return meshes; }

    public ArrayList<Model> getModels() { return models; }

    public String getName() { return name; }

    /**
     * Get the {@link Mesh Mesh} from a Name
     * @param name The mesh's name
     * @return the first {@link Mesh Mesh} of the model whose name match with the argument
     */
    public Mesh getMesh(String name) {
        for (Mesh mesh : meshes) if (mesh.getName().equals(name)) return mesh;
        for (Model model : models) {
            Mesh mesh = model.getMesh(name);
            if (mesh != null) return mesh;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Model: " + name + (meshes.size() == 0 ? "" : "\r\n" + meshes.stream().map(Object::toString).collect(Collectors.joining("\r\n"))) + "\r\n" + models.stream().map(Object::toString).collect(Collectors.joining());
    }
}
