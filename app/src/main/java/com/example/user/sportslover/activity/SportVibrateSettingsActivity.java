package com.example.user.sportslover.activity;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.a.a.V;
import com.example.user.sportslover.R;
import com.example.user.sportslover.application.BaseApplication;

public class SportVibrateSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Drawable drawable[];
    private BaseApplication baseApplication;
    private ToggleButton tglVibration;
    private TextView tvVibrationText;
    private TextView tvVibrationFirst;
    private TextView tvVibrationSecond;
    private TextView tvVibrationThird;
    private TextView tvVibrationForth;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sport_vibrate_settings);
        baseApplication = (BaseApplication) getApplicationContext();
        tglVibration = (ToggleButton) findViewById(R.id.tgl_vibration_switch);
        tvVibrationText = (TextView) findViewById(R.id.tv_sport_settings_vibration_text);
        tvVibrationFirst = (TextView) findViewById(R.id.tv_sport_settings_vibration_first);
        tvVibrationSecond = (TextView) findViewById(R.id.tv_sport_settings_vibration_second);
        tvVibrationThird = (TextView) findViewById(R.id.tv_sport_settings_vibration_third);
        tvVibrationForth = (TextView) findViewById(R.id.tv_sport_settings_vibration_forth);
        ivBack = (ImageView) findViewById(R.id.sport_vibrate_settings_back);
        tvVibrationFirst.setOnClickListener(this);
        tvVibrationSecond.setOnClickListener(this);
        tvVibrationThird.setOnClickListener(this);
        tvVibrationForth.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        drawable = tvVibrationFirst.getCompoundDrawables();
        if (baseApplication.getGlobalSportVibrationSetting() > 1f)
            refleshVibrateDistance(true);
        tglVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                refleshVibrateDistance(isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sport_settings_vibration_first:
                tvVibrationFirst.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
                tvVibrationSecond.setCompoundDrawables(null, null, null, null);
                tvVibrationThird.setCompoundDrawables(null, null, null, null);
                tvVibrationForth.setCompoundDrawables(null, null, null, null);
                baseApplication.setGlobalSportVibrationSetting(500f);
                break;
            case R.id.tv_sport_settings_vibration_second:
                tvVibrationFirst.setCompoundDrawables(null, null, null, null);
                tvVibrationSecond.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
                tvVibrationThird.setCompoundDrawables(null, null, null, null);
                tvVibrationForth.setCompoundDrawables(null, null, null, null);
                baseApplication.setGlobalSportVibrationSetting(1000f);
                break;
            case R.id.tv_sport_settings_vibration_third:
                tvVibrationFirst.setCompoundDrawables(null, null, null, null);
                tvVibrationSecond.setCompoundDrawables(null, null, null, null);
                tvVibrationThird.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
                tvVibrationForth.setCompoundDrawables(null, null, null, null);
                baseApplication.setGlobalSportVibrationSetting(2000f);
                break;
            case R.id.tv_sport_settings_vibration_forth:
                tvVibrationFirst.setCompoundDrawables(null, null, null, null);
                tvVibrationSecond.setCompoundDrawables(null, null, null, null);
                tvVibrationThird.setCompoundDrawables(null, null, null, null);
                tvVibrationForth.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
                baseApplication.setGlobalSportVibrationSetting(5000f);
                break;
            case R.id.iv_sport_map_settings_back:
                finish();
                break;
        }
    }

    private void refleshVibrateDistance(boolean isChecked){
        if (isChecked){
            tvVibrationText.setVisibility(View.VISIBLE);
            tvVibrationFirst.setVisibility(View.VISIBLE);
            tvVibrationSecond.setVisibility(View.VISIBLE);
            tvVibrationThird.setVisibility(View.VISIBLE);
            tvVibrationForth.setVisibility(View.VISIBLE);
            if (baseApplication.getGlobalSportVibrationSetting() > 3000f){
                tvVibrationFirst.setCompoundDrawables(null, null, null, null);
                tvVibrationSecond.setCompoundDrawables(null, null, null, null);
                tvVibrationThird.setCompoundDrawables(null, null, null, null);
                tvVibrationForth.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
                baseApplication.setGlobalSportVibrationSetting(5000f);
            } else if (baseApplication.getGlobalSportVibrationSetting() > 1500f){
                tvVibrationFirst.setCompoundDrawables(null, null, null, null);
                tvVibrationSecond.setCompoundDrawables(null, null, null, null);
                tvVibrationThird.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
                tvVibrationForth.setCompoundDrawables(null, null, null, null);
                baseApplication.setGlobalSportVibrationSetting(2000f);
            } else if (baseApplication.getGlobalSportVibrationSetting() > 800f){
                tvVibrationFirst.setCompoundDrawables(null, null, null, null);
                tvVibrationSecond.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
                tvVibrationThird.setCompoundDrawables(null, null, null, null);
                tvVibrationForth.setCompoundDrawables(null, null, null, null);
                baseApplication.setGlobalSportVibrationSetting(1000f);
            } else {
                tvVibrationFirst.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
                tvVibrationSecond.setCompoundDrawables(null, null, null, null);
                tvVibrationThird.setCompoundDrawables(null, null, null, null);
                tvVibrationForth.setCompoundDrawables(null, null, null, null);
                baseApplication.setGlobalSportVibrationSetting(500f);
            }
        } else {
            tvVibrationText.setVisibility(View.GONE);
            tvVibrationFirst.setVisibility(View.GONE);
            tvVibrationSecond.setVisibility(View.GONE);
            tvVibrationThird.setVisibility(View.GONE);
            tvVibrationForth.setVisibility(View.GONE);
            baseApplication.setGlobalSportVibrationSetting(0f);
        }
    }
}
