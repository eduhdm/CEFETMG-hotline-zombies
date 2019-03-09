/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.utils;

public final class NumberUtils {
    public static int floatToIntBits(float value) {
        return Float.floatToIntBits(value);
    }

    public static int floatToRawIntBits(float value) {
        return Float.floatToRawIntBits(value);
    }

    public static int floatToIntColor(float value) {
        return Float.floatToRawIntBits(value);
    }

    public static float intToFloatColor(int value) {
        return Float.intBitsToFloat(value & -16777217);
    }

    public static float intBitsToFloat(int value) {
        return Float.intBitsToFloat(value);
    }

    public static long doubleToLongBits(double value) {
        return Double.doubleToLongBits(value);
    }

    public static double longBitsToDouble(long value) {
        return Double.longBitsToDouble(value);
    }
}

