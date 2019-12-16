package com.wawacorp.wawagl.core.view.buffer;

import com.wawacorp.wawagl.core.view.buffer.vbo.VertexBufferObject;
import com.wawacorp.wawagl.core.view.buffer.vbo.InstancedFloatArrayVBO;
import com.wawacorp.wawagl.core.model.Mesh;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Instanced version of the VAO class
 * @see VertexArrayObject
 */
public class InstancedVAO extends BufferObject {
    private final ArrayList<VertexBufferObject> buffers;

    public InstancedVAO(Mesh mesh) {
        super(glGenVertexArrays());
        buffers = new ArrayList<>();

        addVBO(new InstancedFloatArrayVBO(mesh.getVertices(), 3, 0, 0));
        if (mesh.getNormals() != null) addVBO(new InstancedFloatArrayVBO(mesh.getNormals(), 3, 1, 0));
        if (mesh.getTexCoords() != null) addVBO(new InstancedFloatArrayVBO(mesh.getTexCoords(), 2, 2, 0));
    }

    public void addVBO(VertexBufferObject buffer) {
        buffers.add(buffer);

        bind();
        buffer.init();
        unbind();
    }

    @Override
    public void bind() {
        glBindVertexArray(handle);
    }

    @Override
    public void unbind() {
        glBindVertexArray(0);
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }
}
