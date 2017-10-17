package com.example.user.sportslover.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by user on 17-9-30.
 */

public class RouteItem extends BmobObject implements Serializable {

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
    public BmobFile pic;
    public String filePath;

    public RouteItem(){}

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

    public BmobFile getPic() {
        return pic;
    }

    public void setPic(BmobFile pic) {
        this.pic = pic;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
