package com.wawacorp.wawagl.core.shader;

import com.wawacorp.wawagl.core.model.animation.Armature;
import com.wawacorp.wawagl.core.view.buffer.texture.Texture;
import com.wawacorp.wawagl.core.model.FlatColor;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.Material;
import com.wawacorp.wawagl.core.view.buffer.texture.TextureAtlas;
import com.wawacorp.wawagl.core.utils.FileUtils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class Shader {
    private final int program;

    public final static String VERTEX_PATH = "shaders/vertex/";
    public final static String FRAGMENT_PATH = "shaders/fragment/";
    public final static String GEOMETRY_PATH = "shaders/geometry/";

    private HashMap<String, Integer> locations;

    private Shader(int program) {
        this.program = program;
        this.locations = new HashMap<>();
    }

    public static Shader loadShader(String vertexPath, String fragmentPath) {
        int program = glCreateProgram();

        int vertex = loadShader(GL_VERTEX_SHADER, vertexPath);
        int fragment = loadShader(GL_FRAGMENT_SHADER, fragmentPath);

        if (vertex > 0 && fragment > 0) {
            glAttachShader(program, vertex);
            glAttachShader(program, fragment);

            glLinkProgram(program);
            if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
                return null;
            }

            glValidateProgram(program);
            if (glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
                return null;
            }
            return new Shader(program);
        }

        return null;
    }

    public static Shader loadShader(String vertexPath, String fragmentPath, String geometryPath) {
        int program = glCreateProgram();

        int vertex = loadShader(GL_VERTEX_SHADER, vertexPath);
        int fragment = loadShader(GL_FRAGMENT_SHADER, fragmentPath);
        int geometry = loadShader(GL_GEOMETRY_SHADER, geometryPath);

        if (vertex > 0 && fragment > 0 && geometry > 0) {
            glAttachShader(program, vertex);
            glAttachShader(program, fragment);
            glAttachShader(program, geometry);

            glLinkProgram(program);
            if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
                return null;
            }

            glValidateProgram(program);
            if (glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
                return null;
            }
            return new Shader(program);
        }

        return null;
    }

    // TODO: delegate to loadShader method
    public static Shader loadShaderRelative(String vertexPath, String fragmentPath) {
        int program = glCreateProgram();

        int vertex = loadShader(GL_VERTEX_SHADER, VERTEX_PATH + vertexPath + ".glsl");
        int fragment = loadShader(GL_FRAGMENT_SHADER, FRAGMENT_PATH + fragmentPath + ".glsl");

        if (vertex > 0 && fragment > 0) {
            glAttachShader(program, vertex);
            glAttachShader(program, fragment);

            glLinkProgram(program);
            if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
                System.err.println(glGetProgramInfoLog(program));
                return null;
            }

            glValidateProgram(program);
            if (glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
                System.err.println(glGetProgramInfoLog(program));
                return null;
            }
            return new Shader(program);
        }

        return null;
    }

    // TODO: delegate to loadShader method
    public static Shader loadShaderRelative(String vertexPath, String fragmentPath, String geometryPath) {
        int program = glCreateProgram();

        int vertex = loadShader(GL_VERTEX_SHADER, VERTEX_PATH + vertexPath + ".glsl");
        int fragment = loadShader(GL_FRAGMENT_SHADER, FRAGMENT_PATH + fragmentPath + ".glsl");
        int geometry = loadShader(GL_GEOMETRY_SHADER, GEOMETRY_PATH + geometryPath + ".glsl");

        if (vertex > 0 && fragment > 0 && geometry > 0) {
            glAttachShader(program, vertex);
            glAttachShader(program, fragment);
            glAttachShader(program, geometry);

            glLinkProgram(program);
            if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
                System.err.println(glGetProgramInfoLog(program));
                return null;
            }

            glValidateProgram(program);
            if (glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
                System.err.println(glGetProgramInfoLog(program));
                return null;
            }
            return new Shader(program);
        }

        return null;
    }

    //TODO: bind calls on an already bound shader should result in a NO OP!!
    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public boolean setTexture(String target) {
        glUniform1i(glGetUniformLocation(program, target), 0);
        return true;
    }

    /**
     * Deprecated by {@link #uploadMaterial(Material)}
     */
    @Deprecated
    public boolean setMaterial(Material material) {
        int uloc = glGetUniformLocation(program, "material.ambient");
        glUniform3f(uloc, material.getAmbient().x, material.getAmbient().y, material.getAmbient().z);
        glUniform1i(glGetUniformLocation(program, "material.ambient_texture"), 0);
        uloc = glGetUniformLocation(program, "material.diffuse");
        glUniform3f(uloc, material.getDiffuse().x, material.getDiffuse().y, material.getDiffuse().z);
        uloc = glGetUniformLocation(program, "material.specular");
        glUniform3f(uloc, material.getSpecular().x, material.getSpecular().y, material.getSpecular().z);
        return true;
    }

    @Deprecated
    public void uploadMaterial(Material material) {
        int uloc = glGetUniformLocation(program, "material.ambient");
        glUniform4f(uloc, material.getAmbient().x, material.getAmbient().y, material.getAmbient().z, material.getAmbient().w);
        uloc = glGetUniformLocation(program, "material.diffuse");
        glUniform4f(uloc, material.getDiffuse().x, material.getDiffuse().y, material.getDiffuse().z, material.getDiffuse().w);
        uloc = glGetUniformLocation(program, "material.specular");
        glUniform4f(uloc, material.getSpecular().x, material.getSpecular().y, material.getSpecular().z, material.getSpecular().w);
    }

    public void uploadMaterial(String name, Material material) {
        int uloc = glGetUniformLocation(program, name + ".ambient");
        glUniform4f(uloc, material.getAmbient().x, material.getAmbient().y, material.getAmbient().z, material.getAmbient().w);
        uloc = glGetUniformLocation(program, name + ".diffuse");
        glUniform4f(uloc, material.getDiffuse().x, material.getDiffuse().y, material.getDiffuse().z, material.getDiffuse().w);
        uloc = glGetUniformLocation(program, name + ".specular");
        glUniform4f(uloc, material.getSpecular().x, material.getSpecular().y, material.getSpecular().z, material.getSpecular().w);
    }

    @Deprecated
    public void uploadFlatColor(FlatColor color) {
        uploadVector4f("color.ambient", color.getAmbient());
    }

    public void uploadFlatColor(String name, FlatColor color) {
        uploadVector4f(name + ".ambient", color.getAmbient());
        uploadVector4f(name + ".diffuse", color.getAmbient());
    }

    @Deprecated
    public void uploadTexture(Texture texture) {
        glActiveTexture(0);
        texture.bind();
    }

    public void uploadTexture(int textureIndex, Texture texture) {
        glActiveTexture(GL_TEXTURE0 + textureIndex);
        texture.bind();
    }

    public void uploadMaterialTexture(int textureIndex, Texture texture) {
        glActiveTexture(textureIndex);
        texture.bind();
    }

    @Deprecated
    public void uploadEntity(Entity entity) {
        uploadMatrix4fv("model", entity.getModel());
    }

    public void uploadEntity(String name, Entity entity) {
        uploadMatrix4fv(name, entity.getModel());
    }

    public void setVec2(String name, Vector2f data) {
        glUniform2f(getUniformLocation(name), data.x, data.y);
    }

    /**
     * Uploads the Vector3f to the specified location
     * Shader must be bound before calling this method
     * @param name The name of the GLSL <b>vec3</b> variable
     * @param data The Vector3f to upload to the location
     */
    public void setVector3f(String name, Vector3f data) {
        glUniform3f(getUniformLocation(name), data.x, data.y, data.z);
    }

    /**
     * Uploads the Vector4f to the specified location
     * Shader must be bound before calling this method
     * @param name The name of the GLSL vec4 variable
     * @param data The data to upload to the location
     */
    public void uploadVector4f(String name, Vector4f data) {
        glUniform4f(getUniformLocation(name), data.x, data.y, data.z, data.w);
    }

    /**
     * Uploads the Vector4f to the specified location
     * Shader must be bound before calling this method
     * @param name The name of the GLSL vec4 variable
     * @param data The data to upload to the location
     */
    public void uploadMatrix4fv(String name, float[] data) {
        glUniformMatrix4fv(getUniformLocation(name), false, data);
    }

    /**
     * Uploads the Vector4f to the specified location
     * Shader must be bound before calling this method
     * @param name The name of the GLSL vec4 variable
     * @param data The data to upload to the location
     */
    public void uploadMatrix4fv(String name, Matrix4f data) {
        glUniformMatrix4fv(getUniformLocation(name), false, data.get(new float[16]));
    }

    public void uploadArmature(String name, Armature armature) {
        for (int i = 0; i < armature.getBoneTransforms().length; i++) {
            uploadMatrix4fv(name + "[" + i + "]", armature.getBoneTransforms()[i]);
        }
    }

    /**
     * Uploads the Vector4f to the specified location
     * Shader must be bound before calling this method
     * @param name The name of the GLSL vec4 variable
     * @param data The data to upload to the location
     */
    public void setMatrix4f(String name, Matrix4f data) {
        glUniform4fv(getUniformLocation(name), data.get(new float[16]));
    }

    public void setIntArray(String name, int[] data) {
        glUniform1iv(getUniformLocation(name), data);
    }

    /**
     * Deprecated by {@link #uploadEntity(Entity)}
     */
    @Deprecated
    public void updateTransform(Entity entity) {
        int uloc = glGetUniformLocation(program, "model");
        if (uloc >= 0) glUniformMatrix4fv(uloc, false, entity.getModel());
    }

    /**
     * Deprecated by {@link #uploadEntity(Entity)}
     */
    @Deprecated
    public void updateTransform(Entity[] entities) {
        int i = 0;
        while (i < entities.length && entities[i] != null) {
            int uloc = getUniformLocation("models[" + i + "]");
            glUniformMatrix4fv(uloc, false, entities[i].getModel());
            i++;
        }
    }

    /**
     * Deprecated by {@link #uploadEntity(Entity)}
     */
    @Deprecated
    public void updateTransform(List<? extends Entity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            int uloc = getUniformLocation("models[" + i + "]");
            if (entities.get(i) != null) {
                glUniformMatrix4fv(uloc, false, entities.get(i).getModel());
            }
        }
    }

    @Deprecated
    public void setTextureAtlas(TextureAtlas textureAtlas, Vector2f offset) {
        glUniform1i(getUniformLocation("cellCount"), textureAtlas.getCellCount());
        glUniform2f(getUniformLocation("offset"), offset.x, offset.y);
    }

    public void setTextureArrayLayer(int layer) {
        glUniform1i(getUniformLocation("layer"), layer);
    }

    /**
     * Get the shader's uniform location
     * @param uniformName
     * @return -1 if the variable was not found, 0 and + otherwise
     */
    private int getUniformLocation(String uniformName) {
        Integer location;
        if ((location = locations.get(uniformName)) != null) return location;
        location = glGetUniformLocation(program, uniformName);
        locations.put(uniformName, location);
        return location;
    }

    /**
     * Loads a shader
     *
     * @param type Type of the shader either GL_FRAGMENT_SHADER or GL_VERTEX_SHADER
     * @param path relative path
     * @return
     */
    private static int loadShader(int type, String path) {
        int shader = glCreateShader(type);
        String content = FileUtils.loadFile(path);
        if (content != null) {
            glShaderSource(shader, content);
            glCompileShader(shader);
            if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
                System.err.println(glGetShaderInfoLog(shader));
                return -2;
            }

            return shader;
        }

        return -1;
    }

    public int getProgram() {
        return program;
    }

    public static Shader getTextureShader() {
        return loadShaderRelative("single", "single_texture");
    }

    public static Shader getColorArrayShader() {
        return loadShaderRelative("single_colors", "single_colors");
    }

    public static Shader getColorArrayFlatShader() {
        return loadShaderRelative("single_colors_flat_shading", "flat_shading");
    }

    public static Shader getMultipleFlatColorShader() {
        return loadShaderRelative("multiple", "multiple_flat_color");
    }

    public static Shader getMaterialShader() {
        return loadShaderRelative("single", "single_material");
    }

    public static Shader getMaterialFlatShader() {
        return loadShaderRelative("single_material_flat_shading", "flat_shading");
    }

    public static Shader getWaterShader() {
        return loadShaderRelative("single_material_flat_shading", "water_flat_shading");
    }

    public static Shader getDebugShader() {
        return loadShaderRelative("single", "debug");
    }
}
