package com.example.user.sportslover.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by user on 17-9-30.
 */

public class TotalRecord extends BmobObject implements Serializable {
    public String UserName;

    public String RunDistance;
    public String RunDuration;
    public String RunSpeed;
    public String RunTimes;

    public String WalkDistance;
    public String WalkDuration;
    public String WalkSpeed;
    public String WalkTimes;

    public String RideDistance;
    public String RideSpeed;
    public String RideTimes;
    public String RideDuration;


    public String getUserName(){return UserName;}
    public void setUserName(String userName){
        UserName = userName;
    }



    public String getRunDistance(){return RunDistance;}
    public void setRunDistance(String runDistance){
        RunDistance = runDistance;
    }

    public String getRunDuration(){return RunDuration;}
    protected void setRunDuration(String runDuration){
        RunDuration = runDuration;
    }

    public String getRunSpeed(){return RunSpeed;}
    public void setRunSpeed(String runSpeed){
        RunSpeed = runSpeed;
    }

    public String getRunTimes(){return RunTimes;}
    public void setRunTimes(String runTimes){
        RunTimes = runTimes;
    }





    public String getRideDistance(){return RideDistance;}
    public void setRideDistance(String rideDistance){
        RideDistance = rideDistance;
    }

    public String getRideDuration(){return RideDuration;}
    protected void setRideDuration(String rideDuration){
        RideDuration = rideDuration;
    }

    public String getRideSpeed(){return RideSpeed;}
    public void setRideSpeed(String rideSpeed){
        RideSpeed = rideSpeed;
    }

    public String getRideTimes(){return RideTimes;}
    public void setRideTimes(String rideTimes){
        RideTimes = rideTimes;
    }





    public String getWalkDistance(){return WalkDistance;}
    public void setWalkDistance(String walkDistance){
        WalkDistance = walkDistance;
    }

    public String getWalkDuration(){return WalkDuration;}
    protected void setWalkDuration(String walkDuration){
        WalkDuration = walkDuration;
    }

    public String getWalkSpeed(){return WalkSpeed;}
    public void setWalkSpeed(String walkSpeed){
        WalkSpeed = walkSpeed;
    }

    public String getWalkTimes(){return WalkTimes;}
    public void setWalkTimes(String walkTimes){
        WalkTimes = walkTimes;
    }


}
