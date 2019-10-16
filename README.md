# WawaGL

3D graphics engine built on top of LWJGL

## Features
    - Model Importer 
    - Images
    - Skybox
    - Entity manipulation 
    - Texture
    
    
    TODO
    - Lights
    - Materials
    
## Hello World

    package com.wawacorp.wawagl.demo;
    
    import com.wawacorp.wawagl.core.opengl.Color;
    import com.wawacorp.wawagl.core.opengl.transform.view.model.Model;
    import com.wawacorp.wawagl.core.opengl.game.Game;
    import com.wawacorp.wawagl.core.utils.io.AssimpLoader;
    import com.wawacorp.wawagl.core.opengl.view.objects.GameObject;
    
    import java.util.ArrayList;
    
    public class GameTest extends Game {
        private ArrayList<GameObject> gameObjects;
    
        public GameTest() {
            super(960, 640);
        }
    
        @Override
        public void onGameCreated() {
            gameObjects = new ArrayList<>();
    
            setWindowTitle("Hello World !");
            setBackgroundColor(new Color(1, 1, 1, 0));
    
            Model model = ModelAdapter.loadModel("./res/models/boy/boy.fbx");
            GameObject view = new GameObject(model);
            gameObjects.add(view);
        }
    
        @Override
        public void onLoop() {
            for (GameObject o : gameObjects) {
                o.rotate(0.01f, 0, 1, 0);
                o.draw();
            }
        }
    
        public static void main(String... args) {
            new GameTest();
        }
    }

