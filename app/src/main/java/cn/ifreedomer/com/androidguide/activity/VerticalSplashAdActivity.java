package cn.ifreedomer.com.androidguide.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ifreedomer.permissionhelpler.PermissionHelper;
import com.miui.zeus.mimo.sdk.ad.AdWorkerFactory;
import com.miui.zeus.mimo.sdk.ad.IAdWorker;
import com.miui.zeus.mimo.sdk.listener.MimoAdListener;
import com.xiaomi.ad.common.pojo.AdType;

import cn.ifreedomer.com.androidguide.R;


/**
 * 您可以参考本类中的代码来接入小米广告SDK中的开屏广告。在接入过程中，有如下事项需要注意：
 * 1.请将POSITION_ID值替换成您在小米开发者网站上申请的开屏广告位。
 */
public class VerticalSplashAdActivity extends Activity {
    private static final String TAG = "VerticalSplash";
    //以下的POSITION_ID 需要使用您申请的值替换下面内容
    private static final String POSITION_ID = "9d3ec9f03fa32b0bfc5d018c1c00d677";
    private ViewGroup mContainer;
    private IAdWorker mWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashad);

        new PermissionHelper(this).requestPermission(new PermissionHelper.Callback() {
            @Override
            public void onPermissionResult(boolean b) {
                showAd();
            }
        },new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE});

        mContainer = (ViewGroup) findViewById(R.id.splash_ad_container);

    }

    private void showAd() {
        try {
            mWorker = AdWorkerFactory.getAdWorker(this, mContainer, new MimoAdListener() {
                @Override
                public void onAdPresent() {
                    // 开屏广告展示
                    Log.d(TAG, "onAdPresent");
                }

                @Override
                public void onAdClick() {
                    //用户点击了开屏广告
                    Log.d(TAG, "onAdClick");
                }

                @Override
                public void onAdDismissed() {
                    //这个方法被调用时，表示从开屏广告消失。
                    Log.d(TAG, "onAdDismissed");
                //    Intent intent = new Intent(VerticalSplashAdActivity.this, MainActivity.class);
                  //  startActivity(intent);
                }

                @Override
                public void onAdFailed(String s) {
                    Log.e(TAG, "ad fail message : " + s);
                //    Intent intent = new Intent(VerticalSplashAdActivity.this, MainActivity.class);
                //    startActivity(intent);
                }

                @Override
                public void onAdLoaded(int size) {
                    //do nothing
                }

                @Override
                public void onStimulateSuccess() {
                }
            }, AdType.AD_SPLASH);

            mWorker.loadAndShow(POSITION_ID);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // 捕获back键，在展示广告期间按back键，不跳过广告
            if (mContainer.getVisibility() == View.VISIBLE) {
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            mWorker.recycle();
        } catch (Exception e) {
        }
    }
}