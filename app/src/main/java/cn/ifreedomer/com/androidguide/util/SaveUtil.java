package cn.ifreedomer.com.androidguide.util;

import android.content.Context;
import android.content.SharedPreferences;

import cn.ifreedomer.com.androidguide.GuideApplication;

/**
 * @author:eavawu
 * @date: 7/3/16.
 * @todo:
 */
public class SaveUtil {

    public static void save(String key, int value) {
        SharedPreferences sharedPreferences = GuideApplication.getInstance().getSharedPreferences("app_prefile", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static void save(String key, boolean value) {
        SharedPreferences sharedPreferences = GuideApplication.getInstance().getSharedPreferences("app_prefile", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static boolean get(String key) {
        SharedPreferences sharedPreferences = GuideApplication.getInstance().getSharedPreferences("app_prefile", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }

}
