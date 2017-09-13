package com.example.user.sportslover.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 17-9-11.
 */

public class Weather {
    public String status;
    public WeatherBasic basic;
    public WeatherAQI aqi;
    public WeatherNow now;
    public WeatherSuggestion suggestion;
    @SerializedName("daily_forecast")
    public List<WeatherForecast> forecastList;
}
