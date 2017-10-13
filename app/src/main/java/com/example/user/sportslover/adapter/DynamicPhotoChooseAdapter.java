package com.example.user.sportslover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.DynamicPhotoItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-10-10.
 */
public class DynamicPhotoChooseAdapter extends BaseAdapter {
    private List<DynamicPhotoItem> albumBeanList;
    private Context mContext;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
    private DisplayImageOptions options;

    public DynamicPhotoChooseAdapter(Context mContext) {
        albumBeanList = new ArrayList<>();
        albumBeanList.add(new DynamicPhotoItem("", true));
        this.mContext = mContext;
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.xiaolian)
                .showImageForEmptyUri(R.drawable.xiaolian)
                .showImageOnFail(R.drawable.xiaolian).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
    }

    @Override
    public int getCount() {
        return albumBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return albumBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DynamicPhotoItem albumBean = albumBeanList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dynamic_gridview, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (albumBean.isPick()) {
            viewHolder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.add_photo));
        } else {
            imageLoader.displayImage("file:///" + albumBean.getFilePath(), viewHolder.imageView, options);
        }
        return convertView;
    }

    public void addData(List<DynamicPhotoItem> mAlbumBeanList) {
        albumBeanList.remove(albumBeanList.size() - 1);
        albumBeanList.addAll(mAlbumBeanList);
        albumBeanList.add(new DynamicPhotoItem("", true));
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        removeData(albumBeanList.get(position));
    }

    public void removeData(DynamicPhotoItem albumBean) {
        if (albumBeanList != null && albumBeanList.contains(albumBean)) {
            //判断当前的数量
            switch (albumBeanList.size()) {
                case 1:
                case 2:
                case 3:
                    albumBeanList.remove(albumBean);
                    break;
                case 4:
                    albumBeanList.remove(albumBean);
                    if (!albumBeanList.get(albumBeanList.size() - 1).isPick()) {
                        albumBeanList.add(new DynamicPhotoItem("", true));
                    }
                    break;
            }
            this.notifyDataSetInvalidated();
        }
    }

    public List<DynamicPhotoItem> getData() {
        return albumBeanList;
    }

    private static class ViewHolder {
        public ImageView imageView;
    }
}
