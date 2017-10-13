package com.example.user.sportslover.presenter;

import com.example.user.sportslover.bean.SportHistoryDataBean;
import com.example.user.sportslover.model.SportHistoryModelImpr;
import com.example.user.sportslover.model.SportHistoryModelInter;

import java.util.List;

/**
 * Created by user on 17-10-10.
 */

public class SportHistoryPresenterWalkingWeekImpr implements SportHistoryPresenter {
    @Override
    public List<SportHistoryDataBean> loadHistoryData() {
        SportHistoryModelInter sportHistoryModelInter = new SportHistoryModelImpr();
        return sportHistoryModelInter.loadHistoryData(7, "walking");
    }
}
