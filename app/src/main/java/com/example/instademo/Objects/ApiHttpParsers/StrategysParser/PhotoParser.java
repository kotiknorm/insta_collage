package com.example.instademo.Objects.ApiHttpParsers.StrategysParser;

import com.example.instademo.Objects.Constants;

/**
 * Created by alexey on 12.08.14.
 */
public class PhotoParser extends StrategyForParser {

    @Override
    public String getUrl() {
        return Constants.APIURL + "/users/" + user + "/media/recent/?access_token=" + Constants.access_token + "&count=800";
    }
}
