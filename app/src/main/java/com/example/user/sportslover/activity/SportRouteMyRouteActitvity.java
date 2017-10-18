package com.example.user.sportslover.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
import com.example.user.sportslover.bean.RouteItem;
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.model.RouteControlImpr;
import com.example.user.sportslover.model.RouteControlInter;
import com.example.user.sportslover.model.SportHistoryModelInter;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.util.BitmapUtil;
import com.example.user.sportslover.util.MapUtil;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

public class SportRouteMyRouteActitvity extends AppCompatActivity implements View.OnClickListener {

    private List<RouteItem> routeItemsList = new ArrayList<>();
    private LatLng point = null;
    private int positionOnSelect;
    SportRouteMyRouteAdapter adapter;

    private UserLocal mUserLocal = new UserLocal();
    private UserModelImpl mUserModelImpl = new UserModelImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_route_my_route);
        mUserLocal = mUserModelImpl.getUserLocal();
        point = getIntent().getParcelableExtra("current_position");
        ImageView ivBack = (ImageView) findViewById(R.id.sport_route_my_route_back);
        ImageView ivSetting = (ImageView) findViewById(R.id.sport_route_my_route_setting);
        ivBack.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sport_route_my_route_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SportRouteMyRouteAdapter(routeItemsList, recyclerView);
        recyclerView.setAdapter(adapter);
        if (point != null){
            adapter.setItemClickListener(new SportRouteMyRouteAdapter.MyItemClickListener() {
                @Override
                public void onItemClick(View view, final int position) {
                    double distance = MapUtil.gps2m(point.latitude, point.longitude,
                            routeItemsList.get(position).getSportsPath().get(0).latitude,
                            routeItemsList.get(position).getSportsPath().get(0).longitude);
                    positionOnSelect = position;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SportRouteMyRouteActitvity.this);
                    dialog.setMessage("You are " + (int) distance + "m from the starting point.\nDo you choose this Route?");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra("route_return", routeItemsList.get(positionOnSelect).getObjectId());
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
                }
            });
        }
        final RouteControlInter routeControlInter = new RouteControlImpr();
        routeControlInter.findRouteByUsername(SportRouteMyRouteActitvity.this, mUserLocal.getName(), new RouteControlImpr.OnRouteFindListener() {
            @Override
            public void onSuccess(List<RouteItem> routeItemList) {
                /*for (int i = 0; i < routeItemList.size(); i++){
                    routeItemList.get(i).setBitmap(BitmapUtil.returnBitMap(routeItemList.get(i).getPic().getUrl()));
                }*/
                routeItemsList.clear();
                routeItemsList.addAll(routeItemList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sport_route_my_route_back:
                finish();
            case R.id.sport_route_my_route_setting:
                Intent intent2 = new Intent(this, SportSettingActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
