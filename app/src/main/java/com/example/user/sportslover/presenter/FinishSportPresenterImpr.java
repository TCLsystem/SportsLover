package com.example.user.sportslover.presenter;

import android.widget.Toast;

import com.example.user.sportslover.activity.FinishSportActivity;
import com.example.user.sportslover.bean.RecordItem;
import com.example.user.sportslover.bean.RouteItem;
import com.example.user.sportslover.bean.TotalRecord;
import com.example.user.sportslover.model.RecordItemControlImpr;
import com.example.user.sportslover.model.RecordItemControlInter;
import com.example.user.sportslover.model.RouteControlImpr;
import com.example.user.sportslover.model.RouteControlInter;
import com.example.user.sportslover.model.TotalRecordFindImpr;
import com.example.user.sportslover.model.TotalRecordFindInter;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by user on 17-10-16.
 */

public class FinishSportPresenterImpr implements FinishSportPresenter {

    FinishSportActivity activity;

    public FinishSportPresenterImpr(FinishSportActivity activity){
        this.activity = activity;
    }

    @Override
    public void uploadCollectedMap() {
        RouteItem routeItem = new RouteItem();
        routeItem.setUserName(activity.mUserLocal.getName());
        routeItem.setDistance(activity.sportInformationItem.getTotalMileages());
        //routeItem.setBitmap(activity.bitmap);
        routeItem.setPlace("Huizhou");
        routeItem.setSportsType(activity.sportInformationItem.getSportType());
        routeItem.setSportsPath(activity.sportInformationItem.getPoints());
        routeItem.setFilePath("/data/data/com.example.user.sportslover/files/test.png");
        RouteControlInter routeControlInter = new RouteControlImpr();
        routeControlInter.uploadRoute(activity.getApplicationContext(), routeItem);
    }

    @Override
    public void updateTotalRecord() {
        TotalRecord totalRecordVari = new TotalRecord();
        switch (activity.sportInformationItem.getSportType()){
            case "Running":
                totalRecordVari.setRunDistance((float) activity.sportInformationItem.getTotalMileages());
                totalRecordVari.setRunDuration((int)activity.sportInformationItem.getCumulativeTime());
                totalRecordVari.setRunTimes(1);
                break;
            case "Walking":
                totalRecordVari.setWalkDistance((float) activity.sportInformationItem.getTotalMileages());
                totalRecordVari.setWalkDuration((int)activity.sportInformationItem.getCumulativeTime());
                totalRecordVari.setWalkTimes(1);
                break;
            case "Riding":
                totalRecordVari.setRideDistance((float) activity.sportInformationItem.getTotalMileages());
                totalRecordVari.setRideDuration((int)activity.sportInformationItem.getCumulativeTime());
                totalRecordVari.setRideTimes(1);
                break;
            default:
                Toast.makeText(activity.getApplicationContext(), "Fail to update TotalRecord! Invalid Sport Type!", Toast.LENGTH_SHORT).show();
        }
        TotalRecordFindInter totalRecordFindInter = new TotalRecordFindImpr();
        totalRecordFindInter.updateTotalRecord(activity.getApplicationContext(), totalRecordVari, activity.mUserLocal.getName());
    }

    @Override
    public void uploadRecordItem() {
        RecordItem recordItem = new RecordItem();
        recordItem.setUserName(activity.mUserLocal.getName());
        recordItem.setDistance((float) activity.sportInformationItem.getTotalMileages());
        recordItem.setDuration((int) activity.sportInformationItem.getCumulativeTime());
        recordItem.setSpeed(activity.sportInformationItem.getAveragePace());
        recordItem.setSportsTime(activity.sportInformationItem.getStartTime() + 8 * 60 * 60 * 1000);
        recordItem.setSportsType(activity.sportInformationItem.getSportType());
        recordItem.setCalories(activity.sportInformationItem.getCalories());
        RecordItemControlInter recordItemControlInter = new RecordItemControlImpr();
        recordItemControlInter.uploadRecordItem(activity.getApplicationContext(), recordItem);
    }
}
