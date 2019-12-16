package com.wawacorp.wawagl.core.view.gui.nanovg.scene;

import com.wawacorp.wawagl.core.view.gui.Hud;
import com.wawacorp.wawagl.core.view.gui.nanovg.component.Text;

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
