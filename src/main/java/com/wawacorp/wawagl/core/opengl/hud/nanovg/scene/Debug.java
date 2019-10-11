package com.wawacorp.wawagl.core.opengl.hud.nanovg.scene;

import com.wawacorp.wawagl.core.opengl.hud.HudScene;
import com.wawacorp.wawagl.core.opengl.hud.nanovg.component.Text;

public class Debug extends HudScene {

    public Debug(StringBuilder fps) {
        super(
                new Text(0, 16, fps)
        );
    }
}
