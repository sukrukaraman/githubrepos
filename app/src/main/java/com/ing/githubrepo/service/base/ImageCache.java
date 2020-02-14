package com.ing.githubrepo.service.base;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by karamans on 13.02.2020.
 */
public class ImageCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public static int getDefaultLruCacheSize() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024L);
        int cacheSize = maxMemory / 8;
        return cacheSize;
    }

    public ImageCache() {
        this(getDefaultLruCacheSize());
    }

    public ImageCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    public Bitmap getBitmap(String url) {
        return (Bitmap) this.get(url);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        this.put(url, bitmap);
    }
}