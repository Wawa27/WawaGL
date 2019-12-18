package com.wawacorp.wawagl.core.view.single;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.GLView;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;

import java.util.Observable;

import static org.lwjgl.opengl.GL46.*;

/**
 * Class for drawing normals of a view
 *
 */
public class GLNormalView extends GLView {
    private GLView meshView;

    public GLNormalView(GLView meshView) {
        this.meshView = meshView;
        this.shader = Shader.loadShaderRelative("line", "debug", "line");
        this.vertexArrayObject = meshView.getVertexArrayObject();
        this.instance = new Instance(new EntityProperty(((EntityProperty) meshView.getInstance().getProperty("model")).getEntity()));
        this.instance.addObserver(this::update);
        this.vertexCount = meshView.getVertexCount();
    }

    @Override
    public void draw() {
        vertexArrayObject.bind();
        shader.bind();
        if (vertexArrayObject.getIndexBufferObject() != null) {
            glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        } else {
            glDrawArrays(GL_TRIANGLES, 0, vertexCount);
        }
        shader.unbind();
        vertexArrayObject.unbind();
    }

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
