package com.example.user.sportslover.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;

public class SportSettingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sport_setting);
        TextView tvMapSettings = (TextView) findViewById(R.id.tv_sport_settings_map);
        TextView tvVibrationSettings = (TextView) findViewById(R.id.tv_sport_settings_vibration);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_sport_settings_back);
        tvMapSettings.setOnClickListener(this);
        tvVibrationSettings.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sport_settings_map:
                Intent intent = new Intent(this, SportMapSettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_sport_settings_vibration:
                Intent intent1 = new Intent(this, SportVibrateSettingsActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_sport_settings_back:
                finish();
            default:
                break;
        }
    }
}
