package com.wawacorp.wawagl.core.view.gui.nanovg;

import com.wawacorp.wawagl.core.view.gui.HudComponent;

public interface HudListener {

    /**
     * This methods is called whenever the cursor entered the hud component bounds
     */
    void onEnter(HudComponent hudComponent);
}
