package com.example.user.sportslover.json;

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

    public class More{

        @SerializedName("txt")
        public String info;

    }
}
