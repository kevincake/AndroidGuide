package cn.ifreedomer.com.androidguide.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import cn.ifreedomer.com.androidguide.GuideApplication;

/**
 * @author:eavawu
 * @date: 7/3/16.
 * @todo:
 */
public class AppInfoUtil {
    public static PackageInfo getPackageInfo() {
        String packageName = GuideApplication.getInstance().getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = GuideApplication.getInstance().getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    public static int getVersionCode() {
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo == null) {
            return 1;
        }
        return packageInfo.versionCode;
    }

}
