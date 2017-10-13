package com.example.user.sportslover.bean;

/**
 * Created by user on 17-10-10.
 */

public class SportHistoryDataBean {

    private String month;
    private String date;
    private float distance;
    private float calories;
    private float cumulativeTime;

    public SportHistoryDataBean(String month, String date, float distance, float calories, float cumulativeTime) {
        this.month = month;
        this.date = date;
        this.distance = distance;
        this.calories = calories;
        this.cumulativeTime = cumulativeTime;
    }

    public float getCalories() {
        return calories;
    }

    public float getCumulativeTime() {
        return cumulativeTime;
    }

    public String getDate() {
        return date;
    }

    public float getDistance() {
        return distance;
    }

    public String getMonth() {
        return month;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public void setCumulativeTime(float cumulativeTime) {
        this.cumulativeTime = cumulativeTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
