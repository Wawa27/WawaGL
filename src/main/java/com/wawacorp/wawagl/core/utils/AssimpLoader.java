package com.wawacorp.wawagl.core.utils;

import com.wawacorp.wawagl.core.camera.FPSCamera;
import com.wawacorp.wawagl.core.camera.projection.Perspective;
import com.wawacorp.wawagl.core.model.*;
import com.wawacorp.wawagl.core.model.animation.Armature;
import com.wawacorp.wawagl.core.model.animation.Bone;
import com.wawacorp.wawagl.core.model.animation.SkeletalAnimation;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.wawacorp.wawagl.core.utils.AssimpUtils.*;
import static com.wawacorp.wawagl.core.utils.FileUtils.ioResourceToByteBuffer;
import static org.lwjgl.assimp.Assimp.*;
import static org.lwjgl.system.MemoryUtil.*;

public class AssimpLoader {

    // TODO: Add another methods without the texture folder
    public static AScene loadScene(String relativePath) {
        return loadScene(relativePath, relativePath);
    }

    // TODO: Add another methods without the texture folder
    public static AScene loadScene(String relativePath, String texturePath) {
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
        AIScene aiScene = Assimp.aiImportFileEx(relativePath,
                aiProcess_Triangulate |
                        aiProcess_GenSmoothNormals |
                        aiProcess_JoinIdenticalVertices |
                        aiProcess_CalcTangentSpace |
                        aiProcess_FixInfacingNormals |
                        aiProcess_LimitBoneWeights |
                        aiProcess_FlipUVs, fileIo);
        if (aiScene == null) {
            System.out.println(Assimp.aiGetErrorString());
            System.err.println("NULL scene");
            return null;
        }
        AINode root = aiScene.mRootNode();
        if (root == null) {
            System.err.println("NULL root");
            return null;
        }

        AScene AScene = new AScene(aiScene.mNumAnimations(), toMatrix4f(root.mTransformation()));
        getAllMaterials(texturePath, aiScene, AScene);
        AScene.setRoot(toModel(aiScene, AScene, root));

        List<AINode> rootBoneNode = findRootBones(AScene, root);
        for (AINode boneNode : rootBoneNode) {
            Bone bone = AScene.getBoneHashMap(boneNode.mName().dataString());
            setHierachy(AScene, bone, boneNode);
            AScene.addRootBone(bone);
        }

        getAllCameras(aiScene, AScene);
        getAllAnimations(aiScene, AScene);
        AScene.getRoot().setSkeletalAnimation(AScene.getAnimations());
        Assimp.aiReleaseImport(aiScene);
        return AScene;
    }

    public static void saveModel() {
        //TODO: save a model using either Assimp or Java Serialisation
    }

    private static void getAllMaterials(String meshPath, AIScene aiScene, AScene AScene) {
        if (aiScene.mNumMaterials() > 0) {
            PointerBuffer buffer = aiScene.mMaterials();
            if (buffer != null) {
                int i = 0;
                for (int j = 0; j < aiScene.mNumMaterials(); j++) {
                    AIMaterial aiMaterial = AIMaterial.create(buffer.get(j));
                    Material material = toMaterial(aiMaterial);
                    AScene.setTexture(getTexture(meshPath, aiMaterial));
                    AScene.addMaterial(i++, material);
                }
            } else throw new RuntimeException("Material not found for scene: ");
        }
    }

    private static void getAllCameras(AIScene aiScene, AScene AScene) {
        PointerBuffer aiCameraBuffer = aiScene.mCameras();
        for (int i = 0; i < aiScene.mNumCameras(); i++) {
            AICamera aiCamera = AICamera.create(aiCameraBuffer.get(i));
            AScene.addCamera(new FPSCamera(new Perspective(aiCamera.mHorizontalFOV(), aiCamera.mAspect(), aiCamera.mClipPlaneNear(), aiCamera.mClipPlaneFar())));
        }
    }

    private static void getAllAnimations(AIScene aiScene, AScene AScene) {
        PointerBuffer buffer = aiScene.mAnimations();
        for (int i = 0; i < aiScene.mNumAnimations(); i++) {
            SkeletalAnimation animation = toAnimation(AScene, AIAnimation.create(buffer.get()));
            AScene.addAnimation(animation);
        }
    }

    private static Model toModel(AIScene aiScene, AScene AScene, AINode aiNode) {
        ArrayList<Mesh> meshes = new ArrayList<>();
        PointerBuffer pointerBuffer = aiScene.mMeshes();

        if (aiNode.mNumMeshes() > 0) {
            IntBuffer intBuffer = aiNode.mMeshes();
            if (intBuffer != null) {
                for (int i = 0; i < aiNode.mNumMeshes(); i++) {
                    AIMesh aiMesh = AIMesh.create(pointerBuffer.get(intBuffer.get()));
                    Mesh mesh = toMesh(AScene, aiMesh);
                    meshes.add(mesh);
                }
            } else throw new RuntimeException("Mesh children not found for node : " + aiNode.mName().dataString());
        }

        Model model = new Model(aiNode.mName().dataString(), meshes);
        if (aiNode.mNumChildren() > 0) {
            PointerBuffer children = aiNode.mChildren();
            if (children != null) {
                for (int i = 0; i < aiNode.mNumChildren(); i++) {
                    AINode child = AINode.create(children.get(i));
                    Model sub = toModel(aiScene, AScene, child);
                    if (sub != null) model.addModel(sub);
                }
            } else throw new RuntimeException("Node children not found for node : " + aiNode.mName().dataString());
        }

        if (AScene.getBoneHashMap(aiNode.mName().dataString()) == null) {
            AScene.addBone(new Bone(aiNode.mName().dataString(), toMatrix4f(aiNode.mTransformation()), false));
        }

        /**
         * We assume every empty models are bones ??
         */
        if (model.getModels().size() == 0 && model.getMeshes().size() == 0) {
            return null;
        }
        return model;
    }

    private static Mesh toMesh(AScene AScene, AIMesh aiMesh) {
        if (aiMesh == null) return null;
        float[] vertices = getVertices3D(aiMesh.mNumVertices(), aiMesh.mVertices());
        float[] normals = getVertices3D(aiMesh.mNumVertices(), aiMesh.mNormals());
        float[] texCoords = getVertices2D(aiMesh.mNumVertices(), aiMesh.mTextureCoords(0));
        Armature armature = null;
        int[] indices = getIndices(aiMesh.mNumFaces(), aiMesh.mFaces());
        if (aiMesh.mNumBones() > 0) {
            armature = getArmature(AScene, aiMesh, aiMesh.mNumBones(), aiMesh.mBones());
        }

        Mesh mesh = new Mesh(aiMesh.mName().dataString(), vertices, normals, texCoords, indices, armature);
        mesh.setMaterial(AScene.getMaterial(aiMesh.mMaterialIndex()));
        return mesh;
    }

    /**
     * Finding the root bone
     * This is done by looking for all the node root's children, if one is in the bone list, it is the root bone
     *
     * @param node    The root node
     * @return The root bone
     */
    public static List<AINode> findRootBones(AScene AScene, AINode node) {
        List<AINode> rootBonesNode = new ArrayList<>();

        PointerBuffer pointerBuffer = node.mChildren();
        for (int i = 0; i < node.mNumChildren(); i++) {
            AINode aiNode = AINode.create(pointerBuffer.get(i));
            if (AScene.getBoneHashMap(aiNode.mName().dataString()) != null) {
                rootBonesNode.add(aiNode);
            }
        }

        return rootBonesNode;
    }

    /**
     * Construct the bone hierarchy
     * There can be more than one bone at the top most level of the tree
     * @param AScene
     * @param bone
     * @param parentNode
     */
    public static void setHierachy(AScene AScene, Bone bone, AINode parentNode) {
        PointerBuffer pointerBuffer = parentNode.mChildren();
        for (int i = 0; i < parentNode.mNumChildren(); i++) {
            AINode boneNode = AINode.create(pointerBuffer.get(i));
            Bone child = AScene.getBoneHashMap(boneNode.mName().dataString());
            bone.addBone(child);
            setHierachy(AScene, child, boneNode);
        }
    }

    private static AINode getNode(AINode node, String name) {
        if (node.mName().dataString().equals(name)) return node;

        PointerBuffer pointerBuffer = node.mChildren();
        for (int i = 0; i < node.mNumChildren(); i++) {
            AINode aiNode = AINode.create(pointerBuffer.get(i));
            if (getNode(aiNode, name) != null) return getNode(aiNode, name);
        }

        return null;
    }

    public static Armature getArmature(AScene AScene, AIMesh aiMesh, int boneCount, PointerBuffer aiBoneBuffer) {
        Bone[] bones = new Bone[boneCount];
        float[] weights = new float[4 * aiMesh.mNumVertices()];
        int[] ids = new int[4 * aiMesh.mNumVertices()];

        for (int i = 0; i < boneCount; i++) {
            AIBone bone = AIBone.create(aiBoneBuffer.get(i));
            Bone b = AScene.getBoneHashMap(bone.mName().dataString());
            if (b == null) {
                b = toBone(bone);
                AScene.addBone(b);
            }
            bones[i] = b;
            AIVertexWeight.Buffer aiVertexWeightBuffer = bone.mWeights();
            for (int j = 0; j < bone.mNumWeights(); j++) {
                AIVertexWeight weight = aiVertexWeightBuffer.get(j);
                if (weights[weight.mVertexId() * 4] == 0) {
                    weights[weight.mVertexId() * 4] =  weight.mWeight();
                    ids[weight.mVertexId() * 4] = i;
                } else if (weights[weight.mVertexId() * 4 + 1] == 0) {
                    weights[weight.mVertexId() * 4 + 1] =  weight.mWeight();
                    ids[weight.mVertexId() * 4 + 1] = i;
                } else if (weights[weight.mVertexId() * 4 + 2] == 0) {
                    weights[weight.mVertexId() * 4 + 2] =  weight.mWeight();
                    ids[weight.mVertexId() * 4 + 2] = i;
                } else if (weights[weight.mVertexId() * 4 + 3] == 0) {
                    weights[weight.mVertexId() * 4 + 3] = weight.mWeight();
                    ids[weight.mVertexId() * 4 + 3] = i;
                }
            }
        }
        return new Armature(bones, ids, weights);
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
            for (int j = 0; j < face.mNumIndices(); j++) {
                indices[i * 3 + j] = intBuffer.get();
            }
        }

        return indices;
    }
}
