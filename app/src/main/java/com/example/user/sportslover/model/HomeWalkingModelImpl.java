package com.example.user.sportslover.model;

/**
 * Created by user on 17-9-26.
 */

public class HomeWalkingModelImpl implements HomeSportModel {
    @Override
    public float loadTotalMileages() {
        return 6.06f;
    }

    @Override
    public float loadComulativeTime() {
        return 4.0f;
    }

    @Override
    public int loadAveragePace() {
        return 920;
    }

    @Override
    public int loadComulativeNumber() {
        return 3;
    }
}
