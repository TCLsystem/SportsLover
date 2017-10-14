package com.example.user.sportslover.application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.user.sportslover.base.UniversalImageLoader;
import com.example.user.sportslover.bean.Weather;
import com.example.user.sportslover.service.MessageHandler;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;


/**
 * Created by user on 17-9-16.
 */

public class BaseApplication extends com.activeandroid.app.Application {

    static Context sContext;
    private Weather globalWeather = null;
    private float globalSportVibrationSetting = 0;
    private int globalSportMapSetting = 0;

    public static Context getmContext() {
        return sContext;
    }

    private static BaseApplication INSTANCE;

    public static BaseApplication INSTANCE() {
        return INSTANCE;
    }

    private void setInstance(BaseApplication app) {
        setBaseApplication(app);
    }

    private static void setBaseApplication(BaseApplication a) {
        BaseApplication.INSTANCE = a;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        sContext = getApplicationContext();
        //  ActiveAndroid.initialize(this);

        setInstance(this);
        //初始化
        Logger.init("SportsLover");
        //只有主进程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
            //im初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new MessageHandler(this));
        }
        //uil初始化
        UniversalImageLoader.initImageLoader(this);
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

    public void setGlobalWeather(Weather weather) {
        this.globalWeather = weather;
    }

    public Weather getGlobalWeather() {
        return globalWeather;
    }

    public void setGlobalSportVibrationSetting(float globalSportVibrationSetting) {
        this.globalSportVibrationSetting = globalSportVibrationSetting;
    }

    public void setGlobalSportMapSetting(int globalSportMapSetting) {
        this.globalSportMapSetting = globalSportMapSetting;
    }

    public int getGlobalSportMapSetting() {
        return globalSportMapSetting;
    }

    public float getGlobalSportVibrationSetting() {
        return globalSportVibrationSetting;
    }

    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

