package com.example.user.sportslover.bean;

import java.io.Serializable;
import java.sql.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by user on 17-9-30.
 */

public class RecordItem extends BmobObject implements Serializable {

    public String UserName;
    public String SportsType;
    public long SportsTime;
    public int Speed;
    public int Duration;
    public float Distance;
    public float Calories;

    public float getDistance() {
        return Distance;
    }

    public void setDistance(float distance) {
        Distance = distance;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public long getSportsTime() {
        return SportsTime;
    }

    public void setSportsTime(long sportsTime) {
        SportsTime = sportsTime;
    }

    public String getSportsType() {
        return SportsType;
    }

    public void setSportsType(String sportsType) {
        SportsType = sportsType;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public float getCalories() {
        return Calories;
    }

    public void setCalories(float calories) {
        Calories = calories;
    }
}
