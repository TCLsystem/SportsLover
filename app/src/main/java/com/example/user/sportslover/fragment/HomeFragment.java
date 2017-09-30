package com.example.user.sportslover.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.example.user.sportslover.R;
import com.example.user.sportslover.activity.BeginSportActivity;
import com.example.user.sportslover.activity.WeatherActivity;
import com.example.user.sportslover.activity.WeatherView;
import com.example.user.sportslover.application.BaseApplication;
import com.example.user.sportslover.bean.Weather;
import com.example.user.sportslover.customview.CircularRingPercentageView;
import com.example.user.sportslover.presenter.HomeRidingPresenterImpr;
import com.example.user.sportslover.presenter.HomeRunningPresenterImpr;
import com.example.user.sportslover.presenter.HomeWalkingPresenterImpr;
import com.example.user.sportslover.presenter.WeatherPresenterImpl;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener, HomeView, WeatherView {

    private ViewPager viewPager;
    private ArrayList<View> pageview;
    private ImageView scrollbar;
    private int offset = 0;
    private int one;
    private int two;
    private int currIndex = 0;
    private View view0;
    private View view1;
    private View view2;
    private TextView textViewvp0;
    private TextView textViewvp1;
    private TextView textViewvp2;
    private TextView tvRuningTotalMileages;
    private TextView tvRuningCumulativeTime;
    private TextView tvRuningAveragePace;
    private TextView tvRuningCumulativeNumber;
    private TextView tvWalkingTotalMileages;
    private TextView tvWalkingCumulativeTime;
    private TextView tvWalkingAveragePace;
    private TextView tvWalkingCumulativeNumber;
    private TextView tvRidingTotalMileages;
    private TextView tvRidingCumulativeTime;
    private TextView tvRidingAveragePace;
    private TextView tvRidingCumulativeNumber;
    private TextView tvRunningWeatherCondition;
    private TextView tvWalkingWeatherCondition;
    private TextView tvRidingWeatherCondition;
    private ImageView ivRunningWeatherIcon;
    private ImageView ivWalkingWeatherIcon;
    private ImageView ivRidingWeatherIcon;
    private CircularRingPercentageView progressCircleRunning;
    private CircularRingPercentageView progressCircleWalking;
    private CircularRingPercentageView progressCircleRiding;
    private HomeRunningPresenterImpr homeRunningPresenterImpr;
    private HomeWalkingPresenterImpr homeWalkingPresenterImpr;
    private HomeRidingPresenterImpr homeRidingPresenterImpr;

    private LocationClient locationClient;
    private WeatherPresenterImpl weatherPresenterImpl;
    private BaseApplication baseApplication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.mainViewPager);
        view0 = inflater.inflate(R.layout.item_home_running_viewpager, null);
        view1 = inflater.inflate(R.layout.item_home_walking_view_pager, null);
        view2 = inflater.inflate(R.layout.item_home_riding_viewpager, null);
        textViewvp0 = (TextView) view.findViewById(R.id.viewpager0);
        textViewvp1= (TextView) view.findViewById(R.id.viewpager1);
        textViewvp2 = (TextView) view.findViewById(R.id.viewpager2);
        scrollbar = (ImageView) view.findViewById(R.id.scrollbar);
        textViewvp0.setOnClickListener(this);
        textViewvp1.setOnClickListener(this);
        textViewvp2.setOnClickListener(this);
        homeRunningPresenterImpr = new HomeRunningPresenterImpr();
        tvRunningWeatherCondition = (TextView) view0.findViewById(R.id.tv_weather_running_condition);
        tvRuningTotalMileages = (TextView) view0.findViewById(R.id.tv_home_running_basic);
        tvRuningCumulativeTime = (TextView) view0.findViewById(R.id.tv_home_running_cumulative_time);
        tvRuningAveragePace = (TextView) view0.findViewById(R.id.tv_home_running_average_pace);
        tvRuningCumulativeNumber = (TextView) view0.findViewById(R.id.tv_home_running_cumulative_number);
        ivRunningWeatherIcon = (ImageView) view0.findViewById(R.id.iv_home_running_weather_icon);
        homeWalkingPresenterImpr = new HomeWalkingPresenterImpr();
        tvWalkingWeatherCondition = (TextView) view1.findViewById(R.id.tv_weather_walking_condition);
        tvWalkingTotalMileages = (TextView) view1.findViewById(R.id.tv_home_walking_basic);
        tvWalkingCumulativeTime = (TextView) view1.findViewById(R.id.tv_home_walking_cumulative_time);
        tvWalkingAveragePace = (TextView) view1.findViewById(R.id.tv_home_walking_average_pace);
        tvWalkingCumulativeNumber = (TextView) view1.findViewById(R.id.tv_home_walking_cumulative_number);
        ivWalkingWeatherIcon = (ImageView) view1.findViewById(R.id.iv_home_walking_weather_icon);
        homeRidingPresenterImpr = new HomeRidingPresenterImpr();
        tvRidingWeatherCondition = (TextView) view2.findViewById(R.id.tv_weather_riding_condition);
        tvRidingTotalMileages = (TextView) view2.findViewById(R.id.tv_home_riding_basic);
        tvRidingCumulativeTime = (TextView) view2.findViewById(R.id.tv_home_riding_cumulative_time);
        tvRidingAveragePace = (TextView) view2.findViewById(R.id.tv_home_riding_average_pace);
        tvRidingCumulativeNumber = (TextView) view2.findViewById(R.id.tv_home_riding_cumulative_number);
        ivRidingWeatherIcon = (ImageView) view2.findViewById(R.id.iv_home_riding_weather_icon);
        refleshRidingTextViews();
        refleshWalkingTextViews();
        refleshRunningTextViews();
        pageview =new ArrayList<View>();
        pageview.add(view0);
        pageview.add(view1);
        pageview.add(view2);
        progressCircleRunning = (CircularRingPercentageView) view0.findViewById(R.id.home_running_progress);
        progressCircleRunning.setRoundWidth(15);
        progressCircleRunning.setProgress(75f);
        progressCircleWalking = (CircularRingPercentageView) view1.findViewById(R.id.home_walking_progress);
        progressCircleWalking.setRoundWidth(15);
        progressCircleWalking.setProgress(75f);
        progressCircleRiding = (CircularRingPercentageView) view2.findViewById(R.id.home_riding_progress);
        progressCircleRiding.setRoundWidth(15);
        progressCircleRiding.setProgress(75f);
        PagerAdapter mPagerAdapter = new PagerAdapter(){

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return pageview.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0==arg1;
            }

            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageview.get(arg1));
            }

            public Object instantiateItem(View arg0, int arg1){
                ((ViewPager)arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }
        };

        viewPager.setAdapter(mPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        int bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.bar).getWidth();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenW = displayMetrics.widthPixels;
        offset = (screenW / 3 - bmpW) / 2;
        one = offset * 2 + bmpW;
        two = one * 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        scrollbar.setImageMatrix(matrix);
        viewPagerClickInit();
        baseApplication = (BaseApplication) getActivity().getApplicationContext();
        weatherPresenterImpl = new WeatherPresenterImpl(this);
        locationClient = new LocationClient(getActivity().getApplicationContext());
        BDAbstractLocationListener myListener = new BDAbstractLocationListener(){
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                        || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                    getWeatherFromLocation(bdLocation);
                }
            }
        };
        locationClient.registerLocationListener(myListener);
        locationClient.start();
        return view;
    }

    private void getWeatherFromLocation(BDLocation bdLocation) {
        weatherPresenterImpl.requestWeather(bdLocation.getLongitude(), bdLocation.getLatitude());
    }

    @Override
    public void showResponse(final Weather weather) {
        baseApplication.setGlobalWeather(weather);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ("ok".equals(weather.status)){
                    refleshWeatherCondition();}
            }
        });
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    refleshRunningTextViews();
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    refleshWalkingTextViews();
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    refleshRidingTextViews();
                    break;
                default:
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(200);
            scrollbar.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.viewpager0:
                switch2ViewPager0();
                break;
            case R.id.viewpager1:
                switch2ViewPager1();
                break;
            case R.id.viewpager2:
                switch2ViewPager2();
                break;
            default:
                break;
        }
    }

    private void viewPagerClickInit(){
        view0.findViewById(R.id.fl_home_running_weather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baseApplication.getGlobalWeather() != null){
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getContext(), "Weather Information Invalid. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view1.findViewById(R.id.fl_home_walking_weather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baseApplication.getGlobalWeather() != null){
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getContext(), "Weather Information Invalid. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view2.findViewById(R.id.fl_home_riding_weather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baseApplication.getGlobalWeather() != null){
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getContext(), "Weather Information Invalid. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view0.findViewById(R.id.fl_start_running).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BeginSportActivity.class);
                intent.putExtra("sport_type", 0);
                startActivity(intent);
            }
        });
        view1.findViewById(R.id.fl_start_walking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BeginSportActivity.class);
                intent.putExtra("sport_type", 1);
                startActivity(intent);
            }
        });
        view2.findViewById(R.id.fl_start_riding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BeginSportActivity.class);
                intent.putExtra("sport_type", 2);
                startActivity(intent);
            }
        });
    }

    @Override
    public void switch2ViewPager0(){
        viewPager.setCurrentItem(0);
    }

    @Override
    public void switch2ViewPager1(){
        viewPager.setCurrentItem(1);
    }

    @Override
    public void switch2ViewPager2(){
        viewPager.setCurrentItem(2);
    }

    private void refleshRunningTextViews(){
        String html;
        textViewvp0.setTextColor(0xff000000);
        textViewvp1.setTextColor(0xff848484);
        textViewvp2.setTextColor(0xff848484);
        html = "Today<br><big><big><big><big><big>" + homeRunningPresenterImpr.loadTotalMileages() + "</big></big></big></big></big>  km<br>Totol mileages";
        tvRuningTotalMileages.setText(Html.fromHtml(html));
        html = "<big><big><big>" + homeRunningPresenterImpr.loadComulativeTime() +"</big></big></big> h<br>Cumulative<br>time";
        tvRuningCumulativeTime.setText(Html.fromHtml(html));
        html = "<big><big><big>" + homeRunningPresenterImpr.loadAveragePace()/60 + "’</big>" + homeRunningPresenterImpr.loadAveragePace()%60 + "”" +"</big></big><br>Average<br>pace";
        tvRuningAveragePace.setText(Html.fromHtml(html));
        html = "<big><big><big>" + homeRunningPresenterImpr.loadComulativeNumber() +"</big></big></big><br>Cumulative<br>number";
        tvRuningCumulativeNumber.setText(Html.fromHtml(html));
    }

    private void refleshWalkingTextViews(){
        String html;
        textViewvp0.setTextColor(0xff848484);
        textViewvp1.setTextColor(0xff000000);
        textViewvp2.setTextColor(0xff848484);
        html = "Today<br><big><big><big><big><big>" + homeWalkingPresenterImpr.loadTotalMileages() + "</big></big></big></big></big>  km<br>Totol mileages";
        tvWalkingTotalMileages.setText(Html.fromHtml(html));
        html = "<big><big><big>" + homeWalkingPresenterImpr.loadComulativeTime() +"</big></big></big> h<br>Cumulative<br>time";
        tvWalkingCumulativeTime.setText(Html.fromHtml(html));
        html = "<big><big><big>" + homeWalkingPresenterImpr.loadAveragePace()/60 + "’</big>" + homeWalkingPresenterImpr.loadAveragePace()%60 + "”" +"</big></big><br>Average<br>pace";
        tvWalkingAveragePace.setText(Html.fromHtml(html));
        html = "<big><big><big>" + homeWalkingPresenterImpr.loadComulativeNumber() +"</big></big></big><br>Cumulative<br>number";
        tvWalkingCumulativeNumber.setText(Html.fromHtml(html));
    }

    private void refleshRidingTextViews(){
        String html;
        textViewvp0.setTextColor(0xff848484);
        textViewvp1.setTextColor(0xff848484);
        textViewvp2.setTextColor(0xff000000);
        html = "Today<br><big><big><big><big><big>" + homeRidingPresenterImpr.loadTotalMileages() + "</big></big></big></big></big>  km<br>Totol mileages";
        tvRidingTotalMileages.setText(Html.fromHtml(html));
        html = "<big><big><big>" + homeRidingPresenterImpr.loadComulativeTime() +"</big></big></big> h<br>Cumulative<br>time";
        tvRidingCumulativeTime.setText(Html.fromHtml(html));
        html = "<big><big><big>" + homeRidingPresenterImpr.loadAveragePace()/60 + "’</big>" + homeRidingPresenterImpr.loadAveragePace()%60 + "”" +"</big></big><br>Average<br>pace";
        tvRidingAveragePace.setText(Html.fromHtml(html));
        html = "<big><big><big>" + homeRidingPresenterImpr.loadComulativeNumber() +"</big></big></big><br>Cumulative<br>number";
        tvRidingCumulativeNumber.setText(Html.fromHtml(html));
    }

    public void refleshWeatherCondition(){
        tvRunningWeatherCondition.setText(baseApplication.getGlobalWeather().now.temperature + "℃\n" + baseApplication.getGlobalWeather().now.more.info);
        tvWalkingWeatherCondition.setText(baseApplication.getGlobalWeather().now.temperature + "℃\n" + baseApplication.getGlobalWeather().now.more.info);
        tvRidingWeatherCondition.setText(baseApplication.getGlobalWeather().now.temperature + "℃\n" + baseApplication.getGlobalWeather().now.more.info);
    }
}
