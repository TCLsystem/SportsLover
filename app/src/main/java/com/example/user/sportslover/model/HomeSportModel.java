package com.example.user.sportslover.model;

import android.app.Application;

import com.example.user.sportslover.activity.MainActivity;

/**
 * Created by user on 17-9-26.
 */

public interface HomeSportModel {
    float loadTotalMileages();
    float loadComulativeTime();
    int loadAveragePace();
    int loadComulativeNumber();
}
