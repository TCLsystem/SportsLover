package com.example.user.sportslover.model;

import android.content.Context;
import android.widget.Toast;

import com.example.user.sportslover.activity.MainActivity;
import com.example.user.sportslover.bean.TotalRecord;
import com.example.user.sportslover.bean.UserLocal;
import com.example.user.sportslover.bean.Weather;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by user on 17-10-14.
 */

public class TotalRecordFindImpr implements TotalRecordFindInter {

    TotalRecord totalRecord = new TotalRecord();

    @Override
    public void loadTotalRecord(final Context context, final String username, final OnRequestTotalRecordListener listener) {

            BmobQuery<TotalRecord> query = new BmobQuery<>();
            query.addWhereEqualTo("UserName", username);
            query.findObjects(context, new FindListener<TotalRecord>() {
                @Override
                public void onSuccess(List<TotalRecord> list) {
                    if (list.size() == 0){
                        initZeroRecord(username);
                        totalRecord.save(context, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(context, "Successfully creat TotalRecord!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(context, "Fail to creat TotalRecord!\n" +s, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (list.size() == 1){
                        totalRecord = list.get(0);
                    } else {
                        Toast.makeText(context, "Database Error", Toast.LENGTH_SHORT).show();
                        initZeroRecord("");
                    }
                    listener.onSuccess(totalRecord);
                }

                @Override
                public void onError(int i, String s) {
                    Toast.makeText(context, "Database Error.\n" + s, Toast.LENGTH_SHORT).show();
                    initZeroRecord(username);
                    listener.onSuccess(totalRecord);
                }
            });
    }

    @Override
    public void updateTotalRecord(final Context context, final TotalRecord totalRecordVari, String username) {
        OnRequestTotalRecordListener listener = new OnRequestTotalRecordListener() {
            @Override
            public void onSuccess(TotalRecord totalRecord1) {
                totalRecord.setUserName(totalRecord1.getUserName());
                totalRecord.setRunDistance(totalRecord1.getRunDistance()+totalRecordVari.getRunDistance());
                totalRecord.setRunDuration(totalRecord1.getRunDuration()+totalRecordVari.getRunDuration());
                totalRecord.setRunSpeed((int)(totalRecord.getRunDistance()<1?599940:totalRecord.getRunDuration()/totalRecord.getRunDistance()*1000));
                totalRecord.setRunTimes(totalRecord1.getRunTimes() + totalRecordVari.getRunTimes());
                totalRecord.setWalkDistance(totalRecord1.getWalkDistance()+totalRecordVari.getWalkDistance());
                totalRecord.setWalkDuration(totalRecord1.getWalkDuration()+totalRecordVari.getWalkDuration());
                totalRecord.setWalkSpeed((int)(totalRecord.getWalkDistance()<1?599940:totalRecord.getWalkDuration()/totalRecord.getWalkDistance()*1000));
                totalRecord.setWalkTimes(totalRecord1.getWalkTimes() + totalRecordVari.getWalkTimes());
                totalRecord.setRideDistance(totalRecord1.getRideDistance()+totalRecordVari.getRideDistance());
                totalRecord.setRideDuration(totalRecord1.getRideDuration()+totalRecordVari.getRideDuration());
                totalRecord.setRideSpeed((int)(totalRecord.getRideDistance()<1?599940:totalRecord.getRideDuration()/totalRecord.getRideDistance()*1000));
                totalRecord.setRideTimes(totalRecord1.getRideTimes() + totalRecordVari.getRideTimes());
                totalRecord.update(context, totalRecord1.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "Successfully update TotalRecord!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(context, "Fail to update TotalRecord!\n" + s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        loadTotalRecord(context, username, listener);
    }

    private void initZeroRecord(String username) {
        totalRecord.setUserName(username);
        totalRecord.setRideDistance(0);
        totalRecord.setRideDuration(0);
        totalRecord.setRideSpeed(0);
        totalRecord.setRideTimes(0);
        totalRecord.setRunDistance(0);
        totalRecord.setRunDuration(0);
        totalRecord.setRunSpeed(0);
        totalRecord.setRunTimes(0);
        totalRecord.setWalkDistance(0);
        totalRecord.setWalkDuration(0);
        totalRecord.setWalkSpeed(0);
        totalRecord.setWalkTimes(0);
    }

    public interface OnRequestTotalRecordListener{
        void onSuccess(TotalRecord totalRecord);
    }

}
