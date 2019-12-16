package com.wawacorp.wawagl.demo.nemesis;


import com.wawacorp.wawagl.core.view.instance.Instance;

import static com.wawacorp.wawagl.demo.nemesis.Chunk.CHUNK_SIZE;

public class ChunkGenerator {

    public static Chunk generate(int chunkX, int chunkZ) {
        Instance[] entities = new Instance[CHUNK_SIZE];
            for (int x = 0; x < CHUNK_SIZE; x++) {
//                int height = (int) ((1 + SimplexNoise.noise(chunkX * CHUNK_SIZE/8f + x/8f, chunkZ * CHUNK_SIZE/8f + z/8f)) * 4);
//                entities[x] = new MultipleFlatColorInstance();
//                entities[x].translate(x*2, height*2, z*2);
            }
        return new Chunk(entities, chunkX, chunkZ);
    }
}
