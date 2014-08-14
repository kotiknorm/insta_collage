package com.example.instademo.Tasks.ImageLoaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.example.instademo.Objects.BitmapGlue;
import com.example.instademo.Objects.Constants;
import com.example.instademo.Objects.Models.PhotosList;


/**
 * Created by alexey on 13.08.14.
 */

public class CollageLoader extends ImageLoader {

    public CollageLoader(Context context) {
        super(context);
    }

    public void displayImage(ImageView imageView, PhotosList infoPhotos) {


        try {
            Log.d(Constants.TAG, "displayImage");

            Bitmap firstLineBitmap = returnImage(infoPhotos.getPhotoList().get(0).getUrlPhoto());
            for (int i = 1; i < infoPhotos.countPhoto() / 2; i++) {
                firstLineBitmap = BitmapGlue.horizontally(firstLineBitmap, returnImage(infoPhotos.getPhotoList().get(i).getUrlPhoto()));
            }

            Bitmap secondLineBitmap = returnImage(infoPhotos.getPhotoList().get(infoPhotos.countPhoto() / 2).getUrlPhoto());
            for (int i = infoPhotos.countPhoto() / 2 + 1; i < infoPhotos.countPhoto(); i++) {
                secondLineBitmap = BitmapGlue.horizontally(secondLineBitmap, returnImage(infoPhotos.getPhotoList().get(i).getUrlPhoto()));
            }

            Bitmap result = BitmapGlue.vertically(firstLineBitmap, secondLineBitmap);

            imageView.setImageBitmap(result);
        } catch (OutOfMemoryError e) {
            memoryCache.clear();
            displayImage(imageView, infoPhotos);
        }
    }

}
