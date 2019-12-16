package com.wawacorp.wawagl.core.model.animation;

import org.joml.Matrix4f;

import java.util.Observable;
import java.util.Observer;

/**
 * Defines the weights and index of the bones
 */
public class Armature extends Observable implements Observer {
    private Bone[] bones;

    private int[] ids;
    private float[] weights;

    public Armature(Bone[] bones, int[] ids, float[] weights) {
        for (Bone bone : bones) bone.addObserver(this::update);
        this.bones = bones;
        this.ids = ids;
        this.weights = weights;
    }

    public float[] getWeights() {
        return weights;
    }

    public int[] getIds() {
        return ids;
    }

    public void setWeights(float[] weights) {
        this.weights = weights;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }

    public Bone[] getBones() {
        return bones;
    }

    public void setBones(Bone[] bones) {
        this.bones = bones;
    }

    public Matrix4f[] getBoneTransforms() {
        Matrix4f[] boneTransforms = new Matrix4f[bones.length];
        for (int i = 0; i < bones.length; i++) {
            boneTransforms[i] = bones[i].getCurrentGlobalTransform();
        }
        return boneTransforms;
    }

    @Override
    public void update(Observable observable, Object o) {
        update();
    }

    /**
     * Checks that for every vertex in the weights array, the sum of the weight is equals to 1
     * @return
     */
    public float validate() {
        for (int i = 0; i < weights.length / 4; i++) {
            float weightSum = 0;
            for (int j = 0; j < 4; j++) {
                weightSum += weights[i * 4 + j];
            }

            if (weightSum > 1.002f || weightSum < 0.998f) return weightSum;
        }

        return 0;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
