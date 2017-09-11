package com.example.user.sportslover.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Toast管理类
 */
public class ToastUtil {

    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static Context mContext;

    public static void register(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 检查上下文环境
     *
     * @return
     */
    private static Boolean check() {
        if (mContext == null) {
            throw new NullPointerException("Must initial call ToastUtil.register(Context " +
                    "context) in your " + "<? " + "extends Application class>");
        }
        return true;
    }

    /**
     * 短时间显示Toast
     *
     * @param context 上下文
     * @param message 消息
     */
    public static void showShort(Context context, CharSequence message) {
        if (check()) Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context 上下文
     * @param message 消息
     */
    public static void showShort(Context context, int message) {
        if (check()) Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context 上下文
     * @param message 消息
     */
    public static void showLong(Context context, CharSequence message) {
        if (check()) Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context 上下文
     * @param message 消息
     */
    public static void showLong(Context context, int message) {
        if (check()) Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间（毫秒)
     *
     * @param context  上下文
     * @param message  消息
     * @param duration 持续时间
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (check()) Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间（毫秒)
     *
     * @param context  上下文
     * @param message  消息
     * @param duration 持续时间
     */
    public static void show(Context context, int message, int duration) {
        if (check()) Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间（毫秒)
     *
     * @param context  上下文
     * @param view     自定义视图
     * @param gravity  弹窗重力
     * @param xOffset  x偏移
     * @param yOffset  y偏移
     * @param duration 持续时间
     */
    public static void show(Context context, View view, int gravity, int xOffset, int yOffset,
                            int duration) {
        if (check()) {
            Toast toast = new Toast(context);
            toast.setGravity(gravity, xOffset, yOffset);
            toast.setDuration(duration);
            toast.setView(view);
            toast.show();
        }
    }
}