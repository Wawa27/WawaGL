package com.wawacorp.wawagl.core.view.instance.property;

import com.wawacorp.wawagl.core.model.entity.AttachedEntity;
import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.shader.Shader;
import org.joml.Vector3f;

import javax.swing.table.AbstractTableModel;

public class AttachedEntityProperty extends Property {
    //TODO: should extends EntityProperty or use it directly ???
    private final AttachedEntity entity;

    public AttachedEntityProperty(String name, Entity parent, Vector3f distance) {
        super(name);
        this.entity = new AttachedEntity(parent, distance);
    }

    @Override
    public void upload(Shader shader) {
        shader.uploadEntity(name, entity);
    }
}
