/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class Logger {
    public static final int NONE = 0;
    public static final int ERROR = 1;
    public static final int INFO = 2;
    public static final int DEBUG = 3;
    private final String tag;
    private int level;

    public Logger(String tag) {
        this(tag, 1);
    }

    public Logger(String tag, int level) {
        this.tag = tag;
        this.level = level;
    }

    public void debug(String message) {
        if (this.level >= 3) {
            Gdx.app.debug(this.tag, message);
        }
    }

    public void debug(String message, Exception exception) {
        if (this.level >= 3) {
            Gdx.app.debug(this.tag, message, exception);
        }
    }

    public void info(String message) {
        if (this.level >= 2) {
            Gdx.app.log(this.tag, message);
        }
    }

    public void info(String message, Exception exception) {
        if (this.level >= 2) {
            Gdx.app.log(this.tag, message, exception);
        }
    }

    public void error(String message) {
        if (this.level >= 1) {
            Gdx.app.error(this.tag, message);
        }
    }

    public void error(String message, Throwable exception) {
        if (this.level >= 1) {
            Gdx.app.error(this.tag, message, exception);
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
}

