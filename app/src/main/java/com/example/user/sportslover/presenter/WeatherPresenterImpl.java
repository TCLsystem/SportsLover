package com.example.user.sportslover.presenter;

import com.example.user.sportslover.json.Weather;
import com.example.user.sportslover.model.WeatherModel;
import com.example.user.sportslover.model.WeatherModelImpl;
import com.example.user.sportslover.view.WeatherActivity;
import com.example.user.sportslover.view.WeatherView;

/**
 * Created by user on 17-9-11.
 */

public class WeatherPresenterImpl implements WeatherPresenter, WeatherModelImpl.OnRequestWeatherListener {
    private WeatherView weatherView;
    public WeatherPresenterImpl(WeatherView weatherView){
        this.weatherView = weatherView;
    }
    @Override
    public void requestWeather(String weatherId) {
        WeatherModelImpl weatherModel = new WeatherModelImpl();
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=8daba80f2e974e348bcf8e497d435083";
        weatherModel.requestWeather(weatherUrl, this);
    }

    @Override
    public void onSuccess(Weather weather) {
        weatherView.showResponse(weather);
    }
}
