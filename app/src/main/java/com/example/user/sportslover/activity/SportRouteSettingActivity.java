package com.example.user.sportslover.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.example.user.sportslover.R;

public class SportRouteSettingActivity extends AppCompatActivity implements View.OnClickListener {

    LatLng point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_route_setting);
        point = getIntent().getParcelableExtra("current_position");
        TextView tvMyRoute = (TextView) findViewById(R.id.tv_sport_route_my_route);
        TextView tvRecommended = (TextView) findViewById(R.id.tv_sport_route_recommended);
        ImageView ivBack = (ImageView) findViewById(R.id.sport_route_back);
        ImageView ivSetting;
        ivSetting = (ImageView) findViewById(R.id.sport_route_setting);
        tvMyRoute.setOnClickListener(this);
        tvRecommended.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sport_route_my_route:
                Intent intent = new Intent(this, SportRouteMyRouteActitvity.class);
                intent.putExtra("current_position", point);
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_sport_route_recommended:
                Intent intent1 = new Intent(this, SportRouteRecommendedActivity.class);
                intent1.putExtra("current_position", point);
                startActivityForResult(intent1, 2);
                break;
            case R.id.sport_route_back:
                finish();
            case R.id.sport_route_setting:
                Intent intent2 = new Intent(this, SportSettingActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
            case 2:
                if (resultCode == RESULT_OK){
                    Intent intent = new Intent();
                    intent.putExtra("route_return", data.getIntExtra("route_return", -1));
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
