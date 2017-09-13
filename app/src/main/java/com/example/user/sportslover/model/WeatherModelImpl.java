package com.example.user.sportslover.model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.user.sportslover.json.Weather;
import com.example.user.sportslover.util.HttpUtil;
import com.example.user.sportslover.util.Utility;
import com.example.user.sportslover.view.WeatherActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by user on 17-9-11.
 */

public class WeatherModelImpl implements WeatherModel {
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
