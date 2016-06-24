package cn.ifreedomer.com.androidguide.util;

import cn.ifreedomer.com.androidguide.GuideApplication;

/**
 * @author:eavawu
 * @date: 6/22/16.
 * @todo:
 */
public class CacheUtil {
    public static String getAppCacheDir() {
        String cacheDir = GuideApplication.getInstance().getCacheDir().getAbsolutePath()+"/";
        return cacheDir;
    }
}
