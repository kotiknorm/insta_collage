package com.example.instademo.Tasks.ImageLoaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by alexey on 13.08.14.
 */
public class SingleImageLoader extends ImageLoader {


    public SingleImageLoader(Context context) {
        super(context);
    }

    public void displayImage(ImageView imageView, String url) {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
        else {
            queuePhoto(url, imageView);
        }
    }

}
