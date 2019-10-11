package com.wawacorp.wawagl.core.opengl.shader.bo.ubo;

import com.wawacorp.wawagl.core.opengl.shader.ShaderException;
import com.wawacorp.wawagl.core.opengl.shader.bo.BufferObject;
import org.joml.*;
import org.lwjgl.BufferUtils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindBufferBase;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;

/**
 * Experimental
 * @param <E>
 */
public class UniformBlock<E> extends BufferObject {
    private final static int TARGET = GL_UNIFORM_BUFFER;

    // Known class associated with its size in bytes
    private final static HashMap<Class, Integer> DEFAULT_CLASS_SIZE;

    // The block which contains fields to put in the buffer
    private E block;

    // TODO:
    private final ByteBuffer buffer;

    // Hashmap containing all public fields of the target with its offset in the buffer
    private final HashMap<Object, Integer> blockFields;

    // Block size in bytes of all the known fields
    private final int blockSize;

    static {
        DEFAULT_CLASS_SIZE = new HashMap<>();
        // Java Primitives
        DEFAULT_CLASS_SIZE.put(Integer.class, Integer.BYTES);
        DEFAULT_CLASS_SIZE.put(Float.class, Float.BYTES);

        // JOML Vectors
        DEFAULT_CLASS_SIZE.put(Vector2f.class, 2 * Float.BYTES);
        DEFAULT_CLASS_SIZE.put(Vector3f.class, 3 * Float.BYTES);
        DEFAULT_CLASS_SIZE.put(Vector4f.class, 4 * Float.BYTES);

        // JOML Matrix
        DEFAULT_CLASS_SIZE.put(Matrix4f.class, 16 * Float.BYTES);
    }

    public UniformBlock(E block) throws IntrospectionException, InvocationTargetException, IllegalAccessException, ShaderException {
        super(glGenBuffers());
        this.block = block;
        blockFields = new HashMap<>();
        int blockSize = 0;
        int maxSize = 0;
        for (PropertyDescriptor pd : Introspector.getBeanInfo(block.getClass()).getPropertyDescriptors()) {
            if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
                Object field = pd.getReadMethod().invoke(block);
                System.out.println(pd.getReadMethod().getName());
                for (Class c : DEFAULT_CLASS_SIZE.keySet()) {
                    if (c.equals(field.getClass())) {
                        int size = DEFAULT_CLASS_SIZE.get(c);
                        blockFields.put(field, blockSize);
                        blockSize += size;
                        if (size > maxSize) {
                            maxSize = size;
                        }
                    }
                }
            }
        }

        this.blockSize = blockSize;

        buffer = BufferUtils.createByteBuffer(maxSize);

        glBindBuffer(TARGET, handle);
        glBufferData(TARGET, this.blockSize, GL_STATIC_DRAW);
        glBindBuffer(TARGET, 0);
    }

    @Override
    public void bind() {
        glBindBufferBase(TARGET, 0, handle);
    }

    @Override
    public void unbind() {
        glBindBufferBase(TARGET, 0, 0);
    }

    @Override
    public void update() {
        glBindBuffer(TARGET, handle);
        for (Object o : blockFields.keySet()) {
            update(o);
        }
        glBindBuffer(TARGET, 0);
    }

    /**
     * Updates a field of the block :
     *  - The buffer must be bound before updating a field
     *  - Results in a NO-OP if the {@code object} is not a field of the uniform block
     * @param object A field of the block
     */
    public void update(Object object) {
        if (!blockFields.containsKey(object)) return;
        // Get the size of the field
        int size = DEFAULT_CLASS_SIZE.get(object.getClass());
        // Set the position at 0 and write to it
        buffer.position(0);
        getBuffer(object);
        buffer.position(0);

        glBufferSubData(TARGET, blockFields.get(size), buffer);
    }

    private void getBuffer(Object object) {
        if (object instanceof Matrix4f) ((Matrix4f) object).get(buffer);
        else if (object instanceof Matrix3f) ((Matrix3f) object).get(buffer);
        else if (object instanceof Float) buffer.putFloat((float) object);
        else if (object instanceof Integer) buffer.putInt((int) object);
    }

    /**
     * @return The size in BYTES of all the block's fields with a getter
     */
    public int getBlockSize() {
        return blockSize;
    }

    @Override
    public void dispose() {

    }
}
