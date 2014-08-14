package com.example.instademo.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.instademo.Objects.ApiHttpParsers.HttpParser;
import com.example.instademo.Objects.ApiHttpParsers.Parser;
import com.example.instademo.Objects.ApiHttpParsers.StrategysParser.StrategyForParser;
import com.example.instademo.Objects.ApiHttpParsers.StrategysParser.UsersSearchParser;
import com.example.instademo.Objects.Constants;
import com.example.instademo.Objects.Models.UsersList;
import com.example.instademo.R;
import com.example.instademo.Views.UsersListActivity;
import com.example.instademo.Views.WaitDialog;

import org.json.JSONArray;

/**
 * Created by alexey on 14.08.14.
 */
public class SearchUsers extends AsyncTask<Void, Void, Integer> {

    private Context ctx;
    private String username;
    private WaitDialog waitDialog;
    private UsersList resultUsers;


    public SearchUsers(Context _ctx, String _username) {
        username = _username;
        ctx = _ctx;
        waitDialog = new WaitDialog(ctx.getString(R.string.titleMain), ctx);
        Log.d(Constants.TAG, "SearchUsers");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        waitDialog.startDialog();
    }

    @Override
    protected Integer doInBackground(Void... arg0) {

        try {
            StrategyForParser searchPeople = new UsersSearchParser();
            searchPeople.setParam(username);
            Parser httpParser = new HttpParser(searchPeople);
            JSONArray jsonArray = httpParser.makeRequest();
            Log.d(Constants.TAG, "urlStringSearchLogin - " + searchPeople.getUrl());

            resultUsers = new UsersList(jsonArray);

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
                ((UsersListActivity) ctx).startDownloadPhoto(resultUsers);
                return;
            default:
                return;
        }
        toast.show();
    }


}
