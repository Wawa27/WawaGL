package com.wawacorp.wawagl.core.view;

public interface Movable {

    void translate(float x, float y, float z);

    void scale(float x, float y, float z);

    void rotate(float x, float y, float z);

    void setTranslation(float x, float y, float z);

    void setScale(float x, float y, float z);

    void setRotation(float x, float y, float z);
}
