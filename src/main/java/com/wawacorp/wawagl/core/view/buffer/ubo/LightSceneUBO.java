package com.wawacorp.wawagl.core.view.buffer.ubo;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.view.buffer.BufferObject;
import com.wawacorp.wawagl.core.view.lighting.LightScene;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindBufferBase;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;


/**
 * A light Scene is composed of :
 * - Active Lights     -> int x 3                                                          -> 3 * 4
 * - Point Lights      -> Position(vec3), Color(vec3)                                        -> 2 * 4 * 4
 * - Directional Lights-> Color(vec3), Direction(vec3)                                       -> 2 * 4 * 4
 * - Spot Lights       -> Position(vec3), Color(vec3), Direction(vec3),  Cutoff (float)      -> 3 * 4 * 4 + 4
 *
 * Alignement :
 *      - 1 int             - 0  -> 8
 *      - 1 int             - 8  -> 16
 *      - 1 int             - 16 -> 24
 *      - 1 vec3            - 32 -> 48
 */
public class LightSceneUBO extends BufferObject {
    private final static int TARGET = GL_UNIFORM_BUFFER;

    private final static int ACTIVE_LIGHT_COUNT_SIZE = 16;
    private final static int POINT_LIGHT_SIZE = 32;
    private final static int DIRECTIONAL_LIGHT_SIZE = 32;
    private final static int SPOT_LIGHT_SIZE = 52;

    // TODO: remove this, should be handled by the UBO class
    private final static int INTERFACE_BLOCK_LOCATION = 1;

    private final LightScene lightScene;

    public LightSceneUBO(LightScene lightScene) {
        super(glGenBuffers());

        this.lightScene = lightScene;

        glBindBuffer(TARGET, handle);
        glBufferData(TARGET, ACTIVE_LIGHT_COUNT_SIZE + LightScene.MAX_LIGHTS * (POINT_LIGHT_SIZE + DIRECTIONAL_LIGHT_SIZE + SPOT_LIGHT_SIZE), GL_STREAM_DRAW);
        glBindBuffer(TARGET, 0);

        update();
    }

    @Override
    public void bind() {
        glBindBufferBase(TARGET, INTERFACE_BLOCK_LOCATION, handle);
    }

    @Override
    public void unbind() {
        glBindBufferBase(TARGET, INTERFACE_BLOCK_LOCATION, 0);
    }

    // TODO: should only be updated when needed
    @Override
    public void update() {
        updateLightCount();
        for (int i = 0; i < lightScene.getPointLights().size(); i++) {
            updatePointLight(i);
        }
        for (int i = 0; i < lightScene.getDirectionalLights().size(); i++) {
            updateDirectionalLight(i);
        }
        for (int i = 0; i < lightScene.getSpotLights().size(); i++) {
            updateSpotLight(i);
        }
    }

    public void updateLightCount() {
        glBindBuffer(TARGET, handle);
        glBufferSubData(TARGET, 0, new int[] {
                lightScene.getActivePointLights(),
                lightScene.getActiveDirectionalLights(),
                lightScene.getActiveSpotLights(),
                0
        });
        glBindBuffer(TARGET, 0);
    }

    public void updatePointLight(int lightOffset) {
        int offset = ACTIVE_LIGHT_COUNT_SIZE + lightOffset * POINT_LIGHT_SIZE;

        glBindBuffer(TARGET, handle);

        Vector4f position = Camera.ACTIVE.getPositionViewSpace(
                new Vector4f(lightScene.getPointLights().get(lightOffset).getEntity().getPosition(), 1.0f)
        );
        glBufferSubData(TARGET, offset, new float[]{position.x, position.y, position.z});

        Vector3f color = lightScene.getPointLights().get(lightOffset).getColor();
        glBufferSubData(TARGET, offset + 16, new float[]{color.x, color.y, color.z});

        glBindBuffer(TARGET, 0);

        updateLightCount();
    }

    public void updateDirectionalLight(int lightOffset) {
        int offset = ACTIVE_LIGHT_COUNT_SIZE + LightScene.MAX_LIGHTS * POINT_LIGHT_SIZE + lightOffset * DIRECTIONAL_LIGHT_SIZE;
        glBindBuffer(TARGET, handle);

        Vector4f direction = Camera.ACTIVE.getVectorViewSpace(
                new Vector4f(lightScene.getDirectionalLights().get(lightOffset).getDirection())
        );
        //TODO: cache the float buffer instead of creating a new one
        glBufferSubData(TARGET, offset, new float[]{direction.x, direction.y, direction.z});

        Vector3f color = lightScene.getDirectionalLights().get(lightOffset).getColor();
        glBufferSubData(TARGET, offset + 16, new float[]{color.x, color.y, color.z});

        glBindBuffer(TARGET, 0);
    }

    public void updateSpotLight(int lightOffset) {
        int offset = ACTIVE_LIGHT_COUNT_SIZE + LightScene.MAX_LIGHTS * (POINT_LIGHT_SIZE + DIRECTIONAL_LIGHT_SIZE) + lightOffset * SPOT_LIGHT_SIZE;
        glBindBuffer(TARGET, handle);

        Vector4f position = new Vector4f(lightScene.getSpotLights().get(lightOffset).getEntity().getPosition(), 1.0f);
        position.mul(Camera.ACTIVE.getViewMatrix());
        glBufferSubData(TARGET, offset, new float[]{position.x, position.y, position.z});

        Vector4f direction = new Vector4f(lightScene.getSpotLights().get(lightOffset).getDirection(), 1f);
        direction.mul(Camera.ACTIVE.getViewMatrix());
        glBufferSubData(TARGET, offset + 16, new float[]{direction.x, direction.y, direction.z});

        Vector3f color = lightScene.getSpotLights().get(lightOffset).getColor();
        glBufferSubData(TARGET, offset + 32, new float[]{color.x, color.y, color.z});

        float cutoff = lightScene.getSpotLights().get(lightOffset).getCutoff();
        glBufferSubData(TARGET, offset + 48, new float[]{cutoff});

        glBindBuffer(TARGET, 0);
    }

    @Override
    public void dispose() {

    }
}
