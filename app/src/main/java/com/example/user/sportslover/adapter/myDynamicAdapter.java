package com.example.user.sportslover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.model.SportModelInter;
import com.example.user.sportslover.model.UserModelImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 17-10-18.
 */

public class myDynamicAdapter  extends BaseAdapter {

    private List<DynamicItem> mDatas;
    private int mLayoutRes;
    private Context mContext;

    public myDynamicAdapter( int layoutRes, List<DynamicItem> datas) {
        mDatas = datas;
        mLayoutRes = layoutRes;
    }


    public List<DynamicItem> returnmDatas() {
        return this.mDatas;
    }

    public void addAll(List<DynamicItem> mDatas) {
        this.mDatas.addAll(mDatas);
    }

    public void setDatas(List<DynamicItem> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        myDynamicAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_dynamic_list, null);
            holder = new myDynamicAdapter.ViewHolder();
            holder.write_name = (TextView) convertView.findViewById(R.id.my_dynamic_writer_name);
            holder.show_dynamic_Name =(TextView) convertView.findViewById(R.id.my_dynamic_activity_Name);
            holder.show_dynamic_type = (TextView) convertView.findViewById(R.id.my_dynamic_Type);
            holder.show_startTime = (TextView)convertView.findViewById(R.id.my_dynamic_startTime);
            holder.show_location = (TextView)convertView.findViewById(R.id.my_dynamic_location);
            holder.show_participantCount=(TextView)convertView.findViewById(R.id.my_dynamic_participantCount);
            convertView.setTag(holder);
        } else {
            holder = (myDynamicAdapter.ViewHolder) convertView.getTag();
        }
        DynamicItem dynamicItem = mDatas.get(position);
        final myDynamicAdapter.ViewHolder viewHolder = holder;
        new UserModelImpl().getUser(dynamicItem.getWriter().getObjectId(), new SportModelInter.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                User user = (User) o;
                viewHolder.write_name.setText(user.getUserName());
            }
            @Override
            public void getFailure() {

            }
        });

        holder.show_dynamic_Name.setText(dynamicItem.getTitle());
        holder.show_dynamic_type.setText(dynamicItem.getSportsType());
        holder.show_startTime.setText(getTime(dynamicItem.getStartTime()));
        holder.show_location.setText(dynamicItem.getArea());
        holder.show_participantCount.setText(Integer.toString(dynamicItem.getParticipantName().size()));;
        return convertView;
    }

    private final class ViewHolder {
        TextView write_name;
        TextView show_dynamic_Name;
        TextView show_dynamic_type;
        TextView show_startTime;
        TextView show_location;
        TextView show_participantCount;
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("YYYY.MM.dd  HH:SS");
        return format.format(date);
    }

}