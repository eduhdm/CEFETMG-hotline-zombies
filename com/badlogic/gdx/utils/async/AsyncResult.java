/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.utils.async;

import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncResult<T> {
    private final Future<T> future;

    AsyncResult(Future<T> future) {
        this.future = future;
    }

    public boolean isDone() {
        return this.future.isDone();
    }

    public T get() {
        try {
            return this.future.get();
        }
        catch (InterruptedException ex) {
            return null;
        }
        catch (ExecutionException ex) {
            throw new GdxRuntimeException(ex.getCause());
        }
    }
}

