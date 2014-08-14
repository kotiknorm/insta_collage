package com.example.instademo.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class BitmapGlue {


    public static Bitmap horizontally(Bitmap c, Bitmap s) throws NullPointerException {
        Bitmap cs;

        int width, height;

        if (c.getWidth() > s.getWidth()) {
            width = c.getWidth() + s.getWidth();
            height = c.getHeight();
        } else {
            width = s.getWidth() + s.getWidth();
            height = c.getHeight();
        }

        cs = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        comboImage.drawColor(Color.WHITE);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, c.getWidth(), 0f, null);

        return cs;
    }

    public static Bitmap vertically(Bitmap c, Bitmap s) throws NullPointerException {
        Bitmap cs;

        int width, height;

        if (c.getHeight() > s.getHeight()) {
            height = c.getHeight() + s.getHeight();
            width = c.getWidth();
        } else {
            height = s.getHeight() + s.getHeight();
            width = c.getWidth();
        }

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);
        comboImage.drawColor(Color.WHITE);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, 0f, s.getHeight(), null);

        return cs;
    }

}
