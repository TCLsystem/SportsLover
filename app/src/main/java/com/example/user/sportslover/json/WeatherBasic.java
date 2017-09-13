package com.example.user.sportslover.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 17-9-11.
 */

public class WeatherBasic {
    //城市名
    @SerializedName("city")
    public String cityname;
    //城市名对应ID
    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        //更新时间
        @SerializedName("loc")
        public String updateTime;
    }
}
