package com.example.user.sportslover.model;

/**
 * Created by user on 17-9-30.
 */

public class CalculateCaloriesWalkingImpl implements CalculateCaloriesInter {
    @Override
    public float calculateCalories(float weight, long time, int averagePace) {
        return weight*time*1.03f/averagePace;
    }

    @Override
    public float calculateCalories(float weight, float distance) {
        return weight*distance*1.03f;
    }
}
