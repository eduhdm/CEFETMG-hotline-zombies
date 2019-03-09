/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.backends.lwjgl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglGraphics;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import java.awt.Canvas;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.LWJGLException;

public class LwjglCursor
implements Cursor {
    org.lwjgl.input.Cursor lwjglCursor = null;

    public LwjglCursor(Pixmap pixmap, int xHotspot, int yHotspot) {
        if (((LwjglGraphics)Gdx.graphics).canvas != null && SharedLibraryLoader.isMac) {
            return;
        }
        try {
            if (pixmap == null) {
                this.lwjglCursor = null;
                return;
            }
            if (pixmap.getFormat() != Pixmap.Format.RGBA8888) {
                throw new GdxRuntimeException("Cursor image pixmap is not in RGBA8888 format.");
            }
            if ((pixmap.getWidth() & pixmap.getWidth() - 1) != 0) {
                throw new GdxRuntimeException("Cursor image pixmap width of " + pixmap.getWidth() + " is not a power-of-two greater than zero.");
            }
            if ((pixmap.getHeight() & pixmap.getHeight() - 1) != 0) {
                throw new GdxRuntimeException("Cursor image pixmap height of " + pixmap.getHeight() + " is not a power-of-two greater than zero.");
            }
            if (xHotspot < 0 || xHotspot >= pixmap.getWidth()) {
                throw new GdxRuntimeException("xHotspot coordinate of " + xHotspot + " is not within image width bounds: [0, " + pixmap.getWidth() + ").");
            }
            if (yHotspot < 0 || yHotspot >= pixmap.getHeight()) {
                throw new GdxRuntimeException("yHotspot coordinate of " + yHotspot + " is not within image height bounds: [0, " + pixmap.getHeight() + ").");
            }
            IntBuffer pixelBuffer = pixmap.getPixels().asIntBuffer();
            int[] pixelsRGBA = new int[pixelBuffer.capacity()];
            pixelBuffer.get(pixelsRGBA);
            int[] pixelsARGBflipped = new int[pixelBuffer.capacity()];
            if (pixelBuffer.order() == ByteOrder.BIG_ENDIAN) {
                for (int y = 0; y < pixmap.getHeight(); ++y) {
                    for (int x = 0; x < pixmap.getWidth(); ++x) {
                        int pixel = pixelsRGBA[x + y * pixmap.getWidth()];
                        pixelsARGBflipped[x + (pixmap.getHeight() - 1 - y) * pixmap.getWidth()] = pixel >> 8 & 16777215 | pixel << 24 & -16777216;
                    }
                }
            } else {
                for (int y = 0; y < pixmap.getHeight(); ++y) {
                    for (int x = 0; x < pixmap.getWidth(); ++x) {
                        int pixel = pixelsRGBA[x + y * pixmap.getWidth()];
                        pixelsARGBflipped[x + (pixmap.getHeight() - 1 - y) * pixmap.getWidth()] = (pixel & 255) << 16 | (pixel & 16711680) >> 16 | pixel & -16711936;
                    }
                }
            }
            this.lwjglCursor = new org.lwjgl.input.Cursor(pixmap.getWidth(), pixmap.getHeight(), xHotspot, pixmap.getHeight() - yHotspot - 1, 1, IntBuffer.wrap(pixelsARGBflipped), null);
        }
        catch (LWJGLException e) {
            throw new GdxRuntimeException("Could not create cursor image.", e);
        }
    }

    @Override
    public void dispose() {
    }
}

