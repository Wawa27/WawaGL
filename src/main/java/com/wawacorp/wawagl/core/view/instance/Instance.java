package com.wawacorp.wawagl.core.view.instance;

import com.wawacorp.wawagl.core.model.entity.Entity;
import com.wawacorp.wawagl.core.shader.Shader;
import com.wawacorp.wawagl.core.view.Bindable;
import com.wawacorp.wawagl.core.view.instance.property.EntityProperty;
import com.wawacorp.wawagl.core.view.instance.property.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class Instance extends Observable implements Observer, Bindable {
    private final ArrayList<Property> properties;

    public Instance() {
        properties = new ArrayList<>();
        update();
    }

    /**
     * Creates a new Instance with an EntityProperty
     * @param entity
     */
    public Instance(Entity entity) {
        this();
        properties.add(new EntityProperty("model", entity));
        update();
    }

    public Instance(Property... properties) {
        this();
        this.properties.addAll(Arrays.asList(properties));
        for (Property property : properties) {
            property.addObserver(this);
        }
        update();
    }

    @Override
    public void update(Observable observable, Object o) {
        setChanged();
        notifyObservers();
    }

    public void upload(Shader shader) {
        for (Property property : properties) {
            property.upload(shader);
        }
    }

    @Override
    public void bind() {
        for (Property p : properties) {
            if (p instanceof Bindable) ((Bindable) p).bind();
        }
    }

    @Override
    public void unbind() {
        for (Property p : properties) {
            if (p instanceof Bindable) ((Bindable) p).unbind();
        }
    }

    public void update() {
        setChanged();
        notifyObservers();
    }

//    //TODO: When a property value change, we should only update the property instead of all the proprerties
//    @Override
//    public void update(Observable observable, Object o) {
//        update();
//    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public Property getProperty(String name) {
        for (Property p : properties) if (p.getName().equals(name)) return p;
        return null;
    }

    public void addProperty(Property property) {
        properties.add(property);
        property.addObserver(this::update);
        update();
    }

    public void onLoop() {

    }
}
