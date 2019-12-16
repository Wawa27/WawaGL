package com.wawacorp.wawagl.core.model.animation;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BoneAnimation {
    private Bone bone;

    private BoneRotationAnimation boneRotationAnimation;
    private BoneScaleAnimation boneScaleAnimation;
    private BoneTranslationAnimation boneTranslationAnimation;

    public BoneAnimation(Bone bone, BoneRotationAnimation rotationAnimation, BoneScaleAnimation scaleAnimation, BoneTranslationAnimation translationAnimation) {
        this.bone = bone;
        this.boneRotationAnimation = rotationAnimation;
        this.boneScaleAnimation = scaleAnimation;
        this.boneTranslationAnimation = translationAnimation;
    }

    /**
     * Updates the current bone transform matrix
     */
    public void updateCurrentBoneTransform(double tickPassed) {
        Quaternionf rotation = boneRotationAnimation.getCurrentRotation(tickPassed);
        Vector3f scale = boneScaleAnimation.getCurrentScale(tickPassed);
        Vector3f translation = boneTranslationAnimation.getCurrentTranslation(tickPassed);

        Matrix4f boneMatrix = new Matrix4f().translate(translation).rotate(rotation).scale(scale);
        bone.setCurrentLocalTransform(boneMatrix);
    }

    public void start() {
        boneRotationAnimation.start();
        boneScaleAnimation.start();
        boneTranslationAnimation.restart();
    }

    public Bone getBone() {
        return bone;
    }

    public void setBone(Bone bone) {
        this.bone = bone;
    }
}
