package com.example.instademo.Views;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.instademo.Controllers.PaginationScrollListener;
import com.example.instademo.Controllers.PhotoPickerAdapter;
import com.example.instademo.Controllers.PreviewPhotoAdapter;
import com.example.instademo.Libs.HorizontalListView;
import com.example.instademo.Objects.Constants;
import com.example.instademo.Objects.Models.PhotosList;
import com.example.instademo.R;

/**
 * Created by alexey on 12.08.14.
 */

public class PhotoPickerActivity extends ActionBarActivity {

    private PhotoPickerAdapter photoPickerAdapter;
    private PreviewPhotoAdapter previewPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.insta_press)));
        setContentView(R.layout.photo_picker);

        PhotosList infoPhotos = getIntent().getParcelableExtra(Constants.TAG);

        getSupportActionBar().setTitle(infoPhotos.getNameUser());

        final ListView photos = (ListView) findViewById(R.id.photos);

        photoPickerAdapter = new PhotoPickerAdapter(infoPhotos, this);
        photos.setAdapter(photoPickerAdapter);

        final PaginationScrollListener pag = new PaginationScrollListener() {
            @Override
            public void startPagination() {
                photoPickerAdapter.downList();
            }
        };
        photos.setOnScrollListener(pag);

        final HorizontalListView horizontalListView = (HorizontalListView) findViewById(R.id.cl);
        previewPhotoAdapter = new PreviewPhotoAdapter(this);
        horizontalListView.setVisibility(View.GONE);
        horizontalListView.setAdapter(previewPhotoAdapter);

        final Button makeCollage = (Button) findViewById(R.id.make);
        makeCollage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCollageActivity();
            }
        });


        horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                previewPhotoAdapter.removeItem(i);
                if (previewPhotoAdapter.getPhotosList().countPhoto() == 0) {
                    horizontalListView.setVisibility(View.GONE);
                    makeCollage.setText(getString(R.string.makeCollage));
                } else {
                    makeCollage.setText(getResources().getQuantityString(R.plurals.photos_button,
                            previewPhotoAdapter.getPhotosList().countPhoto(), previewPhotoAdapter.getPhotosList().countPhoto()));
                }
            }
        });

        photos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                previewPhotoAdapter.addItem((PhotosList.Photo) view.getTag());
                horizontalListView.setVisibility(View.VISIBLE);
                makeCollage.setText(getResources().getQuantityString(R.plurals.photos_button,
                        previewPhotoAdapter.getPhotosList().countPhoto(), previewPhotoAdapter.getPhotosList().countPhoto()));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        photoPickerAdapter.clearCache();
    }

    private void startCollageActivity() {
        int countPhoto = previewPhotoAdapter.getPhotosList().countPhoto();

        if (countPhoto == 0) {
            Toast.makeText(PhotoPickerActivity.this, PhotoPickerActivity.this.getString(R.string.hintPicker_1), Toast.LENGTH_SHORT).show();
        } else if (countPhoto % 2 != 0) {
            Toast.makeText(PhotoPickerActivity.this, PhotoPickerActivity.this.getString(R.string.hintPicker_2), Toast.LENGTH_SHORT).show();
        } else {

            Intent collage = new Intent(PhotoPickerActivity.this, CollageActivity.class);
            collage.putExtra(Constants.TAG, previewPhotoAdapter.getPhotosList());

            startActivity(collage);
//            photoPickerAdapter.clearCache();
        }
    }
}
