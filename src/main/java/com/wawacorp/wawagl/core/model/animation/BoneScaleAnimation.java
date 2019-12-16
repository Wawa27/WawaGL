package com.wawacorp.wawagl.core.model.animation;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BoneScaleAnimation {
    private Vector3f[] scale;
    private double[] timesInTicks;

    private int currentFrame;

    public BoneScaleAnimation(Vector3f[] scale, double[] timesInTicks) {
        this.scale = scale;
        this.timesInTicks = timesInTicks;
    }

    private void updateCurrentFrame(double ticksPassed) {
        if (currentFrame < scale.length - 1) {
            if (ticksPassed >= timesInTicks[currentFrame + 1]) {
                currentFrame++;
            }
        }
    }

    public Vector3f getCurrentScale(double ticksPassed) {
        updateCurrentFrame(ticksPassed);
        if (currentFrame < scale.length - 1) {
            float progress = (float) ((ticksPassed - timesInTicks[currentFrame]) / (timesInTicks[currentFrame + 1] - timesInTicks[currentFrame]));
            return scale[currentFrame].lerp(scale[currentFrame + 1], progress, new Vector3f());
        } else {
            return scale[currentFrame];
        }
    }

    public void start() {
        currentFrame = 0;
    }
}
