package com.wawacorp.wawagl.core.manager;

import com.wawacorp.wawagl.core.particle.ParticleGenerator;

import java.util.ArrayList;

public class ParticleManager {
    private final static ArrayList<ParticleGenerator> particleGenerators;

    static {
        particleGenerators = new ArrayList<>();
    }

    private ParticleManager() {

    }

    public static void addParticleGenerator(ParticleGenerator particleGenerator) {
        particleGenerators.add(particleGenerator);
    }
}
