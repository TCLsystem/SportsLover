package com.example.user.sportslover.adapter;


public interface OnRecyclerViewListener {
    void onItemClick(int position);

    interface OnItemClickNew {

        void onAvatarClick(int position);

        void onItemResendClick(int position);
    }

    boolean onItemLongClick(int position);
}
