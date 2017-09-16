/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.audio;

import com.badlogic.gdx.utils.Disposable;

public interface Sound
extends Disposable {
    public long play();

    public long play(float var1);

    public long play(float var1, float var2, float var3);

    public long loop();

    public long loop(float var1);

    public long loop(float var1, float var2, float var3);

    public void stop();

    public void pause();

    public void resume();

    @Override
    public void dispose();

    public void stop(long var1);

    public void pause(long var1);

    public void resume(long var1);

    public void setLooping(long var1, boolean var3);

    public void setPitch(long var1, float var3);

    public void setVolume(long var1, float var3);

    public void setPan(long var1, float var3, float var4);
}

