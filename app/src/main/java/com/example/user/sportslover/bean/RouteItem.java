package com.example.user.sportslover.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by user on 17-9-30.
 */

public class RouteItem extends BmobObject implements Parcelable {

    public int id;
    public String UserName;
    public String SportsType;
    public String Place;
    public LatLng StartPoint;
    public LatLng EndPoint;
    public double Distance;
    public List<LatLng> SportsPath;
    public Bitmap bitmap;
    public boolean isSelected;

    public RouteItem(){}

    protected RouteItem(Parcel in) {
        id = in.readInt();
        UserName = in.readString();
        SportsType = in.readString();
        Place = in.readString();
        StartPoint = in.readParcelable(LatLng.class.getClassLoader());
        EndPoint = in.readParcelable(LatLng.class.getClassLoader());
        Distance = in.readDouble();
        SportsPath = in.createTypedArrayList(LatLng.CREATOR);
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(UserName);
        dest.writeString(SportsType);
        dest.writeString(Place);
        dest.writeParcelable(StartPoint, flags);
        dest.writeParcelable(EndPoint, flags);
        dest.writeDouble(Distance);
        dest.writeTypedList(SportsPath);
        dest.writeParcelable(bitmap, flags);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RouteItem> CREATOR = new Creator<RouteItem>() {
        @Override
        public RouteItem createFromParcel(Parcel in) {
            return new RouteItem(in);
        }

        @Override
        public RouteItem[] newArray(int size) {
            return new RouteItem[size];
        }
    };

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

    public LatLng getStartPoint(){return StartPoint;}
    public void setStartPoint(LatLng startPoint){
        StartPoint = startPoint;
    }

    public LatLng getEndPoint(){return EndPoint;}
    public void setEndPoint(LatLng endPoint){
        EndPoint = endPoint;
    }

    public double getDistance(){return Distance;}
    public void setDistance(double distance){
        Distance = distance;
    }

    public List<LatLng> getSportsPath(){return SportsPath;}
    public void setSportsPath(List<LatLng> sportsPath){
        SportsPath = sportsPath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
