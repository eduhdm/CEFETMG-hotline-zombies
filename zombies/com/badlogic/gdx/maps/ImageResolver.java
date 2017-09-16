/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.maps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public interface ImageResolver {
    public TextureRegion getImage(String var1);

    public static class TextureAtlasImageResolver
    implements ImageResolver {
        private final TextureAtlas atlas;

        public TextureAtlasImageResolver(TextureAtlas atlas) {
            this.atlas = atlas;
        }

        @Override
        public TextureRegion getImage(String name) {
            return this.atlas.findRegion(name);
        }
    }

    public static class AssetManagerImageResolver
    implements ImageResolver {
        private final AssetManager assetManager;

        public AssetManagerImageResolver(AssetManager assetManager) {
            this.assetManager = assetManager;
        }

        @Override
        public TextureRegion getImage(String name) {
            return new TextureRegion(this.assetManager.get(name, Texture.class));
        }
    }

    public static class DirectImageResolver
    implements ImageResolver {
        private final ObjectMap<String, Texture> images;

        public DirectImageResolver(ObjectMap<String, Texture> images) {
            this.images = images;
        }

        @Override
        public TextureRegion getImage(String name) {
            return new TextureRegion(this.images.get(name));
        }
    }

}

