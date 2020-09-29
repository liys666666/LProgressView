package com.liys.progressview.main2;

import android.util.Log;


/**
 * @Description: 输出日志
 * @Author: liys
 * @CreateDate: 2019/5/28 10:55
 * @UpdateUser: linbin
 * @UpdateDate: 2019/9/4 11:14
 * @UpdateRemark: 正式打包不输出日志
 * @Version: 1.0
 */
public class LogUtils {

    private static final String TAG = "U点管家";
    private static boolean allowD;
    private static boolean allowE;
    private static boolean allowI;
    private static boolean allowV;
    private static boolean allowW;

    static {
//        allowD = allowE = allowI = allowV = allowW = BuildConfig.LOG_DEBUG;
        allowD = allowE = allowI = allowV = allowW = false;
        allowD = true;
    }

    private LogUtils() {
    }

    /**
     * 开启Log
     *
     * @author ZhongDaFeng
     */
    public static void openLog(boolean flag) {
        allowD = flag;
        allowE = flag;
        allowI = flag;
        allowV = flag;
        allowW = flag;
    }

    public static void d(String content) {
        if (!allowD)
            return;
        Log.d(TAG, content);
    }

    public static void e(String content) {
        if (!allowE)
            return;
        Log.e(TAG, content);
    }

    public static void i(String content) {
        if (!allowI)
            return;
        Log.i(TAG, content);
    }

    public static void v(String content) {
        if (!allowV)
            return;
        Log.v(TAG, content);
    }

    public static void w(String content) {
        if (!allowW)
            return;
        Log.w(TAG, content);
    }

    public static void d(String tag, String content) {
        if (!allowD)
            return;
        Log.d(tag, content);
    }

    public static void e(String tag, String content) {
        if (!allowE)
            return;
        Log.e(tag, content);
    }

    public static void i(String tag, String content) {
        if (!allowI)
            return;
        Log.i(tag, content);
    }

    public static void v(String tag, String content) {
        if (!allowV)
            return;
        Log.v(tag, content);
    }

    public static void w(String tag, String content) {
        if (!allowW)
            return;
        Log.w(tag, content);
    }

}
