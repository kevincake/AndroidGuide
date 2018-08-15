package cn.ifreedomer.com.androidguide;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.miui.zeus.mimo.sdk.MimoSdk;

import java.util.List;

/**
 * @author:eavawu
 * @date: 6/19/16.
 * @todo:
 */
public class GuideApplication extends Application {
    private static GuideApplication mContext;
    // 请注意，千万要把以下的 APP_ID 替换成您在小米开发者网站上申请的 AppID。否则，可能会影响你的应用广告收益。
    private static final String APP_ID = "2882303761517486824";
    // 以下两个没有的话就按照以下传入
    private static final String APP_KEY = "fake_app_key";
    private static final String APP_TOKEN = "fake_app_token";

    public static GuideApplication getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        // 必须先初始化

        Fresco.initialize(this);
        initXiaoMIPush();
    }

    private void initXiaoMIPush() {


        // 如果担心sdk自升级会影响开发者自身app的稳定性可以关闭，
        // 但是这也意味着您必须得重新发版才能使用最新版本的sdk, 建议开启自升级
//        MimoSdk.setEnableUpdate(false);
        /**
         * new sdk
         */
        MimoSdk.setDebugOn();
        // 正式上线时候务必关闭stage
        MimoSdk.setStageOn();

        // 如需预置插件请在assets目录下添加mimo_assets.apk
        MimoSdk.init(this, APP_ID, APP_KEY, APP_TOKEN);
//        MiPushClient.registerPush(this, Constants.XIAOMI_PUSNID, Constants.XIAOMI_PUSHKEY);
    }


    public void initWandoujiaADSDK() {
//        AdManager.getInstance(mContext).init(Constants.YOUMI_ID, Constants.YOUMI_SCECRET, true);
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

