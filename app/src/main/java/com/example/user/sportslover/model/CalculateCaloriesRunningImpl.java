package com.example.user.sportslover.model;

/**
 * Created by user on 17-9-30.
 */

public class CalculateCaloriesRunningImpl implements CalculateCaloriesInter {
    @Override
    public float calculateCalories(float weight, long time, int averagePace) {
        return weight*time*720f/averagePace;
    }
}
