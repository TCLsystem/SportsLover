package com.example.user.sportslover.presenter;

import com.example.user.sportslover.model.HomeWalkingModelImpl;

/**
 * Created by user on 17-9-26.
 */

public class HomeWalkingPresenterImpr implements HomeSportPresenter {

    private HomeWalkingModelImpl homeWalkingModel = new HomeWalkingModelImpl();
    @Override
    public float loadTotalMileages() {
        return homeWalkingModel.loadTotalMileages();
    }

    @Override
    public float loadComulativeTime() {
        return homeWalkingModel.loadComulativeTime();
    }

    @Override
    public int loadAveragePace() {
        return homeWalkingModel.loadAveragePace();
    }

    @Override
    public int loadComulativeNumber() {
        return homeWalkingModel.loadComulativeNumber();
    }
}
