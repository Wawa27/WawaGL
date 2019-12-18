package com.wawacorp.wawagl.core.model.animation;

import org.joml.Vector3f;

public class BoneTranslationAnimation {
    private Vector3f[] translation;
    private double[] timesInTicks;

    private int currentFrame;

    public BoneTranslationAnimation(Vector3f[] translation, double[] timesInTicks) {
        this.translation = translation;
        this.timesInTicks = timesInTicks;
    }

    private void updateCurrentFrame(double ticksPassed) {
        if (currentFrame < translation.length - 1) {
            if (ticksPassed >= timesInTicks[currentFrame + 1]) {
                currentFrame++;
            }
        }
    }

    public Vector3f getCurrentTranslation(double ticksPassed) {
        updateCurrentFrame(ticksPassed);
        if (currentFrame < translation.length - 1) {
            float progress = (float) ((ticksPassed - timesInTicks[currentFrame]) / (timesInTicks[currentFrame + 1] - timesInTicks[currentFrame]));
            return translation[currentFrame].lerp(translation[currentFrame + 1], progress, new Vector3f());
        } else {
            return translation[currentFrame];
        }
    }

    public void restart() {
        currentFrame = 0;
    }
}
