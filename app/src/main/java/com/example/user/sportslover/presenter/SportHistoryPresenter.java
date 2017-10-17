package com.example.user.sportslover.presenter;

import android.content.Context;

import com.example.user.sportslover.bean.SportHistoryDataBean;
import com.example.user.sportslover.model.SportHistoryModelImpr;

import java.util.List;

/**
 * Created by user on 17-10-10.
 */

public interface SportHistoryPresenter {
    void loadHistoryData(Context context, SportHistoryModelImpr.OnSportHistoryListener listener);
}
