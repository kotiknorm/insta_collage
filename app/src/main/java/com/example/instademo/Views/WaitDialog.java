package com.example.instademo.Views;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by alexey on 12.08.14.
 */
public class WaitDialog {

    private ProgressDialog progress;

    public WaitDialog(String message, Context activity) {

        progress = new ProgressDialog(activity);
        progress.setMessage(message);
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
    }

    public void startDialog() {
        if (progress != null)
            progress.show();
    }

    public void endDialog() {
        if (progress != null)
            progress.dismiss();
    }
}
