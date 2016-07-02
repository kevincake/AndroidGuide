package cn.ifreedomer.com.androidguide.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import cn.ifreedomer.com.androidguide.GuideApplication;
import cn.ifreedomer.com.androidguide.callback.SimpleRetrofitCallBack;
import cn.ifreedomer.com.androidguide.event.UpdateEvent;
import cn.ifreedomer.com.androidguide.manager.NotifycationManager;
import cn.ifreedomer.com.androidguide.model.UpdateInfo;
import cn.ifreedomer.com.androidguide.network.HttpClient;

/**
 * @author:eavawu
 * @date: 7/2/16.
 * @todo:
 */
public class UpdateService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateService(String name) {
        super(name);
    }

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }


    public static void checkUpdate() {
        String packageName = GuideApplication.getInstance().getPackageName();
        try {
            PackageInfo packageInfo = GuideApplication.getInstance().getPackageManager().getPackageInfo(packageName, 0);
            if (packageInfo != null) {
                final int versionCode = packageInfo.versionCode;
                HttpClient.getInstance().getNewestVersion(versionCode, new SimpleRetrofitCallBack<UpdateInfo>() {
                    @Override
                    public void onSuccess(UpdateInfo update) {
                        if (versionCode < update.getVersionCode()) {
                            NotifycationManager.getInstance().post(new UpdateEvent(update));
                        }
                    }
                });
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }


}
