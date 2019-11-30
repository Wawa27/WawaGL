package com.wawacorp.wawagl.core.view;

import com.wawacorp.wawagl.core.shader.ShaderException;
import com.wawacorp.wawagl.core.view.lighting.DirectionalLight;
import com.wawacorp.wawagl.core.view.lighting.LightScene;
import com.wawacorp.wawagl.core.view.lighting.PointLight;
import com.wawacorp.wawagl.core.view.lighting.SpotLight;
import com.wawacorp.wawagl.ddd.background.SkyBox3D;

@Deprecated
public class Environnement {
    public static SkyBox3D skyBox;
    public static LightScene lightScene;

    static {
            Environnement.lightScene = new LightScene();
    }

    private Environnement() throws ShaderException {

    }

    public static SkyBox3D getSkyBox() {
        return skyBox;
    }

    public static void setSkyBox(SkyBox3D skyBox) {
        Environnement.skyBox = skyBox;
    }

    public static void draw() {
        if (skyBox != null) skyBox.draw();
        for (PointLight l : getLightScene().getPointLights()) l.draw();
        for (DirectionalLight d : getLightScene().getDirectionalLights()) d.draw();
        for (SpotLight s : getLightScene().getSpotLights()) s.draw();
    }

    public static void setLightScene(LightScene lightScene) {
        if (lightScene == null) return;
        Environnement.lightScene = lightScene;
    }

    public static void addLight(PointLight pointLight) {
        lightScene.addLight(pointLight);
        lightScene.update();
    }

    public static void addLight(DirectionalLight directionalLight) {
        lightScene.addLight(directionalLight);
    }

    public static void addLight(SpotLight spotLight) {
        lightScene.addLight(spotLight);
    }

    public static LightScene getLightScene() {
        return lightScene;
    }
}
