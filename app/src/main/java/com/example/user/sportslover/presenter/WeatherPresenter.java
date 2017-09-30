package com.example.user.sportslover.presenter;

/**
 * Created by user on 17-9-11.
 */

public interface WeatherPresenter {
    void requestWeather(final String weatherId);
    void requestWeather(double longitude, double latitude);
}
