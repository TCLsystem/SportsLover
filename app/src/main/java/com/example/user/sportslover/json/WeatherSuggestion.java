package com.example.user.sportslover.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 17-9-11.
 */

public class WeatherSuggestion {
    //舒适度
    @SerializedName("comf")
    public Comfort comfort;
    //洗车建议
    @SerializedName("cw")
    public CarWash carWash;
    //运动建议
    public Sport sport;

    public class Comfort{
        @SerializedName("txt")
        public String info;
    }

    public class CarWash{
        @SerializedName("txt")
        public String info;
    }

    public class Sport{
        @SerializedName("txt")
        public String info;
    }
}
