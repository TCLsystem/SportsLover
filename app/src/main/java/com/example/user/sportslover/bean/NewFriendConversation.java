package com.example.user.sportslover.bean;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.user.sportslover.R;
import com.example.user.sportslover.activity.NewFriendActivity;
import com.example.user.sportslover.application.BaseApplication;
import com.example.user.sportslover.application.Config;
import com.example.user.sportslover.db.NewFriend;
import com.example.user.sportslover.db.NewFriendManager;

public class NewFriendConversation extends Conversation {

    NewFriend lastFriend;

    public NewFriendConversation(NewFriend friend){
        this.lastFriend=friend;
        this.cName="New Friend";
    }

    @Override
    public String getLastMessageContent() {
        if(lastFriend!=null){
            Integer status =lastFriend.getStatus();
            String name = lastFriend.getName();
            if(TextUtils.isEmpty(name)){
                name = lastFriend.getUid();
            }
            //目前的好友请求都是别人发给我的
            if(status==null || status== Config.STATUS_VERIFY_NONE||status == Config.STATUS_VERIFY_READED){
                return name+"请求添加好友";
            }else{
                return "already add"+name;
            }
        }else{
            return "";
        }
    }

    @Override
    public long getLastMessageTime() {
        if(lastFriend!=null){
            return lastFriend.getTime();
        }else{
            return 0;
        }
    }

    @Override
    public Object getAvatar() {
        return R.mipmap.new_friends_icon;
    }

    @Override
    public int getUnReadCount() {
        return NewFriendManager.getInstance(BaseApplication.INSTANCE()).getNewInvitationCount();
    }

    @Override
    public void readAllMessages() {
        //批量更新未读未认证的消息为已读状态
        NewFriendManager.getInstance(BaseApplication.INSTANCE()).updateBatchStatus();
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NewFriendActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onLongClick(Context context) {
        NewFriendManager.getInstance(context).deleteNewFriend(lastFriend);
    }
}
