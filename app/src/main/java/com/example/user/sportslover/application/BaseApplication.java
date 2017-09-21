package com.example.user.sportslover.application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

;

/**
 * Created by user on 17-9-16.
 */

    public class BaseApplication extends Application {
        public static Context getmContext() {
            return sContext;
        }

        static Context sContext;

        @Override
        public void onCreate() {
            super.onCreate();
            sContext = getApplicationContext();
            ActiveAndroid.initialize(this);
        }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
        public void onTerminate() {
            super.onTerminate();
            ActiveAndroid.dispose();
        }
    }

