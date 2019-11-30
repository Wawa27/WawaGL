package com.wawacorp.wawagl.core.particle;

import com.wawacorp.wawagl.core.animation.Animation;

public abstract class Particle extends Animation {
    protected long lifespan;

    public Particle(long lifespan) {
        this.lifespan = lifespan;
    }
}
