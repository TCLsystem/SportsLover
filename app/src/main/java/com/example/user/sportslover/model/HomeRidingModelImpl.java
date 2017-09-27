package com.example.user.sportslover.model;

/**
 * Created by user on 17-9-26.
 */

public class HomeRidingModelImpl implements HomeSportModel {
    @Override
    public float loadTotalMileages() {
        return 12.06f;
    }

    @Override
    public float loadComulativeTime() {
        return 2.0f;
    }

    @Override
    public int loadAveragePace() {
        return 620;
    }

    @Override
    public int loadComulativeNumber() {
        return 3;
    }
}
