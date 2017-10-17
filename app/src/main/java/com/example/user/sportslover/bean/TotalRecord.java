package com.example.user.sportslover.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by user on 17-9-30.
 */

public class TotalRecord extends BmobObject implements Serializable {
    public String UserName;

    public float RunDistance;
    public int RunDuration;
    public int RunSpeed;
    public int RunTimes;

    public float WalkDistance;
    public int WalkDuration;
    public int WalkSpeed;
    public int WalkTimes;

    public float RideDistance;
    public int RideSpeed;
    public int RideTimes;
    public int RideDuration;

    public int getWalkTimes() {
        return WalkTimes;
    }

    public void setWalkTimes(int walkTimes) {
        WalkTimes = walkTimes;
    }

    public float getRideDistance() {
        return RideDistance;
    }

    public void setRideDistance(float rideDistance) {
        RideDistance = rideDistance;
    }

    public int getRideDuration() {
        return RideDuration;
    }

    public void setRideDuration(int rideDuration) {
        RideDuration = rideDuration;
    }

    public int getRideSpeed() {
        return RideSpeed;
    }

    public void setRideSpeed(int rideSpeed) {
        RideSpeed = rideSpeed;
    }

    public int getRideTimes() {
        return RideTimes;
    }

    public void setRideTimes(int rideTimes) {
        RideTimes = rideTimes;
    }

    public float getRunDistance() {
        return RunDistance;
    }

    public void setRunDistance(float runDistance) {
        RunDistance = runDistance;
    }

    public int getRunDuration() {
        return RunDuration;
    }

    public void setRunDuration(int runDuration) {
        RunDuration = runDuration;
    }

    public int getRunSpeed() {
        return RunSpeed;
    }

    public void setRunSpeed(int runSpeed) {
        RunSpeed = runSpeed;
    }

    public int getRunTimes() {
        return RunTimes;
    }

    public void setRunTimes(int runTimes) {
        RunTimes = runTimes;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public float getWalkDistance() {
        return WalkDistance;
    }

    public void setWalkDistance(float walkDistance) {
        WalkDistance = walkDistance;
    }

    public int getWalkDuration() {
        return WalkDuration;
    }

    public void setWalkDuration(int walkDuration) {
        WalkDuration = walkDuration;
    }

    public int getWalkSpeed() {
        return WalkSpeed;
    }

    public void setWalkSpeed(int walkSpeed) {
        WalkSpeed = walkSpeed;
    }
}
