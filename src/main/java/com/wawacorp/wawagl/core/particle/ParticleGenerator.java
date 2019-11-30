package com.wawacorp.wawagl.core.particle;

import com.wawacorp.wawagl.core.manager.ParticleManager;

public abstract class ParticleGenerator {

    public ParticleGenerator() {
        ParticleManager.addParticleGenerator(this);
    }
}
