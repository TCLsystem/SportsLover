package com.example.user.sportslover.presenter;

import com.example.user.sportslover.model.HomeRidingModelImpl;
import com.example.user.sportslover.model.HomeRunningModelImpl;

/**
 * Created by user on 17-9-26.
 */

public class HomeRidingPresenterImpr implements HomeSportPresenter {

    private HomeRidingModelImpl homeRidingModel = new HomeRidingModelImpl();

    @Override
    public float loadTotalMileages() {
        return homeRidingModel.loadTotalMileages();
    }

    @Override
    public float loadComulativeTime() {
        return homeRidingModel.loadComulativeTime();
    }

    @Override
    public int loadAveragePace() {
        return homeRidingModel.loadAveragePace();
    }

    @Override
    public int loadComulativeNumber() {
        return homeRidingModel.loadComulativeNumber();
    }
}
