package com.example.user.sportslover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.customview.RoundImageView;
import com.example.user.sportslover.model.SportModelInter;
import com.example.user.sportslover.model.UserModelImpl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 17-9-19.
 */
public class DynamicAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<DynamicItem> mDatas;
    private int mLayoutRes;
    private Context mContext;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    public DynamicAdapter(Context context, int layoutRes, List<DynamicItem> datas) {
        this.mContext=context;
        this.mDatas = datas;
        this.mLayoutRes = layoutRes;
        mInflater = LayoutInflater.from(context);
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.xiaolian)
                .showImageForEmptyUri(R.drawable.xiaolian)
                .showImageOnFail(R.drawable.xiaolian).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutRes, null);
            holder = new ViewHolder();
            holder.write_photo = (RoundImageView) convertView.findViewById(R.id.write_photo);
            holder.write_name = (TextView) convertView.findViewById(R.id.write_name);
            holder.write_date = (TextView) convertView.findViewById(R.id.write_date);
            holder.dynamic_photo = (ImageView) convertView.findViewById(R.id.dynamic_photo);
            holder.show_dynamic_Name =(TextView) convertView.findViewById(R.id.show_activity_Name);
            holder.show_dynamic_type = (TextView) convertView.findViewById(R.id.show_Type);
       //     holder.dynamic_text = (TextView) convertView.findViewById(R.id.dynamic_text);
            holder.show_startTime = (TextView)convertView.findViewById(R.id.show_startTime);
        //    holder.show_endTime = (TextView) convertView.findViewById(R.id.show_endTime);
       //     holder.show_mile = (TextView)convertView.findViewById(R.id.show_meil);
            holder.show_location = (TextView)convertView.findViewById(R.id.show_location);
            holder.show_participantCount=(TextView)convertView.findViewById(R.id.show_participantCount);
      //      holder.show_participant = (TextView)convertView.findViewById(R.id.show_participant);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DynamicItem dynamicItem = mDatas.get(position);
        final ViewHolder viewHolder = holder;
        new UserModelImpl().getUser(dynamicItem.getWriter().getObjectId(), new SportModelInter.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                User user = (User) o;
                imageLoader.displayImage(user.getPhoto().getUrl(), viewHolder.write_photo, options);
                viewHolder.write_name.setText(user.getUsername());
            }
            @Override
            public void getFailure() {

            }
        });
        viewHolder.write_date.setText(dynamicItem.getCreatedAt());
        imageLoader.displayImage(dynamicItem.getPhotoList().get(0).getUrl(), viewHolder.dynamic_photo, options);
      //  holder.dynamic_text.setText(dynamicItem.getDetail());
        holder.show_dynamic_Name.setText(dynamicItem.getTitle());
        holder.show_dynamic_type.setText(dynamicItem.getSportsType());
        holder.show_startTime.setText(getTime(dynamicItem.getStartTime()));
     //   holder.show_endTime.setText(getTime(dynamicItem.getEndTime()));
    //    holder.show_mile.setText(dynamicItem.getMeil());
         holder.show_location.setText(dynamicItem.getArea());
         holder.show_participantCount.setText(Integer.toString(dynamicItem.getParticipantName().size()));;
  //      String name = dynamicItem.getUserName();
  //      int nameCount = dynamicItem.getParticipantName().size();
  //      for(int i= 0;i<nameCount;i++) {
  //          holder.show_participant.setText(name + " ," + dynamicItem.getParticipantName().get(i));
  //         name = name + name + " ," + dynamicItem.getParticipantName().get(i);
   //     }
        return convertView;
    }

    private final class ViewHolder {
        RoundImageView write_photo;
        TextView write_name;
        TextView write_date;
        ImageView dynamic_photo;
        TextView  show_dynamic_Name;
        TextView  show_dynamic_type;
        TextView dynamic_text;
        TextView show_startTime;
        TextView show_endTime;
        TextView show_mile;
        TextView show_location;
        TextView show_participantCount;
        TextView show_participant;
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("YYYY.MM.dd  HH:SS");
        return format.format(date);
    }

}

