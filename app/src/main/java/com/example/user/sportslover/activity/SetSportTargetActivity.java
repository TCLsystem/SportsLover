package com.example.user.sportslover.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.customview.CircularRingPercentageView;
import com.example.user.sportslover.customview.TuneWheelView;

public class SetSportTargetActivity extends AppCompatActivity implements View.OnClickListener {

    private TuneWheelView tuneWheel;
    private CircularRingPercentageView progressCircle;
    private TextView tvTarget;
    Button buttonRunning;
    Button buttonWalking;
    Button buttonRiding;
    ImageView ivSetTargetBack;
    Drawable drawableClicked;
    Drawable drawableUnclicked;
    private float target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_sport_target);
        tuneWheel = (TuneWheelView) findViewById(R.id.ruler);
        tuneWheel.setMaxValue(100);
        tuneWheel.setValueChangeListener(new TuneWheelView.OnValueChangeListener(){
            @Override
            public void onValueChange(float value) {
                target = value * 200;
                refleshView();
            }
        });
        progressCircle = (CircularRingPercentageView) findViewById(R.id.set_target_progress);
        progressCircle.setRoundWidth(15);
        progressCircle.setProgress(75f);
        progressCircle.setColors(new int[]{Color.WHITE, Color.WHITE});
        progressCircle.setRoundBackgroundColor(Color.WHITE);
        buttonRunning = (Button) findViewById(R.id.set_target_type_running);
        buttonRunning.setOnClickListener(this);
        buttonWalking = (Button) findViewById(R.id.set_target_type_walking);
        buttonWalking.setOnClickListener(this);
        buttonRiding = (Button) findViewById(R.id.set_target_type_riding);
        buttonRiding.setOnClickListener(this);
        ivSetTargetBack = (ImageView) findViewById(R.id.set_target_back);
        ivSetTargetBack.setOnClickListener(this);
        drawableClicked = buttonRunning.getBackground();
        drawableUnclicked = buttonWalking.getBackground();
        tvTarget = (TextView) findViewById(R.id.tv_target);
        refleshView();
    }

    private void refleshView(){
        String html ="0.0calories<br><big><big><big><big><big>" + target/1000 + "</big></big>  km</big></big></big><br>Totol mileages";
        tvTarget.setText(Html.fromHtml(html));
    }

    private void initButtons(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            buttonRunning.setBackground(drawableUnclicked);
            buttonWalking.setBackground(drawableUnclicked);
            buttonRiding.setBackground(drawableUnclicked);
        }
        buttonRunning.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        buttonWalking.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        buttonRiding.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        buttonRunning.setTextColor(Color.WHITE);
        buttonWalking.setTextColor(Color.WHITE);
        buttonRiding.setTextColor(Color.WHITE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_target_type_running:
                initButtons();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    buttonRunning.setBackground(drawableClicked);
                }
                buttonRunning.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                buttonRunning.setTextColor(Color.BLACK);
                tuneWheel.setMaxValue(100);
                break;
            case R.id.set_target_type_walking:
                initButtons();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    buttonWalking.setBackground(drawableClicked);
                }
                buttonWalking.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                buttonWalking.setTextColor(Color.BLACK);
                tuneWheel.setMaxValue(100);
                break;
            case R.id.set_target_type_riding:
                initButtons();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    buttonRiding.setBackground(drawableClicked);
                }
                buttonRiding.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                buttonRiding.setTextColor(Color.BLACK);
                tuneWheel.setMaxValue(200);
                break;
            case R.id.set_target_back:
                Intent intent = new Intent();
                intent.putExtra("result_target", target);
                setResult(RESULT_OK, intent);
                finish();
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("target_return", target);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
