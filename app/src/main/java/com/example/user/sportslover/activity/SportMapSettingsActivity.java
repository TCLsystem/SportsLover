package com.example.user.sportslover.activity;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.application.BaseApplication;

public class SportMapSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Drawable drawable[];
    private BaseApplication baseApplication;
    private TextView tvMapFollowing;
    private TextView tvMapCompass;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sport_map_settings);
        baseApplication = (BaseApplication) getApplicationContext();
        tvMapFollowing = (TextView) findViewById(R.id.tv_sport_settings_map_following_mode);
        tvMapCompass = (TextView) findViewById(R.id.tv_sport_settings_map_compass_mode);
        ivBack = (ImageView) findViewById(R.id.iv_sport_map_settings_back);
        drawable = tvMapFollowing.getCompoundDrawables();
        tvMapFollowing.setOnClickListener(this);
        tvMapCompass.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        if (baseApplication.getGlobalSportMapSetting() == 1){
            tvMapFollowing.setCompoundDrawables(null, null, null, null);
            tvMapCompass.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sport_settings_map_following_mode:
                tvMapFollowing.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
                tvMapCompass.setCompoundDrawables(null, null, null, null);
                baseApplication.setGlobalSportMapSetting(0);
                break;
            case R.id.tv_sport_settings_map_compass_mode:
                tvMapFollowing.setCompoundDrawables(null, null, null, null);
                tvMapCompass.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
                baseApplication.setGlobalSportMapSetting(1);
                break;
            case R.id.iv_sport_map_settings_back:
                finish();
                break;
        }

    }
}
