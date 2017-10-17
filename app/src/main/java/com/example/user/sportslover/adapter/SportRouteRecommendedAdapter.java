package com.example.user.sportslover.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.RouteItem;
import com.example.user.sportslover.util.ColorUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by user on 17-10-12.
 */

public class SportRouteRecommendedAdapter extends RecyclerView.Adapter<SportRouteRecommendedAdapter.ViewHolder> {

    private List<RouteItem> routeItemList;
    private MyItemClickListener myItemClickListener;
    private int clickedPosition = 0;
    private RecyclerView itemViewRes;
    private DecimalFormat textFormat = new DecimalFormat("#0.00");

    static class ViewHolder extends RecyclerView.ViewHolder{

        private MyItemClickListener myItemClickListener;
        private RelativeLayout relativeLayout;
        private ImageView greenBackground;
        private ImageView whiteBackground;
        private ImageView ivOnSelect;
        private ImageView ivOnSelectWhite;
        private TextView distance;
        private TextView place;
        private TextView completed;

        public ViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl_item_recommend_route_view);
            greenBackground = (ImageView) itemView.findViewById(R.id.sport_route_recommended_item_background_green);
            whiteBackground = (ImageView) itemView.findViewById(R.id.sport_route_recommended_item_background_white);
            ivOnSelect = (ImageView) itemView.findViewById(R.id.sport_route_recommended_selected);
            ivOnSelectWhite = (ImageView) itemView.findViewById(R.id.sport_route_recommended_selected_white);
            distance = (TextView) itemView.findViewById(R.id.sport_route_recommended_distance);
            place = (TextView) itemView.findViewById(R.id.sport_route_recommended_place);
            completed = (TextView) itemView.findViewById(R.id.sport_route_recommended_complete);
            this.myItemClickListener = myItemClickListener;
        }
    }

    public SportRouteRecommendedAdapter(List<RouteItem> routeItemList, RecyclerView view) {
        itemViewRes = view;
        this.routeItemList = routeItemList;
    }

    @Override
    public SportRouteRecommendedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommended_route, parent, false);
        SportRouteRecommendedAdapter.ViewHolder holder = new SportRouteRecommendedAdapter.ViewHolder(view, myItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(SportRouteRecommendedAdapter.ViewHolder holder, final int position) {
        final RouteItem routeItem = routeItemList.get(position);
        if (position % 2 == 0){
            holder.greenBackground.setVisibility(View.VISIBLE);
            holder.whiteBackground.setVisibility(View.GONE);
            holder.distance.setTextColor(Color.WHITE);
            holder.place.setTextColor(Color.WHITE);
            holder.completed.setTextColor(Color.WHITE);
        } else {
            holder.greenBackground.setVisibility(View.GONE);
            holder.whiteBackground.setVisibility(View.VISIBLE);
            holder.distance.setTextColor(0xff1de3bd);
            holder.place.setTextColor(0xff1de3bd);
            holder.completed.setTextColor(0xff1de3bd);
        }
        holder.distance.setText(textFormat.format(routeItem.getDistance()/1000) + "km");
        holder.place.setText(routeItem.getPlace());
        holder.completed.setText("0 people have completed");
        if (routeItemList.get(position).isSelected()){
            if (position % 2 == 0){
                holder.ivOnSelect.setVisibility(View.GONE);
                holder.ivOnSelectWhite.setVisibility(View.VISIBLE);
            } else {
                holder.ivOnSelect.setVisibility(View.VISIBLE);
                holder.ivOnSelectWhite.setVisibility(View.GONE);
            }
        } else {
            holder.ivOnSelect.setVisibility(View.GONE);
            holder.ivOnSelectWhite.setVisibility(View.GONE);
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < routeItemList.size(); i++){
                    routeItemList.get(i).setSelected(false);
                }
                routeItemList.get(position).setSelected(true);
                notifyDataSetChanged();
                if (myItemClickListener != null){
                    myItemClickListener.onItemClick(v, position);
                }
            }
        });
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
