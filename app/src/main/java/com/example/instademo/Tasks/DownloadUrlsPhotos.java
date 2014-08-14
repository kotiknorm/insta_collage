package com.example.instademo.Tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.instademo.Objects.ApiHttpParsers.HttpParser;
import com.example.instademo.Objects.ApiHttpParsers.Parser;
import com.example.instademo.Objects.ApiHttpParsers.StrategysParser.PhotoParser;
import com.example.instademo.Objects.ApiHttpParsers.StrategysParser.StrategyForParser;
import com.example.instademo.Objects.Constants;
import com.example.instademo.Objects.Models.PhotosList;
import com.example.instademo.Objects.Models.UsersList;
import com.example.instademo.R;
import com.example.instademo.Views.PhotoPickerActivity;
import com.example.instademo.Views.WaitDialog;

import org.json.JSONArray;

/**
 * Created by alexey on 12.08.14.
 */
public class DownloadUrlsPhotos extends AsyncTask<Void, Void, Integer> {

    private Context ctx;
    private UsersList.User user;
    private WaitDialog waitDialog;
    private PhotosList infoPhotos;

    public DownloadUrlsPhotos(Context _ctx, UsersList.User _user) {
        user = _user;
        ctx = _ctx;
        waitDialog = new WaitDialog(ctx.getString(R.string.loadPhoto), ctx);
        Log.d(Constants.TAG, "DownloadPhotos");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        waitDialog.startDialog();
    }

    @Override
    protected Integer doInBackground(Void... arg0) {

        try {

            StrategyForParser loadUrlPhotos = new PhotoParser();
            loadUrlPhotos.setParam(user.getId());
            Parser httpParser = new HttpParser(loadUrlPhotos);
            Log.d(Constants.TAG, "urlStringSearchPhoto - " + loadUrlPhotos.getUrl());
            JSONArray jsonArray = httpParser.makeRequest();

            infoPhotos = new PhotosList(jsonArray, user.getId(), user.getName());

        } catch (NullPointerException e) {
            Log.e(Constants.TAG, e.toString());
            return 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(Constants.TAG, e.toString());
            return 1;
        } catch (IndexOutOfBoundsException e) {
            Log.e(Constants.TAG, e.toString());
            return 1;
        } catch (Exception e) {
            Log.e(Constants.TAG, e.toString());
            return 2;
        } finally {
            waitDialog.endDialog();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        Toast toast;
        Log.d(Constants.TAG, "onPostExecute");
        switch (result) {
            case 1:
                toast = Toast.makeText(ctx, ctx.getString(R.string.pageNotFound), Toast.LENGTH_SHORT);
                break;
            case 2:
                toast = Toast.makeText(ctx, ctx.getString(R.string.notConnect), Toast.LENGTH_SHORT);
                break;
            case 0:
                Intent picker = new Intent(ctx, PhotoPickerActivity.class);
                picker.putExtra(Constants.TAG, infoPhotos);
                ctx.startActivity(picker);
                return;
            default:
                return;
        }
        toast.show();
    }


}
