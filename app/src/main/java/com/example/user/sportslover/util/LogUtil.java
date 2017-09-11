package com.example.user.sportslover.util;

import android.util.Log;

/**
 * Log管理类
 */

public class LogUtil {

    private LogUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;

    private static final String TAG = "TAG";

    public static void i(String msg) {
        if (isDebug) Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug) Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug) Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug) Log.v(TAG, msg);
    }

    // 自定义tag函数
    public static void i(String tag, String msg) {
        if (isDebug) Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug) Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug) Log.v(tag, msg);
    }
}