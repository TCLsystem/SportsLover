package com.example.user.sportslover.adapter;

import android.content.Context;

import com.example.user.sportslover.R;
import com.example.user.sportslover.adapter.base.BaseRecyclerAdapter;
import com.example.user.sportslover.adapter.base.BaseRecyclerHolder;
import com.example.user.sportslover.adapter.base.IMutlipleItem;
import com.example.user.sportslover.bean.DynamicItem;

import java.util.Collection;

public class DynamicSimpleAdapter extends BaseRecyclerAdapter<DynamicItem> {

    public static final int TYPE_NEW_FRIEND = 0;
    public static final int TYPE_ITEM = 1;

    public DynamicSimpleAdapter(Context context, IMutlipleItem<DynamicItem> items,
                                Collection<DynamicItem> datas) {
        super(context, items, datas);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, DynamicItem dynamicItem, int position) {
        if (holder.layoutId == R.layout.item_contact) {
            if (dynamicItem != null) {
                holder.setImageView(dynamicItem.getTitle() == null ? "未知" : dynamicItem.getTitle
                        (), R.drawable.crew_head, R.id.iv_recent_avatar);


                holder.setText(R.id.tv_recent_name, dynamicItem.getTitle() == null ? "unknown" :
                        dynamicItem.getTitle());
            }
        }
    }

/* private LayoutInflater mInflater;
    private List<DynamicItem> mDatas;
    private int mLayoutRes;
    private Context mContext;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    public DynamicSimpleAdapter(Context context, int layoutRes, List<DynamicItem> datas) {
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
        com.example.user.sportslover.adapter.DynamicSimpleAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutRes, null);
            holder = new com.example.user.sportslover.adapter.DynamicSimpleAdapter.ViewHolder();
            holder.write_photo = (ImageView) convertView.findViewById(R.id.iv_recent_avatar);
            holder.show_dynamic_Name =(TextView) convertView.findViewById(R.id.tv_recent_name);
            convertView.setTag(holder);
        } else {
            holder = (com.example.user.sportslover.adapter.DynamicSimpleAdapter.ViewHolder)
            convertView.getTag();
        }
        DynamicItem dynamicItem = mDatas.get(position);
        final com.example.user.sportslover.adapter.DynamicSimpleAdapter.ViewHolder viewHolder =
        holder;
        return convertView;
    }

    private final class ViewHolder {
        ImageView write_photo;
        TextView  show_dynamic_Name;
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("YYYY.MM.dd  HH:SS");
        return format.format(date);
    }*/

}