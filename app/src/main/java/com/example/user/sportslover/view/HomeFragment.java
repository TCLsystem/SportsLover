package com.example.user.sportslover.view;

import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sportslover.MainActivity;
import com.example.user.sportslover.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener,HomeView {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.mainViewPager);
        view0 = inflater.inflate(R.layout.viewpager0, null);
        view1 = inflater.inflate(R.layout.viewpager1, null);
        view2 = inflater.inflate(R.layout.viewpager2, null);
        TextView textViewvp0 = (TextView) view.findViewById(R.id.viewpager0);
        TextView textViewvp1= (TextView) view.findViewById(R.id.viewpager1);
        TextView textViewvp2 = (TextView) view.findViewById(R.id.viewpager2);
        scrollbar = (ImageView) view.findViewById(R.id.scrollbar);
        textViewvp0.setOnClickListener(this);
        textViewvp1.setOnClickListener(this);
        textViewvp2.setOnClickListener(this);
        pageview =new ArrayList<View>();
        pageview.add(view0);
        pageview.add(view1);
        pageview.add(view2);
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
        return view;
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
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
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
        view0.findViewById(R.id.weather_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                startActivity(intent);
            }
        });
        view0.findViewById(R.id.begin_to_run).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BaiduMapActivity.class);
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
}
