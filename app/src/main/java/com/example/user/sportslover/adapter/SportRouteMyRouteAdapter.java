package com.example.user.sportslover.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.RouteItem;
import com.example.user.sportslover.widget.AsyncImageLoader;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by user on 17-10-12.
 */

public class SportRouteMyRouteAdapter extends RecyclerView.Adapter<SportRouteMyRouteAdapter.ViewHolder> {

    private List<RouteItem> routeItemList;
    private MyItemClickListener myItemClickListener;
    private DecimalFormat textFormat = new DecimalFormat("#0.00");
    private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
    private RecyclerView recyclerView;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mapImage;
        TextView mapDescription;
        private MyItemClickListener myItemClickListener;

        public ViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);
            mapImage = (ImageView) itemView.findViewById(R.id.sport_route_my_route_map);
            mapDescription = (TextView) itemView.findViewById(R.id.sport_route_my_route_dsc);
            //将全局的监听赋值给接口
            this.myItemClickListener = myItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemClickListener != null){
                myItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public SportRouteMyRouteAdapter(List<RouteItem> routeItemList, RecyclerView recyclerView) {
        this.routeItemList = routeItemList;
        this.recyclerView = recyclerView;
    }

    @Override
    public SportRouteMyRouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_route, parent, false);
        ViewHolder holder = new ViewHolder(view, myItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(SportRouteMyRouteAdapter.ViewHolder holder, int position) {
        RouteItem routeItem = routeItemList.get(position);
        if (routeItem.getPic() != null){
            String imageUrl = routeItem.getPic().getUrl();
            //holder.mapImage.setImageBitmap(routeItem.getBitmap());
            holder.mapImage.setTag(imageUrl);
            Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new AsyncImageLoader.ImageCallback() {
                public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                    ImageView imageViewByTag = (ImageView) recyclerView.findViewWithTag(imageUrl);
                    if (imageViewByTag != null) {
                        imageViewByTag.setImageDrawable(imageDrawable);
                    }
                }
            });
            if (cachedImage == null) {
                //holder.mapImage.setImageResource(R.drawable.w);
            }else{
                holder.mapImage.setImageDrawable(cachedImage);
            }
        }
        holder.mapDescription.setText(textFormat.format(routeItem.getDistance()/1000) + "km");
    }

    @Override
    public int getItemCount() {
        return routeItemList.size();
    }


    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }
}
