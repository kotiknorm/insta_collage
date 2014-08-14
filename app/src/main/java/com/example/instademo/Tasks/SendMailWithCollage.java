package com.example.instademo.Tasks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.example.instademo.Objects.Constants;
import com.example.instademo.R;
import com.example.instademo.Views.WaitDialog;

/**
 * Created by alexey on 13.08.14.
 */

public class SendMailWithCollage extends AsyncTask<Void, Void, Uri> {

    private Context ctx;

    private WaitDialog waitDialog;

    private Bitmap collage;


    public SendMailWithCollage(Context _ctx, Bitmap _collage) {
        collage = _collage;
        ctx = _ctx;
        waitDialog = new WaitDialog(ctx.getString(R.string.loadMail), ctx);
        Log.d(Constants.TAG, "SendMailWithCollage");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        waitDialog.startDialog();
    }

    @Override
    protected Uri doInBackground(Void... arg0) {
        String path = MediaStore.Images.Media.insertImage(ctx.getContentResolver(), collage, "title", null);
        Uri screenshotUri = Uri.parse(path);
        return screenshotUri;
    }


    @Override
    protected void onPostExecute(Uri screenshotUri) {
        super.onPostExecute(screenshotUri);
        Log.d(Constants.TAG, "onPostExecute");
        final Intent emailIntent1 = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent1.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        emailIntent1.setType("image/png");
        waitDialog.endDialog();
        ctx.startActivity(Intent.createChooser(emailIntent1, ctx.getString(R.string.titleIntentMail)));
    }

}
