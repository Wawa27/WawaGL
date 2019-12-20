package com.wawacorp.wawagl.core.utils;

import de.javagl.jgltf.impl.v2.GlTF;
import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.io.GltfModelReader;
import de.javagl.jgltf.model.io.v1.GltfReaderV1;
import de.javagl.jgltf.model.io.v2.GltfAssetV2;
import de.javagl.jgltf.model.io.v2.GltfReaderV2;
import de.javagl.jgltf.model.v2.GltfModelV2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class GLTFUtils {

    public static GltfModelV2 load(String path) throws IOException {
        GltfModelReader gltfModelReader = new GltfModelReader();
        return (GltfModelV2) gltfModelReader.read(new File(path).toURI());
    }
}
