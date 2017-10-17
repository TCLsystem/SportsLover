package com.example.user.sportslover.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.sportslover.R;

import java.util.ArrayList;
import java.util.List;

public class VPAdapter extends PagerAdapter {
    private Context context;
    private List<Integer> imgs;

    public VPAdapter(Context context) {
        this.context = context;
        imgs = new ArrayList<>();

        imgs.add(R.mipmap.startpage1);
        imgs.add(R.mipmap.startpage2);
        imgs.add(R.mipmap.startpage3);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.vp_layout_item, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_item);
        iv.setImageResource(imgs.get(position % 3));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
