package com.aji.crudvolley.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by AJISETYA on 2/23/2017.
 * This class is required to handle image cache.
 */

public class LruBitmapCache extends LruCache implements ImageLoader.ImageCache{
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LruBitmapCache(int maxSize) {
        super(maxSize);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return null;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {

    }

    public static int getDefaultLruCacheSize(){
        final int maxMemory = (int) (Runtime.getRuntime()
        .maxMemory()/1024);
        final int cacheSize = maxMemory / 8;
        return cacheSize;
    }

    public LruBitmapCache(){
        this(getDefaultLruCacheSize());
    }
}
