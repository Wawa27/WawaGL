package com.wawacorp.wawagl.core.opengl.animation;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

// TODO: Shader animation (so the bus don't get flooded)
public abstract class Animation {
    /**
     * Holds all the active animations
     */
    private static final ArrayList<Animation> activeAnimations;

    static {
        activeAnimations = new ArrayList<>();
    }

    /**
     * Holds the start of the animation in nanoseconds, -1 if hasn't started yet
     */
    private long start;

    /**
     * Duration of the animation in the given time unit
     */
    private long duration;

    /**
     * The animation to start once this one is finished
     */
    private Animation onFinishedAnimation;

    /**
     * An infinite animation, it is the same as instancing an Animation and setting the next animation to {@code this}
     */
    public Animation() {
        this(600, TimeUnit.SECONDS);
        this.onFinishedAnimation = this;
    }

    /**
     * Creates an Animation
     * @param duration The time of the animation
     * @param timeUnit The time unit of the animation
     */
    public Animation(int duration, TimeUnit timeUnit) {
        this.duration = timeUnit.toNanos(duration);
        this.start = -1;
    }

    /**
     * Runs all the active animations.
     * Removes all the animations that finished.
     * This methods should be called once (and only once) per frame (not necessarily on the main thread)
     */
    public static void runAll() {
        for (Animation animation : activeAnimations) {
            if (System.nanoTime() < animation.start + animation.duration) { // active
                animation.loop();
            } else { // No longer active
                animation.start = -1;
                activeAnimations.remove(animation);
                if (animation.onFinishedAnimation != null) animation.onFinishedAnimation.start();
            }
        }
    }

    /**
     * Starts the animation.
     * Results in a NO-OP if the animation has already started.
     */
    public final void start() {
        if (start != -1) return;
        start = System.nanoTime();
        activeAnimations.add(this);
    }

    /**
     * Resumes the animation.
     * Results in a NO-OP if the animation has already started.
     */
    public final void resume() {
        if (start != -1) return;
        start = -1;
        activeAnimations.add(this);
    }

    /**
     * Stops the animation and resets the timer.
     * #pause() doesn't reset the timer
     * Results in a NO-OP if the animation has not started yet.
     */
    public final void pause() {
        if (start == -1) return;
        start = -1;
        activeAnimations.remove(this);
    }

    /**
     * Stops the animation without resetting the timer
     * Results in a NO-OP if the animation has not started yet.
     */
    public final void stop() {
        if (start == -1) return;
        start = -1;
        activeAnimations.remove(this);
    }

    /**
     * @return The time elapsed since the start of the animation, in nanoseconds
     */
    public final long getTime() {
        return System.nanoTime() - start;
    }

    /**
     * Starts the {@code animation} once {@code this} animation is finished.
     * @param animation The animation to start once this animation is finished,
     *                  - If it has started before {@code this} has finished, nothing will happens.
     *                  - If it is the same as {@code this}, it will results in an infinite animation.
     */
    public final void setOnFinishedAnimation(Animation animation) {
        this.onFinishedAnimation = animation;
    }

    /**
     * This method is called once every frame
     */
    protected abstract void loop();
}
