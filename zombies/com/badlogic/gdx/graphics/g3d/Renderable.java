/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.Matrix4;

public class Renderable {
    public final Matrix4 worldTransform = new Matrix4();
    public final MeshPart meshPart = new MeshPart();
    public Material material;
    public Environment environment;
    public Matrix4[] bones;
    public Shader shader;
    public Object userData;

    public Renderable set(Renderable renderable) {
        this.worldTransform.set(renderable.worldTransform);
        this.material = renderable.material;
        this.meshPart.set(renderable.meshPart);
        this.bones = renderable.bones;
        this.environment = renderable.environment;
        this.shader = renderable.shader;
        this.userData = renderable.userData;
        return this;
    }
}

