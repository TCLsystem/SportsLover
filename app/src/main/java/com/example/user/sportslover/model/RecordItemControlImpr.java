package com.example.user.sportslover.model;

import android.content.Context;
import android.widget.Toast;

import com.example.user.sportslover.bean.RecordItem;
import com.example.user.sportslover.bean.RouteItem;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by user on 17-10-16.
 */

public class RecordItemControlImpr implements RecordItemControlInter {

    private List<RecordItem> recordItemList;

    @Override
    public void uploadRecordItem(final Context context, RecordItem recordItem) {
            recordItem.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Successfully upload sports data!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "Fail to upload sports data!\n" +s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void findRecordItemByTime(final Context context, String sportType, long beginSecond, long endSecond, final OnRecordItemListener listener) {
        BmobQuery<RecordItem> query = new BmobQuery<>();
        query.addWhereGreaterThan("SportsTime", beginSecond);
        query.addWhereLessThanOrEqualTo("SportsTime", endSecond);
        query.addWhereEqualTo("SportsType", sportType);
        query.findObjects(context, new FindListener<RecordItem>() {
            @Override
            public void onSuccess(List<RecordItem> list) {
                listener.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context, "Database Error.\n" + s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public interface OnRecordItemListener{
        void onSuccess(List<RecordItem> recordItemList);
    }
}
