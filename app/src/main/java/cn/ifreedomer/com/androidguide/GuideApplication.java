package cn.ifreedomer.com.androidguide;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

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
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
