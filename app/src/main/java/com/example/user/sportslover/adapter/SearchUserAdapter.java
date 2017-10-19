package com.example.user.sportslover.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.user.sportslover.adapter.base.BaseViewHolder;
import com.example.user.sportslover.bean.GroupInfo;
import com.example.user.sportslover.bean.User;

import java.util.ArrayList;
import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<User> users = new ArrayList<>();
    private List<GroupInfo> groupInfos = new ArrayList<>();

    public SearchUserAdapter() {
    }

    public void setDatas(List<User> list) {
        users.clear();
        if (null != list) {
            users.addAll(list);
        }
    }
    public void setGroupDatas(List<GroupInfo> list) {
        groupInfos.clear();
        if (null != list) {
            groupInfos.addAll(list);
        }
    }


    /**获取用户
     * @param position
     * @return
     */
    public User getItem(int position){
        return users.get(position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchUserHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(users.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
