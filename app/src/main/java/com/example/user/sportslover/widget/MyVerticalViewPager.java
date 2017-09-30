package com.example.user.sportslover.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.baidu.mapapi.map.MapView;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by user on 17-9-22.
 */

public class MyVerticalViewPager extends VerticalViewPager {

    private boolean scrollable = true;

    public MyVerticalViewPager(Context context) {
        super(context);
    }

    public MyVerticalViewPager(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setScrollable(boolean enable){
        scrollable = enable;
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dy, int x, int y) {
        if (v != this && v instanceof MapView)
            return true;
        return super.canScroll(v, checkV, dy, x, y);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (scrollable){
            return super.onInterceptTouchEvent(ev);
        }else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scrollable) {
            return super.onTouchEvent(ev);
        }else {
            return false;
        }
    }
}
