package com.wawacorp.wawagl.core.model;

import com.wawacorp.wawagl.core.animation.Animation;
import com.wawacorp.wawagl.core.model.animation.SkeletalAnimation;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Model {
    private String name;

    private Model parent;
    private ArrayList<Mesh> meshes;
    private ArrayList<Model> models;

    private SkeletalAnimation[] skeletalAnimation;
    private SkeletalAnimation currentAnimation;

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

    /**
     * Returns an array of meshes containing all the meshes of this model, and every sub meshes of this model
     */
    public ArrayList<Mesh> getAllMeshes() {
        ArrayList<Mesh> meshes = new ArrayList<>(this.meshes);
        for (Model model : models) {
            meshes.addAll(model.getAllMeshes());
        }
        return meshes;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public Model getParent() {
        return parent;
    }

    public void setParent(Model parent) {
        this.parent = parent;
    }

    public void setMeshes(ArrayList<Mesh> meshes) {
        this.meshes = meshes;
    }

    public void setModels(ArrayList<Model> models) {
        this.models = models;
    }

    public SkeletalAnimation[] getSkeletalAnimation() {
        return skeletalAnimation;
    }

    public void setSkeletalAnimation(SkeletalAnimation[] skeletalAnimation) {
        this.skeletalAnimation = skeletalAnimation;
    }

    public void startAnimation() {
        skeletalAnimation[0].start();
        skeletalAnimation[0].setAnimationEndListener(this::startAnimation);
    }

    public void startAnimation(String name) {
        for (SkeletalAnimation animation : skeletalAnimation) {
            if (animation.getName().equals(name)) {
                animation.start();
                currentAnimation = animation;
            }
        }
    }

    public SkeletalAnimation getCurrentAnimation() {
        return currentAnimation;
    }

    @Override
    public String toString() {
        return "Model: " + name + (meshes.size() == 0 ? "" : "\r\n" + meshes.stream().map(Object::toString).collect(Collectors.joining("\r\n"))) + "\r\n" + models.stream().map(Object::toString).collect(Collectors.joining());
    }
}
