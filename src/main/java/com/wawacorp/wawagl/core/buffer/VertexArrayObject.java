package com.wawacorp.wawagl.core.buffer;

import com.wawacorp.wawagl.core.buffer.vbo.VertexBufferObject;
import com.wawacorp.wawagl.core.buffer.vbo.FloatBufferObject;
import com.wawacorp.wawagl.core.model.Mesh;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/** Vertex Array Buffer which contains all the Buffer's **/
public class VertexArrayObject extends BufferObject {
    private final ArrayList<VertexBufferObject> buffers;

    public VertexArrayObject(Mesh mesh) {
        super(glGenVertexArrays());
        buffers = new ArrayList<>();

        addVBO(new FloatBufferObject(mesh.getVertices(), 3, 0, false));
        if (mesh.getNormals() != null) addVBO(new FloatBufferObject(mesh.getNormals(), 3, 1, true));
        if (mesh.getTexCoords() != null) addVBO(new FloatBufferObject(mesh.getTexCoords(), 2, 2, false));
    }

    @Override
    public void bind() {
        glBindVertexArray(handle);
    }

    @Override
    public void unbind() {
        glBindVertexArray(0);
    }

    public void addVBO(VertexBufferObject buffer) {
        buffers.add(buffer);

        bind();
        buffer.init();
        unbind();
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }
}
