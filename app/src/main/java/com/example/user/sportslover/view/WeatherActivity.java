package com.example.user.sportslover.view;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sportslover.R;
import com.example.user.sportslover.json.Weather;
import com.example.user.sportslover.presenter.WeatherPresenterImpl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener ,WeatherView {

    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        responseText = (TextView) findViewById(R.id.request_show);
        Button button = (Button) findViewById(R.id.net_request);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.net_request:
                sendRequestWeather("CN101280301");
                break;
            default:
                break;
        }
    }

    private void sendRequestWeather(String weatherId){
        WeatherPresenterImpl weatherPresenter = new WeatherPresenterImpl(this);
        weatherPresenter.requestWeather(weatherId);
    }

    @Override
    public void showResponse(final Weather weather){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(weather.basic.cityname + "\n"
                        + weather.now.temperature + "C\n"
                        + weather.now.more.info + "\n"
                        + weather.aqi.city.aqi + "\n"
                        + weather.suggestion.comfort.info + "\n"
                        + weather.suggestion.sport.info);
            }
        });
    }
}
