package com.example.instademo.Objects.ApiHttpParsers.StrategysParser;

/**
 * Created by alexey on 12.08.14.
 */
public abstract class StrategyForParser {

    protected String user;

    public abstract String getUrl();

    public void setParam(String _user) {
        user = _user;
    }

}
