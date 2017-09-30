package com.example.user.sportslover.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：用户表
 */
public class User extends BmobObject implements Serializable{

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

    String Sex ;//性别



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
        return UserName;
    }

    public void setName(String name) {
        UserName = name;
    }

    public BmobFile getPhoto() {
        return Photo;
    }

    public void setPhoto(BmobFile photo) {
        Photo = photo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public User() {
    }
}


