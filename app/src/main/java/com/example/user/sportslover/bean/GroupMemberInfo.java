package com.example.user.sportslover.bean;

import java.io.Serializable;

public class GroupMemberInfo implements Serializable {

    private String groupNick;
    private User mUser;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getGroupNick() {
        return groupNick;
    }

    public void setGroupNick(String groupNick) {
        this.groupNick = groupNick;
    }
}
