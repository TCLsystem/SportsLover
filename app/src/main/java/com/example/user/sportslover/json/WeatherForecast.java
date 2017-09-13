package com.example.user.sportslover.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 17-9-11.
 */

public class WeatherForecast {
    //预报日期
    public String date;
    //预报高低温
    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature{
        public String max;
        public String min;
    }
    //预报天气
    public class More{
        @SerializedName("txt_d")
        public String info;
    }
}
