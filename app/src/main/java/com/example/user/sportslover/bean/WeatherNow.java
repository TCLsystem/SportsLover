package com.example.user.sportslover.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 17-9-11.
 */

public class WeatherNow {
    //现在气温
    @SerializedName("tmp")
    public String temperature;
    //现在天气情况
    @SerializedName("cond")
    public More more;

    @SerializedName("hum")
    public int humility;

    public class More{

        @SerializedName("txt")
        public String info;

    }

    public Wind wind;

    public class Wind{
        @SerializedName("dir")
        public String direction;
    }
}
