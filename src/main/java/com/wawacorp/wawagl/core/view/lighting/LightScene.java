package com.wawacorp.wawagl.core.view.lighting;

import com.wawacorp.wawagl.core.view.buffer.ubo.LightSceneUBO;

import java.util.ArrayList;

public class LightScene {
    /**
     * Maximum number for each type of light (PointLight, DirectionalLight, SpotLight)
     */
    public final static int MAX_LIGHTS = 64;

    private final LightSceneUBO lightSceneUBO;

    // TODO: Should be array
    private final ArrayList<PointLight> pointLights;
    private final ArrayList<DirectionalLight> directionalLights;
    private final ArrayList<SpotLight> spotLights;

    public LightScene() {
        pointLights = new ArrayList<>(MAX_LIGHTS);
        directionalLights = new ArrayList<>(MAX_LIGHTS);
        spotLights = new ArrayList<>(MAX_LIGHTS);

        lightSceneUBO = new LightSceneUBO(this);
    }

    public ArrayList<PointLight> getPointLights() {
        return pointLights;
    }

    public ArrayList<DirectionalLight> getDirectionalLights() {
        return directionalLights;
    }

    public ArrayList<SpotLight> getSpotLights() {
        return spotLights;
    }

    public void addLight(PointLight pointLight) {
        pointLights.add(pointLight);
        lightSceneUBO.updatePointLight(pointLights.indexOf(pointLight));
    }

    public void addLight(DirectionalLight directionalLight) {
        directionalLights.add(directionalLight);
        lightSceneUBO.updateDirectionalLight(directionalLights.indexOf(directionalLight));
    }

    public void addLight(SpotLight spotLight) {
        spotLights.add(spotLight);
        lightSceneUBO.updateSpotLight(spotLights.indexOf(spotLight));
    }

    public int getActivePointLights() {
        return pointLights.size();
    }

    public int getActiveDirectionalLights() {
        return directionalLights.size();
    }

    public int getActiveSpotLights() {
        return spotLights.size();
    }

    public void bind() {
        lightSceneUBO.bind();
    }

    public void unbind() {
        lightSceneUBO.unbind();
    }

    public void update() {
        lightSceneUBO.update();
    }
}
