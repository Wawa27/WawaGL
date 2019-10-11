package com.wawacorp.wawagl.core.utils.io;

import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Material;
import com.wawacorp.wawagl.core.opengl.model.model.Mesh;
import com.wawacorp.wawagl.core.opengl.model.model.Model;
import org.joml.Vector4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static com.wawacorp.wawagl.core.utils.io.FileUtils.ioResourceToByteBuffer;
import static org.lwjgl.assimp.Assimp.*;
import static org.lwjgl.system.MemoryUtil.*;

public class AssimpLoader {

    // TODO: Add another methods without the texture folder
    public static Model loadModel(String relativePath, String textureFolder) {
        AIFileIO fileIo = AIFileIO.create();
        AIFileOpenProcI fileOpenProc = new AIFileOpenProc() {
            public long invoke(long pFileIO, long fileName, long openMode) {
                AIFile aiFile = AIFile.create();
                final ByteBuffer data;
                String fileNameUtf8 = memUTF8(fileName);
                try {
                    data = ioResourceToByteBuffer(fileNameUtf8, 8192);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Could not open file: " + fileNameUtf8);
                }
                AIFileReadProcI fileReadProc = new AIFileReadProc() {
                    public long invoke(long pFile, long pBuffer, long size, long count) {
                        long max = Math.min(data.remaining(), size * count);
                        memCopy(memAddress(data) + data.position(), pBuffer, max);
                        return max;
                    }
                };
                AIFileSeekI fileSeekProc = new AIFileSeek() {
                    public int invoke(long pFile, long offset, int origin) {
                        if (origin == Assimp.aiOrigin_CUR) {
                            data.position(data.position() + (int) offset);
                        } else if (origin == Assimp.aiOrigin_SET) {
                            data.position((int) offset);
                        } else if (origin == Assimp.aiOrigin_END) {
                            data.position(data.limit() + (int) offset);
                        }
                        return 0;
                    }
                };
                AIFileTellProcI fileTellProc = new AIFileTellProc() {
                    public long invoke(long pFile) {
                        return data.limit();
                    }
                };
                aiFile.ReadProc(fileReadProc);
                aiFile.SeekProc(fileSeekProc);
                aiFile.FileSizeProc(fileTellProc);
                return aiFile.address();
            }
        };
        AIFileCloseProcI fileCloseProc = new AIFileCloseProc() {
            public void invoke(long pFileIO, long pFile) {
                /* Nothing to do */
            }
        };
        fileIo.set(fileOpenProc, fileCloseProc, NULL);
        AIScene scene = Assimp.aiImportFileEx(relativePath,
                        aiProcess_Triangulate |
                                aiProcess_DropNormals |
                                aiProcess_GenSmoothNormals |
                        aiProcess_FlipUVs, fileIo);
        System.out.println("finished loading");
        if (scene == null) {
            System.out.println(Assimp.aiGetErrorString());
            System.err.println("NULL scene");
            return null;
        }
        AINode root = scene.mRootNode();
        if (root == null) {
            System.err.println("NULL root");
            return null;
        }
        Model model = toModel(scene, textureFolder, root);
        Assimp.aiReleaseImport(scene);
        return model;
    }

    public static void saveModel() {
        //TODO: save a model using either Assimp or Java Serialisation
    }

    private static Model toModel(AIScene scene, String folderPath, AINode aiNode) {
        System.out.println(aiNode.mName().dataString() + " : " + aiNode.mNumChildren() + " : " + aiNode.mNumMeshes());
        ArrayList<Mesh> meshes = new ArrayList<>();
        PointerBuffer pointerBuffer = scene.mMeshes();
        IntBuffer intBuffer = aiNode.mMeshes();
        for (int i = 0; i < aiNode.mNumMeshes(); i++) {
            AIMesh aiMesh = AIMesh.create(pointerBuffer.get(intBuffer.get()));
            Mesh mesh = toMesh(aiMesh);
            toMesh(scene, folderPath, mesh, aiMesh);
            meshes.add(mesh);
        }

        Model model = new Model(aiNode.mName().dataString(), meshes);

        PointerBuffer children = aiNode.mChildren();
        for (int i = 0; i < aiNode.mNumChildren(); i++) {
            AINode child = AINode.create(children.get());
            Model sub = toModel(scene, folderPath, child);
            if (sub != null) model.addModel(sub);
        }

        if (model.getModels().size() == 0 && model.getMeshes().size() == 0) return null;
        return model;
    }

    private static Mesh toMesh(AIMesh aiMesh) {
        if (aiMesh == null) return null;

        float[] vertices = getVertices3D(aiMesh.mNumVertices(), aiMesh.mVertices());
        float[] normals = getVertices3D(aiMesh.mNumVertices(), aiMesh.mNormals());
        float[] texCoords = getVertices2D(aiMesh.mNumVertices(), aiMesh.mTextureCoords(0));
        int[] indices = getIndices(aiMesh.mNumFaces(), aiMesh.mFaces());

        return new Mesh(aiMesh.mName().dataString(), vertices, normals, texCoords, indices);
    }

    private static void toMesh(AIScene scene, String folderPath, Mesh mesh, AIMesh aiMesh) {
        AIMaterial aiMaterial = AIMaterial.create(scene.mMaterials().get(aiMesh.mMaterialIndex()));
        AIString path = AIString.create();
        if (aiGetMaterialTextureCount(aiMaterial, aiTextureType_DIFFUSE) > 0) {
            if (aiReturn_SUCCESS != aiGetMaterialTexture(aiMaterial, aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null)) {
                System.err.println("Error loading texture");
                return;
            }
            String textPath = path.dataString();
            if (textPath != null) {
                mesh.setTexturePath(folderPath + textPath);
            }
        }
        Material material = getMaterial(aiMaterial);
        mesh.setMaterial(material);
    }

    private static Material getMaterial(AIMaterial material) {
        Material.Builder builder = new Material.Builder();
        System.out.println("Material : ");

        Vector4f vector4f;
        AIColor4D color = AIColor4D.create();
        aiGetMaterialColor(material, AI_MATKEY_COLOR_AMBIENT, 0, 0, color);
        vector4f = new Vector4f(color.r(), color.g(), color.b(), color.a());
        builder.ambient(vector4f);

        aiGetMaterialColor(material, AI_MATKEY_COLOR_DIFFUSE, 0, 0, color);
        vector4f = new Vector4f(color.r(), color.g(), color.b(), color.a());
        builder.diffuse(vector4f);

        aiGetMaterialColor(material, AI_MATKEY_COLOR_SPECULAR, 0, 0, color);
        vector4f = new Vector4f(color.r(), color.g(), color.b(), color.a());
        builder.specular(vector4f);

        return builder.build();
    }

    private static float[] getVertices3D(int size, AIVector3D.Buffer buffer) {
        if (buffer == null) return null;

        float[] array = new float[size * 3];
        for (int i = 0; i < size; i++) {
            AIVector3D vertex = buffer.get(i);
            array[i * 3] = vertex.x();
            array[i * 3 + 1] = vertex.y();
            array[i * 3 + 2] = vertex.z();
        }

        return array;
    }

    private static float[] getVertices2D(int size, AIVector3D.Buffer buffer) {
        if (buffer == null) return null;

        float[] array = new float[size * 2];

        for (int i = 0; i < size; i++) {
            AIVector3D normal = buffer.get();
            array[i * 2] = normal.x();
            array[i * 2 + 1] = normal.y();
        }

        return array;
    }

    private static int[] getIndices(int size, AIFace.Buffer buffer) {
        if (buffer == null) return null;

        int[] indices = new int[size * 3];

        for (int i = 0; i < size; i++) {
            AIFace face = buffer.get();
            IntBuffer intBuffer = face.mIndices();
            indices[i * 3] = intBuffer.get();
            indices[i * 3 + 1] = intBuffer.get();
            indices[i * 3 + 2] = intBuffer.get();
        }

        return indices;
    }
}
