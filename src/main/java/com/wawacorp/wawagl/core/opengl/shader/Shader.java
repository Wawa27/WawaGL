package com.wawacorp.wawagl.core.opengl.shader;

import com.wawacorp.wawagl.core.opengl.entity.Entity;
import com.wawacorp.wawagl.core.opengl.shader.bo.meta.Material;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.TextureArray;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.TextureAtlas;
import com.wawacorp.wawagl.core.utils.io.FileUtils;
import org.joml.Vector2f;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final int program;

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

    public boolean setMaterial(Material material) {
        int uloc = glGetUniformLocation(program, "material.ambient");
        glUniform3f(uloc, material.getAmbient().x, material.getAmbient().y, material.getAmbient().z);
        uloc = glGetUniformLocation(program, "material.diffuse");
        glUniform3f(uloc, material.getDiffuse().x, material.getDiffuse().y, material.getDiffuse().z);
        uloc = glGetUniformLocation(program, "material.specular");
        glUniform3f(uloc, material.getSpecular().x, material.getSpecular().y, material.getSpecular().z);
        return true;
    }

    public void setVec2(String name, Vector2f vec2) {
        glUniform2f(getUniformLocation(name), vec2.x, vec2.y);
    }

    public void updateTransform(Entity entity) {
        int uloc = glGetUniformLocation(program, "model");
        if (uloc >= 0) glUniformMatrix4fv(uloc, false, entity.getModel());
    }

    public void updateTransform(Entity[] entities) {
        int i = 0;
        while (i < entities.length && entities[i] != null) {
            int uloc = glGetUniformLocation(program, "models[" + i + "]");
            if (uloc >= 0) glUniformMatrix4fv(uloc, false, entities[i].getModel());
            i++;
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
        return loadShader("shaders/vert/world_vertex_shader.glsl", "shaders/frag/simple_texture.glsl");
    }

    public static Shader getTextureAtlasShader() {
        return loadShader("shaders/vert/world_vertex_shader_texture_array.glsl", "shaders/frag/simple_texture.glsl");
    }

    public static Shader getTextureArrayShader() {
        return loadShader("shaders/vert/world_vertex_shader_texture.glsl", "shaders/frag/simple_texture_array.glsl");
    }

    public static Shader getMaterialShader() {
        return loadShader("shaders/vert/world_vertex_shader.glsl", "shaders/frag/simple_material.glsl");
    }

    public static Shader getInstancedMaterialShader() {
        return loadShader("shaders/vert/instanced_world_vertex_shader.glsl", "shaders/frag/material_shader_lights.glsl");
    }

    public static Shader getInstancedTextureShader() {
        return loadShader("shaders/vert/instanced_world_vertex_shader.glsl",  "shaders/frag/texture_shader_lights.glsl");
    }

    public static Shader getDefaultShader() {
        return loadShader("shaders/vert/default.glsl",  "shaders/frag/default.glsl");
    }
}
