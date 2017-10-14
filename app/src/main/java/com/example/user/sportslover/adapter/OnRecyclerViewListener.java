package com.example.user.sportslover.adapter;

/**
 * 为RecycleView添加点击事件
 *
 * @author smile
 * @project OnRecyclerViewListener
 * @date 2016-03-03-16:39
 */
public interface OnRecyclerViewListener {
    void onItemClick(int position);

    interface OnItemClickNew {

        void onAvatarClick(int position);

        void onItemResendClick(int position);
    }

    boolean onItemLongClick(int position);
}
