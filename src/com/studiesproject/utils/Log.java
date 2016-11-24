package com.studiesproject.utils;

import com.sun.istack.internal.NotNull;

/**
 * Created by mrlukashem on 23.11.16.
 */
public class Log {
    private static String mTAG = "LOG";

    public static void setTag(@NotNull String tag) {
        mTAG = tag;
    }

    public static void LOGE(String msg) {
        System.err.println(mTAG + " : " + msg);
    }

    public static void LOGV(String msg) {
        System.out.println(mTAG + " : " + msg);
    }
}
