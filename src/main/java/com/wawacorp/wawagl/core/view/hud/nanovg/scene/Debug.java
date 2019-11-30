package com.wawacorp.wawagl.core.view.hud.nanovg.scene;

import com.wawacorp.wawagl.core.view.hud.Hud;
import com.wawacorp.wawagl.core.view.hud.nanovg.component.Text;

public class Debug extends Hud {
    private final Text fpsText;

    public Debug(String fps) {
        fpsText = new Text(0, 16, fps);
        addComponent(fpsText);
    }

    public void setFps(String fps) {
        fpsText.setText(fps);
    }
}
