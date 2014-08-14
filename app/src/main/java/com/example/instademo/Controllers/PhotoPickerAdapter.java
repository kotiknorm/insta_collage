package com.example.instademo.Controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instademo.Objects.Constants;
import com.example.instademo.Objects.Models.PhotosList;
import com.example.instademo.R;
import com.example.instademo.Tasks.ImageLoaders.SingleImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 12.08.14.
 */

public class PhotoPickerAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private PhotosList accountList;
    private SingleImageLoader imageLoader;
    private List<PhotosList.Photo> photo = new ArrayList<PhotosList.Photo>();
    private int cursor = Constants.COUNT_PAGINATION;
    private Bitmap defaultIcon;

    public PhotoPickerAdapter(PhotosList _accountList, Context context) {
        accountList = _accountList;

        if (accountList.countPhoto() - photo.size() < Constants.COUNT_PAGINATION) {
            photo = accountList.getPhotoList();
        } else {
            photo = accountList.getPhotoList().subList(0, cursor);
        }

        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new SingleImageLoader(context);

        defaultIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.free);
    }

    @Override
    public int getCount() {
        return photo.size();
    }

    @Override
    public PhotosList.Photo getItem(int i) {
        return accountList.getPhotoList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_photo_picker, viewGroup, false);
        }
        int count = photo.get(i).getCountLikes();
        view.setTag(photo.get(i));

        TextView textView = (TextView) view.findViewById(R.id.count_likes);
        textView.setText(count + " likes");

        TextView info = (TextView) view.findViewById(R.id.location);
        info.setText(photo.get(i).getLocation());

        ImageView photoImage = (ImageView) view.findViewById(R.id.photo);
        photoImage.setImageBitmap(defaultIcon);

        imageLoader.displayImage(photoImage, photo.get(i).getUrlPhoto());


        return view;
    }

    public void downList() {
        if (accountList.countPhoto() - photo.size() == 0) {
            photo = accountList.getPhotoList();
            return;
        } else if (accountList.countPhoto() - photo.size() < Constants.COUNT_PAGINATION) {
            photo = accountList.getPhotoList();
        } else {
            cursor = cursor + Constants.COUNT_PAGINATION;
            Log.d(Constants.TAG, "cursor - " + cursor);
            photo = accountList.getPhotoList().subList(0, cursor);
        }
        notifyDataSetChanged();
    }

    public void clearCache() {
        imageLoader.clearMemoryCache();
    }


}
