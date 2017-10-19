package com.example.user.sportslover.bean;

import cn.bmob.v3.BmobObject;

public class Friend extends BmobObject {

    private User user;
    private User friendUser;
    private String userType;// 用户类型
    private String sortLetters; // 显示数据拼音的首字母
    private String firstSpell;// 姓名缩写

    //拼音
    private transient String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
    }

    public  String getUsertype() {
        return userType;
    }

    public  void setUsertype(String userType) {
        this.userType = userType;
    }

    public  String getSortLetters() {
        return sortLetters;
    }

    public  void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public  String getfirstSpell() {
        return firstSpell;
    }

    public  void setfirstSpell(String firstSpell) {
        this.firstSpell = firstSpell;
    }
}
