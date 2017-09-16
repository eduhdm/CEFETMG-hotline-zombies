/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.backends.lwjgl;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class LwjglPreferences
implements Preferences {
    private final String name;
    private final Properties properties;
    private final FileHandle file;

    public LwjglPreferences(String name, String directory) {
        this(new LwjglFileHandle(new File(directory, name), Files.FileType.External));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LwjglPreferences(FileHandle file) {
        this.properties = new Properties();
        this.name = file.name();
        this.file = file;
        if (!file.exists()) {
            return;
        }
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(file.read());
            this.properties.loadFromXML(in);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            StreamUtils.closeQuietly(in);
        }
    }

    @Override
    public Preferences putBoolean(String key, boolean val) {
        this.properties.put(key, Boolean.toString(val));
        return this;
    }

    @Override
    public Preferences putInteger(String key, int val) {
        this.properties.put(key, Integer.toString(val));
        return this;
    }

    @Override
    public Preferences putLong(String key, long val) {
        this.properties.put(key, Long.toString(val));
        return this;
    }

    @Override
    public Preferences putFloat(String key, float val) {
        this.properties.put(key, Float.toString(val));
        return this;
    }

    @Override
    public Preferences putString(String key, String val) {
        this.properties.put(key, val);
        return this;
    }

    @Override
    public Preferences put(Map<String, ?> vals) {
        for (Map.Entry val : vals.entrySet()) {
            if (val.getValue() instanceof Boolean) {
                this.putBoolean(val.getKey(), (Boolean)val.getValue());
            }
            if (val.getValue() instanceof Integer) {
                this.putInteger(val.getKey(), (Integer)val.getValue());
            }
            if (val.getValue() instanceof Long) {
                this.putLong(val.getKey(), (Long)val.getValue());
            }
            if (val.getValue() instanceof String) {
                this.putString(val.getKey(), (String)val.getValue());
            }
            if (!(val.getValue() instanceof Float)) continue;
            this.putFloat(val.getKey(), ((Float)val.getValue()).floatValue());
        }
        return this;
    }

    @Override
    public boolean getBoolean(String key) {
        return this.getBoolean(key, false);
    }

    @Override
    public int getInteger(String key) {
        return this.getInteger(key, 0);
    }

    @Override
    public long getLong(String key) {
        return this.getLong(key, 0);
    }

    @Override
    public float getFloat(String key) {
        return this.getFloat(key, 0.0f);
    }

    @Override
    public String getString(String key) {
        return this.getString(key, "");
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return Boolean.parseBoolean(this.properties.getProperty(key, Boolean.toString(defValue)));
    }

    @Override
    public int getInteger(String key, int defValue) {
        return Integer.parseInt(this.properties.getProperty(key, Integer.toString(defValue)));
    }

    @Override
    public long getLong(String key, long defValue) {
        return Long.parseLong(this.properties.getProperty(key, Long.toString(defValue)));
    }

    @Override
    public float getFloat(String key, float defValue) {
        return Float.parseFloat(this.properties.getProperty(key, Float.toString(defValue)));
    }

    @Override
    public String getString(String key, String defValue) {
        return this.properties.getProperty(key, defValue);
    }

    @Override
    public Map<String, ?> get() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (Map.Entry val : this.properties.entrySet()) {
            if (val.getValue() instanceof Boolean) {
                map.put((String)val.getKey(), Boolean.parseBoolean((String)val.getValue()));
            }
            if (val.getValue() instanceof Integer) {
                map.put((String)val.getKey(), Integer.parseInt((String)val.getValue()));
            }
            if (val.getValue() instanceof Long) {
                map.put((String)val.getKey(), Long.parseLong((String)val.getValue()));
            }
            if (val.getValue() instanceof String) {
                map.put((String)val.getKey(), (String)val.getValue());
            }
            if (!(val.getValue() instanceof Float)) continue;
            map.put((String)val.getKey(), Float.valueOf(Float.parseFloat((String)val.getValue())));
        }
        return map;
    }

    @Override
    public boolean contains(String key) {
        return this.properties.containsKey(key);
    }

    @Override
    public void clear() {
        this.properties.clear();
    }

    @Override
    public void flush() {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(this.file.write(false));
            this.properties.storeToXML(out, null);
        }
        catch (Exception ex) {
            throw new GdxRuntimeException("Error writing preferences: " + this.file, ex);
        }
        finally {
            StreamUtils.closeQuietly(out);
        }
    }

    @Override
    public void remove(String key) {
        this.properties.remove(key);
    }
}

