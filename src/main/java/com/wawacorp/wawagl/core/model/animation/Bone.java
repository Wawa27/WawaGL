package com.wawacorp.wawagl.core.model.animation;

import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Observable;

public class Bone extends Observable {
    private String name;

    /**
     * Children bones
     */
    private ArrayList<Bone> bones;

    /**
     * Transforms vertices from bind pose to bone space
     */
    private final Matrix4f offsetMatrix;

    private Matrix4f currentLocalTransform;
    private Matrix4f currentGlobalTransform;

    public Bone(String name, Matrix4f offsetMatrix) {
        this.name = name;
        this.offsetMatrix = offsetMatrix; // identity for bind pose
        this.currentLocalTransform = new Matrix4f();  // identity for bind pose
        this.currentGlobalTransform = new Matrix4f();
        this.bones = new ArrayList<>();
        System.out.println(offsetMatrix);
    }

    public Bone(String name, Matrix4f currentLocalTransform, boolean zzz) {
        this.name = name;
        this.currentLocalTransform = currentLocalTransform; // identity for bind pose
        this.currentGlobalTransform = new Matrix4f();
        this.bones = new ArrayList<>();
        this.offsetMatrix = new Matrix4f();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Matrix4f getOffsetMatrix() {
        return offsetMatrix;
    }

    public void setOffsetMatrix(Matrix4f matrix) {
        this.offsetMatrix.set(matrix);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Bone> getBones() {
        return bones;
    }

    public void setBones(ArrayList<Bone> bones) {
        this.bones = bones;
    }

    public Bone getBone(String name) {
        if (this.name.equals(name)) return this;

        for (Bone b : bones) {
            Bone result = b.getBone(name);
            if (result != null) return result;
        }

        return null;
    }

    public Matrix4f getCurrentLocalTransform() {
        return currentLocalTransform;
    }

    public Matrix4f getCurrentGlobalTransform() {
        return currentGlobalTransform;
    }

    public void setCurrentLocalTransform(Matrix4f currentLocalTransform) {
        this.currentLocalTransform = currentLocalTransform;
    }

    public void addBone(Bone bone) {
        this.bones.add(bone);
    }

    public void applyBoneTransform(Matrix4f globalInverseTransform, Matrix4f parentTransform) {
//        System.out.println("Bone: " + name);

//        System.out.println("Local translation: " + currentLocalTransform.getTranslation(new Vector3f()));
//        System.out.println("Local rotation: " + currentLocalTransform.getRotation(new AxisAngle4f()));
//        System.out.println("Local scale: " + currentLocalTransform.getScale(new Vector3f()));

        currentGlobalTransform = parentTransform.mul(currentLocalTransform, new Matrix4f()); // P * J

//        System.out.println("Global translation: " + parentTransform.getTranslation(new Vector3f()));
//        System.out.println("Global rotation: " + parentTransform.getRotation(new AxisAngle4f()));
//        System.out.println("Global scale: " + parentTransform.getScale(new Vector3f()));

        for (Bone bone : bones) {
            bone.applyBoneTransform(globalInverseTransform, currentGlobalTransform);
        }

        currentGlobalTransform.mul(offsetMatrix); // (P * J) * O
        currentGlobalTransform = globalInverseTransform.mul(currentGlobalTransform, new Matrix4f()); // G * (P * J) * O

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return name;
    }
}
