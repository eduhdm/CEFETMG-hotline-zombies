/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.graphics.g3d.particles.renderers;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderData;

public class ModelInstanceControllerRenderData
extends ParticleControllerRenderData {
    public ParallelArray.ObjectChannel<ModelInstance> modelInstanceChannel;
    public ParallelArray.FloatChannel colorChannel;
    public ParallelArray.FloatChannel scaleChannel;
    public ParallelArray.FloatChannel rotationChannel;
}

