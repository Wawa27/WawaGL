package com.wawacorp.wawagl.core.view.lighting;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.Environnement;
import com.wawacorp.wawagl.core.view.single.GLSingleMesh;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Light {
    private Vector3f color;
    private GLSingleMesh mesh;

    //TODO: Lights meshes should have the colors of the lights in the shader
    public Light(Mesh mesh, Vector3f position, Vector3f color) {
        this.mesh = mesh == null ? null : new GLSingleMesh(mesh, new Entity());
        translate(position.x, position.y, position.z);
        this.color = color;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public void draw() {
        if (mesh != null) mesh.draw();
    }

    public void scale(float x, float y, float z) {
        mesh.scale(x, y, z);
        Environnement.getLightScene().update();
    }

    public void translate(float x, float y, float z) {
        mesh.translate(x, y, z);
        Environnement.getLightScene().update();
    }

    public void rotate(float x, float y, float z) {
        mesh.rotate(x, y, z);
        Environnement.getLightScene().update();
    }

    public Vector4f getPosition() {
        return new Vector4f(mesh.getPosition(), 1);
    }

    public void setScale(float x, float y, float z) {
        mesh.setScale(x, y, z);
        Environnement.getLightScene().update();
    }

    public void setTranslation(float x, float y, float z) {
        mesh.setTranslation(x, y, z);
        Environnement.getLightScene().update();
    }

    public void setRotation(float x, float y, float z) {
        mesh.setRotation(x, y, z);
        Environnement.getLightScene().update();
    }

    public Matrix4f getModelMatrix() {
        return mesh.getModelMatrix();
    }

    public void updateTransform() {
        mesh.updateTransform();
    }

    public Vector3f getRotation() {
        return mesh.getRotation();
    }

    public Vector3f getScale() {
        return mesh.getScale();
    }

    public void setMaterial(Material material) {
        mesh.setMaterial(material);
    }

    public void setShader(Shader shader) {
        mesh.setShader(shader);
    }

    public Material getMaterial() {
        return mesh.getMaterial();
    }

    public int getVertexCount() {
        return mesh.getVertexCount();
    }

    public String getName() {
        return mesh.getName();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
