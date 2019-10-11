package com.wawacorp.wawagl.core.opengl.event;

import com.wawacorp.wawagl.core.opengl.view.Drawable;

public interface PickedObjectListener {

    /**
     * Triggered when the cursor is on the object
     */
    void onHover(Drawable drawable);

    /**
     * Triggered when the cursor goes outside the object
     */
    void onLeftHover(Drawable drawable);
}
