package com.wawacorp.wawagl.core.animation;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public abstract class Animation {
    /**
     * Holds all the active animations
     */
    private static final ArrayList<Animation> activeAnimations;

    private AnimationEndListener animationEndListener;

    static {
        activeAnimations = new ArrayList<>();
    }

    /**
     * Holds the start of the animation in milliseconds, -1 if hasn't started yet
     */
    protected long start;

    /**
     * Duration of the animation in MILLI
     */
    protected long duration;

    /**
     * An infinite animation
     */
    public Animation() {
        this(600, TimeUnit.SECONDS);
    }

    /**
     * Creates an Animation
     * @param duration The time of the animation in Milliseconds
     */
    public Animation(long duration) {
        this.duration = duration;
        this.start = -1;
    }

    /**
     * Creates an Animation
     * @param duration The time of the animation
     * @param timeUnit The time unit of the animation
     */
    public Animation(long duration, TimeUnit timeUnit) {
        this.duration = timeUnit.toMillis(duration);
        this.start = -1;
    }

    /**
     * Runs all the active animations.
     * Removes all the animations that finished.
     * This methods should be called once (and only once) per frame (not necessarily on the main thread)
     */
    public static void loop() {
        for (int i = activeAnimations.size() - 1; i >= 0; i--) {
            Animation animation = activeAnimations.get(i);
            if (System.currentTimeMillis() < animation.start + animation.duration) { // active
                animation.onLoop();
            } else { // No longer active
                activeAnimations.remove(animation);
                animation.end();
            }
        }
    }

    /**
     * Starts the animation.
     * NO-OP if the animation has already started.
     */
    public final void start() {
        if (start != -1) return;
        start = System.currentTimeMillis();
        activeAnimations.add(this);
        onStart();
    }

    /**
     * Resumes the animation.
     * NO-OP if the animation has already started.
     */
    public final void resume() {
        if (start != -1) return;
        activeAnimations.add(this);
    }

    /**
     * Stops the animation without resetting the timer
     * NO-OP if the animation has not started yet.
     */
    public final void pause() {
        if (start == -1) return;
        activeAnimations.remove(this);
    }

    /**
     * Stops the animation and reset the timer
     * NO-OP if the animation has not started yet.
     * This method doesn't start the next animation, nor trigger the {@link onAnimationEnd() method}
     */
    public final void stop() {
        if (start == -1) return;
        start = -1;
        activeAnimations.remove(this);
    }

    /**
     * Stops the animation and reset the timer
     * NO-OP if the animation has not started yet.
     * This method starts the next animation if any and trigger the {@link onAnimationEnd() method}
     */
    public final void end() {
        start = -1;
        onEnd();
        if (animationEndListener != null) {
            animationEndListener.onAnimationEnd();
        }
    }

    public final boolean isPaused() {
        return start != -1 && !activeAnimations.contains(this);
    }

    public final boolean isPlaying() {
        return start != -1 && activeAnimations.contains(this);
    }

    public final void setAnimationEndListener(AnimationEndListener listener) {
        this.animationEndListener = listener;
    }

    /**
     * Time passed since start of the animation
     * @return Time passed in Millieconds from the start
     */
    protected final float timePassed() {
        if (!isPlaying()) return -1;
        return System.currentTimeMillis() - start;
    }

    protected abstract void onStart();

    /**
     * This method is called once every frame
     */
    protected abstract void onLoop();

    /**
     * This method is called once every frame
     */
    protected abstract void onEnd();
}
