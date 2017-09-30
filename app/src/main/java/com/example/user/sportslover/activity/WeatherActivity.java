package com.example.user.sportslover.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.application.BaseApplication;
import com.example.user.sportslover.bean.Weather;
import com.example.user.sportslover.presenter.WeatherPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener, WeatherView {

    private ImageView ivBack;
    private TextView tvWeatherCondition;
    private TextView tvTemperature;
    private TextView tvDate;
    private TextView tvDetailInformation;
    private BaseApplication baseApplication;
    private WeatherPresenterImpl weatherPresenter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        tvWeatherCondition = (TextView) findViewById(R.id.tv_weather_basic_condition);
        tvTemperature = (TextView) findViewById(R.id.tv_weather_basic_temperature);
        tvDate = (TextView) findViewById(R.id.tv_weather_date);
        tvDetailInformation = (TextView) findViewById(R.id.tv_weather_item_info);
        ivBack = (ImageView) findViewById(R.id.weather_back);
        baseApplication = (BaseApplication) getApplicationContext();
        weatherPresenter = new WeatherPresenterImpl(this);
        ivBack.setOnClickListener(this);
        refleshWeather();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.weather_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void refleshWeather(){
        String html;
        Date date = null;
        tvWeatherCondition.setText(baseApplication.getGlobalWeather().now.more.info);
        tvTemperature.setText(baseApplication.getGlobalWeather().now.temperature + "℃");
        /*html = "<big><big><big>" + homeRidingPresenterImpr.loadComulativeNumber() +"</big></big></big><br>Cumulative<br>number";
        tvRidingCumulativeNumber.setText(Html.fromHtml(html));*/
        try {
            date = dateFormat.parse(baseApplication.getGlobalWeather().basic.update.updateTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        html = month[date.getMonth()] +
                "<br><big><big><big>" + date.getDate() + "</big></big></big>";
        tvDate.setText(Html.fromHtml(html));
        tvDate.setLineSpacing(0,1.0f);
        html = "<big><big>" + baseApplication.getGlobalWeather().aqi.city.aqi + "</big></big><br>" +
                "<big><big>" + baseApplication.getGlobalWeather().now.humility + "</big></big>%<br>" +
                "<big><big>" + baseApplication.getGlobalWeather().now.wind.direction + "</big></big>";
        tvDetailInformation.setText(Html.fromHtml(html));
        tvDetailInformation.setLineSpacing(0,1.7f);
    }

    @Override
    public void showResponse(Weather weather) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*String weatherId = baseApplication.getGlobalWeather().basic.weatherId;
                weatherPresenter.requestWeather(weatherId);*/
                refleshWeather();
            }
        });
    }
}
