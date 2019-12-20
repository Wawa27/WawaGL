package com.wawacorp.wawagl.core.model.animation;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class BoneScaleAnimation {
    private Vector3f[] scale;
    private double[] timesInTicks;
    private Vector3f currentScale;

    private int currentFrame;

    public BoneScaleAnimation(Vector3f[] scale, double[] timesInTicks) {
        this.scale = scale;
        this.timesInTicks = timesInTicks;
        this.currentScale = new Vector3f(1, 1, 1);
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
            scale[currentFrame].lerp(scale[currentFrame + 1], progress, currentScale);
        }
        return currentScale;
    }

    public void start() {
        currentFrame = 0;
    }
}
