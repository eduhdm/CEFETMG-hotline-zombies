/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.utils.SharedLibraryLoader;

public final class Box2D {
    private Box2D() {
    }

    public static void init() {
        new SharedLibraryLoader().load("gdx-box2d");
    }
}

