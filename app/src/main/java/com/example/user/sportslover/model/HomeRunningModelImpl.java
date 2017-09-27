package com.example.user.sportslover.model;

/**
 * Created by user on 17-9-26.
 */

public class HomeRunningModelImpl implements HomeSportModel {
    @Override
    public float loadTotalMileages() {
        return 2.02f;
    }

    @Override
    public float loadComulativeTime() {
        return 0.5f;
    }

    @Override
    public int loadAveragePace() {
        return 920;
    }

    @Override
    public int loadComulativeNumber() {
        return 1;
    }
}
