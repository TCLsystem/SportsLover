package com.example.user.sportslover.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by user on 17-9-19.
 */
public class DynamicItem extends BmobObject implements Serializable {

    public User getWriter() {
        return Writer;
    }

    public void setWriter(User writer) {
        Writer = writer;
    }

    public User Writer;

    //作者上传图片集合
    public List<BmobFile> PhotoList;

    //作者描述文字
    public String Detail;


    public String UserName;

    public String SportsType;

    public String Title;
    //作者上传图片集合
    public BmobFile Poster;

    public Date ApplicationDeadline;

    public Date StartTime;

    public Date EndTime;

    public String Meil;

    public String Place;

    public String Area;

    public List<String> ParticipantName;

    public List<String> getParticipantName() {
        return ParticipantName;
    }
    public void setParticipantName(List<String> participantName) {
        ParticipantName = participantName;
    }

    public String getUserName() {
        return UserName;
    }
    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getSportsType() {
        return SportsType;
    }
    public void setSportsType(String sportsType) {
        SportsType = sportsType;
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }

    public BmobFile getPoster() {
        return Poster;
    }

    public void setPoster(BmobFile poster) {
        Poster = poster;
    }

    public  Date getStartTime(){return StartTime;}
    public void  setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public  Date getEndTime(){return EndTime;}
    public void  setEndtTime(Date endTime) {
        EndTime = endTime;
    }

    public  Date getApplicationDeadline(){return ApplicationDeadline;}
    public void  setApplicationDeadline(Date applicationDeadline) {
        ApplicationDeadline = applicationDeadline;
    }

    public String getMeil() {return Meil;}
    public void setMeil(String meil){
        Meil = meil;
    }

    public String getPlace() {return Place;}
    public void setPlace(String place){
        Place = place;
    }

    public String getArea(){return Area;}
    public void setArea(String area){
        Area = area;
    }


    public List<BmobFile> getPhotoList() {
        return PhotoList;
    }

    public void setPhotoList(List<BmobFile> photoList) {
        PhotoList = photoList;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }
}

