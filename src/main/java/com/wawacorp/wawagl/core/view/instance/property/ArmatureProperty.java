package com.wawacorp.wawagl.core.view.instance.property;

import com.wawacorp.wawagl.core.model.animation.Armature;
import com.wawacorp.wawagl.core.shader.Shader;

import javax.swing.plaf.synth.SynthUI;
import java.util.Observable;
import java.util.Observer;

public class ArmatureProperty extends Property implements Observer {
    private final Armature armature;

    public ArmatureProperty(String name, Armature armature) {
        super(name);
        this.armature = armature;
        this.armature.addObserver(this::update);
    }

    @Override
    public void update(Observable observable, Object o) {
        setChanged();
        notifyObservers();
    }

    @Override
    public void upload(Shader shader) {
        shader.uploadArmature(this.name, armature);
    }
}
