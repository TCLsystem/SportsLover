package com.example.user.sportslover.application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.user.sportslover.bean.Weather;


/**
 * Created by user on 17-9-16.
 */

public class BaseApplication extends com.activeandroid.app.Application {

    static Context sContext;
    private Weather globalWeather = null;
    public static Context getmContext() {
            return sContext;
        }


    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
      //  ActiveAndroid.initialize(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
        public void onTerminate() {
            super.onTerminate();
       //     ActiveAndroid.dispose();
        }

    public void setGlobalWeather(Weather weather){
        this.globalWeather = weather;
    }

    public Weather getGlobalWeather(){
        return globalWeather;
    }
}

