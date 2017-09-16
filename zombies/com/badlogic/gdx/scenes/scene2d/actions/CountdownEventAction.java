/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.actions.EventAction;

public class CountdownEventAction<T extends Event>
extends EventAction<T> {
    int count;
    int current;

    public CountdownEventAction(Class<? extends T> eventClass, int count) {
        super(eventClass);
        this.count = count;
    }

    @Override
    public boolean handle(T event) {
        ++this.current;
        return this.current >= this.count;
    }
}

