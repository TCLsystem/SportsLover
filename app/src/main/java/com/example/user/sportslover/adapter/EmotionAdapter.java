package com.example.user.sportslover.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-10-12.
 */

public class EmotionAdapter extends PagerAdapter {
    private List<View> mViews = new ArrayList<>();

    public EmotionAdapter(List<View> list) {
        if (list != null && list.size() > 0) {
            mViews.clear();
            mViews.addAll(list);
        }
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }
}
