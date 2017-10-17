package com.example.user.sportslover.presenter;

import android.content.Context;

import com.example.user.sportslover.bean.SportHistoryDataBean;
import com.example.user.sportslover.model.SportHistoryModelImpr;
import com.example.user.sportslover.model.SportHistoryModelInter;

import java.util.List;

/**
 * Created by user on 17-10-10.
 */

public class SportHistoryPresenterRunningWeekImpr implements SportHistoryPresenter {
    @Override
    public void loadHistoryData(Context context, SportHistoryModelImpr.OnSportHistoryListener listener) {
        SportHistoryModelInter sportHistoryModelInter = new SportHistoryModelImpr();
        sportHistoryModelInter.loadHistoryData(context, 7, "Walking", listener);
    }
}
