package com.example.user.sportslover.bean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by user on 17-9-30.
 */

public class RouteItem extends BmobObject implements Serializable {

    public String UserName;
    public String SportsType;
    public String Place;
    public String StartPoint;
    public String EndPoint;
    public String Distance;
    public List SportsPath;


    public String getUserName(){return UserName;}
    public void setUserName(String userName){
        UserName = userName;
    }

    public String getSportsType(){return SportsType;}
    public void setSportsType(String sportsType){
        SportsType = sportsType;
    }

    public String getPlace(){return Place;}
    public void setPlace(String place){
        Place= place;
    }

    public String getStartPoint(){return StartPoint;}
    public void setStartPoint(String startPoint){
        StartPoint = startPoint;
    }

    public String getEndPoint(){return EndPoint;}
    public void setEndPoint(String endPoint){
        EndPoint = endPoint;
    }

    public String getDistance(){return Distance;}
    public void setDistance(String distance){
        Distance = distance;
    }

    public List getSportsPath(){return SportsPath;}
    public void setSportsPath(List sportsPath){
        SportsPath = sportsPath;
    }
}
