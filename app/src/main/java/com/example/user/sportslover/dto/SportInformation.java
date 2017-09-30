package com.example.user.sportslover.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 17-9-29.
 */

public class SportInformation implements Parcelable {

    private float sportProgress;
    private double totalMileages;
    private long cumulativeTime;
    private int averagePace;
    private float calories;
    private List<LatLng> points;

    public SportInformation(){}

    protected SportInformation(Parcel in) {
        sportProgress = in.readFloat();
        totalMileages = in.readDouble();
        cumulativeTime = in.readLong();
        averagePace = in.readInt();
        calories = in.readFloat();
        points = in.createTypedArrayList(LatLng.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(sportProgress);
        dest.writeDouble(totalMileages);
        dest.writeLong(cumulativeTime);
        dest.writeInt(averagePace);
        dest.writeFloat(calories);
        dest.writeTypedList(points);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SportInformation> CREATOR = new Creator<SportInformation>() {
        @Override
        public SportInformation createFromParcel(Parcel in) {
            return new SportInformation(in);
        }

        @Override
        public SportInformation[] newArray(int size) {
            return new SportInformation[size];
        }
    };

    public int getAveragePace() {
        return averagePace;
    }

    public float getCalories() {
        return calories;
    }

    public long getCumulativeTime() {
        return cumulativeTime;
    }

    public float getSportProgress() {
        return sportProgress;
    }

    public double getTotalMileages() {
        return totalMileages;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public void setAveragePace(int averagePace) {
        this.averagePace = averagePace;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public void setCumulativeTime(long cumulativeTime) {
        this.cumulativeTime = cumulativeTime;
    }

    public void setTotalMileages(double totalMileages) {
        this.totalMileages = totalMileages;
    }

    public void setSportProgress(float sportProgress) {
        this.sportProgress = sportProgress;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }


}