package com.example.user.sportslover.adapter;

import android.content.Context;
import android.view.View;

import com.example.user.sportslover.R;
import com.example.user.sportslover.adapter.base.BaseRecyclerAdapter;
import com.example.user.sportslover.adapter.base.BaseRecyclerHolder;
import com.example.user.sportslover.adapter.base.IMutlipleItem;
import com.example.user.sportslover.bean.Conversation;
import com.example.user.sportslover.util.FaceTextUtil;
import com.example.user.sportslover.util.TimeUtil;

import java.util.Collection;

public class ConversationAdapter extends BaseRecyclerAdapter<Conversation> {

    public ConversationAdapter(Context context, IMutlipleItem<Conversation> items, Collection<Conversation> datas) {
        super(context,items,datas);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, Conversation conversation, int position) {
        holder.setText(R.id.tv_recent_msg, FaceTextUtil.toSpannableString(context,conversation.getLastMessageContent()));
        holder.setText(R.id.tv_recent_time,TimeUtil.getChatTime(false,conversation.getLastMessageTime()));
        //会话图标
        Object obj = conversation.getAvatar();
        if(obj instanceof String){
            String avatar=(String)obj;
            holder.setImageView(avatar, R.mipmap.head, R.id.iv_recent_avatar);
        }else{
            int defaultRes = (int)obj;
            holder.setImageView(null, defaultRes, R.id.iv_recent_avatar);
        }
        //会话标题
        holder.setText(R.id.tv_recent_name, conversation.getcName());
        //查询指定未读消息数
        long unread = conversation.getUnReadCount();
        if(unread>0){
            holder.setVisible(R.id.tv_recent_unread, View.VISIBLE);
            holder.setText(R.id.tv_recent_unread, String.valueOf(unread));
        }else{
            holder.setVisible(R.id.tv_recent_unread, View.GONE);
        }
    }
}