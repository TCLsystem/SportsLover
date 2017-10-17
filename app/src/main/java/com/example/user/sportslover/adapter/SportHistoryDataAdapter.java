package com.example.user.sportslover.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.SportHistoryDataBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by user on 17-10-10.
 */

public class SportHistoryDataAdapter extends ArrayAdapter<SportHistoryDataBean> {

    private int resourceId;

    public SportHistoryDataAdapter(Context context, int resource, List<SportHistoryDataBean> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DecimalFormat textFormat1 = new DecimalFormat("#0.0");
        DecimalFormat textFormat2 = new DecimalFormat("#0");
        SportHistoryDataBean sportHistoryDataBean = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvHistoryMonth = (TextView) view.findViewById(R.id.tv_history_month);
            viewHolder.tvHistoryDay = (TextView) view.findViewById(R.id.tv_history_day);
            viewHolder.tvHistoryDistance = (TextView) view.findViewById(R.id.tv_history_distance);
            viewHolder.tvHistoryCalories = (TextView) view.findViewById(R.id.tv_history_calories);
            viewHolder.tvHistoryCumulativeTime = (TextView) view.findViewById(R.id.tv_history_cumulative_time);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvHistoryMonth.setText(sportHistoryDataBean.getMonth());
        viewHolder.tvHistoryDay.setText(sportHistoryDataBean.getDate());
        viewHolder.tvHistoryDistance.setText(textFormat1.format(sportHistoryDataBean.getDistance()/1000));
        viewHolder.tvHistoryCalories.setText(textFormat2.format(sportHistoryDataBean.getCalories()));
        viewHolder.tvHistoryCumulativeTime.setText(textFormat1.format(sportHistoryDataBean.getCumulativeTime()/3600));
        return view;
    }

    private class ViewHolder{
        TextView tvHistoryMonth;
        TextView tvHistoryDay;
        TextView tvHistoryDistance;
        TextView tvHistoryCalories;
        TextView tvHistoryCumulativeTime;
    }
}
