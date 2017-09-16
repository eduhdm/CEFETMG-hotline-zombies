/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;

public class MoveByAction
extends RelativeTemporalAction {
    private float amountX;
    private float amountY;

    @Override
    protected void updateRelative(float percentDelta) {
        this.target.moveBy(this.amountX * percentDelta, this.amountY * percentDelta);
    }

    public void setAmount(float x, float y) {
        this.amountX = x;
        this.amountY = y;
    }

    public float getAmountX() {
        return this.amountX;
    }

    public void setAmountX(float x) {
        this.amountX = x;
    }

    public float getAmountY() {
        return this.amountY;
    }

    public void setAmountY(float y) {
        this.amountY = y;
    }
}

