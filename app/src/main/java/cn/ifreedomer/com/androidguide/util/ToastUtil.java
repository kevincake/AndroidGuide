package cn.ifreedomer.com.androidguide.util;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.ifreedomer.com.androidguide.activity.base.ActivityStackManager;

/**
 * @author:eavawu
 * @date: 6/26/16.
 * @todo:
 */
public class ToastUtil {
    public static void showToast(String tips){
        AppCompatActivity appCompatActivity = ActivityStackManager.getScreenManager().currentActivity();
        Toast.makeText(appCompatActivity, tips, Toast.LENGTH_SHORT).show();
    }
}
