package com.example.user.sportslover.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Environment;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.example.user.sportslover.application.BaseApplication;
import com.example.user.sportslover.bean.RouteItem;
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.customview.CircularRingPercentageView;
import com.example.user.sportslover.customview.LongClickButton;
import com.example.user.sportslover.model.RouteControlImpr;
import com.example.user.sportslover.model.RouteControlInter;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.util.ToastUtil;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private BaseApplication baseApplication;
    private boolean backFromKilled = false;

    private UserLocal mUserLocal = new UserLocal();
    private UserModelImpl mUserModelImpl = new UserModelImpl();

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
    private List<LatLng> targetPoints = new ArrayList<LatLng>();
    private LatLng newPoint;
    private TextView tvSportType;
    private TextView tvDistance;
    private TextView tvHours;
    private TextView tvSportAveragePace;
    private TextView tvCalories;
    private ImageView ivLock0;
    private ImageView ivLock1;
    private ImageView ivUnlock0;
    private ImageView ivUnlock1;
    private ImageView ivMap;
    private ImageView ivSetting;
    private Button buttonPause0;
    private Button buttonResume0;
    private LongClickButton buttonStop0;
    private Button buttonTarget1;
    private Button buttonStart1;
    private Button buttonRoute1;
    private Button buttonPause1;
    private Button buttonResume1;
    private LongClickButton buttonStop1;
    private ImageView ivStrechMap;
    private RelativeLayout rlBeginSportLayout;
    private LinearLayout llBeginSportStatus;
    private RelativeLayout rlProgressBar;
    private RelativeLayout rlCount3;
    private RelativeLayout rlCount2;
    private RelativeLayout rlCount1;
    private RelativeLayout rlCount0;

    private Timer timer = new Timer();
    private TimerTask task;
    private TimerTask task2;
    private int gSeconds = 0;
    private long remoteSeconds = 0;
    private int averagePace= 0;
    private boolean timerValidFlag = false;
    private long startTime;
    private CircularRingPercentageView progressCircle;
    private String mapId = null;
    private float target = 0;
    private float weight = 60;
    private int localProgress = 0;
    private CalculateCaloriesInter calculateCaloriesInter;
    private DecimalFormat textFormat = new DecimalFormat("#0.00");
    private DecimalFormat timeFormat = new DecimalFormat("#0.0");
    private MyVerticalViewPager myVerticalViewPager;
    private Intent intentSetSportTarget;
    private SportTrackService.SportTrackServiceControlBinder sportTrackServiceControlBinder;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
        String html;
        switch (msg.what){
            case 1:
                baiduMap.clear();
                if (sportTrackServiceControlBinder.getCurrentPoint() != null){
                    remoteLocate(sportTrackServiceControlBinder.getCurrentPoint());
                }
                //OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(points);
                remotePoints = sportTrackServiceControlBinder.getPoints();

                if (targetPoints.size() >= 2){
                    OverlayOptions ooPolylineTarget = new PolylineOptions().width(15).color(0xAA00ff00).points(targetPoints);
                    baiduMap.addOverlay(ooPolylineTarget);
                }

                if (remotePoints.size() > 2){
                    OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(remotePoints);
                    baiduMap.addOverlay(ooPolyline);
                }

                mLocationClient.requestLocation();
                remoteSeconds = sportTrackServiceControlBinder.getSeconds();
                html = "<big><big><big>" + timeFormat.format((float)remoteSeconds/60.0/60.0) +"</big></big></big> h<br>Cumulative<br>time";
                tvHours.setText(Html.fromHtml(html));
                remoteDistance = sportTrackServiceControlBinder.getDistance();
                if (target > 1){
                    html = "<big><big><big><big><big>" + textFormat.format(remoteDistance/1000f) + "</big></big>  km</big></big></big><br>in "+ textFormat.format(target/1000) +"km<br>Totol mileages";
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
                        html = "<big><big><big>" + timeFormat.format(calculateCaloriesInter.calculateCalories(weight, remoteSeconds, averagePace)) +"</big></big></big>KCAL<br>Calories";
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
                    refleshBackgroundColors(localProgress, (remoteDistance/target>1)?100f:((float)remoteDistance/target*100f));
                } else {
                    refleshBackgroundColors(localProgress, 0);
                    progressCircle.setProgress(100f);
                }
                break;
            case 2:
                rlProgressBar.setVisibility(View.VISIBLE);
                timerValidFlag = false;
                sportTrackServiceControlBinder.pauseService();
                Intent stopSportTrackService = new Intent(BeginSportActivity.this, SportTrackService.class);
                stopService(stopSportTrackService);
                task.cancel();
                timer.cancel();
                buttonStart1.setVisibility(View.VISIBLE);
                buttonPause0.setVisibility(View.GONE);
                buttonPause1.setVisibility(View.GONE);
                buttonResume0.setVisibility(View.GONE);
                buttonResume1.setVisibility(View.GONE);
                buttonStop0.setVisibility(View.GONE);
                buttonStop1.setVisibility(View.GONE);
                sportInformationItem.setAveragePace(averagePace);
                sportInformationItem.setCalories(calculateCaloriesInter.calculateCalories(weight, remoteSeconds, averagePace));
                sportInformationItem.setCumulativeTime(remoteSeconds);
                sportInformationItem.setSportProgress((remoteDistance/target > 1 || target < 100)?100f:((float)remoteDistance/target*100f));
                sportInformationItem.setTotalMileages(remoteDistance);
                sportInformationItem.setPoints(remotePoints);
                sportInformationItem.setStartTime(startTime);
                if (target >= 100){
                    refleshFinishColors(0, (remoteDistance/target>1)?100f:((float)remoteDistance/target*100f));
                } else {
                    refleshFinishColors(0, 0);
                }

                /*List<LatLng> fake = new ArrayList<>();
                fake.add(new LatLng(23.035529,114.357632));
                fake.add(new LatLng(23.135529,114.357632));
                fake.add(new LatLng(23.235529,114.457632));
                fake.add(new LatLng(23.335529,114.357632));
                fake.add(new LatLng(23.035529,114.357632));
                double fakedistance=0;
                for (int i=0;i<fake.size()-1;i++){
                    fakedistance += MapUtil.gps2m(fake.get(i).latitude, fake.get(i).longitude, fake.get(i+1).latitude, fake.get(i+1).longitude);
                }*/
                baiduMap.clear();
                if (remotePoints.size()>1){
                    OverlayOptions ooPolylineTarget = new PolylineOptions().width(15).color(0xAA00ff00).points(remotePoints);
                    baiduMap.addOverlay(ooPolylineTarget);
                }
                //autoZoom(fake);

                autoZoom(remotePoints);
                baiduMap.setMyLocationEnabled(false);
                handler.sendEmptyMessageDelayed(10, 2000);
                break;
            case 3:
                localProgress += 15;
                if (localProgress < 100){
                    if (target >= 100){
                        refleshFinishColors(localProgress, (remoteDistance/target>1)?100f:((float)remoteDistance/target*100f));
                    } else {
                        refleshFinishColors(localProgress, 0);
                    }
                }else {
                    handler.sendEmptyMessageDelayed(2, 50);
                }
                break;
            case 4:
                if (localProgress > 3)
                    localProgress -= 3;
                else
                    localProgress = 0;
                if (localProgress < 100) {
                    if (target >= 100) {
                        refleshFinishColors(localProgress, (remoteDistance / target > 1) ? 100f : ((float) remoteDistance / target * 100f));
                    } else {
                        refleshFinishColors(localProgress, 0);
                    }
                }
                break;
            case 5:
                rlCount3.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(6, 1000);
                break;
            case 6:
                rlCount2.setVisibility(View.VISIBLE);
                rlCount3.setVisibility(View.GONE);
                handler.sendEmptyMessageDelayed(7, 1000);
                break;
            case 7:
                rlCount1.setVisibility(View.VISIBLE);
                rlCount2.setVisibility(View.GONE);
                handler.sendEmptyMessageDelayed(8, 1000);
                break;
            case 8:
                rlCount0.setVisibility(View.VISIBLE);
                rlCount1.setVisibility(View.GONE);
                handler.sendEmptyMessageDelayed(9, 1000);
                break;
            case 9:
                startTime = System.currentTimeMillis();
                rlCount0.setVisibility(View.GONE);
                timerValidFlag = true;
                myVerticalViewPager.setCurrentItem(0);
                currPage = 0;
                sportTrackServiceControlBinder.setSportType(sportType);
                sportTrackServiceControlBinder.startService();
                llBeginSportStatus.setVisibility(View.VISIBLE);
                buttonTarget1.setVisibility(View.GONE);
                ivLock1.setVisibility(View.VISIBLE);
                buttonRoute1.setVisibility(View.INVISIBLE);
                buttonStart1.setVisibility(View.GONE);
                buttonPause0.setVisibility(View.VISIBLE);
                buttonPause1.setVisibility(View.VISIBLE);
                ivStrechMap.setVisibility(View.VISIBLE);
                myVerticalViewPager.setScrollable(true);
                break;
            case 10:
                mapView.getMap().snapshot(new BaiduMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        //sportInformationItem.setBitmap(bitmap);
                        FileOutputStream out;
                        try {
                            out = openFileOutput("test.png", MODE_PRIVATE);
                            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                                out.flush();
                                out.close();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent1 = new Intent(BeginSportActivity.this, FinishSportActivity.class);
                        intent1.putExtra("sport_information", sportInformationItem);
                        startActivity(intent1);
                        rlProgressBar.setVisibility(View.GONE);
                        finish();
                    }
                });
            default:
                break;
        }
        }

    };
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sportTrackServiceControlBinder = (SportTrackService.SportTrackServiceControlBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    private SportInformationItem sportInformationItem = new SportInformationItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); setContentView(R.layout.main);
        intentSetSportTarget = new Intent(this, SetSportTargetActivity.class);
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
        baseApplication = (BaseApplication) getApplicationContext();
        mUserLocal = mUserModelImpl.getUserLocal();
        mUserLocal.setWeight("60");
        if (mUserLocal != null){
            try{
                weight = Float.parseFloat(mUserLocal.getWeight());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        tvSportType = (TextView) findViewById(R.id.sport_type);
        view0 = getLayoutInflater().inflate(R.layout.item_cicular_viewpager, null);
        view1 = getLayoutInflater().inflate(R.layout.item_baidu_map_viewpager, null);
        pageview =new ArrayList<View>();
        pageview.add(view0);
        pageview.add(view1);
        rlCount0 = (RelativeLayout) findViewById(R.id.rl_begin_sport_count_down_go);
        rlCount1 = (RelativeLayout) findViewById(R.id.rl_begin_sport_count_down_one);
        rlCount2 = (RelativeLayout) findViewById(R.id.rl_begin_sport_count_down_two);
        rlCount3 = (RelativeLayout) findViewById(R.id.rl_begin_sport_count_down_three);
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
        ivLock0 = (ImageView) view0.findViewById(R.id.iv_lock0);
        ivLock1 = (ImageView) view1.findViewById(R.id.iv_lock1);
        ivUnlock0 = (ImageView) view0.findViewById(R.id.iv_unlock0);
        ivUnlock1 = (ImageView) view1.findViewById(R.id.iv_unlock1);
        ivMap = (ImageView) view0.findViewById(R.id.cicular_view_show_map);
        ivStrechMap = (ImageView) view1.findViewById(R.id.iv_strech_map);
        ivSetting = (ImageView) findViewById(R.id.begin_sport_setting);
        buttonPause0 = (Button) view0.findViewById(R.id.pause0);
        buttonResume0 = (Button) view0.findViewById(R.id.resume0);
        buttonStop0 = (LongClickButton) view0.findViewById(R.id.stop0);
        buttonTarget1 = (Button) view1.findViewById(R.id.target1);
        buttonStart1 = (Button) view1.findViewById(R.id.start1);
        buttonRoute1 = (Button) view1.findViewById(R.id.route1);
        buttonPause1 = (Button) view1.findViewById(R.id.pause1);
        buttonResume1 = (Button) view1.findViewById(R.id.resume1);
        buttonStop1 = (LongClickButton) view1.findViewById(R.id.stop1);
        ivLock0.setOnClickListener(this);
        ivLock1.setOnClickListener(this);
        ivUnlock0.setOnClickListener(this);
        ivUnlock1.setOnClickListener(this);
        ivMap.setOnClickListener(this);
        ivStrechMap.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        buttonPause0.setOnClickListener(this);
        buttonResume0.setOnClickListener(this);
        buttonStop0.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
            @Override
            public void repeatAction() {
                handler.sendEmptyMessage(3);
            }
        });
        buttonTarget1.setOnClickListener(this);
        buttonStart1.setOnClickListener(this);
        buttonRoute1.setOnClickListener(this);
        buttonPause1.setOnClickListener(this);
        buttonResume1.setOnClickListener(this);
        buttonStop1.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
            @Override
            public void repeatAction() {
                handler.sendEmptyMessage(3);
            }
        });

        rlBeginSportLayout = (RelativeLayout) findViewById(R.id.begin_sport_layout);
        rlProgressBar = (RelativeLayout) findViewById(R.id.rl_begin_sport_progress_bar);
        rlProgressBar.setVisibility(View.GONE);
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
        refleshBackgroundColors(0, 0);
        Intent intent = getIntent();
        sportType = intent.getIntExtra("sport_type", -1);
        switch (sportType){
            case 0:
                tvSportType.setText("Running");
                calculateCaloriesInter = new CalculateCaloriesRunningImpl();
                intentSetSportTarget.putExtra("sport_type", 0);
                sportInformationItem.setSportType("Running");
                break;
            case 1:
                tvSportType.setText("Walking");
                calculateCaloriesInter = new CalculateCaloriesWalkingImpl();
                intentSetSportTarget.putExtra("sport_type", 1);
                sportInformationItem.setSportType("Walking");
                break;
            case 2:
                tvSportType.setText("Riding");
                calculateCaloriesInter = new CalculateCaloriesRidingImpl();
                intentSetSportTarget.putExtra("sport_type", 2);
                sportInformationItem.setSportType("Riding");
                break;
            default:
                Log.d("Begin Sport", "Invalid sport type");
                break;
        }
        Intent startSportTrackService = new Intent(this, SportTrackService.class);
        startService(startSportTrackService);
        startTimer();
        if (backFromKilled){
            if (mapId != null){
                RouteControlInter routeFindInter = new RouteControlImpr();
                routeFindInter.findRouteById(BeginSportActivity.this, mapId, new RouteControlImpr.OnRouteFindListener() {
                    @Override
                    public void onSuccess(List<RouteItem> routeItemList) {
                        targetPoints = routeItemList.get(0).getSportsPath();
                        target = (float) routeItemList.get(0).getDistance();
                    }
                });
            }
            timerValidFlag = true;
            myVerticalViewPager.setCurrentItem(0);
            currPage = 0;
            sportTrackServiceControlBinder.startService();
            llBeginSportStatus.setVisibility(View.VISIBLE);
            buttonTarget1.setVisibility(View.GONE);
            ivLock1.setVisibility(View.VISIBLE);
            buttonRoute1.setVisibility(View.INVISIBLE);
            buttonStart1.setVisibility(View.GONE);
            buttonPause0.setVisibility(View.VISIBLE);
            buttonPause1.setVisibility(View.VISIBLE);
            ivStrechMap.setVisibility(View.VISIBLE);
            myVerticalViewPager.setScrollable(true);
        }
    }

    private void remoteLocate(LatLng point){
        MyLocationConfiguration mConfig;
        MyLocationData locationData = new MyLocationData.Builder()
                .direction(mDirection).latitude(point.latitude)
                .longitude(point.longitude).build();
        baiduMap.setMyLocationData(locationData);
        if (baseApplication.getGlobalSportMapSetting() == 1)
            mConfig = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, null);
        else
            mConfig = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
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
        MyLocationConfiguration mConfig;
        MyLocationData locationData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(mDirection).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        baiduMap.setMyLocationData(locationData);
        if (baseApplication.getGlobalSportMapSetting() == 1)
            mConfig = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, null);
        else
            mConfig = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
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
    public void onBackPressed() {
        super.onBackPressed();
        sportTrackServiceControlBinder.pauseService();
        Intent stopSportTrackService = new Intent(BeginSportActivity.this, SportTrackService.class);
        stopService(stopSportTrackService);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK)
                    target = data.getFloatExtra("target_return", 0f);
                break;
            case 2:
                if (resultCode == RESULT_OK){
                    mapId = data.getStringExtra("route_return");
                    if (mapId != null){
                        /*RouteItem routeItem = routeFindInter.findRouteById(id);
                        targetPoints = routeItem.getSportsPath();
                        double toStartPoint = MapUtil.gps2m(sportTrackServiceControlBinder.getCurrentPoint().latitude,
                                sportTrackServiceControlBinder.getCurrentPoint().longitude,
                                targetPoints.get(0).latitude, targetPoints.get(0).longitude);
                        double toStartPoint = 0;
                        target = (float) (toStartPoint + routeItem.getDistance());*/
                        RouteControlInter routeFindInter = new RouteControlImpr();
                        routeFindInter.findRouteById(BeginSportActivity.this, mapId, new RouteControlImpr.OnRouteFindListener() {
                            @Override
                            public void onSuccess(List<RouteItem> routeItemList) {
                                targetPoints = routeItemList.get(0).getSportsPath();
                                target = (float) routeItemList.get(0).getDistance();
                            }
                        });
                    }
                }
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.begin_sport_setting:
                Intent intent = new Intent(this, SportSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_lock0:
            case R.id.iv_lock1:
                ivLock0.setVisibility(View.INVISIBLE);
                ivLock1.setVisibility(View.GONE);
                buttonPause0.setVisibility(View.GONE);
                buttonPause1.setVisibility(View.GONE);
                ivUnlock0.setVisibility(View.VISIBLE);
                ivUnlock1.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_unlock0:
            case R.id.iv_unlock1:
                ivLock0.setVisibility(View.VISIBLE);
                ivLock1.setVisibility(View.VISIBLE);
                buttonPause0.setVisibility(View.VISIBLE);
                buttonPause1.setVisibility(View.VISIBLE);
                ivUnlock0.setVisibility(View.GONE);
                ivUnlock1.setVisibility(View.GONE);
                break;
            case R.id.iv_strech_map:
                myVerticalViewPager.setCurrentItem(0);
                break;
            case R.id.cicular_view_show_map:
                myVerticalViewPager.setCurrentItem(1);
                break;
            case R.id.target1:
                startActivityForResult(intentSetSportTarget, 1);
                break;
            case R.id.start1:
                handler.sendEmptyMessage(5);
                break;
            case R.id.route1:
                Intent intent1 = new Intent(this, SportRouteSettingActivity.class);
                intent1.putExtra("current_position", sportTrackServiceControlBinder.getCurrentPoint());
                startActivityForResult(intent1, 2);
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
                ivLock0.setVisibility(View.GONE);
                ivLock1.setVisibility(View.GONE);
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
                ivLock0.setVisibility(View.VISIBLE);
                ivLock1.setVisibility(View.VISIBLE);
                ivMap.setVisibility(View.VISIBLE);
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

        if (task2 == null) {
            task2 = new TimerTask() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Message message = new Message();
                    message.what = 4;
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

        if (timer != null && task2 != null) {
            try {
                //task.cancel();
                timer.schedule(task2, 100, 100);
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
    private void refleshBackgroundColors(int finishProgress, float localProcess){

        int beginColorTop = 0xff5bc0e5;
        int beginColorBottom = 0xff6ee4bc;
        int endColorTop = 0xffff967a;
        int endColorBottom = 0xfffcde6c;

        GradientDrawable gradientDrawableBackground;
        GradientDrawable gradientDrawableGradientButton;

        @ColorInt int colors[] = new int[] {ColorUtil.getProgressColor(localProcess, beginColorTop, endColorTop),
                ColorUtil.getProgressColor(localProcess, beginColorBottom, endColorBottom)};

        gradientDrawableBackground = (GradientDrawable) rlBeginSportLayout.getBackground();
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
        refleshFinishColors(finishProgress, localProcess);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void refleshFinishColors(int finishProgress, float localProcess){

        int beginColorTop = 0xff5bc0e5;
        int beginColorBottom = 0xff6ee4bc;
        int endColorTop = 0xffff967a;
        int endColorBottom = 0xfffcde6c;


        @ColorInt int colors[] = new int[] {ColorUtil.getProgressColor(localProcess, beginColorTop, endColorTop),
                ColorUtil.getProgressColor(localProcess, beginColorBottom, endColorBottom)};

        @ColorInt int colors2[] = new int[] {ColorUtil.getProgressColor(finishProgress, Color.WHITE, colors[0]),
                ColorUtil.getProgressColor(finishProgress, Color.WHITE, colors[1])};

        GradientDrawable gradientDrawableWhiteButton;

        gradientDrawableWhiteButton = (GradientDrawable) buttonStop0.getBackground();
        gradientDrawableWhiteButton.setColors(colors2);
        gradientDrawableWhiteButton.setStroke(4, ColorUtil.getProgressColor(localProcess, beginColorTop, endColorTop));
        //buttonStop0.setBackground(gradientDrawableWhiteButton);

        gradientDrawableWhiteButton = (GradientDrawable) buttonStop1.getBackground();
        gradientDrawableWhiteButton.setColors(colors2);
        gradientDrawableWhiteButton.setStroke(4, ColorUtil.getProgressColor(localProcess, beginColorTop, endColorTop));
        //buttonStop1.setBackground(gradientDrawableWhiteButton);

        gradientDrawableWhiteButton = (GradientDrawable) ivLock1.getBackground();
        gradientDrawableWhiteButton.setStroke(4, ColorUtil.getProgressColor(localProcess, beginColorTop, endColorTop));
        //buttonStop0.setBackground(gradientDrawableWhiteButton);

        gradientDrawableWhiteButton = (GradientDrawable) ivUnlock0.getBackground();
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

    private void autoZoom(List<LatLng> points){
        if(points.size() > 0){
            double maxLng = points.get(0).longitude;
            double minLng = points.get(0).longitude;
            double maxLat = points.get(0).latitude;
            double minLat = points.get(0).latitude;
            LatLng res;
            for (int i = points.size() - 1; i >= 0; i--) {
                res = points.get(i);
                if(res.longitude > maxLng) maxLng =res.longitude;
                if(res.longitude < minLng) minLng =res.longitude;
                if(res.latitude > maxLat) maxLat =res.latitude;
                if(res.latitude < minLat) minLat =res.latitude;
            };
            double cenLng =(maxLng+minLng)/2;
            double cenLat = (maxLat+minLat)/2;
            float zoom = getZoom(maxLng, minLng, maxLat, minLat);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(new LatLng(cenLat, cenLng)).zoom(zoom);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    private float getZoom (double maxLng, double minLng, double maxLat, double minLat) {
        float zoom[] = {50,100,200,500,1000,2000,5000,10000,20000,25000,50000,100000,200000,500000,1000000,2000000};//级别18到3。
        double distance = MapUtil.gps2m(maxLat, maxLng, minLat, minLng);  //获取两点距离,保留小数点后两位
        for (int i = 0,zoomLen = zoom.length; i < zoomLen; i++) {
            if(zoom[i] - distance > 0){
                return (float) 18-i+3;//之所以会多3，是因为地图范围常常是比例尺距离的10倍以上。所以级别会增加3。
            }
        }
        return 3;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putFloat("Target", target);
        outState.putString("MapId", mapId);
        outState.putLong("StartTime", startTime);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        target = savedInstanceState.getFloat("Target");
        mapId = savedInstanceState.getString("MapId", null);
        startTime = savedInstanceState.getLong("StartTime");
        backFromKilled = true;
    }
}
