package com.example.instademo.Objects.Cache;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.instademo.Objects.Constants;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by alexey on 04.07.14.
 */
public class MemoryCache {


    public Map<String, Bitmap> cache = Collections
            .synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));
    private long size = 0;
    private long limit = 1000000;

    public MemoryCache() {
        setLimit(Runtime.getRuntime().maxMemory() / 4);
    }

    public void setLimit(long new_limit) {
        limit = new_limit;
        Log.d(Constants.TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");
    }

    public Bitmap get(String id) {
        try {
            if (!cache.containsKey(id))
                return null;
            return cache.get(id);
        } catch (NullPointerException e) {
            Log.e(Constants.TAG, e.toString());
            return null;
        }
    }

    public void put(String id, Bitmap bitmap) {
        try {
            if (cache.containsKey(id))
                size -= getSizeInBytes(cache.get(id));
            cache.put(id, bitmap);
            size += getSizeInBytes(bitmap);
            checkSize();
        } catch (Throwable th) {
            Log.e(Constants.TAG, th.toString());
        }
    }

    private void checkSize() {
        Log.d(Constants.TAG, "cache size=" + size + " length=" + cache.size());
        if (size > limit) {
            Iterator<Map.Entry<String, Bitmap>> iter = cache.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry<String, Bitmap> entry = iter.next();
                size -= getSizeInBytes(entry.getValue());
                iter.remove();
                if (size <= limit)
                    break;
            }
            Log.d(Constants.TAG, "Clean cache. New size " + cache.size());
        }
    }

    public void clear() {
        Iterator<Map.Entry<String, Bitmap>> iter = cache.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Bitmap> entry = iter.next();
            if (entry.getValue() != null && !entry.getValue().isRecycled()) {
                entry.getValue().recycle();
                entry.setValue(null);
            }
        }
        try {
            cache.clear();
            size = 0;
            System.gc();
        } catch (NullPointerException ex) {
            Log.e(Constants.TAG, ex.toString());
        }
    }

    long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null)
            return 0;
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}