package com.example.user.sportslover.model;

import com.example.user.sportslover.bean.Weather;
import com.example.user.sportslover.util.HttpUtil;
import com.example.user.sportslover.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by user on 17-9-11.
 */

public class WeatherModelInterImpl implements WeatherModelInter {
    Weather weather = null;
    @Override
    public void requestWeather(final String weatherUrl, final OnRequestWeatherListener listener) {
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                weather = Utility.handleWeatherResponse(responseText);
                listener.onSuccess(weather);
            }
        });
    }

    public interface OnRequestWeatherListener{
        void onSuccess(Weather weather);
    }
}
