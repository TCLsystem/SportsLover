package com.example.user.sportslover.model;

/**
 * Created by user on 17-9-11.
 */

public interface WeatherModelInter {

    void requestWeather(String weatherUrl, WeatherModelInterImpl.OnRequestWeatherListener listener);
}
