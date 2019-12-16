package com.wawacorp.wawagl.core.model.animation;

import org.joml.Quaternionf;

public class BoneRotationAnimation {
    private Quaternionf[] rotations;
    private double[] timesInTicks;

    private int currentFrame = 0;

    public BoneRotationAnimation(Quaternionf[] rotations, double[] timesInTicks) {
        this.rotations = rotations;
        this.timesInTicks = timesInTicks;
    }

    private void updateCurrentFrame(double ticksPassed) {
        if (currentFrame < rotations.length - 1) {
            if (ticksPassed >= timesInTicks[currentFrame + 1]) {
                currentFrame++;
            }
        }
    }

    public Quaternionf getCurrentRotation(double ticksPassed) {
        updateCurrentFrame(ticksPassed);
        if (currentFrame < rotations.length - 1) {
            float progress = (float) ((ticksPassed - timesInTicks[currentFrame]) / (timesInTicks[currentFrame + 1] - timesInTicks[currentFrame]));
            return rotations[currentFrame].slerp(rotations[currentFrame + 1], progress, new Quaternionf()).normalize();
        } else {
            return rotations[currentFrame];
        }
    }

    public void start() {
        currentFrame = 0;
    }
}
