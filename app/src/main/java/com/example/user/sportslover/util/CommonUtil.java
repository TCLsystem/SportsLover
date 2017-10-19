package com.example.user.sportslover.util;

import android.os.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通用工具类
 */
public class CommonUtil {

    /**
     * 是否有SDCard
     *
     * @return 是否有SDCard
     */
    public static boolean hasSDCard() {

        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取应用运行的最大内存
     *
     * @return 最大内存
     */
    public static long getMaxMemory() {

        return Runtime.getRuntime().maxMemory() / 1024;
    }


    public static List<String> string2list(String string) {
        if (string != null && string.contains(",")) {
            LogUtil.e("这里内容需要转义");
            return new ArrayList<>(Arrays.asList(string.split(",")));
        } else if (string != null) {
            LogUtil.e("只有一个字符串，没有分隔符");
            List<String> list = new ArrayList<>();
            list.add(string);
            return list;
        } else {
            LogUtil.e("string中为空");
            return null;
        }
    }

    public static String list2string(List<String> likeUsers) {
        if (likeUsers == null || likeUsers.size() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < likeUsers.size(); i++) {
            if (i == 0) {
                stringBuilder.append(likeUsers.get(i));
            } else {
                stringBuilder.append("," + likeUsers.get(i));
            }
        }
        return stringBuilder.toString();
    }
}
