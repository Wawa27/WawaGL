package com.wawacorp.wawagl.core.utils;

import com.wawacorp.wawagl.core.manager.AssetManager;
import com.wawacorp.wawagl.core.model.AScene;
import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.model.MaterialTexture;
import com.wawacorp.wawagl.core.model.animation.Bone;
import com.wawacorp.wawagl.core.model.animation.BoneAnimation;
import com.wawacorp.wawagl.core.model.animation.SkeletalAnimation;
import com.wawacorp.wawagl.core.model.animation.BoneRotationAnimation;
import com.wawacorp.wawagl.core.model.animation.BoneScaleAnimation;
import com.wawacorp.wawagl.core.model.animation.BoneTranslationAnimation;
import com.wawacorp.wawagl.core.view.buffer.texture.Texture;
import com.wawacorp.wawagl.core.view.buffer.texture.Texture2D;
import com.wawacorp.wawagl.core.view.gui.nanovg.component.Text;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.IntBuffer;

import static org.lwjgl.assimp.Assimp.*;

public class AssimpUtils {

    public static Vector3f toVector3f(AIVector3D vector3D) {
        return new Vector3f(vector3D.x(), vector3D.y(), vector3D.z());
    }

    public static Quaternionf toQuaternion(AIQuaternion quaternion) {
        return new Quaternionf(quaternion.x(), quaternion.y(), quaternion.z(), quaternion.w());
    }

    public static Matrix4f toMatrix4f(AIMatrix4x4 aiMatrix) {
        return new Matrix4f(
                aiMatrix.a1(), aiMatrix.b1(), aiMatrix.c1(), aiMatrix.d1(),
                aiMatrix.a2(), aiMatrix.b2(), aiMatrix.c2(), aiMatrix.d2(),
                aiMatrix.a3(), aiMatrix.b3(), aiMatrix.c3(), aiMatrix.d3(),
                aiMatrix.a4(), aiMatrix.b4(), aiMatrix.c4(), aiMatrix.d4()
        );
    }

    public static Bone toBone(AIBone aiBone) {
        return new Bone(aiBone.mName().dataString(), toMatrix4f(aiBone.mOffsetMatrix()));
    }

    public static MaterialTexture toMaterialTexture(String meshPath, AIMaterial aiMaterial) {
        MaterialTexture materialTexture = new MaterialTexture();
        if (aiGetMaterialTextureCount(aiMaterial, aiTextureType_AMBIENT) > 0) {
            AIString path = AIString.create();
            if (aiReturn_SUCCESS != aiGetMaterialTexture(aiMaterial, aiTextureType_AMBIENT, 0, path, (IntBuffer) null, null, null, null, null, null)) {
                System.err.println("Error loading texture");
            }
            materialTexture.setAmbientPath(meshPath + "/" + path.dataString());
        }
        if (aiGetMaterialTextureCount(aiMaterial, aiTextureType_DIFFUSE) > 0) {
            AIString path = AIString.create();
            if (aiReturn_SUCCESS != aiGetMaterialTexture(aiMaterial, aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null)) {
                System.err.println("Error loading texture");
            }
            materialTexture.setDiffusePath(meshPath + "/" + path.dataString());
        }
        if (aiGetMaterialTextureCount(aiMaterial, aiTextureType_SPECULAR) > 0) {
            AIString path = AIString.create();
            if (aiReturn_SUCCESS != aiGetMaterialTexture(aiMaterial, aiTextureType_SPECULAR, 0, path, (IntBuffer) null, null, null, null, null, null)) {
                System.err.println("Error loading texture");
            }
            materialTexture.setDiffusePath(meshPath + "/" + path.dataString());
        }
        return materialTexture;
    }

    public static Material toMaterial(AIMaterial aiMaterial) {
        Material material = new Material();

        Vector4f vector4f;
        AIColor4D color = AIColor4D.create();
        aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_AMBIENT, 0, 0, color);
        vector4f = new Vector4f(color.r(), color.g(), color.b(), color.a());
        material.setAmbient(vector4f);

        aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_DIFFUSE, 0, 0, color);
        vector4f = new Vector4f(color.r(), color.g(), color.b(), color.a());
        material.setDiffuse(vector4f);

        aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_SPECULAR, 0, 0, color);
        vector4f = new Vector4f(color.r(), color.g(), color.b(), color.a());
        material.setSpecular(vector4f);

        AIString name = AIString.create();
        aiGetMaterialString(aiMaterial, AI_MATKEY_NAME, 0, 0, name);
        material.setName(name.dataString());

        return material;
    }

    public static SkeletalAnimation toAnimation(AScene AScene, AIAnimation aiAnimation) {
        SkeletalAnimation skeletalAnimation = new SkeletalAnimation(
                aiAnimation.mName().dataString(),
                AScene.getInverseGlobalWorldMatrix(),
                AScene.getRootBones(),
                aiAnimation.mDuration(),
                aiAnimation.mTicksPerSecond());
        PointerBuffer buffer = aiAnimation.mChannels();
        for (int i = 0; i < aiAnimation.mNumChannels(); i++) { // num of keyframes
            AINodeAnim nodeAnim = AINodeAnim.create(buffer.get(i));

            AIVectorKey.Buffer positionBuffer = nodeAnim.mPositionKeys();
            AIQuatKey.Buffer rotationBuffer = nodeAnim.mRotationKeys();
            AIVectorKey.Buffer scaleBuffer = nodeAnim.mScalingKeys();

            Quaternionf[] rotations = new Quaternionf[nodeAnim.mNumRotationKeys()];
            double[] rotationsTimes = new double[nodeAnim.mNumRotationKeys()];

            for (int j = 0; j < nodeAnim.mNumRotationKeys(); j++) {
                AIQuatKey quatKey = rotationBuffer.get(j);

                rotations[j] = toQuaternion(quatKey.mValue());
                rotationsTimes[j] = quatKey.mTime();
            }

            Vector3f[] scale = new Vector3f[nodeAnim.mNumScalingKeys()];
            double[] scaleTimes = new double[nodeAnim.mNumScalingKeys()];

            for (int j = 0; j < nodeAnim.mNumScalingKeys(); j++) {
                AIVectorKey vectorKey = scaleBuffer.get(j);

                scale[j] = toVector3f(vectorKey.mValue());
                scaleTimes[j] = vectorKey.mTime();
            }

            Vector3f[] translation = new Vector3f[nodeAnim.mNumPositionKeys()];
            double[] translationTimes = new double[nodeAnim.mNumPositionKeys()];

            for (int j = 0; j < nodeAnim.mNumPositionKeys(); j++) {
                AIVectorKey vectorKey = positionBuffer.get(j);

                translation[j] = toVector3f(vectorKey.mValue());
                translationTimes[j] = vectorKey.mTime();
            }

            Bone bone = AScene.getBone(nodeAnim.mNodeName().dataString());
            BoneAnimation boneAnimation = new BoneAnimation(
                    bone,
                    new BoneRotationAnimation(rotations, rotationsTimes),
                    new BoneScaleAnimation(scale, scaleTimes),
                    new BoneTranslationAnimation(translation, translationTimes)
            );
            skeletalAnimation.addBoneAnimation(boneAnimation);
        }
        return skeletalAnimation;
    }
}
