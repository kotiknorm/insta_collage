package com.example.instademo.Objects.ApiHttpParsers.StrategysParser;

import com.example.instademo.Objects.Constants;

/**
 * Created by alexey on 12.08.14.
 */
public class UsersSearchParser extends StrategyForParser {

    @Override
    public String getUrl() {
        return Constants.APIURL + "/users/search?q=" + user + "&access_token=" + Constants.access_token;
    }

}
