package com.example.user.sportslover.view;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.user.sportslover.R;
import com.example.user.sportslover.customview.MyVerticalViewPager;
import com.example.user.sportslover.listener.MyOrientationListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BeginSport extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<View> pageview;
    private View view0;
    private View view1;

    public LocationClient mLocationClient;

    private MapView mapView;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;

    private int mDirection = 100;

    private double sum_distance = 0;
    private final double EARTH_RADIUS = 6378137.0;

    private MyOrientationListener myOrientationListener;

    private List<LatLng> pointstwo = new ArrayList<LatLng>();
    private LatLng p1;
    private LatLng p2;
    private TextView tvDistance;
    private TextView tvHours;
    private TextView tvMinutes;
    private TextView tvSeconds;
    private TextView tvTarget;
    Button buttonTarget;
    Button buttonStart;
    Button buttonRoute;
    Button buttonPause;
    Button buttonResume;
    Button buttonStop;

    private Timer timer = new Timer();
    private TimerTask task;
    private int gSeconds = 0;
    private boolean timerValidFlag = false;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            // 调用相机回调接口由于MainActivity已经实现了回调接口，所以MainActivity.this即可

            if (msg.what == 1) {
                // if(points.size()>3){
                // pointstwo.add((LatLng)points.get(points.size()-3));
                // pointstwo.add((LatLng)points.get(points.size()-2));
                // pointstwo.add((LatLng)points.get(points.size()-1));
                // }else{
                // pointstwo.add((LatLng)points.get(count));
                // }
                // Log.d("points",""+points.get(points.size()-3).latitude);
                // Log.d("points",""+points.get(points.size()-2).latitude);
                // Log.d("points",""+points.get(points.size()-1).latitude);
                pointstwo.add(p1);
                pointstwo.add(p2);
                // distance+=DistanceUtil. getDistance(p1, p2);
                // LatLng llDot = new LatLng(p2.latitude,p2.longitude);
                // try{
                OverlayOptions ooDot = new DotOptions().center(p2).radius(6)
                        .color(0xAAFF0000);
                baiduMap.addOverlay(ooDot);

                OverlayOptions ooPolyline = new PolylineOptions().width(4)
                        .color(0xAAFF0000).points(pointstwo);
                baiduMap.addOverlay(ooPolyline);
                p1 = p2;
                mLocationClient.requestLocation();

                tvHours.setText(timeFormat.format(gSeconds/60/60)+":");
                tvMinutes.setText(timeFormat.format(gSeconds/60%60)+":");
                tvSeconds.setText(timeFormat.format(gSeconds%60)+"");

                progressCircle.setProgress((float)sum_distance/target*100f);
                // }catch(Exception e){
                // e.printStackTrace();
                // }

            }
        }

    };

    private CircularRingPercentageView progressCircle;
    private TuneWheelView tuneWheel;
    private float target = 100000;
    private DecimalFormat textFormat = new DecimalFormat("#0.0");
    private DecimalFormat timeFormat = new DecimalFormat("#00.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); setContentView(R.layout.main);
        mLocationClient = new LocationClient(getApplicationContext());
        BDAbstractLocationListener myListener = new BDAbstractLocationListener(){
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                        || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                    drawTrack(bdLocation);
                }
            }
        };
        mLocationClient.registerLocationListener(myListener);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.begin_sport_layout);
        view0 = getLayoutInflater().inflate(R.layout.baidu_map_layout, null);
        view1 = getLayoutInflater().inflate(R.layout.show_map_pager, null);
        pageview =new ArrayList<View>();
        pageview.add(view0);
        pageview.add(view1);
        MyVerticalViewPager myVerticalViewPager = (MyVerticalViewPager) findViewById(R.id.verticalviewpager);
        PagerAdapter mPageAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pageview.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0==arg1;
            }

            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((MyVerticalViewPager) arg0).removeView(pageview.get(arg1));
            }

            public Object instantiateItem(View arg0, int arg1){
                ((MyVerticalViewPager)arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }

            @Override
            public float getPageWidth(int position) {
                if (position == 0)
                    return 0.8f;
                else
                    return super.getPageWidth(position);
            }
        };
        myVerticalViewPager.setAdapter(mPageAdapter);
        myVerticalViewPager.setCurrentItem(0);

        buttonTarget = (Button) view0.findViewById(R.id.target0);
        buttonStart = (Button) view0.findViewById(R.id.start0);
        buttonRoute = (Button) view0.findViewById(R.id.route0);
        buttonPause = (Button) view0.findViewById(R.id.pause0);
        buttonResume = (Button) view0.findViewById(R.id.resume0);
        buttonStop = (Button) view0.findViewById(R.id.stop0);
        buttonTarget.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
        buttonRoute.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonResume.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        tvDistance = (TextView) view1.findViewById(R.id.distance);
        tvHours = (TextView) view1.findViewById(R.id.tv_hours);
        tvMinutes = (TextView) view1.findViewById(R.id.tv_minutes);
        tvSeconds = (TextView) view1.findViewById(R.id.tv_seconds);
        tvTarget = (TextView) view0.findViewById(R.id.tv_target);
        progressCircle = (CircularRingPercentageView) view0.findViewById(R.id.progress);
        tuneWheel = (TuneWheelView) view0.findViewById(R.id.ruler);
        tuneWheel.setValueChangeListener(new TuneWheelView.OnValueChangeListener(){
            @Override
            public void onValueChange(float value) {
                target = value * 1000;
            }
        });
        initOritationListener();
        mapView = (MapView) view1.findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(BeginSport.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(BeginSport.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(BeginSport.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(BeginSport.this, permissions, 1);
        } else {
            requestLocation();
        }
    }


    private void drawTrack(BDLocation location){

        if (isFirstLocate){
            p1 = p2 = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(p2).zoom(16f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            /*MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);*/
            isFirstLocate = false;
        } else {
            LatLng p0 = p2;
            p2 = new LatLng(location.getLatitude(), location.getLongitude());
            if (timerValidFlag){
                sum_distance = sum_distance + gps2m(p0.latitude,p0.longitude,p2.latitude,p2.longitude);
                MapStatus.Builder builder = new MapStatus.Builder();
                OverlayOptions ooDot = new DotOptions().center(p2).radius(6)
                        .color(0xAAFF0000);
                baiduMap.addOverlay(ooDot);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
        /*MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();*/
        MyLocationData locationData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(mDirection).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        baiduMap.setMyLocationData(locationData);
        MyLocationConfiguration mConfig = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null);
        baiduMap.setMyLocationConfiguration(mConfig);

        //tvDistance.setText("现在走过的距离是："+sum_distance+"米");
        tvDistance.setText("现在走过的距离是："+textFormat.format(sum_distance)+"米");
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    for (int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }


    private void initOritationListener()
    {
        myOrientationListener = new MyOrientationListener(
                getApplicationContext());
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener()
                {
                    @Override
                    public void onOrientationChanged(float x) {
                        mDirection = (int) x;
                    }
                });
    }

    private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        //s = Math.round(s * 10000) / 10000;
        return 1.38*s;

    }

    @Override
    protected void onStart() {
        super.onStart();
        myOrientationListener.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myOrientationListener.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.target0:
                tvTarget.setText(textFormat.format(target/1000f) + "千米");
                progressCircle.setTextTarget(target/1000);
                break;
            case R.id.start0:
                startTimer();
                buttonStart.setVisibility(View.GONE);
                buttonPause.setVisibility(View.VISIBLE);
                buttonStop.setVisibility(View.VISIBLE);
                break;
            case R.id.route0:
                break;
            case R.id.pause0:
                pauseTimer();
                buttonPause.setVisibility(View.GONE);
                buttonResume.setVisibility(View.VISIBLE);
                break;
            case R.id.resume0:
                startTimer();
                buttonPause.setVisibility(View.VISIBLE);
                buttonResume.setVisibility(View.GONE);
                break;
            case R.id.stop0:
                gSeconds = 0;
                sum_distance = 0;
                pauseTimer();
                buttonStart.setVisibility(View.VISIBLE);
                buttonPause.setVisibility(View.GONE);
                buttonResume.setVisibility(View.GONE);
                buttonStop.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }

        if (task == null) {
            task = new TimerTask() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    gSeconds++;
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            };
        }

        if (timer != null && task != null) {
            try {
                //task.cancel();
                timerValidFlag = true;
                timer.schedule(task, 1000, 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        timerValidFlag = false;
    }

}