package com.wawacorp.wawagl.demo.camera;

import com.wawacorp.wawagl.core.camera.Camera;
import com.wawacorp.wawagl.core.camera.TPSCamera;
import com.wawacorp.wawagl.core.camera.projection.Perspective;
import com.wawacorp.wawagl.core.controller.event.KeyboardListener;
import com.wawacorp.wawagl.core.controller.event.ScrollListener;
import com.wawacorp.wawagl.core.game.Game;
import com.wawacorp.wawagl.core.model.FlatColor;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.model.entity.Player;
import com.wawacorp.wawagl.core.model.shape.Cube;
import com.wawacorp.wawagl.core.scene.Scene;
import com.wawacorp.wawagl.core.utils.AssimpLoader;
import com.wawacorp.wawagl.core.view.GLModel;
import com.wawacorp.wawagl.core.view.instance.Instance;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.FlatColorProperty;
import com.wawacorp.wawagl.core.view.single.mesh.GLSingleMesh;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class FPSCameraDemo extends Scene {
    private final Player player;
    private final GLSingleMesh cube;
    private final GLModel playerView;
    private final TPSCamera camera;
    private float cameraAngleZ;
    private float cameraAngleY;
    private float distance = 4;
    private final GLSingleMesh floor;

    public FPSCameraDemo() {
        cube = new GLSingleMesh(new Cube(), new Instance(
                new EntityProperty(),
                new FlatColorProperty("color", FlatColor.DEFAULT)
        ));
        player = new Player();
        player.translate(0, 0, 8);
//        player.rotate(0, (float) Math.PI, 0);
        playerView = GLModel.getSingleModel(AssimpLoader.loadScene("models/character/character.obj").getRoot(), new Entity());
        cameraAngleZ = 0;
        camera = new TPSCamera(Perspective.DEFAULT, player, 8, (float) Math.PI/4, 0);
        Entity floorEntity = new Entity();
        floor = new GLSingleMesh(new Cube(), new Instance(
                new FlatColorProperty("color", FlatColor.WHITE),
                new EntityProperty("model", floorEntity)
        ));
        floorEntity.setScale(256, .001f, 256);
        Camera.setActive(camera);
        new KeyboardListener() {
            @Override
            public void onKeyPressed(int key) {
                if (key == GLFW_KEY_Z) {
                    player.forward();
                } else if (key == GLFW_KEY_S) {
                    player.backward();
                } else if (key == GLFW_KEY_D) {
                    player.rotate(0, .1f, 0);
                } else if (key == GLFW_KEY_Q) {
                    player.rotate(0, -.1f, 0);
                } else if (key == GLFW_KEY_SPACE) {
                    player.translate(0, 1, 0);
                } else if (key == GLFW_KEY_UP) {
                    camera.setAngleZ(cameraAngleZ += .1f);
                } else if (key == GLFW_KEY_DOWN) {
                    camera.setAngleZ(cameraAngleZ -= .1f);
                } else if (key == GLFW_KEY_RIGHT) {
                    camera.setAngleY(cameraAngleY += .1f);
                } else if (key == GLFW_KEY_LEFT) {
                    camera.setAngleY(cameraAngleY -= .1f);
                }
            }

            @Override
            public void onKeyReleased(int key) {

            }

            @Override
            public void onKeyRepeated(int key) {

            }
        };

        new ScrollListener() {
            @Override
            public void onScroll(double xoffset, double yoffset) {
                camera.setDistance(distance += yoffset);
            }
        };
    }

    @Override
    public void onLoop() {
        playerView.draw();
        cube.draw();
        floor.draw();
    }

    public static void main(String[] args) {
        Game game = new Game(960, 640, "Nemesis");
        game.setScene(new FPSCameraDemo());
        game.setBackgroundColor(new Vector4f(.1f, .1f, .1f, 1));
        game.start();
    }
}
