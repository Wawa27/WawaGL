package com.wawacorp.wawagl.core.view;

import com.wawacorp.wawagl.core.manager.AssetManager;
import com.wawacorp.wawagl.core.model.FlatColor;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.entity.AttachedEntity;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.utils.FileUtils;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.*;
import com.wawacorp.wawagl.core.view.single.GLSingleView;
import com.wawacorp.wawagl.demo.wolf.WolfScene;
import org.joml.Vector3f;

import java.io.File;
import java.util.ArrayList;

public class GLModel implements View {
    protected final Model model;

    protected final ArrayList<GLView> meshes;
    protected final ArrayList<GLModel> models;
    protected final Instance instance;

    public GLModel(Model model, ArrayList<GLView> meshes, ArrayList<GLModel> models, Instance instance) {
        this.model = model;
        this.meshes = meshes;
        this.models = models;
        this.instance = instance;
    }

    public void draw() {
        for (GLView mesh : meshes) mesh.draw();
        for (GLModel model : models) model.draw();
    }

    public static GLModel getSingleModel(Model model, String textureFolderPath) {
        ArrayList<String> fileNames = new ArrayList<>();
        File[] files = new File(FileUtils.getAbsolutePath(textureFolderPath)).listFiles();
        for (File f : files) {
            fileNames.add(f.getName().split("\\.")[0]);
        }
        ArrayList<GLView> meshes = new ArrayList<>();
        for (Mesh mesh : model.getMeshes()) {
            Instance instance = new Instance(new Entity() {
                @Override
                public void onLoop() {

                }
            });
            if (fileNames.contains(mesh.getName())) {
                instance.addProperty(new TextureProperty("texture.ambient", AssetManager.getTexture2D(textureFolderPath + "" + mesh.getName() + "")));
                meshes.add(new GLSingleView(mesh, instance, Shader.getTextureShader()));
            } else {
                instance.addProperty(new FlatColorProperty("color", FlatColor.DEFAULT));
                meshes.add(new GLSingleView(mesh, new Instance(), Shader.getMaterialShader()));
            }
        }
        ArrayList<GLModel> models = new ArrayList<>();
        for (Model m : model.getModels()) {
            models.add(getSingleModel(m, textureFolderPath));
        }
        return new GLModel(model, meshes, models, new Instance());
    }

    public static GLModel getSingleModel(Model model, Entity rootEntity) {
        ArrayList<GLView> meshes = new ArrayList<>();
        for (Mesh mesh : model.getMeshes()) {
            Instance instance = new Instance(
                    // Common properties
                    new EntityProperty(new AttachedEntity(rootEntity, new Vector3f())),
                    new MaterialProperty("material", mesh.getMaterial()),
                    new LightSceneProperty("Lightscene", WolfScene.lightScene)
            );
            String vertexShadex;
            String fragmentShader;

            if (mesh.getArmature() != null) {
                vertexShadex = "single_rigged";
                instance.addProperty(new ArmatureProperty("armature", mesh.getArmature()));
            } else {
                vertexShadex = "single";
            }

            if (mesh.getMaterialTexture() != null) {
                fragmentShader = "single_texture";
            } else {
                fragmentShader = "single_material";
            }

            if (mesh.getMaterialTexture() != null && mesh.getMaterialTexture().getAmbientPath() != null && mesh.getMaterialTexture().getDiffusePath() != null
            && mesh.getMaterialTexture().getSpecularPath() != null) {
                instance.addProperty(new MaterialTextureProperty("texture", new MaterialTextureView(mesh.getMaterialTexture())));
            }
            Shader shader = Shader.loadShaderRelative(vertexShadex, fragmentShader);
            meshes.add(new GLSingleView(mesh, instance, shader));
        }
        ArrayList<GLModel> models = new ArrayList<>();
        for (Model m : model.getModels()) {
            models.add(getSingleModel(m, rootEntity));
        }
        return new GLModel(model, meshes, models, new Instance(
                new EntityProperty(rootEntity)
        ));
    }

    @Override
    @Deprecated
    public void draw(Shader shader) {
    }

    public Instance getInstance() {
        return instance;
    }

    public String getName() {
        return model.getName();
    }

    public Model getModel() {
        return model;
    }

    @Override
    public String toString() {
        return getName();
    }
}
