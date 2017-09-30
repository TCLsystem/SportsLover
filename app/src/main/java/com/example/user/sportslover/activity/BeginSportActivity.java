package com.example.user.sportslover.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.user.sportslover.R;
import com.example.user.sportslover.customview.CircularRingPercentageView;
import com.example.user.sportslover.widget.MyVerticalViewPager;
import com.example.user.sportslover.bean.SportInformationItem;
import com.example.user.sportslover.bean.MyOrientationListener;
import com.example.user.sportslover.model.CalculateCaloriesInter;
import com.example.user.sportslover.model.CalculateCaloriesRidingImpl;
import com.example.user.sportslover.model.CalculateCaloriesRunningImpl;
import com.example.user.sportslover.model.CalculateCaloriesWalkingImpl;
import com.example.user.sportslover.service.SportTrackService;
import com.example.user.sportslover.util.ColorUtil;
import com.example.user.sportslover.util.MapUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BeginSportActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<View> pageview;
    private View view0;
    private View view1;
    private int currPage;
    private int sportType;

    public LocationClient mLocationClient;

    private MapView mapView;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;

    private int mDirection = 100;

    private double sum_distance = 0;
    private double remoteDistance = 0;

    private MyOrientationListener myOrientationListener;

    private List<LatLng> points = new ArrayList<LatLng>();
    private List<LatLng> remotePoints = new ArrayList<LatLng>();
    private LatLng newPoint;
    private TextView tvSportType;
    private TextView tvDistance;
    private TextView tvHours;
    private TextView tvSportAveragePace;
    private TextView tvCalories;
    ImageView ivLock;
    ImageView ivMap;
    Button buttonPause0;
    Button buttonResume0;
    Button buttonStop0;
    Button buttonTarget1;
    Button buttonStart1;
    Button buttonRoute1;
    Button buttonPause1;
    Button buttonResume1;
    Button buttonStop1;
    ImageView ivStrechMap;
    LinearLayout llBeginSportLayout;
    LinearLayout llBeginSportStatus;

    private Timer timer = new Timer();
    private TimerTask task;
    private int gSeconds = 0;
    private long remoteSeconds = 0;
    private int averagePace= 0;
    private boolean timerValidFlag = false;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
        String html;
        // 调用相机回调接口由于MainActivity已经实现了回调接口，所以MainActivity.this即可
        if (msg.what == 1) {
            baiduMap.clear();

            if (sportTrackServiceControlBinder.getCurrentPoint() != null){
                remoteLocate(sportTrackServiceControlBinder.getCurrentPoint());
            }
            //OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(points);
            remotePoints = sportTrackServiceControlBinder.getPoints();
            if (remotePoints.size() > 2){
                OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(remotePoints);
                baiduMap.addOverlay(ooPolyline);
            }
            mLocationClient.requestLocation();
            remoteSeconds = sportTrackServiceControlBinder.getSeconds();
            html = "<big><big><big>" + timeFormat.format((float)remoteSeconds/60.0/60.0) +"</big></big></big> h<br>Cumulative<br>time";
            tvHours.setText(Html.fromHtml(html));
            remoteDistance = sportTrackServiceControlBinder.getDistance();
            if (target > 100){
                html = "<big><big><big><big><big>" + textFormat.format(remoteDistance/1000f) + "</big></big>  km</big></big></big><br>in "+ target/1000 +"km<br>Totol mileages";
            } else {
                html = "<big><big><big><big><big>" + textFormat.format(remoteDistance/1000f) + "</big></big>  km</big></big></big><br>Totol mileages";
            }
            tvDistance.setText(Html.fromHtml(html));
            //tvDistance.setText("现在走过的距离是："+textFormat.format(sum_distance)+"米");

            averagePace = (int)(remoteSeconds/remoteDistance*1000);
            if (remoteDistance <= 0 || averagePace >= 599940){
                averagePace = 599940;
            }
            html = "<big><big><big>" + averagePace/60 + "’</big>" + averagePace%60 + "”" +"</big></big><br>Average<br>pace";
            tvSportAveragePace.setText(Html.fromHtml(html));

            switch (currPage){
                case 0:
                    html = "<big><big><big>" + textFormat.format(calculateCaloriesInter.calculateCalories(weight, remoteSeconds, averagePace)) +"</big></big></big>KCAL<br>Calories";
                    tvCalories.setText(Html.fromHtml(html));
                    break;
                case 1:
                    html = "<big><big><big>" + textFormat.format(remoteDistance/1000f) +"</big></big></big>Km<br>Total Mileages";
                    tvCalories.setText(Html.fromHtml(html));
                    break;
                default:
                    break;
            }

            if (target >= 100){
                progressCircle.setProgress((remoteDistance/target>1)?100f:((float)remoteDistance/target*100f));
                refleshBackgroundColors((remoteDistance/target>1)?100f:((float)remoteDistance/target*100f));
            } else {
                refleshBackgroundColors(0);
                progressCircle.setProgress(100f);
            }
            //progressCircle.setProgress((float)sum_distance/target*100f);
        }
        }

    };

    private CircularRingPercentageView progressCircle;
    private float target = 0;
    private float weight = 60;
    private CalculateCaloriesInter calculateCaloriesInter;
    private DecimalFormat textFormat = new DecimalFormat("#0.0000");
    private DecimalFormat timeFormat = new DecimalFormat("#0.0000");
    private MyVerticalViewPager myVerticalViewPager;
    private SportTrackService.SportTrackServiceControlBinder sportTrackServiceControlBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sportTrackServiceControlBinder = (SportTrackService.SportTrackServiceControlBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

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
                    //drawTrack(bdLocation);
                }
            }
        };
        mLocationClient.registerLocationListener(myListener);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_begin_sport);
        tvSportType = (TextView) findViewById(R.id.sport_type);
        Intent intent = getIntent();
        sportType = intent.getIntExtra("sport_type", -1);
        switch (sportType){
            case 0:
                tvSportType.setText("Running");
                calculateCaloriesInter = new CalculateCaloriesRunningImpl();
                break;
            case 1:
                tvSportType.setText("Walking");
                calculateCaloriesInter = new CalculateCaloriesWalkingImpl();
                break;
            case 2:
                tvSportType.setText("Riding");
                calculateCaloriesInter = new CalculateCaloriesRidingImpl();
                break;
            default:
                Log.d("Begin Sport", "Invalid sport type");
        }
        view0 = getLayoutInflater().inflate(R.layout.item_cicular_viewpager, null);
        view1 = getLayoutInflater().inflate(R.layout.item_baidu_map_viewpager, null);
        pageview =new ArrayList<View>();
        pageview.add(view0);
        pageview.add(view1);
        myVerticalViewPager = (MyVerticalViewPager) findViewById(R.id.verticalviewpager);
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
        };
        myVerticalViewPager.setAdapter(mPageAdapter);
        myVerticalViewPager.setCurrentItem(1);
        myVerticalViewPager.setScrollable(false);
        myVerticalViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        llBeginSportStatus = (LinearLayout) findViewById(R.id.ll_begin_sport_status);
        ivLock = (ImageView) view0.findViewById(R.id.circular_view_lock);
        ivMap = (ImageView) view0.findViewById(R.id.cicular_view_show_map);
        ivStrechMap = (ImageView) view1.findViewById(R.id.iv_strech_map);
        buttonPause0 = (Button) view0.findViewById(R.id.pause0);
        buttonResume0 = (Button) view0.findViewById(R.id.resume0);
        buttonStop0 = (Button) view0.findViewById(R.id.stop0);
        buttonTarget1 = (Button) view1.findViewById(R.id.target1);
        buttonStart1 = (Button) view1.findViewById(R.id.start1);
        buttonRoute1 = (Button) view1.findViewById(R.id.route1);
        buttonPause1 = (Button) view1.findViewById(R.id.pause1);
        buttonResume1 = (Button) view1.findViewById(R.id.resume1);
        buttonStop1 = (Button) view1.findViewById(R.id.stop1);
        ivLock.setOnClickListener(this);
        ivMap.setOnClickListener(this);
        ivStrechMap.setOnClickListener(this);
        buttonPause0.setOnClickListener(this);
        buttonResume0.setOnClickListener(this);
        buttonStop0.setOnClickListener(this);
        buttonTarget1.setOnClickListener(this);
        buttonStart1.setOnClickListener(this);
        buttonRoute1.setOnClickListener(this);
        buttonPause1.setOnClickListener(this);
        buttonResume1.setOnClickListener(this);
        buttonStop1.setOnClickListener(this);
        llBeginSportLayout = (LinearLayout) findViewById(R.id.begin_sport_layout);
        ivStrechMap = (ImageView) view1.findViewById(R.id.iv_strech_map);
        tvDistance = (TextView) view0.findViewById(R.id.tv_distance);
        tvHours = (TextView) findViewById(R.id.tv_begin_sport_cumulative_time);
        tvSportAveragePace = (TextView) findViewById(R.id.tv_begin_sport_average_pace);
        tvCalories = (TextView) findViewById(R.id.tv_begin_sport_calories);
        progressCircle = (CircularRingPercentageView) view0.findViewById(R.id.progress);
        progressCircle.setRoundWidth(15);
        progressCircle.setProgress(75f);
        progressCircle.setColors(new int[]{Color.WHITE, Color.WHITE});
        progressCircle.setRoundBackgroundColor(Color.WHITE);
        initOritationListener();
        mapView = (MapView) view1.findViewById(R.id.bmapView);
        mapView.showZoomControls(false);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(BeginSportActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(BeginSportActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(BeginSportActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(BeginSportActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
        refleshBackgroundColors(0);
        Intent startSportTrackService = new Intent(this, SportTrackService.class);
        startService(startSportTrackService);
        startTimer();
    }

    private void remoteLocate(LatLng point){
        MyLocationData locationData = new MyLocationData.Builder()
                .direction(mDirection).latitude(point.latitude)
                .longitude(point.longitude).build();
        baiduMap.setMyLocationData(locationData);
        MyLocationConfiguration mConfig = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        baiduMap.setMyLocationConfiguration(mConfig);

        if (isFirstLocate){
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(point).zoom(18f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            isFirstLocate = false;
        } else {
            float zoom = baiduMap.getMapStatus().zoom;
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(point).zoom(zoom);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    private void drawTrack(BDLocation location){

        MyLocationData locationData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(mDirection).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        baiduMap.setMyLocationData(locationData);
        MyLocationConfiguration mConfig = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        baiduMap.setMyLocationConfiguration(mConfig);

        if (isFirstLocate){
            newPoint = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(newPoint).zoom(18f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            isFirstLocate = false;
            points.add(newPoint);
        } else {
            LatLng tmpPoint = newPoint;
            newPoint = new LatLng(location.getLatitude(), location.getLongitude());
            if (timerValidFlag){
                double distance = MapUtil.gps2m(tmpPoint.latitude,tmpPoint.longitude,newPoint.latitude,newPoint.longitude);
                sum_distance = sum_distance + distance;
                if (distance > 10)
                    points.add(newPoint);
            }
            float zoom = baiduMap.getMapStatus().zoom;
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(newPoint).zoom(zoom);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
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

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            String html;
            switch (arg0) {
                case 0:
                    html = "<big><big><big>" + textFormat.format(calculateCaloriesInter.calculateCalories(weight, remoteSeconds, averagePace)) +"</big></big></big>KCAL<br>Calories";
                    tvCalories.setText(Html.fromHtml(html));
                    break;
                case 1:
                    html = "<big><big><big>" + textFormat.format(remoteDistance/1000f) +"</big></big></big>Km<br>Total Mileages";
                    tvCalories.setText(Html.fromHtml(html));
                    break;
                default:
                    break;
            }
            currPage = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myOrientationListener.start();
        Intent bindIntent = new Intent(this, SportTrackService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myOrientationListener.stop();
        sportTrackServiceControlBinder.pauseService();
        unbindService(connection);
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
        pauseTimer();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK)
                    target = data.getFloatExtra("target_return", 0f);
                    Log.d("TAG", Float.toString(target));
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circular_view_lock:
                break;
            case R.id.iv_strech_map:
                myVerticalViewPager.setCurrentItem(0);
                break;
            case R.id.cicular_view_show_map:
                myVerticalViewPager.setCurrentItem(1);
                break;
            case R.id.target1:
                Intent intent = new Intent(this, SetSportTargetActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.start1:
                timerValidFlag = true;
                myVerticalViewPager.setCurrentItem(0);
                currPage = 0 ;
                sportTrackServiceControlBinder.startService();
                llBeginSportStatus.setVisibility(View.VISIBLE);
                buttonTarget1.setVisibility(View.GONE);
                buttonRoute1.setVisibility(View.GONE);
                buttonStart1.setVisibility(View.GONE);
                buttonPause0.setVisibility(View.VISIBLE);
                buttonPause1.setVisibility(View.VISIBLE);
                ivStrechMap.setVisibility(View.VISIBLE);
                myVerticalViewPager.setScrollable(true);
                break;
            case R.id.route1:
                break;
            case R.id.pause0:
            case R.id.pause1:
                timerValidFlag = false;
                sportTrackServiceControlBinder.pauseService();
                buttonPause0.setVisibility(View.GONE);
                buttonPause1.setVisibility(View.GONE);
                buttonResume0.setVisibility(View.VISIBLE);
                buttonResume1.setVisibility(View.VISIBLE);
                buttonStop0.setVisibility(View.VISIBLE);
                buttonStop1.setVisibility(View.VISIBLE);
                ivLock.setVisibility(View.GONE);
                ivMap.setVisibility(View.GONE);
                break;
            case R.id.resume0:
            case R.id.resume1:
                timerValidFlag = true;
                sportTrackServiceControlBinder.startService();
                buttonPause0.setVisibility(View.VISIBLE);
                buttonPause1.setVisibility(View.VISIBLE);
                buttonResume0.setVisibility(View.GONE);
                buttonResume1.setVisibility(View.GONE);
                buttonStop0.setVisibility(View.GONE);
                buttonStop1.setVisibility(View.GONE);
                ivLock.setVisibility(View.VISIBLE);
                ivMap.setVisibility(View.VISIBLE);
                break;
            case R.id.stop0:
            case R.id.stop1:
                timerValidFlag = false;
                sportTrackServiceControlBinder.pauseService();
                Intent stopSportTrackService = new Intent(this, SportTrackService.class);
                stopService(stopSportTrackService);
                buttonStart1.setVisibility(View.VISIBLE);
                buttonPause0.setVisibility(View.GONE);
                buttonPause1.setVisibility(View.GONE);
                buttonResume0.setVisibility(View.GONE);
                buttonResume1.setVisibility(View.GONE);
                buttonStop0.setVisibility(View.GONE);
                buttonStop1.setVisibility(View.GONE);
                SportInformationItem sportInformationItem = new SportInformationItem();
                sportInformationItem.setAveragePace(averagePace);
                sportInformationItem.setCalories(calculateCaloriesInter.calculateCalories(weight, remoteSeconds, averagePace));
                sportInformationItem.setCumulativeTime(remoteSeconds);
                sportInformationItem.setSportProgress((remoteDistance/target > 1 || target < 100)?100f:((float)remoteDistance/target*100f));
                sportInformationItem.setTotalMileages(remoteDistance);
                sportInformationItem.setPoints(remotePoints);
                Intent intent1 = new Intent(BeginSportActivity.this, FinishSportActivity.class);
                intent1.putExtra("sport_information", sportInformationItem);
                startActivity(intent1);
                finish();
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
                    if (timerValidFlag)
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
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void refleshBackgroundColors(float localProcess){

        int beginColorTop = 0xff5bc0e5;
        int beginColorBottom = 0xff6ee4bc;
        int endColorTop = 0xffff967a;
        int endColorBottom = 0xfffcde6c;

        GradientDrawable gradientDrawableBackground;
        GradientDrawable gradientDrawableGradientButton;
        GradientDrawable gradientDrawableWhiteButton;

        @ColorInt int colors[] = new int[] {ColorUtil.getProgressColor(localProcess, beginColorTop, endColorTop),
                ColorUtil.getProgressColor(localProcess, beginColorBottom, endColorBottom)};

        gradientDrawableBackground = (GradientDrawable) llBeginSportLayout.getBackground();
        gradientDrawableBackground.setColors(colors);
        //llBeginSportLayout.setBackground(gradientDrawableBackground);

        gradientDrawableGradientButton = (GradientDrawable) buttonStart1.getBackground();
        gradientDrawableGradientButton.setColors(colors);
        //buttonStart1.setBackground(gradientDrawableGradientButton);

        gradientDrawableGradientButton = (GradientDrawable) buttonPause0.getBackground();
        gradientDrawableGradientButton.setColors(colors);
        //buttonPause0.setBackground(gradientDrawableGradientButton);

        gradientDrawableGradientButton = (GradientDrawable) buttonPause1.getBackground();
        gradientDrawableGradientButton.setColors(colors);
        //buttonPause1.setBackground(gradientDrawableGradientButton);

        gradientDrawableGradientButton = (GradientDrawable) buttonResume0.getBackground();
        gradientDrawableGradientButton.setColors(colors);
        //buttonResume0.setBackground(gradientDrawableGradientButton);

        gradientDrawableGradientButton = (GradientDrawable) buttonResume1.getBackground();
        gradientDrawableGradientButton.setColors(colors);
        //buttonResume1.setBackground(gradientDrawableGradientButton);

        gradientDrawableWhiteButton = (GradientDrawable) buttonStop0.getBackground();
        gradientDrawableWhiteButton.setColor(Color.WHITE);
        gradientDrawableWhiteButton.setStroke(4, ColorUtil.getProgressColor(localProcess, beginColorTop, endColorTop));
        //buttonStop0.setBackground(gradientDrawableWhiteButton);

        gradientDrawableWhiteButton = (GradientDrawable) buttonStop1.getBackground();
        gradientDrawableWhiteButton.setColor(Color.WHITE);
        gradientDrawableWhiteButton.setStroke(4, ColorUtil.getProgressColor(localProcess, beginColorTop, endColorTop));
        //buttonStop1.setBackground(gradientDrawableWhiteButton);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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

}
