package com.example.user.sportslover.model;

import android.content.Context;

import com.example.user.sportslover.bean.TotalRecord;
import com.example.user.sportslover.bean.UserLocal;

/**
 * Created by user on 17-10-14.
 */

public interface TotalRecordFindInter {
    void loadTotalRecord(Context context, String userName, TotalRecordFindImpr.OnRequestTotalRecordListener listener);
    void updateTotalRecord(Context context, TotalRecord totalRecord, String username);
}
