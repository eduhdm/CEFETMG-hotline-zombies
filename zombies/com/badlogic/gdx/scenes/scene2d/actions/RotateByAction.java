/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;

public class RotateByAction
extends RelativeTemporalAction {
    private float amount;

    @Override
    protected void updateRelative(float percentDelta) {
        this.target.rotateBy(this.amount * percentDelta);
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float rotationAmount) {
        this.amount = rotationAmount;
    }
}

