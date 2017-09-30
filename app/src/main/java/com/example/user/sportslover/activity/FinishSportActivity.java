package com.example.user.sportslover.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
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
        MapView mapView = (MapView) findViewById(R.id.finish_sport_map);
        BaiduMap baiduMap = mapView.getMap();
        mapView.showZoomControls(false);
        if (sportInformationItem.getPoints().size() >= 2){
            OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(sportInformationItem.getPoints());
            baiduMap.addOverlay(ooPolyline);
        }
        double lat = 0, lng = 0;
        for (int i = 0; i < sportInformationItem.getPoints().size(); i++){
            lat += sportInformationItem.getPoints().get(i).latitude;
            lng += sportInformationItem.getPoints().get(i).longitude;
        }
        lat /= sportInformationItem.getPoints().size();
        lng /= sportInformationItem.getPoints().size();
        LatLng centerPoint = new LatLng(lat, lng);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(centerPoint).zoom(15f);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
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
}
