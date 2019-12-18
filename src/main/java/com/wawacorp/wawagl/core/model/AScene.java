package com.wawacorp.wawagl.core.model;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.model.animation.Bone;
import com.wawacorp.wawagl.core.model.animation.SkeletalAnimation;
import com.wawacorp.wawagl.core.view.buffer.texture.Texture;
import org.joml.Matrix4f;

import javax.swing.plaf.synth.SynthUI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AScene {
    private final HashMap<Integer, Material> materials;
    private final HashMap<Integer, MaterialTexture> materialTextures;

    private final SkeletalAnimation[] animations;
    private int currentAnimation = 0;
    private final HashMap<String, Bone> bones;
    private ArrayList<Bone> rootBones;
    private Model root;
    private ArrayList<Camera> cameras;

    /**
     * Transforms a point from Bone Space to Global space
     */
    private final Matrix4f inverseGlobalWorldMatrix;

    public AScene(int animationCount, Matrix4f inverseGlobalWorldMatrix) {
        this.inverseGlobalWorldMatrix = inverseGlobalWorldMatrix;
        materials = new HashMap<>();
        materialTextures = new HashMap<>();
        animations = new SkeletalAnimation[animationCount];
        bones = new HashMap<>();
        this.rootBones = new ArrayList<>();
        this.cameras = new ArrayList<>();
    }

    public void setRoot(Model root) {
        this.root = root;
    }

    public Model getRoot() {
        return root;
    }

    public void addMaterial(int index, Material material) {
        this.materials.put(index, material);
    }

    public void setMaterialTexture(int materialIndex, MaterialTexture texture) {
        materialTextures.put(materialIndex, texture);
    }

    public MaterialTexture getMaterialTexture(int materialIndex) {
        return materialTextures.get(materialIndex);
    }

    public void addAnimation(SkeletalAnimation animation) {
        this.animations[currentAnimation++] = animation;
    }

    public SkeletalAnimation[] getAnimations() {
        return animations;
    }

    public Material getMaterial(int index) {
        return this.materials.get(index);
    }

    public void addBone(Bone bone) {
        bones.put(bone.getName(), bone);
    }

    public Bone getBoneHashMap(String boneName) {
        return bones.get(boneName);
    }

    public Collection<Bone> getBones() {
        return bones.values();
    }

    public HashMap<Integer, Material> getMaterials() {
        return materials;
    }

    public void setCurrentAnimation(int currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public Matrix4f getInverseGlobalWorldMatrix() {
        return inverseGlobalWorldMatrix;
    }

    public void addCamera(Camera camera) {
        this.cameras.add(camera);
    }

    public ArrayList<Camera> getCameras() {
        return cameras;
    }

    public void addRootBone(Bone rootBone) {
        rootBones.add(rootBone);
    }

    public List<Bone> getRootBones() {
        return rootBones;
    }

    public Bone getBone(String name) {
        for (Bone bone : rootBones) {
            if (bone.getBone(name) != null) return bone.getBone(name);
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Bone bone : bones.values()) {
            stringBuilder.append(bone + "\r\n");
        }
        return stringBuilder.toString();
    }
}
