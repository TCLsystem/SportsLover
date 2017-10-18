package com.example.user.sportslover.model;

/**
 * Created by user on 17-9-30.
 */

public class CalculateCaloriesRidingImpl implements CalculateCaloriesInter {
    @Override
    public float calculateCalories(float weight, long time, int averagePace) {
        return weight*time*0.5f/averagePace;
    }

    @Override
    public float calculateCalories(float weight, float distance) {
        return weight*distance*0.5f;
    }
}
