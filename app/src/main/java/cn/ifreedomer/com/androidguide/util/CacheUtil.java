package cn.ifreedomer.com.androidguide.util;

import android.os.Environment;

import java.io.File;

import cn.ifreedomer.com.androidguide.GuideApplication;
import cn.ifreedomer.com.androidguide.R;

/**
 * @author:eavawu
 * @date: 6/22/16.
 * @todo:
 */
public class CacheUtil {
    public static String getAppCacheDir() {
        String cacheDir = GuideApplication.getInstance().getCacheDir().getAbsolutePath() + "/";
        return cacheDir;
    }

    public static String getSdCacheDir() {
        String sdCacheDir = Environment.getExternalStorageDirectory().getPath();
        ;
        String appSdCacheDir = sdCacheDir +File.separator+ GuideApplication.getInstance().getResources().getString(R.string.app_name);
        File file = new File(appSdCacheDir);
        if (!file.exists()) {
            file.mkdir();
        }
        return appSdCacheDir;

    }
}
