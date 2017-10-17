package com.example.user.sportslover.model;

import android.content.Context;

import com.example.user.sportslover.bean.SportHistoryDataBean;

import java.util.List;

/**
 * Created by user on 17-10-10.
 */

public interface SportHistoryModelInter {

    void loadHistoryData(Context context, int days, String type, SportHistoryModelImpr.OnSportHistoryListener listener);

}
