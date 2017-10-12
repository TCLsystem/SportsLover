package com.example.user.sportslover.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

import java.util.List;

/**
 * Created by user on 17-9-29.
 */

public class SportInformationItem implements Parcelable {

    private float sportProgress;
    private double totalMileages;
    private long cumulativeTime;
    private int averagePace;
    private float calories;
    private List<LatLng> points;
    private Bitmap bitmap;

    public SportInformationItem(){}

    protected SportInformationItem(Parcel in) {
        sportProgress = in.readFloat();
        totalMileages = in.readDouble();
        cumulativeTime = in.readLong();
        averagePace = in.readInt();
        calories = in.readFloat();
        points = in.createTypedArrayList(LatLng.CREATOR);
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        //bitmap = Bitmap.CREATOR.createFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(sportProgress);
        dest.writeDouble(totalMileages);
        dest.writeLong(cumulativeTime);
        dest.writeInt(averagePace);
        dest.writeFloat(calories);
        dest.writeTypedList(points);
        dest.writeParcelable(bitmap, flags);
        //bitmap.writeToParcel(dest, 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SportInformationItem> CREATOR = new Creator<SportInformationItem>() {
        @Override
        public SportInformationItem createFromParcel(Parcel in) {
            return new SportInformationItem(in);
        }

        @Override
        public SportInformationItem[] newArray(int size) {
            return new SportInformationItem[size];
        }
    };

    public int getAveragePace() {
        return averagePace;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getCalories() {
        return calories;
    }

    public long getCumulativeTime() {
        return cumulativeTime;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public float getSportProgress() {
        return sportProgress;
    }

    public double getTotalMileages() {
        return totalMileages;
    }

    public void setAveragePace(int averagePace) {
        this.averagePace = averagePace;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public void setCumulativeTime(long cumulativeTime) {
        this.cumulativeTime = cumulativeTime;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }

    public void setSportProgress(float sportProgress) {
        this.sportProgress = sportProgress;
    }

    public void setTotalMileages(double totalMileages) {
        this.totalMileages = totalMileages;
    }
}
