package com.studiesproject.utils;

/**
 * Created by mrlukashem on 23.11.16.
 */
public class Log {
    public static void e(String tag, String msg) {
        System.err.println(tag + " : " + msg);
    }

    public static void v(String tag, String msg) {
        System.out.println(tag + " : " + msg);
    }
}
