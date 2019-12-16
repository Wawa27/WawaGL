package com.wawacorp.wawagl.core.model.animation;

import com.wawacorp.wawagl.core.animation.Animation;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.assimp.AINode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SkeletalAnimation extends Animation {
    /**
     * The name of the animation
     */
    private String name;

    /**
     * The length in ticks of the animation
     */
    private double duration;
    private double ticksPerSecond;

    private ArrayList<BoneAnimation> boneAnimations;

    private Matrix4f inverseGlobalTransform;

    private List<Bone> rootBones;

    public SkeletalAnimation(String name, Matrix4f inverseGlobalTransform, List<Bone> rootBones, double duration, double ticksPerSecond) {
        super((long) (duration/ticksPerSecond * 1000), TimeUnit.MILLISECONDS);
        this.inverseGlobalTransform = inverseGlobalTransform;
        this.rootBones = rootBones;
        this.name = name;
        this.duration = duration;
        this.ticksPerSecond = ticksPerSecond;
        this.boneAnimations = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        System.out.println("Started Animation : " + this);
        for (BoneAnimation boneAnimation : boneAnimations) {
            boneAnimation.start();
        }
    }

    @Override
    protected void onLoop() {
        double tickPassed = (timePassed() / ticksPerSecond);

        for (BoneAnimation boneAnimation : boneAnimations) {
            boneAnimation.updateCurrentBoneTransform(tickPassed);
        }

        for (Bone bone : rootBones) {
            bone.applyBoneTransform(inverseGlobalTransform, new Matrix4f());
        }
    }

    @Override
    protected void onEnd() {
        System.out.println("Ended Animation : " + this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getTicksPerSecond() {
        return ticksPerSecond;
    }

    public void setTicksPerSecond(double ticksPerSecond) {
        this.ticksPerSecond = ticksPerSecond;
    }

    public void addBoneAnimation(BoneAnimation boneAnimation) {
        this.boneAnimations.add(boneAnimation);
    }

    public void setInverseGlobalTransform(Matrix4f inverseGlobalTransform) {
        this.inverseGlobalTransform = inverseGlobalTransform;
    }

    public ArrayList<BoneAnimation> getBoneAnimations() {
        return boneAnimations;
    }

    @Override
    public String toString() {
        return "Animation: " + name + " duration: " + super.duration + "(ms)";
    }
}
