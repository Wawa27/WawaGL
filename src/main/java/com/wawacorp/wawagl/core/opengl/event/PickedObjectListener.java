package com.wawacorp.wawagl.core.opengl.event;

import com.wawacorp.wawagl.core.opengl.view.View;

public interface PickedObjectListener {

    /**
     * Triggered when the cursor is on the object
     */
    void onHover(View view);

    /**
     * Triggered when the cursor goes outside the object
     */
    void onLeftHover(View view);
}
