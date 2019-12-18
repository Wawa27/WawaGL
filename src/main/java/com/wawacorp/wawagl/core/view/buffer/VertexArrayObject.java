package com.wawacorp.wawagl.core.view.buffer;

import com.wawacorp.wawagl.core.model.animation.Armature;
import com.wawacorp.wawagl.core.view.buffer.vbo.IntArrayVBO;
import com.wawacorp.wawagl.core.view.buffer.vbo.VertexBufferObject;
import com.wawacorp.wawagl.core.view.buffer.vbo.FloatArrayVBO;
import com.wawacorp.wawagl.core.model.Mesh;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/** Vertex Array Buffer which contains all the Buffer's **/
public class VertexArrayObject extends BufferObject {
    private final ArrayList<VertexBufferObject> buffers;
    private IndexBufferObject indexBufferObject;

    public VertexArrayObject() {
        super(glGenVertexArrays());
        buffers = new ArrayList<>();
    }

    public VertexArrayObject(Mesh mesh) {
        this();

        addVBO(new FloatArrayVBO(mesh.getVertices(), 3, 0, false));
        if (mesh.getNormals() != null) addVBO(new FloatArrayVBO(mesh.getNormals(), 3, 1, true));
        if (mesh.getTexCoords() != null) addVBO(new FloatArrayVBO(mesh.getTexCoords(), 2, 2, false));
        if (mesh.getArmature() != null) {
            addVBO(new FloatArrayVBO(mesh.getArmature().getWeights(), 4, 3, true)); //TODO: normalize bone weights ???
            addVBO(new IntArrayVBO(mesh.getArmature().getIds(), 4, 4, false));
        }
        if (mesh.getColors() != null) {
            addVBO(new FloatArrayVBO(mesh.getColors(), 3, 5, true));
        }
        if (mesh.getIndices() != null) {
            bind();
            indexBufferObject = new IndexBufferObject(mesh.getIndices());
            unbind();
        } else {
            indexBufferObject = null;
        }
    }

    public VertexBufferObject getVBO(int index) {
        return buffers.get(index);
    }

    @Override
    public void bind() {
        glBindVertexArray(handle);
    }

    @Override
    public void unbind() {
        glBindVertexArray(0);
    }

    /**
     * TODO: VAO should be bound before calling this method
     * @param buffer
     */
    public void addVBO(VertexBufferObject buffer) {
        buffers.add(buffer);

        bind();
        buffer.init();
        unbind();
    }

    @Override
    public void update() {

    }

    public IndexBufferObject getIndexBufferObject() {
        return indexBufferObject;
    }

    @Override
    public void dispose() {

    }
}
