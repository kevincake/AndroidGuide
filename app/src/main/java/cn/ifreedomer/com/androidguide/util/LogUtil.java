package cn.ifreedomer.com.androidguide.util;

import android.util.Log;

/**
 * 创建者:eava
 * 创建时间:2016/3/1 14:05
 * 功能说明:打印log工具类
 */
public class LogUtil {
    private static boolean showLog = true;

    public static void info(String tag,String logStr){
        if (showLog){
            Log.i(tag,logStr);
        }

    }
    public static void error(String tag,String logStr){
        if (showLog){
            Log.e(tag,logStr);
        }

    }

}
