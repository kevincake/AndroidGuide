package cn.ifreedomer.com.androidguide;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xiaomi.mipush.sdk.MiPushClient;

import net.youmi.android.AdManager;

import java.util.List;

import cn.ifreedomer.com.androidguide.constants.Constants;
import cn.ifreedomer.com.androidguide.constants.SaveConstants;
import cn.ifreedomer.com.androidguide.util.SaveUtil;

/**
 * @author:eavawu
 * @date: 6/19/16.
 * @todo:
 */
public class GuideApplication extends Application {
    private static GuideApplication mContext;

    public static GuideApplication getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        // 必须先初始化

        Fresco.initialize(this);
        if (shouldInit() && SaveUtil.get(SaveConstants.ISPUSH)) {
            initXiaoMIPush();
        }

        initWandoujiaADSDK();
    }

    private void initXiaoMIPush() {
        MiPushClient.registerPush(this, Constants.XIAOMI_PUSNID, Constants.XIAOMI_PUSHKEY);
    }


    public void initWandoujiaADSDK() {
        AdManager.getInstance(mContext).init(Constants.YOUMI_ID, Constants.YOUMI_SCECRET, true);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

};

