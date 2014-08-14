package com.example.instademo.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.instademo.Objects.Constants;
import com.example.instademo.Objects.Models.PhotosList;
import com.example.instademo.R;
import com.example.instademo.Tasks.ImageLoaders.CollageLoader;
import com.example.instademo.Tasks.SendMailWithCollage;


/**
 * Created by alexey on 12.08.14.
 */

public class CollageActivity extends ActionBarActivity implements View.OnClickListener {

    private ImageView collageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.insta_press)));
        getSupportActionBar().setTitle(R.string.titleCollage);

        setContentView(R.layout.collage);

        PhotosList infoPhotos = getIntent().getParcelableExtra(Constants.TAG);
        collageView = (ImageView) findViewById(R.id.collage_view);

        CollageLoader imageLoader = new CollageLoader(this);

        imageLoader.displayImage(collageView, infoPhotos);

        Button sendMail = (Button) findViewById(R.id.send_mail);
        Button newCollage = (Button) findViewById(R.id.new_collage);

        sendMail.setOnClickListener(this);
        newCollage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_mail:
                sendCollage();
                break;
            case R.id.new_collage:
                openMainScreen();
                break;
        }
    }

    private void sendCollage() {
        SendMailWithCollage sendMailWithCollage = new SendMailWithCollage(this, ((BitmapDrawable) collageView.getDrawable()).getBitmap());
        sendMailWithCollage.execute();
    }

    private void openMainScreen() {
        Intent intent = new Intent(this, UsersListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
