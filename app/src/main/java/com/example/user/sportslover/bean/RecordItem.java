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
    public Date SportsTime;
    public String Speed;
    public String Duration;
    public BmobFile SportPicture;
    public User    Sportsman;

    public String getUserName(){return UserName;}
    public void setUserName(String userName){
        UserName = userName;
    }

    public String getSportsType(){return SportsType;}
    public void setSportsType(String sportsType){
        SportsType = sportsType;
    }

    public Date getSportsTime(){return SportsTime;}
    public void setSportsTime(Date sportsTime){
        SportsTime = sportsTime;
    }

    public String getSpeed(){return Speed;}
    public void setSpeed(String speed){
        Speed = speed;
    }

    public String getDuration(){return Duration;}
    public void setDuration(String duration){
        Duration = duration;
    }

    public BmobFile getSportPicture(){return SportPicture;}
    public void setSportPicture(BmobFile sportPicture){
        SportPicture = sportPicture;
    }

    public User getSportsman(){return Sportsman;}
    public void setSportsman(User sportsman){
        Sportsman = sportsman;
    }
}
