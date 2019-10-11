package com.wawacorp.wawagl.core.opengl.view.instanced;

import com.wawacorp.wawagl.core.opengl.model.model.Model;
import com.wawacorp.wawagl.core.opengl.shader.Shader;
import com.wawacorp.wawagl.core.opengl.view.Drawable;
import com.wawacorp.wawagl.core.opengl.view.Movable;

import java.util.ArrayList;

/**
 * Game object designed for large numbers of instances
 */
public class InstancedDrawable implements Drawable, Movable {
    public final static int MAX_INSTANCE_COUNT = 256;
    private int activeInstance;
    private GLInstancedModel model;

    public InstancedDrawable(Model model, int instanceCount) {
        this.model = new GLInstancedModel(model, instanceCount);
        this.activeInstance = 0;
    }

    @Override
    public void draw() {
        model.draw();
    }

    @Override
    public void draw(Shader shader) {
        model.draw(shader);
    }

    @Override
    public void translate(float x, float y, float z) {
        model.translate(x, y, z);
    }

    @Override
    public void rotate(float x, float y, float z) {
        model.rotate(x, y, z);
    }

    @Override
    public void scale(float x, float y, float z) {
        model.scale(x, y, z);
    }

    @Override
    public void setTranslation(float x, float y, float z) {
        model.setTranslation(x, y, z);
    }

    @Override
    public void setRotation(float x, float y, float z) {
        model.setRotation(x, y, z);
    }

    @Override
    public void setScale(float x, float y, float z) {
        model.setScale(x, y, z);
    }

    public void translate(int instance, float x, float y, float z) {
        model.translate(instance, x, y, z);
    }

    public void rotate(int instance, float x, float y, float z) {
        model.rotate(instance, x, y, z);
    }

    public void scale(int instance, float x, float y, float z) {
        model.scale(instance, x, y, z);
    }

    public int addInstance() {
        model.addInstance(activeInstance);
        return activeInstance++;
    }

    public void removeInstance(int instance) {
        model.removeInstance(instance, activeInstance);
    }

    public ArrayList<GLInstancedMesh> getMeshes() {
        return model.getMeshes();
    }

    public ArrayList<GLInstancedModel> getChildren() {
        return model.getChildren();
    }

    public String getName() {
        return model.getName();
    }
}
