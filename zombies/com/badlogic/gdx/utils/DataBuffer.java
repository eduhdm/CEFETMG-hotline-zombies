/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.utils;

import com.badlogic.gdx.utils.DataOutput;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.OutputStream;

public class DataBuffer
extends DataOutput {
    private final StreamUtils.OptimizedByteArrayOutputStream outStream;

    public DataBuffer() {
        this(32);
    }

    public DataBuffer(int initialSize) {
        super(new StreamUtils.OptimizedByteArrayOutputStream(initialSize));
        this.outStream = (StreamUtils.OptimizedByteArrayOutputStream)this.out;
    }

    public byte[] getBuffer() {
        return this.outStream.getBuffer();
    }

    public byte[] toArray() {
        return this.outStream.toByteArray();
    }
}

