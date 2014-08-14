package com.example.instademo.Controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instademo.Objects.Constants;
import com.example.instademo.Objects.Models.PhotosList;
import com.example.instademo.R;
import com.example.instademo.Tasks.ImageLoaders.SingleImageLoader;

/**
 * Created by alexey on 12.08.14.
 */
public class PreviewPhotoAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private SingleImageLoader imageLoader;

    private PhotosList photosList = new PhotosList();
    private Bitmap defaultIcon;
    private Context context;

    public PreviewPhotoAdapter(Context _context) {

        context = _context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new SingleImageLoader(context);
        defaultIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.free);
        context = _context;
    }

    @Override
    public int getCount() {
        return photosList.countPhoto();
    }

    @Override
    public PhotosList.Photo getItem(int i) {
        return photosList.getPhotoList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_preview_collage, viewGroup, false);
        }

        ImageView photoImage = (ImageView) view.findViewById(R.id.photo);
        photoImage.setImageBitmap(defaultIcon);

        imageLoader.displayImage(photoImage, photosList.getPhotoList().get(i).getUrlPhoto());

        return view;
    }

    public PhotosList getPhotosList() {
        return photosList;
    }

    public void addItem(PhotosList.Photo photo) {

        if (photosList.countPhoto() < Constants.MAX_COUNT_PHOTO_IN_COLLAGE) {
            photosList.addPhoto(photo);
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, context.getString(R.string.maxCountPhotoInCollage), Toast.LENGTH_SHORT).show();
        }
    }

    public void removeItem(int index) {
        photosList.getPhotoList().remove(index);
        notifyDataSetChanged();
    }

}

