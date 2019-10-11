package com.wawacorp.wawagl.core.opengl.lighting;

import com.wawacorp.wawagl.core.opengl.entity.Entity;
import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Material;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.Texture;
import com.wawacorp.wawagl.core.opengl.model.model.Mesh;
import com.wawacorp.wawagl.core.opengl.shader.Shader;
import com.wawacorp.wawagl.core.opengl.Environnement;
import com.wawacorp.wawagl.core.opengl.view.simple.GLSimpleMesh;
import org.joml.Vector3f;

public class Light {
    private Vector3f color;
    private GLSimpleMesh mesh;

    //TODO: Lights meshes should have the colors of the lights in the shader
    public Light(Mesh mesh, Vector3f position, Vector3f color) {
        this.mesh = mesh == null ? null : new GLSimpleMesh(mesh, new Entity());
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

    public Vector3f getPosition() {
        return mesh.getPosition();
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

    public void updateTransform() {
        mesh.updateTransform();
    }

    public Vector3f getRotation() {
        return mesh.getRotation();
    }

    public Vector3f getScale() {
        return mesh.getScale();
    }

    public void setTexture(Texture texture) {
        mesh.setTexture(texture);
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
