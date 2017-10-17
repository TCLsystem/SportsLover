package com.example.user.sportslover.model;

import android.content.Context;

import com.example.user.sportslover.bean.RecordItem;

/**
 * Created by user on 17-10-16.
 */

public interface RecordItemControlInter {
    void uploadRecordItem(Context context, RecordItem recordItem);
    void findRecordItemByTime(Context context, String sportType, long beginSecond, long endSecond, RecordItemControlImpr.OnRecordItemListener listener);
}
