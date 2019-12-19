package com.wawacorp.wawagl.core.game;

import com.wawacorp.wawagl.core.model.entity.EntityManager;

public class GameManager {

    public GameManager() {

    }

    public void onLoop() {
        EntityManager.loop();
    }
}
