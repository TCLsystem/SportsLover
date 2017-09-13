package com.example.user.sportslover.model;

import com.example.user.sportslover.json.Weather;

/**
 * Created by user on 17-9-11.
 */

public interface WeatherModel {

    void requestWeather(String weatherUrl, WeatherModelImpl.OnRequestWeatherListener listener);
}
