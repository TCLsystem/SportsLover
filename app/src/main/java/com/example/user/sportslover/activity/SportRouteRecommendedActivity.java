package com.example.user.sportslover.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.example.user.sportslover.R;
import com.example.user.sportslover.adapter.SportRouteMyRouteAdapter;
import com.example.user.sportslover.adapter.SportRouteRecommendedAdapter;
import com.example.user.sportslover.bean.RouteItem;
import com.example.user.sportslover.model.RouteControlImpr;
import com.example.user.sportslover.model.RouteControlInter;
import com.example.user.sportslover.util.BitmapUtil;
import com.example.user.sportslover.util.MapUtil;
import com.example.user.sportslover.widget.AsyncImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SportRouteRecommendedActivity extends AppCompatActivity implements View.OnClickListener {

    private List<RouteItem> routeItemsList = new ArrayList<>();
    private ImageView ivMap;
    private int positionOnSelect;
    private LatLng point;
    private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_route_recommended);
        point = getIntent().getParcelableExtra("current_position");
        ImageView ivBack = (ImageView) findViewById(R.id.sport_route_recommended_back);
        ImageView ivSetting = (ImageView) findViewById(R.id.sport_route_recommended_setting);
        ivMap = (ImageView) findViewById(R.id.sport_route_recommended_map);
        TextView ivGo = (TextView) findViewById(R.id.sport_route_recommended_begin_sport);
        ivBack.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        ivGo.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sport_route_recommended_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        final SportRouteRecommendedAdapter adapter = new SportRouteRecommendedAdapter(routeItemsList, recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new SportRouteRecommendedAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //ivMap.setImageBitmap(routeItemsList.get(position).getBitmap());
                ImageView ivOnSelect = (ImageView) view.findViewById(R.id.sport_route_recommended_selected);
                ivOnSelect.setVisibility(View.VISIBLE);
                if (routeItemsList.get(position).getPic() != null){
                    String imageUrl = routeItemsList.get(position).getPic().getUrl();
                    Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new AsyncImageLoader.ImageCallback() {
                        public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                            ivMap.setImageDrawable(imageDrawable);
                        }
                    });
                    if (cachedImage == null) {
                        //holder.mapImage.setImageResource(R.drawable.w);
                    }else{
                        ivMap.setImageDrawable(cachedImage);
                    }
                }
            }
        });
        final RouteControlInter routeControlInter = new RouteControlImpr();
        routeControlInter.findRouteByUsername(SportRouteRecommendedActivity.this, "", new RouteControlImpr.OnRouteFindListener() {
            @Override
            public void onSuccess(List<RouteItem> routeItemList) {
                routeItemsList.clear();
                routeItemsList.addAll(routeItemList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sport_route_recommended_back:
                finish();
            case R.id.sport_route_recommended_setting:
                Intent intent2 = new Intent(this, SportSettingActivity.class);
                startActivity(intent2);
                break;
            case R.id.sport_route_recommended_begin_sport:
                double distance = MapUtil.gps2m(point.latitude, point.longitude,
                        routeItemsList.get(positionOnSelect).getSportsPath().get(0).latitude,
                        routeItemsList.get(positionOnSelect).getSportsPath().get(0).longitude);
                AlertDialog.Builder dialog = new AlertDialog.Builder(SportRouteRecommendedActivity.this);
                dialog.setMessage("You are " + (int) distance + "m from the starting point.\nDo you choose this Route?");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra("route_return", routeItemsList.get(positionOnSelect).getId());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
    }
}
