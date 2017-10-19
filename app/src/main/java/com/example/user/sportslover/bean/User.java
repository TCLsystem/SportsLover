package com.example.user.sportslover.bean;

import com.example.user.sportslover.db.NewFriend;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser implements Serializable {

    //姓名
    String UserName;
    //头像
    BmobFile Photo;
    //密码
    String Password;
    //手机号码
    String Number;

    String Birthday;//生日

    String Weight;//体重

    String Height;//身高

    String Sex;//性别

    public User() {
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }


    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String number) {
        Weight = number;
    }


    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }


    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getName() {
        return this.UserName;
    }

    public void setName(String name) {
        this.UserName = name;
    }

    public BmobFile getPhoto() {
        return Photo;
    }

    public void setPhoto(BmobFile photo) {
        Photo = photo;
    }

    public String getPassword() {
        return this.Password;
    }


    private String avatar;

    public User(NewFriend friend) {
        setObjectId(friend.getUid());
        setUsername(friend.getName());
        setAvatar(friend.getAvatar());
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}


