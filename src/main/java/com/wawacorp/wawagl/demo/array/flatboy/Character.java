package com.wawacorp.wawagl.demo.array.flatboy;

import com.wawacorp.wawagl.core.opengl.animation.SpriteAnimation;
import com.wawacorp.wawagl.core.opengl.entity.Entity;
import com.wawacorp.wawagl.core.opengl.game.Game;
import com.wawacorp.wawagl.core.opengl.manager.AssetManager;
import com.wawacorp.wawagl.core.opengl.model.shape.Rectangle;
import com.wawacorp.wawagl.core.opengl.view.View;
import com.wawacorp.wawagl.core.opengl.view.simple.GLTextureArrayMesh;

public class Character extends GLTextureArrayMesh implements View {
    private final static int[] DEAD_ANIMATION = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private final static int[] RUN_ANIMATION = {14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28};
    private final static int[] WALK_ANIMATION = {29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43};
    private final static int[] IDLE_ANIMATION = {44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58};
    private final static int[] JUMP_ANIMATION = {59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73};

    private SpriteAnimation deadAnimation;
    private SpriteAnimation runAnimation;
    private SpriteAnimation walkAnimation;
    private SpriteAnimation idleAnimation;
    private SpriteAnimation jumpAnimation;

    private SpriteAnimation currentAnimation;

    public Character() {
        super(
                new Rectangle(),
                new Entity(),
                AssetManager.getTextureArrayInFolder(
                        "assets/flatboy/",
                        "dead1.png",
                        "dead2.png",
                        "dead3.png",
                        "dead4.png",
                        "dead5.png",
                        "dead6.png",
                        "dead7.png",
                        "dead8.png",
                        "dead9.png",
                        "dead11.png",
                        "dead12.png",
                        "dead13.png",
                        "dead14.png",
                        "dead15.png",
                        "run1.png",
                        "run2.png",
                        "run3.png",
                        "run4.png",
                        "run5.png",
                        "run6.png",
                        "run7.png",
                        "run8.png",
                        "run9.png",
                        "run10.png",
                        "run11.png",
                        "run12.png",
                        "run13.png",
                        "run14.png",
                        "run15.png",
                        "walk1.png",
                        "walk2.png",
                        "walk3.png",
                        "walk4.png",
                        "walk5.png",
                        "walk6.png",
                        "walk7.png",
                        "walk8.png",
                        "walk9.png",
                        "walk10.png",
                        "walk11.png",
                        "walk12.png",
                        "walk13.png",
                        "walk14.png",
                        "walk15.png",
                        "idle1.png",
                        "idle2.png",
                        "idle3.png",
                        "idle4.png",
                        "idle5.png",
                        "idle6.png",
                        "idle7.png",
                        "idle8.png",
                        "idle9.png",
                        "idle10.png",
                        "idle11.png",
                        "idle12.png",
                        "idle13.png",
                        "idle14.png",
                        "idle15.png",
                        "jump1.png",
                        "jump2.png",
                        "jump3.png",
                        "jump4.png",
                        "jump5.png",
                        "jump6.png",
                        "jump7.png",
                        "jump8.png",
                        "jump9.png",
                        "jump10.png",
                        "jump11.png",
                        "jump12.png",
                        "jump13.png",
                        "jump14.png",
                        "jump15.png"),
                0
        );
        deadAnimation = new SpriteAnimation(this, DEAD_ANIMATION, 50);

        runAnimation = new SpriteAnimation(this, RUN_ANIMATION, 50);
        runAnimation.setOnFinishedAnimation(runAnimation); // infinite animation

        walkAnimation = new SpriteAnimation(this, WALK_ANIMATION, 50);
        walkAnimation.setOnFinishedAnimation(walkAnimation); // infinite animation

        idleAnimation = new SpriteAnimation(this, IDLE_ANIMATION, 75);
        idleAnimation.setOnFinishedAnimation(idleAnimation); // infinite animation

        jumpAnimation = new SpriteAnimation(this, JUMP_ANIMATION, 50);
        jumpAnimation.setOnFinishedAnimation(runAnimation);

        scale(160, 160, 1);
        run();
    }

    public void die() {
        deadAnimation.start();
        currentAnimation = deadAnimation;
    }

    public void run() {
        runAnimation.start();
        currentAnimation = runAnimation;
    }

    public void walk() {
        walkAnimation.start();
        currentAnimation = walkAnimation;
    }

    public void idle() {
        idleAnimation.start();
        currentAnimation = idleAnimation;
    }

    public void jump() {
        jumpAnimation.start();
        currentAnimation = jumpAnimation;
    }

    @Override
    public void draw() {
        super.draw();
    }
}
