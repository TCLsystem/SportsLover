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
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.customview.CircularRingPercentageView;
import com.example.user.sportslover.customview.TuneWheelView;
import com.example.user.sportslover.model.CalculateCaloriesInter;
import com.example.user.sportslover.model.CalculateCaloriesRidingImpl;
import com.example.user.sportslover.model.CalculateCaloriesRunningImpl;
import com.example.user.sportslover.model.CalculateCaloriesWalkingImpl;
import com.example.user.sportslover.model.UserModelImpl;

import java.text.DecimalFormat;

public class SetSportTargetActivity extends AppCompatActivity implements View.OnClickListener {

    private TuneWheelView tuneWheel;
    private CircularRingPercentageView progressCircle;
    private TextView tvTarget;
    private Button buttonRunning;
    private Button buttonWalking;
    private Button buttonRiding;
    private ImageView ivSetTargetBack;
    private Drawable drawableClicked;
    private Drawable drawableUnclicked;
    private float target;
    private CalculateCaloriesInter calculateCaloriesInter;
    private float weight = 60;

    private UserLocal mUserLocal = new UserLocal();
    private UserModelImpl mUserModelImpl = new UserModelImpl();

    private DecimalFormat textFormat = new DecimalFormat("#0.0");

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
        //buttonRunning.setOnClickListener(this);
        buttonWalking = (Button) findViewById(R.id.set_target_type_walking);
        //buttonWalking.setOnClickListener(this);
        buttonRiding = (Button) findViewById(R.id.set_target_type_riding);
        //buttonRiding.setOnClickListener(this);
        ivSetTargetBack = (ImageView) findViewById(R.id.set_target_back);
        ivSetTargetBack.setOnClickListener(this);
        drawableClicked = buttonRunning.getBackground();
        drawableUnclicked = buttonWalking.getBackground();
        tvTarget = (TextView) findViewById(R.id.tv_target);
        mUserLocal = mUserModelImpl.getUserLocal();
        if (mUserLocal.getWeight()!=null)
            weight = Float.parseFloat(mUserLocal.getWeight());
        refleshView();
        setButtonFromIntent(getIntent().getIntExtra("sport_type", -1));
    }

    private void refleshView(){
        String html = textFormat.format(calculateCaloriesInter.calculateCalories(weight, target/1000)) + "KCAL<br><big><big><big><big><big>"
                + textFormat.format(target/1000) + "</big></big>  km</big></big></big><br>Totol mileages";
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

    private void setButtonFromIntent(int sportType){
        switch (sportType){
            case 0:
                initButtons();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    buttonRunning.setBackground(drawableClicked);
                }
                buttonRunning.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                buttonRunning.setTextColor(Color.BLACK);
                tuneWheel.setMaxValue(100);
                calculateCaloriesInter = new CalculateCaloriesRunningImpl();
                break;
            case 1:
                initButtons();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    buttonWalking.setBackground(drawableClicked);
                }
                buttonWalking.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                buttonWalking.setTextColor(Color.BLACK);
                tuneWheel.setMaxValue(100);
                calculateCaloriesInter = new CalculateCaloriesWalkingImpl();
                break;
            case 2:
                initButtons();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    buttonRiding.setBackground(drawableClicked);
                }
                buttonRiding.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                buttonRiding.setTextColor(Color.BLACK);
                tuneWheel.setMaxValue(200);
                calculateCaloriesInter = new CalculateCaloriesRidingImpl();
                break;
            default:
                break;
        }
    }
}
