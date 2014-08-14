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
import com.example.instademo.Objects.Models.UsersList;
import com.example.instademo.R;
import com.example.instademo.Tasks.ImageLoaders.SingleImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 14.08.14.
 */
public class UsersAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private UsersList accountList;
    private SingleImageLoader imageLoader;
    private List<UsersList.User> photo = new ArrayList<UsersList.User>();
    private int cursor = Constants.COUNT_PAGINATION;
    private Bitmap defaultIcon;

    public UsersAdapter(UsersList _accountList, Context context) {
        accountList = _accountList;

        if (accountList.countPhoto() - photo.size() < Constants.COUNT_PAGINATION) {
            photo = accountList.getUserList();
        } else {
            photo = accountList.getUserList().subList(0, cursor);
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
    public UsersList.User getItem(int i) {
        return accountList.getUserList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_list_user, viewGroup, false);
        }
        view.setTag(photo.get(i));

        TextView textView = (TextView) view.findViewById(R.id.name_user);
        textView.setText(photo.get(i).getName());

        TextView info = (TextView) view.findViewById(R.id.info);
        info.setText(photo.get(i).getInfo());

        ImageView photoImage = (ImageView) view.findViewById(R.id.photo);
        photoImage.setImageBitmap(defaultIcon);

        imageLoader.displayImage(photoImage, photo.get(i).getUrlPhoto());


        return view;
    }

    public void downList() {
        if (accountList.countPhoto() - photo.size() == 0) {
            photo = accountList.getUserList();
            return;
        } else if (accountList.countPhoto() - photo.size() < Constants.COUNT_PAGINATION) {
            photo = accountList.getUserList();
        } else {
            cursor = cursor + Constants.COUNT_PAGINATION;
            Log.d(Constants.TAG, "cursor - " + cursor);
            photo = accountList.getUserList().subList(0, cursor);
        }
        notifyDataSetChanged();
    }


}
