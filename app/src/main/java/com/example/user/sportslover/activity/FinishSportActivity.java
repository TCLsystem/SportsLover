package com.example.user.sportslover.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.SportInformationItem;
import com.example.user.sportslover.customview.CircularRingPercentageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class FinishSportActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_finish_sport);
        DecimalFormat textFormat = new DecimalFormat("#0.00");
        SportInformationItem sportInformationItem = getIntent().getParcelableExtra("sport_information");
        CircularRingPercentageView circularRingPercentageView;
        circularRingPercentageView = (CircularRingPercentageView) findViewById(R.id.finish_sport_progress);
        circularRingPercentageView.setCircleWidth(circularRingPercentageView.getCircleWidth()*2/3);
        circularRingPercentageView.setRoundWidth(15);
        circularRingPercentageView.setProgress(sportInformationItem.getSportProgress());
        circularRingPercentageView.setColors(new int[]{Color.WHITE, Color.WHITE});
        circularRingPercentageView.setRoundBackgroundColor(Color.WHITE);
        String html;
        TextView tvDistance = (TextView) findViewById(R.id.tv_finish_sport_distance);
        html = "Completes " + (int)(sportInformationItem.getSportProgress()) + "%<br><big><big><big><big>" + textFormat.format(sportInformationItem.getTotalMileages()/1000) + "</big></big></big></big>  km<br>Totol mileages";
        tvDistance.setText(Html.fromHtml(html));
        TextView tvAveragePace = (TextView) findViewById(R.id.tv_finish_sport_average_pace);
        html = "<big><big><big>" + sportInformationItem.getAveragePace()/60 + "’</big>" + sportInformationItem.getAveragePace()%60 + "”" +"</big></big><br>Average<br>pace";
        tvAveragePace.setText(Html.fromHtml(html));
        TextView tvCalories = (TextView) findViewById(R.id.tv_finish_sport_calories);
        html = "<big><big><big>" + sportInformationItem.getCalories() +"</big></big></big>KCAL<br>Calories";
        tvCalories.setText(Html.fromHtml(html));
        TextView tvCumulativeTime = (TextView) findViewById(R.id.tv_finish_sport_cumulative_time);
        html = "<big><big><big>" + sportInformationItem.getCumulativeTime()/60/60 +"</big></big></big> h<br>Cumulative<br>time";
        tvCumulativeTime.setText(Html.fromHtml(html));
        ImageView ivFinishSportMap = (ImageView) findViewById(R.id.iv_finish_sport_map);
        Bitmap bitmap = BitmapFactory.decodeFile("/data/data/com.example.user.sportslover/files/test.png");
        ivFinishSportMap.setImageBitmap(bitmap);
        Button buttonCollectMap = (Button) findViewById(R.id.finish_sport_collect_map);
        Button buttonFinish = (Button) findViewById(R.id.finish_sport_finish);
        buttonCollectMap.setOnClickListener(this);
        buttonFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finish_sport_collect_map:
                break;
            case R.id.finish_sport_finish:
                finish();
                break;
            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        GradientDrawable drawable = (GradientDrawable)findViewById(R.id.finish_sport_layout).getBackground();
        drawable.setColors(new int[]{0xff5bc0e5, 0xff6ee4bc});
        GradientDrawable drawable1 = (GradientDrawable) findViewById(R.id.finish_sport_finish).getBackground();
        drawable1.setColors(new int[]{0xff5bc0e5, 0xff6ee4bc});
        GradientDrawable drawable2 = (GradientDrawable)findViewById(R.id.finish_sport_collect_map).getBackground();
        drawable2.setStroke(4, 0xff5bc0e5);
    }
}
