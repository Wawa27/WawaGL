package com.wawacorp.wawagl.core.opengl.event;

import com.wawacorp.wawagl.core.opengl.game.Game;
import com.wawacorp.wawagl.core.opengl.view.View;
import com.wawacorp.wawagl.core.opengl.shader.Shader;
import com.wawacorp.wawagl.core.opengl.shader.bo.FrameBufferObject;
import com.wawacorp.wawagl.core.opengl.shader.bo.texture.Texture2D;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL30.GL_RG;

/**
 * Simple algorithm for object picking
 * We draw every pickable objects in a frame buffer and read the pixel
 * at the cursor position
 */
public class PickedObjectEvent extends FrameBufferObject {
    /**
     * The pixel under the cursor at each frame is stored in this array of length 1
     */
    private final FloatBuffer pixel;

    private final Vector2f pixelColor;

    private final static int FORMAT = GL_RG;

    private final ArrayList<View> pickableObjects;

    private final Shader pickingShader;

    private View pickedObject;

    private PickedObjectListener pickedObjectListener;

    public PickedObjectEvent(PickedObjectListener pickedObjectListener) {
        this.pickedObjectListener = pickedObjectListener;
        pixel = BufferUtils.createFloatBuffer(2);
        pixelColor = new Vector2f();
        pickableObjects = new ArrayList<>();
        pickingShader = Shader.loadShader("shaders/vert/world_vertex_shader_picking.glsl", "shaders/frag/color_picking.glsl");
        init();
    }

    /**
     * Inits the FBO
     */
    private void init() {
        bind();
        addTexture(new Texture2D(Game.width, Game.height));
        addDepthBuffer();
        glEnable(GL_DEPTH_TEST);
        unbind();
    }

    public void addPickableObject(View view) {
        this.pickableObjects.add(view);
    }

    @Override
    public void draw() {
        bind();
        clear();
        for (int i = 0; i < pickableObjects.size(); i++) {
            pickingShader.bind();
            int location = glGetUniformLocation(pickingShader.getProgram(), "color");
            glUniform2f(location, (i % 255) / 255f, (i / 255) / 255f);
            pickableObjects.get(i).draw(pickingShader);
            pickingShader.unbind();
        }

        double[] cursorPos = Game.getContext().getCursorPos();
        glReadPixels((int) cursorPos[0], Game.height - (int) cursorPos[1], 1, 1, FORMAT, GL_FLOAT, pixel);
        pixelColor.set(pixel.get(0), pixel.get(1));
        unbind();

        int index = Math.round(pixelColor.x * 255 + pixelColor.y * 255 * 255);
        if (index <= pickableObjects.size() && index >= 0) {
            if (pickedObject != null && pickedObject != pickableObjects.get(index)) {
                // Not the same picked object
                pickedObjectListener.onLeftHover(pickedObject);
                pickedObject = pickableObjects.get(index);
                pickedObjectListener.onHover(pickedObject);
            } else if (pickedObject != null){
                // same object, we do nothing
            } else {
                pickedObject = pickableObjects.get(index);
                pickedObjectListener.onHover(pickedObject);
            }
        } else { // no object picked
            if (pickedObject != null) {
                pickedObjectListener.onLeftHover(pickedObject);
                pickedObject = null;
            }
        }
    }

    public void setPickedObjectListener(PickedObjectListener pickedObjectListener) {
        this.pickedObjectListener = pickedObjectListener;
    }

    /**
     * @return the pixel under the window's cursor at the current frame
     */
    public Vector2f getPixelColor() {
        return pixelColor;
    }

    @Override
    public void draw(Shader shader) throws RuntimeException {
        throw new RuntimeException("Not supported");
    }
}
