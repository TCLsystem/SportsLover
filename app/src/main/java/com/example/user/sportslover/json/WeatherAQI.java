package com.example.user.sportslover.json;

/**
 * Created by user on 17-9-11.
 */

public class WeatherAQI {

    public AQICity city;

    public class AQICity{
        //空气质量指数
        public String aqi;
        //pm2.5指数
        public String pm25;
    }
}
