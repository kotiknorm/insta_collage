package com.example.instademo.Objects.ApiHttpParsers;

import android.util.Log;

import com.example.instademo.Objects.ApiHttpParsers.StrategysParser.StrategyForParser;
import com.example.instademo.Objects.Constants;
import com.example.instademo.Objects.HttpRequestBuilder;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by alexey on 12.08.14.
 */

public class HttpParser implements Parser {

    private InputStream is;
    private JSONObject jObj;
    private String json = "";

    private StrategyForParser strategyForParser;

    public HttpParser(StrategyForParser _strategyForParser) {
        strategyForParser = _strategyForParser;
    }

    public JSONArray makeRequest() {

        try {
            is = new HttpRequestBuilder(strategyForParser.getUrl()).getConnection().getInputStream();

        } catch (UnsupportedEncodingException e) {
            Log.e(Constants.TAG, e.toString());
        } catch (ClientProtocolException e) {
            Log.e(Constants.TAG, e.toString());
        } catch (IOException e) {
            Log.e(Constants.TAG, e.toString());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e(Constants.TAG, e.toString());
        }

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e(Constants.TAG, e.toString());
        }
        JSONArray jsonResult = null;
        try {
            jsonResult = jObj.getJSONArray("data");
        } catch (JSONException e) {
            Log.e(Constants.TAG, e.toString());
        }

        return jsonResult;

    }
}
