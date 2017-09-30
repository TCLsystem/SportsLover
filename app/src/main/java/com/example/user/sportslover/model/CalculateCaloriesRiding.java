package com.example.user.sportslover.model;

/**
 * Created by user on 17-9-30.
 */

public class CalculateCaloriesRiding implements CalculateCalories {
    @Override
    public float calculateCalories(float weight, long time, int averagePace) {
        return weight*time*1.05f/averagePace;
    }
}
