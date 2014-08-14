package com.example.instademo.Objects;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by alexey on 13.08.14.
 */
public class HttpRequestBuilder {

    private HttpURLConnection conn;

    public HttpRequestBuilder(String urlString){
        try {
            URL url = new URL(urlString);

        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(20000);
        conn.setReadTimeout(20000);
        conn.setInstanceFollowRedirects(true);

        } catch (Exception e) {
            Log.e(Constants.TAG, e.toString());
        }
    }

public HttpURLConnection getConnection(){
    return conn;
}

}
