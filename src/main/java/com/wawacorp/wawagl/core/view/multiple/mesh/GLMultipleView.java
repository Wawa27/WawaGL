package com.wawacorp.wawagl.core.view.multiple.mesh;

import com.wawacorp.wawagl.core.view.buffer.IndexBufferObject;
import com.wawacorp.wawagl.core.view.buffer.InstancedVAO;
import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.model.Mesh;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.GLView;
import com.wawacorp.wawagl.core.view.instance.Instance;

import java.util.Observable;
import java.util.Observer;

import static org.lwjgl.opengl.GL46.*;

public class GLMultipleView extends GLView implements Observer {
    private InstancedVAO vertexArray;
    private IndexBufferObject indexBuffer;
    private Material material;
    private final Instance[] instances;

    public GLMultipleView(Mesh mesh, Shader shader, Instance[] instances) {
        this.instances = instances;
        this.vertexArray = new InstancedVAO(mesh);
        this.material = mesh.getMaterial();
        if (this.material == null) this.material = Material.DEFAULT;
        this.indexBuffer = new IndexBufferObject(mesh.getIndices());
        this.shader = shader;
        for (Instance instance : instances) {
            if (instance != null) instance.addObserver(this);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        shader.bind();
    }

    @Override
    public void draw() {
        bind();
        glDrawElementsInstanced(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0, instances.length);
        unbind();
    }

    @Override
    public void draw(Shader shader) {
        bind();
        shader.bind();
        glDrawElementsInstanced(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0, instances.length);
        shader.unbind();
        unbind();
    }

    public void addInstance(Instance instance) {
        for (int i = 0; i < instances.length; i++) {
            if (instances[i] == null) {
                instances[i] = instance; break;
            }
        }
        upload();
    }

    public void bind() {
        vertexArray.bind();
        indexBuffer.bind();
        shader.bind();
    }

    public void unbind() {
        shader.unbind();
        indexBuffer.unbind();
        vertexArray.unbind();
    }

    public void upload() {
        shader.bind();
        for (int i = 0; i < instances.length; i++) {
            if (instances[i] != null) instances[i].upload(shader);
        }
        shader.setMaterial(material);
        shader.unbind();
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Material getMaterial() {
        return material;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public String getName() {
        return name;
    }
}
