package com.example.user.sportslover.presenter;

import com.example.user.sportslover.model.HomeRunningModelImpl;

/**
 * Created by user on 17-9-26.
 */

public class HomeRunningPresenterImpr implements HomeSportPresenter {

    private HomeRunningModelImpl homeRunningModel = new HomeRunningModelImpl();
    @Override
    public float loadTotalMileages() {
        return homeRunningModel.loadTotalMileages();
    }

    @Override
    public float loadComulativeTime() {
        return homeRunningModel.loadComulativeTime();
    }

    @Override
    public int loadAveragePace() {
        return homeRunningModel.loadAveragePace();
    }

    @Override
    public int loadComulativeNumber() {
        return homeRunningModel.loadComulativeNumber();
    }
}
