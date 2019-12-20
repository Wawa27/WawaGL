package com.wawacorp.wawagl.core.view.single;

import com.wawacorp.wawagl.core.model.Model;
import com.wawacorp.wawagl.core.model.animation.Bone;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.GLModel;
import com.wawacorp.wawagl.core.view.GLView;
import com.wawacorp.wawagl.core.view.buffer.VertexArrayObject;
import com.wawacorp.wawagl.core.view.buffer.vbo.FloatArrayVBO;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.util.Observable;

import static org.lwjgl.opengl.GL46.*;

/**
 * Class for drawing normals of a view
 */
public class GLBoneView extends GLView {

    public GLBoneView(GLModel model, Bone rootBone) {
        this.shader = Shader.loadShaderRelative("line", "debug", "point");
        this.vertexArrayObject = new VertexArrayObject();
        Vector3f position = rootBone.getCurrentLocalTransform().getTranslation(new Vector3f());
        this.instance = new Instance(new EntityProperty(((EntityProperty) model.getInstance().getProperty("model")).getEntity()));
        this.instance.addObserver(this);
        this.vertexArrayObject.bind();
        this.vertexArrayObject.addVBO(new FloatArrayVBO(
                new float[]{0, 0, 0, 0, 0, 1, 1, 0, 0},
                3,
                0,
                false
        )); // Position
        this.vertexArrayObject.unbind();
        this.vertexCount = 3;
    }

    @Override
    public void draw() {
        vertexArrayObject.bind();
        shader.bind();
        glDrawArrays(GL_POINTS, 0, vertexCount);
        shader.unbind();
        vertexArrayObject.unbind();
    }

    @Deprecated
    @Override
    public void draw(Shader shader) {

    }

    @Override
    public void update(Observable observable, Object o) {
        update();
    }

    public void update() {
        shader.bind();
        instance.upload(shader);
        shader.unbind();
    }
}
